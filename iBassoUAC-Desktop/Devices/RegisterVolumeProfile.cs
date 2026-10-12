using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>
/// Shared plumbing for the register-opcode family (DC03 Pro / DC04 Pro / Macaron):
/// 4-byte-offset register writes on 0x60/0x62 plus 2-byte-offset "ROOM" commits on 0xA2,
/// all carried inside a 16-byte HID feature report.
/// </summary>
public abstract class RegisterVolumeProfile : DeviceProfile
{
    protected RegisterVolumeProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }

    public override int MaxVolume => 100;
    public override bool SupportsDigitalFilter => true;
    public override bool SupportsGain => GainModes.Length > 0;
    public override bool SupportsOutput => HasOutputModes;
    public override bool SupportsSpdif => HasSpdif;

    protected virtual bool HasSpdif => false;
    protected virtual bool HasOutputModes => false;

    /// <summary>Hardware byte written for UI gain index n; empty = no gain control.</summary>
    protected virtual int[] GainModes => Array.Empty<int>();

    /// <summary>Hardware byte written for UI output index n.</summary>
    protected virtual byte OutputByte(int index) => (byte)(index == 1 ? 0x1E : 0x1C);

    public override byte VolumeToRaw(int index) => VolumeTables.Step2[Math.Clamp(index, 0, MaxVolume)];

    public override int RawToVolume(int raw) => VolumeTables.NearestIndex(VolumeTables.Step2, raw, MaxVolume);

    // -------------------------------------------------------------- register I/O

    private Task Write4Async(byte seq, byte address, byte o1, byte o2, byte o3, byte o4, byte value, CancellationToken ct)
    {
        // Fire-and-forget on purpose. This family never acknowledges a register write (see the
        // hardware report), so RequestAsync would push the packet, wait 600 ms, throw, and
        // abort the remaining nine steps — the device would end up half-configured.
        Link.Send(UacLink.BuildWrite(seq, RegOp.Write4ByteOffset, address, new[] { o1, o2, o3, o4 }, value));
        return Task.CompletedTask;
    }

    private Task Write2Async(byte seq, byte address, byte hi, byte lo, byte value, CancellationToken ct)
    {
        Link.Send(UacLink.BuildWrite(seq, RegOp.Write2ByteOffset, address, new[] { hi, lo }, value));
        return Task.CompletedTask;
    }

    private async Task<byte> Read2Async(byte seq, byte address, byte hi, byte lo, CancellationToken ct)
    {
        var pkt = UacLink.BuildRead(seq, RegOp.Read2ByteOffset, address, new[] { hi, lo });
        var reply = await Link.RequestAsync(pkt, seq, ct).ConfigureAwait(false);
        return reply.Payload.Length > 0 ? reply.Payload[0] : (byte)0;
    }

    private async Task<byte> Read4Async(byte seq, byte address, byte o1, byte o2, byte o3, byte o4, CancellationToken ct)
    {
        var pkt = UacLink.BuildRead(seq, RegOp.Read4ByteOffset, address, new[] { o1, o2, o3, o4 });
        var reply = await Link.RequestAsync(pkt, seq, ct).ConfigureAwait(false);
        return reply.Payload.Length > 0 ? reply.Payload[0] : (byte)0;
    }

    /// <summary>
    /// Pushes both channels: per-channel volume registers on 0x60/0x62, DSD shadow
    /// registers, and the final "room" commit words on 0xA2.
    /// </summary>
    protected async Task PushVolumeAsync(
        byte lSeq1, byte lSeq2, byte lSeq3, byte lSeq4,
        byte rSeq1, byte rSeq2, byte rSeq3, byte rSeq4,
        byte left, byte right, CancellationToken ct)
    {
        await Write4Async(lSeq1, 0x60, 9, 0, 1, 0, left, ct);
        await Write4Async(lSeq2, 0x60, 9, 0, 2, 0, left, ct);
        await Write4Async(rSeq1, 0x62, 9, 0, 1, 0, right, ct);
        await Write4Async(rSeq2, 0x62, 9, 0, 2, 0, right, ct);
        await Write4Async(lSeq3, 0x60, 7, 0, 0, 0, left, ct);
        await Write4Async(lSeq4, 0x60, 7, 0, 1, 0, left, ct);
        // The "room" commit carries the attenuation too, not the volume index:
        // setDc03ProVolume() forwards its attenuation arguments to these two tasks.
        await Write2Async(19, 0xA2, 0x00, 0x10, left, ct);
        await Write4Async(rSeq3, 0x62, 7, 0, 0, 0, right, ct);
        await Write4Async(rSeq4, 0x62, 7, 0, 1, 0, right, ct);
        await Write2Async(20, 0xA2, 0x00, 0x11, right, ct);
    }

    public override async Task SetVolumeAsync(int index, CancellationToken ct = default)
    {
        index = Math.Clamp(index, 0, MaxVolume);
        var v = VolumeToRaw(index);
        var balance = Balance;

        // No readback on Windows, so the local shadow has to be updated by hand or the UI
        // would keep showing whatever the user last asked for — even if the write failed.
        await WriteVolumeAsync(index, v, balance, ct);

        VolumeIndex = index;
    }

    private async Task WriteVolumeAsync(int index, byte leftRaw, int balance, CancellationToken ct)
    {
        var v = leftRaw;

        if (balance == 0)
        {
            await PushVolumeAsync(1, 2, 9, 10, 3, 4, 11, 12, v, v, ct);
        }
        else if (balance > 0)
        {
            var r = Mute(v, balance);
            await PushVolumeAsync(1, 2, 9, 10, 7, 8, 15, 16, v, r, ct);
        }
        else
        {
            var l = Mute(v, -balance);
            await PushVolumeAsync(5, 6, 13, 14, 3, 4, 11, 12, l, v, ct);
        }
    }

    private static byte Mute(byte attenuation, int delta)
    {
        if (attenuation == 255) return 255;
        return (byte)Math.Max(0, attenuation - delta);
    }

    public override async Task SetBalanceAsync(int value, CancellationToken ct = default)
    {
        Balance = value;
        var index = VolumeIndex;
        var v = VolumeToRaw(index);
        var (left, right) = value >= 0 ? (v, Mute(v, value)) : (Mute(v, -value), v);
        await PushVolumeAsync(5, 6, 13, 14, 7, 8, 15, 16, left, right, ct);
    }

    public override async Task SetDigitalFilterAsync(int value, CancellationToken ct = default)
    {
        await Write4Async(Cmd.SetDigitalFilter1, 0x60, 9, 0, 0, 0, (byte)value, ct);
        await Write4Async(Cmd.SetDigitalFilter2, 0x62, 9, 0, 0, 0, (byte)value, ct);
        DigitalFilterIndex = value;
    }

    public override async Task SetGainAsync(int value, CancellationToken ct = default)
    {
        if (GainModes.Length == 0) return;
        var index = Math.Clamp(value, 0, GainModes.Length - 1);
        var hardware = (byte)GainModes[index];
        await Write4Async(Cmd.SetGain1, 0x60, 8, 0, 0, 0, hardware, ct);
        await Write4Async(Cmd.SetGain2, 0x62, 8, 0, 0, 0, hardware, ct);
    }

    public override async Task SetOutputAsync(int value, CancellationToken ct = default)
    {
        if (!HasOutputModes) return;
        var hardware = OutputByte(value);
        await Write4Async(Cmd.SetOutput1, 0x60, 11, 0, 0, 0, hardware, ct);
        await Write4Async(Cmd.SetOutput2, 0x62, 11, 0, 0, 0, hardware, ct);
    }

    public override Task RefreshAsync(DeviceState state, CancellationToken ct = default)
    {
        // This firmware family exposes no interrupt IN endpoint: ReadFile never completes even
        // when the hardware volume buttons are pressed, and SET_REPORT carries no IN stage, so
        // there is no way to read state back through the Windows HID class driver. Writes are
        // confirmed working on hardware, so fall back to the locally tracked state instead of
        // hanging or throwing on every refresh.
        state.ReadbackSupported = false;
        state.Volume = VolumeIndex;
        state.Balance = Balance;
        state.DigitalFilter = DigitalFilterIndex;
        return Task.CompletedTask;
    }

    // Cached locally: the app keeps these in SharedPreferences, we keep them in memory.
    protected int Balance { get; private set; }
    protected int VolumeIndex { get; set; } = 60;
    protected int DigitalFilterIndex { get; set; }

    public override void ApplyResponse(UacResponse response)
    {
        if (response.IsNativeVolumeChange && response.Payload.Length >= 2)
        {
            VolumeIndex = RawToVolume(response.Payload[0]);
            Balance = RawToVolume(response.Payload[0]) - RawToVolume(response.Payload[1]);
        }
    }
}

/// <summary>iBasso DC03 Pro — register family, gain + output only.</summary>
public sealed class Dc03ProProfile : RegisterVolumeProfile
{
    public Dc03ProProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC03 Pro";
    protected override int[] GainModes => new[] { 0, 32, 49 };
    protected override bool HasOutputModes => true;
}

/// <summary>iBasso DC04 Pro — DC03 Pro plus SPDIF/codec switch and FPGA version read.</summary>
public sealed class Dc04ProProfile : RegisterVolumeProfile
{
    public Dc04ProProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC04 Pro";
    protected override int[] GainModes => new[] { 0, 32, 49 };
    protected override bool HasOutputModes => true;
    protected override bool HasSpdif => true;

    public override async Task SetSpdifAsync(int value, CancellationToken ct = default)
    {
        // The original writes a 16-byte frame *and* a 2-byte-offset register commit.
        var on = value == 1;
        var frame = new byte[16];
        frame[0] = Cmd.SetCodecExternal;          // 25
        frame[1] = Cmd.SetDigitalFilter1;        // 0x11
        frame[2] = RegOp.Write1ByteOffset;       // 0x98
        frame[3] = 44;                           // 0x2C
        frame[4] = 2;
        frame[6] = 1;
        frame[7] = on ? (byte)32 : (byte)0;

        Link.Send(frame);
        var pkt = UacLink.BuildWrite(Cmd.SetCodecExternalRoom, RegOp.Write2ByteOffset, 0xA2, new byte[] { 0x00, 0x12 }, (byte)(on ? 32 : 0));
        await Link.RequestAsync(pkt, Cmd.SetCodecExternalRoom, ct).ConfigureAwait(false);
    }
}

/// <summary>iBasso Macaron (DC08) — register family without gain readout.</summary>
public sealed class Dc08Profile : RegisterVolumeProfile
{
    public Dc08Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso Macaron";
    protected override int[] GainModes => new[] { 0, 32, 49 };
    protected override bool HasOutputModes => true;
}

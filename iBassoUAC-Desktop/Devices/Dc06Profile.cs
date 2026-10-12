using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>
/// iBasso DC06 / DC06 Pro — 1-byte-offset register opcodes on I2C devices 0x90 / 0x92.
/// DC06 Pro additionally commits to 0xA2 and can switch the external codec.
/// </summary>
public class Dc06Profile : DeviceProfile
{
    private readonly bool _pro;

    public Dc06Profile(UacLink link, UsbDeviceInfo info, bool pro = false) : base(link, info)
    {
        _pro = pro;
    }

    public override string DisplayName => _pro ? "iBasso DC06 Pro" : "iBasso DC06";
    public override int MaxVolume => 100;
    public override bool SupportsDigitalFilter => true;
    public override bool SupportsSpdif => _pro;

    private int Balance { get; set; }
    private int VolumeIndex { get; set; } = 60;
    private int FilterValue { get; set; }

    public override byte VolumeToRaw(int index) => VolumeTables.Step2[Math.Clamp(index, 0, MaxVolume)];
    public override int RawToVolume(int raw) => VolumeTables.NearestIndex(VolumeTables.Step2, raw, MaxVolume);

    private Task Write1Async(byte seq, byte address, byte offset, byte value, CancellationToken ct)
    {
        // Same transport generation as the DC03 Pro: register writes are not acknowledged, so
        // waiting for a reply would abort the remaining steps of a multi-register sequence.
        Link.Send(UacLink.BuildWrite(seq, RegOp.Write1ByteOffset, address, new[] { offset }, value));
        return Task.CompletedTask;
    }

    private async Task<byte> Read1Async(byte seq, byte address, byte offset, CancellationToken ct)
    {
        var pkt = UacLink.BuildRead(seq, RegOp.Read1ByteOffset, address, new[] { offset });
        var reply = await Link.RequestAsync(pkt, seq, ct).ConfigureAwait(false);
        return reply.Payload.Length > 0 ? reply.Payload[0] : (byte)0;
    }

    private async Task SetChannelAsync(byte s1, byte s2, byte address, byte value, CancellationToken ct)
    {
        await Write1Async(s1, address, 0x10, value, ct);
        await Write1Async(s2, address, 0x0F, value, ct);
    }

    private Task SetLeftAsync(byte s1, byte s2, byte value, CancellationToken ct) => SetChannelAsync(s1, s2, 0x90, value, ct);
    private Task SetRightAsync(byte s1, byte s2, byte value, CancellationToken ct) => SetChannelAsync(s1, s2, 0x92, value, ct);

    private async Task CommitAsync(byte lSeq, byte rSeq, byte left, byte right, CancellationToken ct)
    {
        if (!_pro) return;
        await Write1Async(lSeq, 0xA2, 0x10, left, ct).ConfigureAwait(false);
        await Write1Async(rSeq, 0xA2, 0x11, right, ct).ConfigureAwait(false);
    }

    private static byte Mute(byte attenuation, int delta)
    {
        if (attenuation == 255) return 255;
        return (byte)Math.Max(0, attenuation - delta);
    }

    public override async Task SetVolumeAsync(int index, CancellationToken ct = default)
    {
        index = Math.Clamp(index, 0, MaxVolume);
        VolumeIndex = index;
        var v = VolumeToRaw(index);

        if (Balance == 0)
        {
            await SetLeftAsync(Cmd.SetVolumeLeft1, Cmd.SetVolumeLeft2, v, ct);
            await SetRightAsync(Cmd.SetVolumeRight1, Cmd.SetVolumeRight2, v, ct);
            await CommitAsync(19, 20, v, v, ct);
        }
        else if (Balance > 0)
        {
            await SetRightAsync(Cmd.SetBalanceRight1, Cmd.SetBalanceRight2, Mute(v, Balance), ct);
            await SetLeftAsync(Cmd.SetVolumeLeft1, Cmd.SetVolumeLeft2, v, ct);
            await CommitAsync(19, 20, v, Mute(v, Balance), ct);
        }
        else
        {
            await SetLeftAsync(Cmd.SetBalanceLeft1, Cmd.SetBalanceLeft2, Mute(v, -Balance), ct);
            await SetRightAsync(Cmd.SetVolumeRight1, Cmd.SetVolumeRight2, v, ct);
            await CommitAsync(19, 20, Mute(v, -Balance), v, ct);
        }
    }

    public override async Task SetBalanceAsync(int value, CancellationToken ct = default)
    {
        Balance = value;
        var v = VolumeToRaw(VolumeIndex);
        var (left, right) = value >= 0 ? (v, Mute(v, value)) : (Mute(v, -value), v);
        await SetLeftAsync(Cmd.SetBalanceLeft1, Cmd.SetBalanceLeft2, left, ct);
        await SetRightAsync(Cmd.SetBalanceRight1, Cmd.SetBalanceRight2, right, ct);
        await CommitAsync(19, 20, left, right, ct);
    }

    public override async Task SetDigitalFilterAsync(int value, CancellationToken ct = default)
    {
        FilterValue = value;
        var packed = (byte)((value & 0x1F) | FilterValue);
        await Write1Async(Cmd.SetDigitalFilter1, 0x90, 0x07, packed, ct);
        await Write1Async(Cmd.SetDigitalFilter2, 0x92, 0x07, packed, ct);
    }

    public override async Task SetSpdifAsync(int value, CancellationToken ct = default)
    {
        if (!_pro) return;

        // DC06 Pro inverts the polarity relative to DC04 Pro.
        var on = value == 0;
        var frame = new byte[16];
        frame[0] = Cmd.SetCodecExternal;
        frame[1] = Cmd.SetDigitalFilter1;
        frame[2] = RegOp.Write1ByteOffset;
        frame[3] = 44;
        frame[4] = 2;
        frame[6] = 1;
        frame[7] = on ? (byte)32 : (byte)0;
        Link.Send(frame);

        var pkt = UacLink.BuildWrite(Cmd.SetCodecExternalRoom, RegOp.Write2ByteOffset, 0xA2, new byte[] { 0x00, 0x12 }, (byte)(on ? 32 : 0));
        await Link.RequestAsync(pkt, Cmd.SetCodecExternalRoom, ct).ConfigureAwait(false);
    }

    public override Task RefreshAsync(DeviceState state, CancellationToken ct = default)
    {
        // No interrupt IN endpoint on this firmware generation (verified on the DC03 Pro), so
        // register reads never come back over the Windows HID class driver. Report the locally
        // tracked values and tell the UI they are not a device readout.
        state.ReadbackSupported = false;
        state.Volume = VolumeIndex;
        state.Balance = Balance;
        state.DigitalFilter = FilterValue;
        return Task.CompletedTask;
    }

    public override void ApplyResponse(UacResponse response)
    {
        if (!response.IsNativeVolumeChange || response.Payload.Length < 2) return;
        var l = RawToVolume(response.Payload[0]);
        var r = RawToVolume(response.Payload[1]);
        VolumeIndex = Math.Min(l, r);
        Balance = l - r;
    }
}

public sealed class Dc06ProProfile : Dc06Profile
{
    public Dc06ProProfile(UacLink link, UsbDeviceInfo info) : base(link, info, pro: true) { }
}

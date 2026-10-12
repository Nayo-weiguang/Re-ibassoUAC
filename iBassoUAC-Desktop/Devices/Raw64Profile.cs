using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>
/// Family that speaks the 64-byte state frame {0x4B, dir, 0x3F, 0x08, cmd, group, v0..v4}
/// over the HID OUT endpoint: Macchiato, DC04U and D18.
/// State changes are read-modify-write on a whole group, exactly as in the app.
/// </summary>
public class Raw64Profile : DeviceProfile
{
    protected const byte DirWrite = 0x01;
    protected virtual byte DirRead => 0x80;   // D18 uses 0x00

    protected readonly Dictionary<byte, byte[]> _groups = new()
    {
        [0x10] = new byte[5],
        [0x20] = new byte[5],
        [0x30] = new byte[5],
        [0x40] = new byte[5],
        [0x50] = new byte[5],
    };

    public Raw64Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }

    public override string DisplayName => "iBasso (64B family)";
    public override int MaxVolume => 100;
    public override bool SupportsDigitalFilter => true;
    public override bool SupportsSpdif => true;
    public override bool SupportsAmpMode => true;
    public override bool SupportsDeviceSettings => true;
    public override bool SupportsParametricEq => SupportsEqOverWire;

    protected virtual bool SupportsEqOverWire => false;

    private byte[] Slot(byte g) => _groups.TryGetValue(g, out var v) ? v : new byte[5];

    protected int GetSlot(byte group, int slot)
    {
        var buffer = Slot(group);
        return slot >= 0 && slot < buffer.Length ? buffer[slot] : -1;
    }

    protected void SetSlot(byte group, int slot, int value)
    {
        var buffer = Slot(group);
        if (slot >= 0 && slot < buffer.Length) buffer[slot] = (byte)Math.Clamp(value, 0, 255);
    }

    // group slot assignments (override per model where different)
    protected virtual int VolumeSlot => 0;
    protected virtual int DigitalFilterSlot => 0;
    protected virtual int EqualizerSlot => 1;
    protected virtual int DreSlot => 2;
    protected virtual int SpdifSlot => 0;
    protected virtual int BalanceSlot => 1;
    protected virtual int GainSlot => 2;
    protected virtual int UacVersionSlot => 0;
    protected virtual int AmpModeSlot => 1;
    protected virtual int MicModeSlot => 2;
    protected virtual int ThemeSlot => 3;

    private Task SendAsync(byte[] frame, CancellationToken ct)
        => Link.RequestAsync(frame, frame[5], ct);

    // ----------------------------------------------------------------- setters

    public override async Task SetVolumeAsync(int index, CancellationToken ct = default)
    {
        var v = (byte)Math.Clamp(index, 0, MaxVolume);
        SetSlot(0x10, VolumeSlot, v);
        // The firmware expects the level twice in the volume group payload.
        await SendAsync(Raw64(Cmd.SetVolumeLeft1, 0x10, true, v, v, 0, 0, 0), ct).ConfigureAwait(false);
    }

    public override async Task SetBalanceAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x30, BalanceSlot, value);
        await SendAsync(Raw64(Cmd.SetCodecExternal, 0x30, true, Slot(0x30)), ct).ConfigureAwait(false);
    }

    public override async Task SetSpdifAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x30, SpdifSlot, value);
        await SendAsync(Raw64(Cmd.SetCodecExternal, 0x30, true, Slot(0x30)), ct).ConfigureAwait(false);
    }

    public override async Task SetGainAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x30, GainSlot, value);
        await SendAsync(Raw64(Cmd.SetCodecExternal, 0x30, true, Slot(0x30)), ct).ConfigureAwait(false);
    }

    public override async Task SetDigitalFilterAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x20, DigitalFilterSlot, value);
        await SendAsync(Raw64(Cmd.SetDigitalFilter1, 0x20, true, Slot(0x20)), ct).ConfigureAwait(false);
    }

    public override Task SetAmpModeAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x50, AmpModeSlot, value);
        return SendAsync(Raw64(Cmd.SetUac, 0x50, true, Slot(0x50)), ct);
    }

    // ------------------------------------------------------------------ refresh

    public override async Task RefreshAsync(DeviceState state, CancellationToken ct = default)
    {
        await Probe(Cmd.GetFpgaVersion, 0xF0, ct);
        await Probe(Cmd.GetVolumeLeft2, 0x10, ct);
        await Probe(Cmd.GetFilter, 0x20, ct);
        await Probe(Cmd.GetSpdif, 0x30, ct);
        await Probe(Cmd.GetVolumeKeyControl, 0x40, ct);
        await Probe(Cmd.GetUac, 0x50, ct);
        if (SupportsEqOverWire) await ReadEqAsync(ct).ConfigureAwait(false);
    }

    private async Task Probe(byte command, byte group, CancellationToken ct)
    {
        try
        {
            var buffer = new byte[64];
            buffer[0] = Cmd.GetDc03ProDigitalFilter1;
            buffer[1] = DirRead;
            buffer[2] = 0x3F;
            buffer[3] = 0x08;
            buffer[4] = command;
            buffer[5] = group;
            await Link.RequestAsync(buffer, group, ct).ConfigureAwait(false);
        }
        catch (TimeoutException)
        {
            // Silent probe — the device may not answer every group.
        }
    }

    public override void ApplyResponse(UacResponse response)
    {
        var raw = response.Raw;
        if (raw.Length != 64) return;

        if (raw[0] == Cmd.GetDc03ProDigitalFilter1 && raw[2] == 9 && raw[3] == 31)
        {
            // parametric-EQ band payload
            return;
        }

        var group = raw[5];
        var buffer = Slot(group);
        for (var i = 0; i < buffer.Length; i++) buffer[i] = raw[6 + i];
    }

    // ---------------------------------------------------------------------- EQ

    /// <summary>
    /// Opaque FPGA/command blob the firmware expects in bytes 9..27 of every
    /// parametric-EQ frame (MacchiatoDeviceController :654). No derivation
    /// exists in the app — it is a fixed constant.
    /// </summary>
    private static ReadOnlySpan<byte> EqTail =>
    [
        0x8F, 89, 13, 64, 0x95, 0xAF, 74, 0x80, 0xC4,
        59, 0xA6, 63, 107, 80, 0xB5, 127, 0xAD, 106, 74,
    ];

    /// <summary>Composes one EQ band frame exactly as the app lays it out.</summary>
    public static byte[] BuildEqBandFrame(int presetSlot, int band, EqBandSetting setting, int totalGain, byte dirWrite)
    {
        var frame = new byte[64];
        frame[0] = Cmd.GetDc03ProDigitalFilter1;   // 0x4B
        frame[1] = dirWrite;                       // 0x01
        frame[2] = 9;
        frame[3] = 31;
        frame[4] = (byte)(presetSlot + 7);
        frame[5] = (byte)band;

        var tail = EqTail;
        for (var i = 0; i < tail.Length && 9 + i < frame.Length; i++) frame[9 + i] = tail[i];

        var frequency = (ushort)Math.Clamp(setting.Frequency, 20, 20000);
        var q = (ushort)Math.Round(Math.Clamp(setting.Factor, 0, 4) * 256);
        var gain = (short)Math.Round(Math.Clamp(setting.Gain, -24, 12) * 256);

        frame[28] = (byte)(frequency & 0xFF);
        frame[29] = (byte)(frequency >> 8);
        frame[30] = (byte)(q & 0xFF);
        frame[31] = (byte)(q >> 8);
        frame[32] = (byte)(gain & 0xFF);
        frame[33] = (byte)((gain >> 8) & 0xFF);
        frame[34] = (byte)setting.FilterType;
        frame[35] = (byte)totalGain;
        return frame;
    }

    public async Task ReadEqAsync(CancellationToken ct = default)
    {
        for (var preset = 7; preset <= 9; preset++)
        {
            for (var band = 0; band < 10; band++)
            {
                var frame = new byte[64];
                frame[0] = Cmd.GetDc03ProDigitalFilter1;
                frame[1] = DirRead;
                frame[2] = 9;
                frame[3] = 31;
                frame[4] = (byte)preset;
                frame[5] = (byte)band;
                try
                {
                    var reply = await Link.RequestAsync(frame, (byte)preset, ct).ConfigureAwait(false);
                    if (reply.Raw.Length != 64) continue;

                    var data = reply.Raw;
                    var frequency = data[28] | (data[29] << 8);
                    var q = (data[30] | (data[31] << 8)) / 256.0;
                    var gain = (short)(data[32] | (data[33] << 8)) / 256.0;
                    var type = data[34];
                    var total = (sbyte)data[35];

                    EqRead?.Invoke(new EqBand(preset - 7, band, type, frequency, gain, q, total));
                }
                catch (TimeoutException)
                {
                    // band missing
                }
            }
        }
    }

    public event Action<EqBand>? EqRead;

    /// <summary>Writes a 10-band preset using the app's exact 3-phase sequence.</summary>
    public async Task WriteEqAsync(int presetSlot, EqPreset preset, CancellationToken ct = default)
    {
        if (!SupportsEqOverWire) return;

        for (var band = 0; band < 10; band++)
        {
            Link.Send(BuildEqBandFrame(presetSlot, band, preset.Bands[band], preset.TotalGain, DirWrite));
            await Task.Delay(2, ct).ConfigureAwait(false);
        }

        // commit
        var commit = new byte[64];
        commit[0] = Cmd.GetDc03ProDigitalFilter1;
        commit[1] = DirWrite;
        commit[2] = 10;
        commit[3] = 4;
        commit[4] = (byte)(presetSlot + 7);
        commit[5] = 0;
        commit[6] = 1;
        commit[7] = 1;
        Link.Send(commit);

        await Task.Delay(100, ct).ConfigureAwait(false);

        // apply
        var apply = new byte[64];
        apply[0] = Cmd.GetDc03ProDigitalFilter1;
        apply[1] = DirWrite;
        apply[2] = 1;
        apply[3] = 4;
        apply[6] = 2;
        Link.Send(apply);
    }
}

public sealed class MacchiatoProfile : Raw64Profile
{
    public MacchiatoProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso Macchiato";
    protected override bool SupportsEqOverWire => true;
}

public sealed class Dc04UProfile : Raw64Profile
{
    public Dc04UProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC04U";
    protected override bool SupportsEqOverWire => true;
}

/// <summary>D18 portable DAP — same 64-byte family but uses direction byte 0x00 for reads.</summary>
public sealed class D18Profile : Raw64Profile
{
    public D18Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso D18";
    protected override byte DirRead => 0x00;
    public override bool SupportsAmpMode => true;
    public override bool SupportsSpdif => true;
}




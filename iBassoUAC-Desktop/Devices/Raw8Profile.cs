using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>
/// Family that speaks the 8-byte command frame {cmd, group, marker, 0, v0, v1, v2, 0}
/// over the HID OUT endpoint: DC-Elite, DC07 Pro, DC-Nunchaku and DC-Tonfa.
/// </summary>
public class Raw8Profile : DeviceProfile
{
    // markers are the negated third byte of the matching request frame
    private const byte MVol = 0xEF;   // -17
    private const byte MFilter = 0xDF; // -33
    private const byte MSpdif = 0xCF;  // -49
    private const byte MDisplay = 0xBF;// -65
    private const byte MFpga = 0xF1;   // -15

    private readonly Dictionary<byte, byte[]> _groups = new()
    {
        [0x20] = new byte[4],
        [0x30] = new byte[4],
        [0x40] = new byte[4],
        [0x50] = new byte[4],
    };

    public Raw8Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }

    public override string DisplayName => "iBasso (8B family)";
    public override int MaxVolume => 100;
    public override bool SupportsDigitalFilter => true;
    public override bool SupportsDsdFilter => DsdGroupSlot >= 0;
    public override bool SupportsHpFilter => HpGroupSlot >= 0;
    public override bool SupportsSpdif => true;
    public override bool SupportsGain => GainSlot >= 0;
    public override bool SupportsOutput => OutputSlot >= 0;
    public override bool SupportsDeviceSettings => true;

    /// <summary>Slot layout overrides per model.</summary>
    protected virtual int FilterSlot => 0;
    protected virtual int SecondFilterSlot => -1;   // DSD / HP filter
    protected virtual int SpdifSlot => 0;
    protected virtual int BalanceSlot => 1;
    protected virtual int GainSlot => -1;
    protected virtual int PcmTrimSlot => -1;
    protected virtual int DsdVolCompSlot => -1;
    protected virtual int RotationSlot => -1;
    protected virtual int KeyControlSlot => -1;
    protected virtual int ScreenTimeSlot => -1;
    protected virtual int OutputSlot => -1;

    protected int DsdGroupSlot => SecondFilterSlot;
    protected int HpGroupSlot => SecondFilterSlot;

    private byte[] Slot(byte g) => _groups.TryGetValue(g, out var v) ? v : new byte[4];

    private void SetSlot(byte group, int slot, int value)
    {
        var buffer = Slot(group);
        if (slot >= 0 && slot < buffer.Length) buffer[slot] = (byte)Math.Clamp(value, 0, 255);
    }

    private int GetSlot(byte group, int slot)
    {
        var buffer = Slot(group);
        return slot >= 0 && slot < buffer.Length ? buffer[slot] : -1;
    }

    protected Task SendFrameAsync(byte[] frame, CancellationToken ct)
        => Link.RequestAsync(frame, frame[0], ct);

    // ----------------------------------------------------------------- setters

    public override async Task SetVolumeAsync(int index, CancellationToken ct = default)
    {
        var raw = (byte)Math.Clamp(index, 0, MaxVolume);
        SetSlot(0x10, 0, raw);
        await SendFrameAsync(Raw8(Cmd.SetVolumeLeft1, 0x10, MVol, raw, raw, 0, 0), ct).ConfigureAwait(false);
    }

    public override async Task SetBalanceAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x30, BalanceSlot, value);
        await SendFrameAsync(Raw8(Cmd.SetCodecExternal, 0x30, MSpdif, 0,
            Slot(0x30)[0], Slot(0x30)[1], Slot(0x30)[2], Slot(0x30)[3]), ct).ConfigureAwait(false);
    }

    public override async Task SetSpdifAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x30, SpdifSlot, value);
        await SendFrameAsync(Raw8(Cmd.SetCodecExternal, 0x30, MSpdif, 0,
            Slot(0x30)[0], Slot(0x30)[1], Slot(0x30)[2], Slot(0x30)[3]), ct).ConfigureAwait(false);
    }

    public override Task SetGainAsync(int value, CancellationToken ct = default)
    {
        if (GainSlot < 0) return Task.CompletedTask;
        SetSlot(0x30, GainSlot, value);
        return SendFrameAsync(Raw8(Cmd.SetCodecExternal, 0x30, MSpdif, 0,
            Slot(0x30)[0], Slot(0x30)[1], Slot(0x30)[2], Slot(0x30)[3]), ct);
    }

    public override async Task SetOutputAsync(int value, CancellationToken ct = default)
    {
        if (OutputSlot < 0) return;
        SetSlot(0x40, OutputSlot, value);
        await SendFrameAsync(Raw8(Cmd.SetCodecExternal, 0x40, MDisplay, 0,
            Slot(0x40)[0], Slot(0x40)[1], Slot(0x40)[2], Slot(0x40)[3]), ct).ConfigureAwait(false);
    }

    public override async Task SetDigitalFilterAsync(int value, CancellationToken ct = default)
    {
        SetSlot(0x20, FilterSlot, value);
        await SendFrameAsync(Raw8(Cmd.SetDigitalFilter1, 0x20, MFilter, 0,
            Slot(0x20)[0], Slot(0x20)[1], 0, 0), ct).ConfigureAwait(false);
    }

    public override Task SetDsdFilterAsync(int value, CancellationToken ct = default)
    {
        if (SecondFilterSlot < 0) return Task.CompletedTask;
        SetSlot(0x20, SecondFilterSlot, value);
        return SendFrameAsync(Raw8(Cmd.SetDigitalFilter1, 0x20, MFilter, 0,
            Slot(0x20)[0], Slot(0x20)[1], 0, 0), ct);
    }

    public override Task SetHpFilterAsync(int value, CancellationToken ct = default)
    {
        if (SecondFilterSlot < 0) return Task.CompletedTask;
        SetSlot(0x20, SecondFilterSlot, value);
        return SendFrameAsync(Raw8(Cmd.SetDigitalFilter1, 0x20, MFilter, 0,
            Slot(0x20)[0], Slot(0x20)[1], 0, 0), ct);
    }

    public override async Task RefreshAsync(DeviceState state, CancellationToken ct = default)
    {
        await RequestQuietly(Raw8(Cmd.GetFpgaVersion, 0xF1, MFpga, 0, 0, 0, 0, 0), ct);
        await RequestQuietly(Raw8(Cmd.GetFilter, 0x21, MFilter, 0, 0, 0, 0, 0), ct);
        await RequestQuietly(Raw8(Cmd.GetVolumeLeft2, 0x11, MVol, 0, 0, 0, 0, 0), ct);
        await RequestQuietly(Raw8(Cmd.GetSpdif, 0x31, MSpdif, 0, 0, 0, 0, 0), ct);
        await RequestQuietly(Raw8(Cmd.GetVolumeKeyControl, 0x41, MDisplay, 0, 0, 0, 0, 0), ct);
    }

    private async Task RequestQuietly(byte[] frame, CancellationToken ct)
    {
        try
        {
            await SendFrameAsync(frame, ct).ConfigureAwait(false);
        }
        catch (TimeoutException)
        {
            // Some models ignore these probes; that is not fatal.
        }
    }

    public override void ApplyResponse(UacResponse response)
    {
        if (response.Raw.Length != 8) return;

        switch (response.SeqId)
        {
            case Cmd.GetFilter:
                if (response.Raw[1] == 0x21) ApplyGroup(0x20, response.Raw);
                break;
            case Cmd.GetSpdif:
                if (response.Raw[1] == 0x31) ApplyGroup(0x30, response.Raw);
                break;
            case Cmd.GetVolumeKeyControl:
                if (response.Raw[1] == 0x41) ApplyGroup(0x40, response.Raw);
                break;
        }
    }

    private void ApplyGroup(byte group, byte[] raw)
    {
        var buffer = Slot(group);
        for (var i = 0; i < buffer.Length; i++) buffer[i] = raw[3 + i];
    }
}

/// <summary>iBasso DC07 Pro — group 0x30 = {spdif, balance, gain}.</summary>
public sealed class Dc07ProProfile : Raw8Profile
{
    public Dc07ProProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC07 Pro";
    protected override int SpdifSlot => 0;
    protected override int BalanceSlot => 1;
    protected override int GainSlot => 2;
    protected override int RotationSlot => 0;
    protected override int KeyControlSlot => 1;
    protected override int ScreenTimeSlot => 2;
}

/// <summary>iBasso DC-Nunchaku — DC07 Pro plus an output selector in group 0x40.</summary>
public sealed class Dc09Profile : Raw8Profile
{
    public Dc09Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC-Nunchaku";
    protected override int SpdifSlot => 0;
    protected override int BalanceSlot => 1;
    protected override int GainSlot => 2;
    protected override int RotationSlot => 0;
    protected override int KeyControlSlot => 1;
    protected override int ScreenTimeSlot => 2;
    protected override int OutputSlot => 3;
}

/// <summary>iBasso DC-Tonfa — same frame family, no output selector.</summary>
public sealed class TonfaProfile : Raw8Profile
{
    public TonfaProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC-Tonfa";
    protected override int SpdifSlot => 0;
    protected override int BalanceSlot => 1;
    protected override int GainSlot => 2;
    protected override int RotationSlot => 0;
    protected override int KeyControlSlot => 1;
    protected override int ScreenTimeSlot => 2;
}

/// <summary>iBasso DC-Elite — group 0x30 holds {spdif, pcmTrim, dsdVolComp, keyControl}.</summary>
public sealed class Dc07Profile : Raw8Profile
{
    public Dc07Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }
    public override string DisplayName => "iBasso DC-Elite";
    protected override int SpdifSlot => 0;
    protected override int PcmTrimSlot => 1;
    protected override int DsdVolCompSlot => 2;
    protected override int KeyControlSlot => 3;
    protected override int FilterSlot => 0;
    protected override int SecondFilterSlot => 1;   // DSD filter
    protected override int BalanceSlot => -1;
}



namespace iBassoUAC.Devices;

/// <summary>Everything the UI renders, kept in one observable bag.</summary>
public sealed class DeviceState
{
    public int Volume { get; set; } = -1;
    public int Balance { get; set; }
    public int DigitalFilter { get; set; } = -1;
    public int DsdFilter { get; set; } = -1;
    public int HpFilter { get; set; } = -1;
    public int Gain { get; set; } = -1;
    public int Output { get; set; } = -1;
    public int Spdif { get; set; } = -1;
    public int AmpMode { get; set; } = -1;
    public int PcmTrim { get; set; } = -1;
    public int DsdVolumeComp { get; set; } = -1;
    public int KeyControl { get; set; } = -1;
    public int UacVersion { get; set; } = -1;
    public int MicMode { get; set; } = -1;
    public int Language { get; set; } = -1;
    public int ScreenRotation { get; set; } = -1;
    public int OpVoltage { get; set; } = -1;
    public int ScreenTime { get; set; } = -1;
    public int Equalizer { get; set; } = -1;
    public int Dre { get; set; } = -1;
    public string FpgaVersion { get; set; } = "-";
    public DateTime? LastNativeVolumeChange { get; set; }

    /// <summary>
    /// False when the firmware cannot be read back through the host transport. The DC03 Pro
    /// family exposes no interrupt IN endpoint, so on Windows the app is write-only and the
    /// displayed values are the last ones it pushed, not a device readout.
    /// </summary>
    public bool ReadbackSupported { get; set; } = true;

    /// <summary>Shown next to values that are locally tracked rather than read from hardware.</summary>
    public bool IsWriteOnly => !ReadbackSupported;

    public void Reset()
    {
        Volume = -1;
        Balance = 0;
        DigitalFilter = DsdFilter = HpFilter = Gain = Output = Spdif = AmpMode = -1;
        PcmTrim = DsdVolumeComp = KeyControl = UacVersion = MicMode = Language = -1;
        ScreenRotation = OpVoltage = ScreenTime = Equalizer = Dre = -1;
        FpgaVersion = "-";
        LastNativeVolumeChange = null;
    }
}
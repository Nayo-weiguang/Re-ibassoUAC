using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>Base for every per-model controller, mirroring the *DeviceController classes of the app.</summary>
public abstract class DeviceProfile
{
    protected DeviceProfile(UacLink link, UsbDeviceInfo info)
    {
        Link = link;
        Info = info;
    }

    protected UacLink Link { get; }
    protected UsbDeviceInfo Info { get; }

    public abstract string DisplayName { get; }
    public virtual int MaxVolume => 100;
    public virtual bool SupportsBalance => true;
    public virtual bool SupportsDigitalFilter => true;
    public virtual bool SupportsDsdFilter => false;
    public virtual bool SupportsHpFilter => false;
    public virtual bool SupportsGain => false;
    public virtual bool SupportsOutput => false;
    public virtual bool SupportsSpdif => false;
    public virtual bool SupportsAmpMode => false;
    public virtual bool SupportsParametricEq => false;
    public virtual bool SupportsDeviceSettings => false;

    /// <summary>Hardware attenuation byte for a UI volume index (0..MaxVolume).</summary>
    public virtual byte VolumeToRaw(int index)
    {
        var clamped = Math.Clamp(index, 0, MaxVolume);
        return (byte)clamped;
    }

    /// <summary>Nearest UI volume index for a raw hardware value.</summary>
    public virtual int RawToVolume(int raw)
    {
        var best = 0;
        var bestDelta = int.MaxValue;
        for (var i = 0; i <= MaxVolume; i++)
        {
            var delta = Math.Abs(VolumeToRaw(i) - raw);
            if (delta < bestDelta)
            {
                bestDelta = delta;
                best = i;
            }
        }

        return best;
    }

    public virtual double VolumeToDb(int index) => index - MaxVolume;

    // ------------------------------------------------------------ capability

    public virtual Task RefreshAsync(DeviceState state, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetVolumeAsync(int index, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetBalanceAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetDigitalFilterAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetDsdFilterAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetHpFilterAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetGainAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetOutputAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetSpdifAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    public virtual Task SetAmpModeAsync(int value, CancellationToken ct = default)
        => Task.CompletedTask;

    /// <summary>Applies a device-originated notification / polled reply to the UI state.</summary>
    public virtual void ApplyResponse(UacResponse response) { }

    // ---------------------------------------------------------- raw builders

    /// <summary>8-byte command frame used by DC-Elite / DC07 Pro / DC-Nunchaku / Tonfa.</summary>
    protected static byte[] Raw8(byte command, byte group, byte marker, params byte[] values)
    {
        var p = new byte[8];
        p[0] = command;
        p[1] = group;
        p[2] = marker;
        for (var i = 0; i < values.Length && i + 3 < p.Length; i++) p[3 + i] = values[i];
        return p;
    }

    /// <summary>64-byte state frame used by Macchiato / DC04U / D18.</summary>
    protected static byte[] Raw64(byte command, byte group, bool isWrite, params byte[] values)
    {
        var p = new byte[64];
        p[0] = Cmd.GetDc03ProDigitalFilter1;  // 0x4B
        p[1] = isWrite ? (byte)0x01 : (byte)0x80;
        p[2] = 0x3F;                          // okio Utf8.REPLACEMENT_BYTE
        p[3] = 0x08;
        p[4] = command;
        p[5] = group;
        for (var i = 0; i < values.Length && i + 6 < p.Length; i++) p[6 + i] = values[i];
        return p;
    }

    /// <summary>Device-side "group" addresses shared by the 64-byte family.</summary>
    protected static class Group
    {
        public const byte Volume = 0x10;
        public const byte Filter = 0x20;
        public const byte Spdif = 0x30;
        public const byte Display = 0x40;
        public const byte Uac = 0x50;
    }
}

/// <summary>Fallback for anything that does not match a known product name.</summary>
public sealed class GenericProfile : DeviceProfile
{
    public GenericProfile(UacLink link, UsbDeviceInfo info) : base(link, info) { }

    public override string DisplayName => "未知型号（原始通道）";
    public override int MaxVolume => 63;
    public override bool SupportsBalance => false;
    public override bool SupportsDigitalFilter => false;
    public override bool SupportsGain => false;
    public override bool SupportsOutput => false;
    public override bool SupportsSpdif => false;
    public override bool SupportsAmpMode => false;
    public override bool SupportsParametricEq => false;
    public override bool SupportsDeviceSettings => false;
}

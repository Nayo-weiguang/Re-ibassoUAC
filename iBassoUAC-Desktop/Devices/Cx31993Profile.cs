using iBassoUAC.Protocol;

namespace iBassoUAC.Devices;

/// <summary>
/// CX31993 based units only expose a 16-bit volume write over HID SetReport
/// (report id 0x02). There is no read-back command in the firmware protocol.
/// </summary>
public sealed class Cx31993Profile : DeviceProfile
{
    public Cx31993Profile(UacLink link, UsbDeviceInfo info) : base(link, info) { }

    public override string DisplayName => "CX31993 HiFi";
    public override int MaxVolume => 63;
    public override bool SupportsBalance => false;
    public override bool SupportsDigitalFilter => false;
    public override bool SupportsGain => false;
    public override bool SupportsOutput => false;
    public override bool SupportsSpdif => false;
    public override bool SupportsAmpMode => false;
    public override bool SupportsParametricEq => false;
    public override bool SupportsDeviceSettings => false;

    public override byte VolumeToRaw(int index) => (byte)(VolumeTables.Step[Math.Clamp(index, 0, MaxVolume)] & 0xFF);

    public override int RawToVolume(int raw)
    {
        var best = 0;
        var bestDelta = int.MaxValue;
        for (var i = 0; i <= MaxVolume; i++)
        {
            var delta = Math.Abs(VolumeTables.Step[i] - raw);
            if (delta < bestDelta)
            {
                bestDelta = delta;
                best = i;
            }
        }

        return best;
    }

    public override Task SetVolumeAsync(int index, CancellationToken ct = default)
    {
        index = Math.Clamp(index, 0, MaxVolume);
        var word = VolumeTables.Step[index];
        var payload = new[] { (byte)(word & 0xFF), (byte)(word >> 8) };
        Link.Send(payload);
        return Task.CompletedTask;
    }

    public override Task RefreshAsync(DeviceState state, CancellationToken ct = default) => Task.CompletedTask;

    public override void ApplyResponse(UacResponse response)
    {
        if (!response.IsNativeVolumeChange || response.Payload.Length < 1) return;
    }
}

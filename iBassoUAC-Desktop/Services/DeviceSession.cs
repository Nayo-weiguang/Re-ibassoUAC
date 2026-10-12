using iBassoUAC.Devices;
using iBassoUAC.Interop;
using iBassoUAC.Protocol;

namespace iBassoUAC.Services;

/// <summary>
/// Owns the current connection: enumerate → open → identify → talk.
/// </summary>
public sealed class DeviceSession : IDisposable
{
    private UacLink? _link;
    private DeviceProfile? _profile;

    public UsbDeviceInfo? Device { get; private set; }
    public DeviceProfile? Profile => _profile;
    public UacLink? Link => _link;
    public bool IsConnected => _link?.IsOpen == true;
    public string Status { get; private set; } = "未连接";

    public event Action? StateChanged;
    public event Action<string>? StatusChanged;
    public event Action<ProtocolLogEntry>? Log;
    public event Action<UacResponse>? NativeStateChanged;

    public void Connect(UsbDeviceInfo info)
    {
        Disconnect();

        var handle = HidApi.Open(info.Path);
        if (!HidApi.IsValidHandle(handle))
        {
            Status = $"无法打开设备（Win32 {System.Runtime.InteropServices.Marshal.GetLastWin32Error()}）。请尝试以管理员身份运行。";
            StatusChanged?.Invoke(Status);
            return;
        }

        Device = info;
        _link = new UacLink(handle, info);
        _link.Log += e => Log?.Invoke(e);
        _link.ResponseReceived += OnResponse;

        var catalog = ProductCatalog.Match(info.ProductName);
        if (catalog is not null)
        {
            _profile = catalog.Factory(_link, info);
            Status = $"已连接 · {_profile.DisplayName} ({info.VidPid})";
        }
        else
        {
            _profile = new GenericProfile(_link, info);
            Status = string.IsNullOrWhiteSpace(info.ProductName)
                ? $"已连接 · 未知 HID 设备 ({info.VidPid})，仅提供原始通道"
                : $"已连接 · 未识别型号 \"{info.ProductName}\"，仅提供原始通道";
        }

        StatusChanged?.Invoke(Status);
        StateChanged?.Invoke();
    }

    private void OnResponse(UacResponse response)
    {
        _profile?.ApplyResponse(response);
        NativeStateChanged?.Invoke(response);
    }

    public void Disconnect()
    {
        _link?.Dispose();
        _link = null;
        _profile = null;
        Device = null;
        Status = "未连接";
        StatusChanged?.Invoke(Status);
        StateChanged?.Invoke();
    }

    public void Dispose() => Disconnect();
}
using iBassoUAC.Devices;
using iBassoUAC.Interop;

namespace iBassoUAC.Protocol;

public sealed record UsbDeviceInfo(string Path, ushort VendorId, ushort ProductId, string ProductName, string Manufacturer)
{
    public string VidPid => $"{VendorId:X4}:{ProductId:X4}";
    public string DisplayName => string.IsNullOrWhiteSpace(ProductName) ? $"HID {VidPid}" : ProductName;
    public bool IsIbasoFamily => UsbIds.Known.Contains(VendorId);
}

public static class DeviceEnumerator
{
    /// <summary>Lists every attached HID device, cheapest-field-first.</summary>
    public static IReadOnlyList<UsbDeviceInfo> Enumerate()
    {
        var result = new List<UsbDeviceInfo>();
        foreach (var path in HidApi.EnumerateHidPaths())
        {
            var handle = HidApi.Open(path);
            if (!HidApi.IsValidHandle(handle))
            {
                // Read-only handles still let us read the strings; retry with read access only.
                handle = OpenReadOnly(path);
                if (!HidApi.IsValidHandle(handle)) continue;
            }

            try
            {
                var attributes = HidApi.GetAttributes(handle);
                if (attributes.Size == 0) continue;

                result.Add(new UsbDeviceInfo(
                    path,
                    attributes.VendorID,
                    attributes.ProductID,
                    HidApi.GetString(handle, product: true),
                    HidApi.GetString(handle, product: false)));
            }
            finally
            {
                HidApi.CloseHandle(handle);
            }
        }

        return result
            .OrderByDescending(d => d.IsIbasoFamily)
            .ThenBy(d => d.DisplayName)
            .ToList();
    }

    /// <summary>Dumps raw SetupAPI state so enumeration problems are diagnosable.</summary>
    public static string Diagnose()
    {
        var sb = new System.Text.StringBuilder();
        HidApi.HidD_GetHidGuid(out var guid);
        sb.AppendLine($"HidD_GetHidGuid -> {guid}  err={System.Runtime.InteropServices.Marshal.GetLastWin32Error()}");

        var set = HidApi.SetupDiGetClassDevs(ref guid, null, 0, 0x02 | 0x10);
        sb.AppendLine($"SetupDiGetClassDevs(0x12) -> 0x{((ulong)(nint)set).ToString("X16")}  err={System.Runtime.InteropServices.Marshal.GetLastWin32Error()}");
        if (!HidApi.IsValidHandle(set)) return sb.ToString();

        var iface = new HidApi.SP_DEVICE_INTERFACE_DATA { cbSize = System.Runtime.InteropServices.Marshal.SizeOf<HidApi.SP_DEVICE_INTERFACE_DATA>() };
        sb.AppendLine($"SP_DEVICE_INTERFACE_DATA size = {iface.cbSize}");
        uint index = 0;
        int found = 0;
        while (HidApi.SetupDiEnumDeviceInterfaces(set, 0, ref guid, index++, ref iface))
        {
            HidApi.SetupDiGetDeviceInterfaceDetail(set, ref iface, 0, 0, out var required, 0);
            if (required == 0) continue;

            var detail = System.Runtime.InteropServices.Marshal.AllocHGlobal((int)required);
            try
            {
                System.Runtime.InteropServices.Marshal.WriteInt32(detail, 8);
                if (!HidApi.SetupDiGetDeviceInterfaceDetail(set, ref iface, detail, required, out _, 0)) continue;
                var path = System.Runtime.InteropServices.Marshal.PtrToStringUni(detail + 4);
                if (string.IsNullOrEmpty(path) || !path.StartsWith(@"\\?\", StringComparison.Ordinal))
                {
                    var alt = System.Runtime.InteropServices.Marshal.PtrToStringUni(detail + 8);
                    if (!string.IsNullOrEmpty(alt) && alt.StartsWith(@"\\?\", StringComparison.Ordinal)) path = alt;
                }
                found++;
                if (found <= 8) sb.AppendLine($"  [{index - 1}] {path}");

                var h = HidApi.Open(path!);
                if (!HidApi.IsValidHandle(h))
                {
                    var err = System.Runtime.InteropServices.Marshal.GetLastWin32Error();
                    if (found <= 8) sb.AppendLine($"        CreateFile 失败 err={err}");
                    continue;
                }

                var at = HidApi.GetAttributes(h);
                sb.AppendLine($"        打开成功  VID={at.VendorID:X4} PID={at.ProductID:X4} Size={at.Size} "
                            + $"产品=\"{HidApi.GetString(h, product: true)}\" 厂商=\"{HidApi.GetString(h, product: false)}\"");
                HidApi.CloseHandle(h);
            }
            finally
            {
                System.Runtime.InteropServices.Marshal.FreeHGlobal(detail);
            }
        }

        sb.AppendLine($"共枚举到 {found} 个 HID 接口。");
        HidApi.SetupDiDestroyDeviceInfoList(set);
        return sb.ToString();
    }

    private static nint OpenReadOnly(string path)
    {
        const uint GENERIC_READ = 0x80000000;
        const uint FILE_SHARE_READ = 0x00000001;
        const uint FILE_SHARE_WRITE = 0x00000002;
        const uint OPEN_EXISTING = 3;
        return CreateFileReadOnly(path, GENERIC_READ, FILE_SHARE_READ | FILE_SHARE_WRITE, OPEN_EXISTING);
    }

    [System.Runtime.InteropServices.DllImport("kernel32.dll", CharSet = System.Runtime.InteropServices.CharSet.Unicode,
        EntryPoint = "CreateFileW", SetLastError = true)]
    private static extern nint CreateFileReadOnly(string fileName, uint desiredAccess, uint shareMode, uint creationDisposition);
}

/// <summary>
/// Maps the USB product-name string reported by the firmware onto a controller
/// profile, exactly like UacManager.onDcDevicePermissionRequired() does.
/// </summary>
public sealed record ProductProfile(string Selector, string DisplayName, Func<UacLink, UsbDeviceInfo, DeviceProfile> Factory);

public static class ProductCatalog
{
    public static readonly IReadOnlyList<ProductProfile> All = new List<ProductProfile>
    {
        new("iBasso-DC06",        "iBasso DC06",        (l, i) => new Dc06Profile(l, i)),
        new("iBasso-DC06-Pro",    "iBasso DC06 Pro",    (l, i) => new Dc06ProProfile(l, i)),
        new("iBasso-DC03-Pro",    "iBasso DC03 Pro",    (l, i) => new Dc03ProProfile(l, i)),
        new("iBasso-DC04-Pro",    "iBasso DC04 Pro",    (l, i) => new Dc04ProProfile(l, i)),
        new("Macaron",            "iBasso Macaron",     (l, i) => new Dc08Profile(l, i)),
        new("iBasso DC-Elite",    "iBasso DC-Elite",    (l, i) => new Dc07Profile(l, i)),
        new("iBasso DC-Tonfa",    "iBasso DC-Tonfa",    (l, i) => new TonfaProfile(l, i)),
        new("iBasso DC07 Pro",    "iBasso DC07 Pro",    (l, i) => new Dc07ProProfile(l, i)),
        new("iBasso DC-Nunchaku", "iBasso DC-Nunchaku", (l, i) => new Dc09Profile(l, i)),
        new("DC04U",              "iBasso DC04U",       (l, i) => new Dc04UProfile(l, i)),
        new("D18",                "iBasso D18",         (l, i) => new D18Profile(l, i)),
        new("Macchiato",          "iBasso Macchiato",   (l, i) => new MacchiatoProfile(l, i)),
        new("CX31993 384Khz HIFI AUDIO", "CX31993 HiFi", (l, i) => new Cx31993Profile(l, i)),
    };

    public static ProductProfile? Match(string? productName)
    {
        if (string.IsNullOrWhiteSpace(productName)) return null;
        var trimmed = productName.Trim();
        foreach (var profile in All)
        {
            if (string.Equals(trimmed, profile.Selector, StringComparison.OrdinalIgnoreCase)) return profile;
        }

        return null;
    }

    /// <summary>
    /// Fallback identification by USB VID/PID for the cases where Windows hands back an
    /// empty product string (seen on DC03 Pro after the HID stack re-enumerates).
    /// Only ids observed on real hardware are listed; everything else still needs the
    /// product string.
    /// </summary>
    public static ProductProfile? MatchByVidPid(ushort vendorId, ushort productId)
    {
        var selector = (vendorId, productId) switch
        {
            (0x262A, 0x187E) => "iBasso-DC03-Pro",
            _ => null,
        };

        return selector is null
            ? null
            : All.FirstOrDefault(p => string.Equals(p.Selector, selector, StringComparison.OrdinalIgnoreCase));
    }

    /// <summary>Product-name match first, USB ids as the fallback.</summary>
    public static ProductProfile? Match(UsbDeviceInfo device)
        => Match(device.ProductName) ?? MatchByVidPid(device.VendorId, device.ProductId);
}
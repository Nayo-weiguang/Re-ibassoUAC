using System.IO;
using System.Runtime.InteropServices;

namespace iBassoUAC.Interop;

/// <summary>
/// Raw USB access through WinUSB, used once the protocol interface is bound to the WinUSB
/// driver instead of HidUsb.
///
/// Why this is needed: the Windows HID class driver only exposes SET_REPORT(Output) with an
/// OUT stage — <c>HidD_SetOutputReport</c> gives the firmware no place to answer. The iBasso
/// firmware replies in the IN stage of the very same control transfer, which Android's
/// <c>UsbDeviceConnection.controlTransfer</c> collects automatically. WinUSB lets us issue the
/// identical SETUP packet and get both stages back, so register reads finally work.
///
/// Nothing here is iBasso-specific; the 0x21/0x09/0x0200 request triple lives in the protocol
/// layer, not in this interop wrapper.
/// </summary>
internal static class WinUsbApi
{
    /// <summary>GUID_DEVINTERFACE_USB_DEVICE — published by WinUSB for every bound device.</summary>
    internal static readonly Guid GuidUsbDeviceInterface = new("a5dcbf10-6530-11d2-901f-00c04fb951ed");

    /// <summary>GUID_DEVCLASS_USB; a plain devnode walk on this class covers composite MI_xx nodes.</summary>
    private static readonly Guid GuidDevClassUsb = new("36fc9e60-c465-11cf-8056-444553540000");

    /// <summary>Every device-interface GUID a WinUSB-bound node may publish itself under.</summary>
    internal static readonly Guid[] InterfaceGuidCandidates =
    [
        new("dee824ef-729b-4a0e-9c14-b7117d33a817"), // GUID_DEVINTERFACE_WINUSB_DEVICE
        new("6a1ede13-3f01-41dd-93fa-6166d8a053aa"), // published by WinUSB on the interface node
        GuidUsbDeviceInterface,                       // GUID_DEVINTERFACE_USB_DEVICE (parent nodes)
    ];

    /// <summary>
    /// A composite device's interface nodes are registered under the class of their <em>function</em>,
    /// not under USB, so MI_00 (HID) has to be looked for under HIDClass. All plausible classes of a
    /// DAC are scanned.
    /// </summary>
    private static readonly Guid[] DevNodeClassGuids =
    [
        GuidDevClassUsb,
        new("745a17a0-74d3-11d0-b6fe-00a0c90f57da"), // GUID_DEVCLASS_HIDCLASS
        new("4d36e96c-e325-11ce-bfc1-08002be10318"), // GUID_DEVCLASS_AUDIO
        new("a5dcbf10-6530-11d2-901f-00c04fb951ed"), // GUID_DEVCLASS_USB (interface GUID form)
    ];

    private const uint DIGCF_PRESENT = 0x02;
    private const uint DIGCF_DEVICEINTERFACE = 0x10;
    private const uint GENERIC_READ = 0x80000000;
    private const uint GENERIC_WRITE = 0x40000000;
    private const uint FILE_SHARE_READ = 0x00000001;
    private const uint FILE_SHARE_WRITE = 0x00000002;
    private const uint OPEN_EXISTING = 3;
    private const uint FILE_FLAG_OVERLAPPED = 0x40000000;

    internal const int ErrorIoPending = 997;
    internal const int ErrorOperationAborted = 995;

    internal sealed record UsbDeviceInfoRow(string Path, string InstanceId, string FriendlyName);

    // Pack = 4 is mandatory: the CLR otherwise marshals the embedded Guid with pack = 1 and hands
    // SetupAPI a 28-byte SP_DEVICE_INTERFACE_DATA / SP_DEVINFO_DATA instead of 32, which makes
    // enumeration fail with ERROR_INVALID_DATA and, worse, lets the API overwrite 4 bytes of
    // whatever follows the buffer.
    [StructLayout(LayoutKind.Sequential, Pack = 4)]
    internal struct SP_DEVICE_INTERFACE_DATA
    {
        public int cbSize;
        public Guid InterfaceClassGuid;
        public int Flags;
        public nint Reserved;
    }

    [StructLayout(LayoutKind.Sequential, Pack = 4)]
    internal struct SP_DEVINFO_DATA
    {
        public int cbSize;
        public Guid ClassGuid;
        public uint DevInstData;
        public nint Reserved;
    }

    /// <summary>WinUSB wants the caller to pass a cbSize-only struct as the device-info argument.</summary>
    [StructLayout(LayoutKind.Sequential)]
    internal struct UsbDeviceInterfaceData
    {
        public uint cbSize;
    }

    // USB_INTERFACE_DESCRIPTOR starts with two UCHARs (bLength, bDescriptorType). Declaring bLength
    // as ushort shifts every following field by one byte, so bNumEndpoints read back the interface
    // class instead — which is how a HID interface (class 0x03) came out as "3 endpoints".
    [StructLayout(LayoutKind.Sequential)]
    public struct UsbInterfaceDescriptor
    {
        public byte Length;
        public byte DescriptorType;
        public byte InterfaceNumber;
        public byte AlternateSetting;
        public byte NumEndpoints;
        public byte InterfaceClass;
        public byte InterfaceSubClass;
        public byte InterfaceProtocol;
        public byte Interface;
    }

    [StructLayout(LayoutKind.Explicit, Size = 12)]
    internal struct PipeInformation
    {
        [FieldOffset(0)] public PipeType PipeType;
        [FieldOffset(4)] public byte PipeId;
        [FieldOffset(6)] public ushort MaximumPacketSize;
        [FieldOffset(8)] public byte Interval;
    }

    internal enum PipeType : int
    {
        Control = 0,
        Isochronous = 1,
        Bulk = 2,
        Interrupt = 3,
    }

    // ------------------------------------------------------------------ SetupAPI

    [DllImport("setupapi.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern nint SetupDiGetClassDevs(ref Guid classGuid, string? enumerator, nint hwndParent, uint flags);

    [DllImport("setupapi.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiEnumDeviceInterfaces(nint deviceInfoSet, uint deviceInfoData, ref Guid interfaceClassGuid, uint memberIndex, ref SP_DEVICE_INTERFACE_DATA deviceInterfaceData);

    [DllImport("setupapi.dll", EntryPoint = "SetupDiGetDeviceInterfaceDetailW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiGetDeviceInterfaceDetailW(nint deviceInfoSet, ref SP_DEVICE_INTERFACE_DATA deviceInterfaceData, nint deviceInterfaceDetailData, uint deviceInterfaceDetailDataSize, out uint requiredSize, nint deviceInfoData);

    /// <summary>Class-devnode walk with an "all classes" search (a null GUID argument).</summary>
    [DllImport("setupapi.dll", EntryPoint = "SetupDiGetClassDevsW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern nint SetupDiGetClassDevsAll(nint classGuid, string? enumerator, nint hwndParent, uint flags);

    [DllImport("setupapi.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiEnumDeviceInfo(nint deviceInfoSet, uint memberIndex, ref SP_DEVINFO_DATA deviceInfoData);

    /// <summary>Returns the instance id, i.e. the hardware id plus the \…\9&amp;1234ab56&amp;1&amp;0000 tail.</summary>
    [DllImport("setupapi.dll", EntryPoint = "SetupDiGetDeviceInstanceIdW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiGetDeviceInstanceIdW(nint deviceInfoSet, ref SP_DEVINFO_DATA deviceInfoData, byte[] deviceInstanceId, uint deviceInstanceIdSize, out uint requiredSize);

    /// <summary>Configuration manager's own instance-id lookup, driven by the DevInst we enumerated.</summary>
    [DllImport("cfgmgr32.dll", EntryPoint = "CM_Get_Device_IDW", ExactSpelling = true, CharSet = CharSet.Unicode)]
    internal static extern int CM_Get_Device_ID(uint dnDevInst, System.Text.StringBuilder buffer, uint bufferLen, uint flags);

    /// <summary>
    /// Decodes the DevicePath out of an SP_DEVICE_INTERFACE_DETAIL_DATA buffer.
    /// The W entry point is requested explicitly, but the buffer is still sniffed for a UTF-16
    /// marker so an ANSI answer (should the A variant ever be bound instead) decodes correctly
    /// rather than turning into mojibake.
    /// </summary>
    private static string ReadDevicePath(nint detail, int size)
    {
        var raw = new byte[Math.Max(0, size)];
        if (raw.Length == 0) return string.Empty;
        Marshal.Copy(detail, raw, 0, raw.Length);

        var start = 4; // { DWORD cbSize; WCHAR DevicePath[]; }
        var isUnicode = raw.Length > start + 1 && raw[start + 1] == 0x00 && raw[start] != 0x00;

        var encoding = isUnicode ? System.Text.Encoding.Unicode : System.Text.Encoding.UTF8;
        return encoding.GetString(raw, start, raw.Length - start).Split('\0')[0].Trim();
    }

    [DllImport("setupapi.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiDestroyDeviceInfoList(nint deviceInfoSet);

    [DllImport("setupapi.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool SetupDiGetDeviceRegistryProperty(nint deviceInfoSet, ref SP_DEVINFO_DATA deviceInfoData, int property, out uint propertyRegDataType, byte[]? propertyBuffer, uint propertyBufferSize, out uint requiredSize);

    [DllImport("kernel32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern nint CreateFile(string fileName, uint desiredAccess, uint shareMode, nint securityAttributes, uint creationDisposition, uint flagsAndAttributes, nint templateFile);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool CloseHandle(nint handle);

    // ------------------------------------------------------------------- WinUSB

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_Initialize(nint deviceHandle, out nint interfaceHandle);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_Free(nint interfaceHandle);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_QueryInterfaceSettings(nint interfaceHandle, byte alternateInterfaceNumber, out UsbInterfaceDescriptor descriptor);

    /// <summary>WINUSB_SETUP_PACKET, an 8-byte #pragma pack(1) struct passed by value.</summary>
    [StructLayout(LayoutKind.Sequential, Pack = 1, Size = 8)]
    internal struct WinUsbSetupPacket
    {
        public byte RequestType;
        public byte Request;
        public ushort Value;
        public ushort Index;
        public ushort Length;
    }

    // The native entry point takes the setup packet *by value* as a packed struct and ends in an
    // OVERLAPPED pointer; there is no separate DeviceHandle and no TimeoutMs. Declaring it with the
    // 11-argument shape used in prose samples shifts every register and faults the stack.
    // bmRequestType 0x21 has bit7 clear, so the control transfer only has an OUT data stage — the
    // firmware answers by pushing the reply onto its interrupt IN pipe, exactly as the Android app
    // receives it. That pipe is therefore the only place a register read can come from.
    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_GetDescriptor(nint interfaceHandle, byte descriptorType, byte index, ushort languageId, nint buffer, uint bufferLength, out uint lengthTransferred);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_ReadPipe(nint interfaceHandle, byte pipeId, nint buffer, uint bufferLength, out uint lengthTransferred, nint overlapped);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_ResetPipe(nint interfaceHandle, byte pipeId);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_FlushPipe(nint interfaceHandle, byte pipeId);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_ControlTransfer(
        nint interfaceHandle,
        WinUsbSetupPacket setupPacket,
        nint buffer,
        uint bufferLength,
        out uint lengthTransferred,
        nint overlapped);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_QueryPipe(nint interfaceHandle, byte alternateInterfaceNumber, byte pipeIndex, out PipeInformation pipe);

    /// <summary>Absolute minimum path: open, initialize, one transfer. No descriptor queries.</summary>
    internal static string MinimalControlProbe(string path, byte requestType, byte request, ushort value, ushort index, int length)
    {
        var device = Open(path);
        if (!IsValid(device)) return $"CreateFile 失败，Win32 = {Marshal.GetLastWin32Error()}";

        try
        {
            if (!WinUsb_Initialize(device, out var usb))
                return $"WinUsb_Initialize 失败，Win32 = {Marshal.GetLastWin32Error()}";

            try
            {
                var buffer = new byte[length];
                var moved = ControlTransfer(usb, requestType, request, value, index, buffer);
                return $"成功：传输长度={moved}  回包={Convert.ToHexString(buffer)}";
            }
            finally
            {
                if (IsValid(usb)) WinUsb_Free(usb);
            }
        }
        catch (Exception ex)
        {
            return $"{ex.GetType().Name}: {ex.Message}";
        }
        finally
        {
            CloseHandle(device);
        }
    }

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_InterruptTransfer(
        nint interfaceHandle,
        byte pipeId,
        byte[] buffer,
        uint bufferLength,
        out uint lengthTransferred,
        nint overlapped);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_AbortPipe(nint interfaceHandle, byte pipeId);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WinUsb_ResetDevice(nint interfaceHandle);

    // --------------------------------------------------------------- OVERLAPPED

    [StructLayout(LayoutKind.Sequential)]
    internal struct NativeOverlapped
    {
        public nint Internal;
        public nint InternalHigh;
        public nint Offset;
        public nint OffsetHigh;
        public nint Pointer;
        public nint Event;
    }

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern nint CreateEventW(nint attrs, bool manualReset, bool initialState, string? name);

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern uint WaitForSingleObject(nint handle, uint milliseconds);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool ResetEvent(nint handle);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool CancelIoEx(nint handle, nint overlapped);

    internal const uint WaitObject0 = 0x00000000;

    internal static nint AllocOverlapped(nint eventHandle)
    {
        var p = Marshal.AllocHGlobal(Marshal.SizeOf<NativeOverlapped>());
        for (var i = 0; i < Marshal.SizeOf<NativeOverlapped>(); i++) Marshal.WriteByte(p, i, 0);
        Marshal.WriteIntPtr(p, (int)Marshal.OffsetOf<NativeOverlapped>(nameof(NativeOverlapped.Event)), eventHandle);
        return p;
    }

    // ------------------------------------------------------------------ helpers

    internal static bool IsValid(nint handle) => handle != 0 && handle != -1;

    /// <summary>Every currently present WinUSB interface, paired with its PnP instance id.</summary>
    internal static List<UsbDeviceInfoRow> Enumerate()
    {
        var rows = new List<UsbDeviceInfoRow>();

        // A node bound to WinUSB publishes GUID_DEVINTERFACE_WINUSB_DEVICE, not
        // GUID_DEVINTERFACE_USB_DEVICE — the latter is only published by the parent USB device,
        // so scanning just that one finds the composite node and never the protocol interface.
        foreach (var interfaceGuid in InterfaceGuidCandidates)
        {
            var guid = interfaceGuid;
            var set = SetupDiGetClassDevs(ref guid, null, 0, DIGCF_PRESENT | DIGCF_DEVICEINTERFACE);
            if (!IsValid(set)) continue;

            try
            {
                var iface = new SP_DEVICE_INTERFACE_DATA { cbSize = Marshal.SizeOf<SP_DEVICE_INTERFACE_DATA>() };
                for (uint index = 0; SetupDiEnumDeviceInterfaces(set, 0, ref guid, index, ref iface); index++)
                {
                    SetupDiGetDeviceInterfaceDetailW(set, ref iface, 0, 0, out var required, 0);
                    if (required == 0) continue;

                    var detail = Marshal.AllocHGlobal((int)required);
                    try
                    {
                        Marshal.WriteInt32(detail, IntPtr.Size == 8 ? 8 : 6);
                        if (!SetupDiGetDeviceInterfaceDetailW(set, ref iface, detail, required, out _, 0)) continue;

                        var path = ReadDevicePath(detail, (int)required);
                        if (string.IsNullOrEmpty(path)) continue;
                        if (rows.Any(r => r.Path.Equals(path, StringComparison.OrdinalIgnoreCase))) continue;

                        rows.Add(new UsbDeviceInfoRow(path, ReadInstanceId(set, ref iface), string.Empty));
                    }
                    finally
                    {
                        Marshal.FreeHGlobal(detail);
                    }
                }
            }
            finally
            {
                SetupDiDestroyDeviceInfoList(set);
            }
        }

        return rows;
    }

    /// <summary>Raw first bytes of each interface-detail buffer, for diagnosing path decoding.</summary>
    internal static List<(string Hex, string Unicode)> EnumerateRaw(int previewBytes = 96)
    {
        var rows = new List<(string, string)>();
        var guid = GuidUsbDeviceInterface;
        var set = SetupDiGetClassDevs(ref guid, null, 0, DIGCF_PRESENT | DIGCF_DEVICEINTERFACE);
        if (!IsValid(set)) return rows;

        try
        {
            var iface = new SP_DEVICE_INTERFACE_DATA { cbSize = Marshal.SizeOf<SP_DEVICE_INTERFACE_DATA>() };
            for (uint index = 0; SetupDiEnumDeviceInterfaces(set, 0, ref guid, index, ref iface); index++)
            {
                SetupDiGetDeviceInterfaceDetailW(set, ref iface, 0, 0, out var required, 0);
                if (required == 0) continue;

                var detail = Marshal.AllocHGlobal((int)required);
                try
                {
                    Marshal.WriteInt32(detail, IntPtr.Size == 8 ? 8 : 6);
                    if (!SetupDiGetDeviceInterfaceDetailW(set, ref iface, detail, required, out _, 0)) continue;

                    var count = (int)Math.Min(previewBytes, required);
                    var raw = new byte[count];
                    Marshal.Copy(detail, raw, 0, count);
                    rows.Add((Convert.ToHexString(raw), ReadDevicePath(detail, (int)required)));
                }
                finally
                {
                    Marshal.FreeHGlobal(detail);
                }
            }
        }
        finally
        {
            SetupDiDestroyDeviceInfoList(set);
        }

        return rows;
    }

        // -------------------------------------------------- driver rebinding

    /// <summary>
    /// Rebinds a device node to a driver INF. Unlike the device-manager wizard this does not
    /// consult the INF's hardware-id match, which is why the inbox winusb.inf — that lists no
    /// iBasso hardware id at all — can still be pushed onto MI_00. This is the same API Zadig
    /// uses, so no third-party tool or self-signed package is needed.
    /// </summary>
    [DllImport("newdev.dll", EntryPoint = "UpdateDriverForPlugAndPlayDevicesW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool UpdateDriverForPlugAndPlayDevicesW(
        string hardwareInstanceId,
        string fullInfPath,
        string installSection,
        uint flags,
        [MarshalAs(UnmanagedType.Bool)] out bool rebootRequired);

    /// <summary>The inbox WinUSB driver, used to give the protocol interface a raw pipe.</summary>
    internal const string WinUsbInf = @"C:\Windows\INF\winusb.inf";

    /// <summary>Inbox HID driver, used to undo <see cref="BindWinUsb"/>.</summary>
    internal const string HidUsbInf = @"C:\Windows\INF\input.inf";

    internal const string HidUsbInstallSection = "USB_Install";

    internal enum Privilege
    {
        Unknown = 0,
        WinUsb = 1,
        HidUsb = 2,
    }

    /// <summary>Rebinds MI_00 to WinUSB (or back to HidUsb) without needing Zadig.</summary>
    internal static bool Rebind(ushort vendorId, ushort productId, byte interfaceNumber, Privilege privilege, out string detail)
    {
        var scanned = new List<string>();
        var hwid = FindHardwareInstanceId(vendorId, productId, interfaceNumber, scanned);
        var (inf, section) = privilege == Privilege.WinUsb
            ? (WinUsbInf, "WINUSB.NT")
            : (HidUsbInf, HidUsbInstallSection);

        if (hwid.Length == 0)
        {
            detail = $"绑定失败：在设备树中找不到 VID_{vendorId:X4}&PID_{productId:X4} 的 MI_{interfaceNumber:X2} 接口节点。\n"
                     + $"    扫描到的同厂牌硬件 ID（{scanned.Count} 个）：\n"
                     + (scanned.Count == 0 ? "      （无）" : string.Join("\n", scanned.Select(s => "      " + s)));
            return false;
        }

        if (!UpdateDriverForPlugAndPlayDevicesW(hwid, inf, section, 0, out var reboot))
        {
            var err = Marshal.GetLastWin32Error();
            detail = $"绑定失败：hwid={hwid}\n    inf={inf} [{section}]\n    Win32={err}";
            return false;
        }

        detail = $"绑定成功：{hwid}\n    → {inf} [{section}]" + (reboot ? "\n    （提示：可能需要重启或拔插设备）" : string.Empty);
        return true;
    }

    /// <summary>The instance id that would be handed to the driver binder; empty when absent.</summary>
    internal static string ResolveInstanceId(ushort vendorId, ushort productId, byte interfaceNumber)
        => FindHardwareInstanceId(vendorId, productId, interfaceNumber);

    /// <summary>Lists every devnode of one USB vid/pid, for diagnostics; needs no privileges.</summary>
    internal static IReadOnlyList<string> DescribeUsbNodes(ushort vendorId, ushort productId)
    {
        var scanned = new List<string>();
        FindHardwareInstanceId(vendorId, productId, byte.MaxValue, scanned);
        return scanned;
    }

    /// <summary>Dumps enough of the devnode walk to see whether SetupAPI cooperated at all.</summary>
    internal static string DescribeDevnodeWalk(ushort vendorId, ushort productId)
    {
        const int SPDRP_HARDWAREID = 0x00000001;
        var sb = new System.Text.StringBuilder();
        var usbClass = GuidDevClassUsb;
        var set = SetupDiGetClassDevs(ref usbClass, null, 0, DIGCF_PRESENT);
        sb.AppendLine($"SetupDiGetClassDevs(GUID_DEVCLASS_USB, PRESENT) = 0x{set:X} valid={IsValid(set)}");

        if (IsValid(set))
        {
            try
            {
                var total = 0;
                var withHwId = 0;
                var usbHits = new List<string>();
                var samples = new List<string>();
                var info = new SP_DEVINFO_DATA { cbSize = Marshal.SizeOf<SP_DEVINFO_DATA>() };
                sb.AppendLine($"SP_DEVINFO_DATA 大小 = {Marshal.SizeOf<SP_DEVINFO_DATA>()}");
                if (!SetupDiEnumDeviceInfo(set, 0, ref info))
                {
                    sb.AppendLine($"SetupDiEnumDeviceInfo(0) 失败，Win32 = {Marshal.GetLastWin32Error()}");
                }

                total = 0;
                for (uint index = 0; SetupDiEnumDeviceInfo(set, index, ref info); index++)
                {
                    total++;
                    SetupDiGetDeviceRegistryProperty(set, ref info, SPDRP_HARDWAREID, out _, null, 0, out var required);
                    if (required == 0) continue;

                    var buffer = new byte[required];
                    if (!SetupDiGetDeviceRegistryProperty(set, ref info, SPDRP_HARDWAREID, out _, buffer, (uint)buffer.Length, out var size)) continue;

                    withHwId++;
                    foreach (var id in System.Text.Encoding.Unicode
                                 .GetString(buffer, 0, (int)size)
                                 .Split('\0', StringSplitOptions.RemoveEmptyEntries))
                    {
                        if (samples.Count < 5) samples.Add(id);
                        if (id.StartsWith($"USB\\VID_{vendorId:X4}&PID_{productId:X4}", StringComparison.OrdinalIgnoreCase)) usbHits.Add(id);
                    }
                }

                sb.AppendLine($"枚举到设备节点 {total} 个，其中有硬件 ID 的 {withHwId} 个");
                sb.AppendLine($"其中 VID_{vendorId:X4}&PID_{productId:X4} 命中 {usbHits.Count} 个：");
                foreach (var h in usbHits) sb.AppendLine("    " + h);
                sb.AppendLine("硬件 ID 样本：");
                foreach (var s in samples) sb.AppendLine("    " + s);
            }
            finally
            {
                SetupDiDestroyDeviceInfoList(set);
            }
        }

        sb.AppendLine();
        sb.AppendLine("注册表路径 HKLM:\\SYSTEM\\CurrentControlSet\\Enum\\USB\\VID_262A&PID_187E&MI_00：");
        try
        {
            using var key = Microsoft.Win32.Registry.LocalMachine
                .OpenSubKey($@"SYSTEM\CurrentControlSet\Enum\USB\VID_{vendorId:X4}&PID_{productId:X4}&MI_00");
            if (key is null)
            {
                sb.AppendLine("    （打不开该键）");
            }
            else
            {
                var names = key.GetSubKeyNames();
                sb.AppendLine($"    实例 {names.Length} 个：");
                foreach (var n in names)
                {
                    sb.AppendLine($"    USB\\VID_{vendorId:X4}&PID_{productId:X4}&MI_00\\{n}");
                }
            }
        }
        catch (Exception ex)
        {
            sb.AppendLine($"    异常：{ex.GetType().Name}: {ex.Message}");
        }

        sb.AppendLine();
        sb.AppendLine("注册表候选实例 ID（USB 与 HID 两棵子树）：");
        foreach (var root in new[] { "USB", "HID" })
        foreach (var tail in new[] { "", "&REV_0006" })
        foreach (var mi in new[] { "", "&MI_00", "&MI_01" })
        {
            var path = $@"SYSTEM\CurrentControlSet\Enum\{root}\VID_{vendorId:X4}&PID_{productId:X4}{tail}{mi}";
            try
            {
                using var key = Microsoft.Win32.Registry.LocalMachine.OpenSubKey(path);
                if (key is null) continue;
                foreach (var n in key.GetSubKeyNames())
                {
                    sb.AppendLine($"    {root}\\VID_{vendorId:X4}&PID_{productId:X4}{tail}{mi}\\{n}");
                }
            }
            catch (Exception ex)
            {
                sb.AppendLine($"    {path} -> {ex.GetType().Name}");
            }
        }

        return sb.ToString();
    }

    /// <summary>
    /// Recovers the hardware instance id of one interface node, for example
    /// <c>USB\VID_262A&amp;PID_187E&amp;MI_00\9&amp;362D5A02&amp;1&amp;0000</c>, which is what
    /// UpdateDriverForPlugAndPlayDevices expects. The id segment of a device-interface path cannot
    /// be used instead: it names the parent container, and interface nodes never publish
    /// GUID_DEVINTERFACE_USB_DEVICE at all, so the scan has to walk plain devnodes.
    /// </summary>
    private static string FindHardwareInstanceId(ushort vendorId, ushort productId, byte interfaceNumber, List<string>? scanned = null)
    {
        const int SPDRP_HARDWAREID = 0x00000001;
        var vidPid = $"USB\\VID_{vendorId:X4}&PID_{productId:X4}";

        foreach (var classGuid in DevNodeClassGuids)
        {
            var guid = classGuid;
            var set = SetupDiGetClassDevs(ref guid, null, 0, DIGCF_PRESENT);
            if (!IsValid(set)) continue;

            try
            {
                var info = new SP_DEVINFO_DATA { cbSize = Marshal.SizeOf<SP_DEVINFO_DATA>() };
                for (uint index = 0; SetupDiEnumDeviceInfo(set, index, ref info); index++)
                {
                    SetupDiGetDeviceRegistryProperty(set, ref info, SPDRP_HARDWAREID, out _, null, 0, out var required);
                    if (required == 0) continue;

                    var buffer = new byte[required];
                    if (!SetupDiGetDeviceRegistryProperty(set, ref info, SPDRP_HARDWAREID, out _, buffer, (uint)buffer.Length, out var size)) continue;

                    foreach (var id in System.Text.Encoding.Unicode
                                 .GetString(buffer, 0, (int)size)
                                 .Split('\0', StringSplitOptions.RemoveEmptyEntries))
                    {
                        if (!id.StartsWith(vidPid, StringComparison.OrdinalIgnoreCase)) continue;
                        if (scanned is not null && !scanned.Contains(id)) scanned.Add(id);
                        if (!id.StartsWith($"{vidPid}&MI_{interfaceNumber:X2}", StringComparison.OrdinalIgnoreCase)) continue;

                        // SPDRP_HARDWAREID stops at the instance tail; the driver API needs the
                        // full instance id, so resolve it from the devnode's DevInst.
                        var instanceId = new System.Text.StringBuilder(512);
                        if (CM_Get_Device_ID(info.DevInstData, instanceId, 512, 0) != 0) continue;
                        return instanceId.ToString();
                    }
                }
            }
            finally
            {
                SetupDiDestroyDeviceInfoList(set);
            }
        }

        return string.Empty;
    }

    // ------------------------------------------------------------- elevation

    [DllImport("kernel32.dll")]
    internal static extern nint GetCurrentProcess();

    [DllImport("advapi32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool OpenProcessToken(nint processHandle, uint desiredAccess, out nint tokenHandle);

    [DllImport("advapi32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool GetTokenInformation(nint tokenHandle, int tokenInformationClass, out TOKEN_ELEVATION elevation, uint elevationTokenSize, out uint returnLength);

    [StructLayout(LayoutKind.Sequential)]
    internal struct TOKEN_ELEVATION
    {
        public int TokenIsElevated;
    }

    private const uint TOKEN_QUERY = 0x0008;
    private const int TokenElevation = 20;

    internal static bool IsAdministrator()
    {
        if (!OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, out var token)) return false;
        try
        {
            return GetTokenInformation(token, TokenElevation, out var elevation, (uint)Marshal.SizeOf<TOKEN_ELEVATION>(), out _)
                   && elevation.TokenIsElevated != 0;
        }
        finally
        {
            CloseHandle(token);
        }
    }

    private const uint SEE_MASK_NOCLOSEPROCESS = 0x00000040;

    [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
    private struct SHELLEXECUTEINFOW
    {
        public int cbSize;
        public uint fMask;
        public nint hwnd;
        [MarshalAs(UnmanagedType.LPWStr)] public string lpVerb;
        [MarshalAs(UnmanagedType.LPWStr)] public string lpFile;
        [MarshalAs(UnmanagedType.LPWStr)] public string lpParameters;
        [MarshalAs(UnmanagedType.LPWStr)] public string lpDirectory;
        public int nShow;
        public nint hInstApp;
        public nint lpIDList;
        [MarshalAs(UnmanagedType.LPWStr)] public string lpClass;
        public nint hkeyClass;
        public uint dwHotKey;
        public nint hIcon;
        public nint hProcess;
    }

    /// <summary>Every instance id the registry knows for this device's interface and HID subtrees.</summary>
    internal static IReadOnlyList<string> CandidateInstanceIds(ushort vendorId, ushort productId)
    {
        var ids = new List<string>();
        foreach (var root in new[] { "USB", "HID" })
        foreach (var tail in new[] { "", "&REV_0006" })
        foreach (var mi in new[] { "", "&MI_00", "&MI_01" })
        {
            var path = $@"SYSTEM\CurrentControlSet\Enum\{root}\VID_{vendorId:X4}&PID_{productId:X4}{tail}{mi}";
            try
            {
                using var key = Microsoft.Win32.Registry.LocalMachine.OpenSubKey(path);
                if (key is null) continue;
                foreach (var n in key.GetSubKeyNames())
                {
                    var id = $"{root}\\VID_{vendorId:X4}&PID_{productId:X4}{tail}{mi}\\{n}";
                    if (!ids.Contains(id)) ids.Add(id);
                }
            }
            catch { /* an unreadable subtree simply contributes nothing */ }
        }

        return ids;
    }

    /// <summary>Where the WinUSB driver package actually lives once staged; empty when not present.</summary>
    internal static IReadOnlyList<string> WinUsbInfCandidates()
    {
        var found = new List<string> { WinUsbInf };
        try
        {
            foreach (var dir in System.IO.Directory.EnumerateDirectories(
                         @"C:\Windows\System32\DriverStore\FileRepository", "winusb.inf_*"))
            {
                var inf = System.IO.Path.Combine(dir, "winusb.inf");
                if (System.IO.File.Exists(inf)) found.Add(inf);
            }
        }
        catch { /* fall back to the INF-copy path only */ }

        return found;
    }

    [DllImport("kernel32.dll", EntryPoint = "SetLastError", ExactSpelling = true, SetLastError = false)]
    private static extern void SetThreadError(uint code);

    /// <summary>One attempt against an explicit install section and flag set, for the probe matrix.</summary>
    internal static (bool Ok, int Win32) TryUpdateDriver(string hardwareInstanceId, string inf, string installSection, uint flags)
    {
        // Poison the last-error slot first: the driver API does not always call SetLastError when
        // it fails, so reading whatever an earlier SetupAPI call left there turns every distinct
        // failure into the same misleading code.
        SetThreadError(0xBEEF);
        var ok = UpdateDriverForPlugAndPlayDevicesW(hardwareInstanceId, inf, installSection, flags, out _);
        return (ok, Marshal.GetLastWin32Error());
    }

    [StructLayout(LayoutKind.Sequential)]
    private struct LUID
    {
        public uint LowPart;
        public int HighPart;
    }

    [StructLayout(LayoutKind.Sequential)]
    private struct TOKEN_PRIVILEGES
    {
        public uint PrivilegeCount;
        public LUID Luid;
        public uint Attributes;
    }

    [DllImport("advapi32.dll", EntryPoint = "LookupPrivilegeValueW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool LookupPrivilegeValue(string systemName, string name, out LUID luid);

    [DllImport("advapi32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool AdjustTokenPrivileges(nint tokenHandle, [MarshalAs(UnmanagedType.Bool)] bool disableAll, ref TOKEN_PRIVILEGES newState, uint bufferLength, out uint returnLength, nint previousState);

    private const uint TOKEN_ADJUST_PRIVILEGES = 0x0020;
    private const uint SE_PRIVILEGE_ENABLED = 0x00000002;

    /// <summary>Driver installs need SeLoadDriverPrivilege; returns 0 on success or the Win32 code.</summary>
    internal static int EnableLoadDriverPrivilege()
    {
        if (!OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, out var token))
            return Marshal.GetLastWin32Error();
        try
        {
            if (!LookupPrivilegeValue(null!, "SeLoadDriverPrivilege", out var luid))
                return Marshal.GetLastWin32Error();

            var state = new TOKEN_PRIVILEGES { PrivilegeCount = 1, Luid = luid, Attributes = SE_PRIVILEGE_ENABLED };
            if (!AdjustTokenPrivileges(token, false, ref state, (uint)Marshal.SizeOf<TOKEN_PRIVILEGES>(), out _, 0))
                return Marshal.GetLastWin32Error();

            // AdjustTokenPrivileges reports success even when it silently drops the privilege;
            // ERROR_NOT_ALL_ASSIGNED (1300) is how that shows up.
            return Marshal.GetLastWin32Error();
        }
        finally
        {
            CloseHandle(token);
        }
    }

    [DllImport("shell32.dll", EntryPoint = "IsUserAnAdmin", ExactSpelling = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool IsUserAnAdmin();

    [DllImport("shell32.dll", EntryPoint = "ShellExecuteExW", ExactSpelling = true, CharSet = CharSet.Unicode, SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool ShellExecuteEx(ref SHELLEXECUTEINFOW info);

    /// <summary>Relaunches the current executable elevated and waits for it; returns its exit code.</summary>
    internal static int RelaunchElevated(string[] arguments, int timeoutMs = 180_000)
    {
        var exe = Environment.ProcessPath
                  ?? throw new InvalidOperationException("无法确定当前可执行文件路径。");

        var info = new SHELLEXECUTEINFOW
        {
            cbSize = Marshal.SizeOf<SHELLEXECUTEINFOW>(),
            fMask = SEE_MASK_NOCLOSEPROCESS,
            lpVerb = "runas",
            lpFile = exe,
            lpParameters = string.Join(' ', arguments.Select(Quote)),
            lpDirectory = Environment.CurrentDirectory,
            nShow = 1, // SW_SHOWNORMAL
        };

        if (!ShellExecuteEx(ref info))
        {
            throw new InvalidOperationException(
                $"请求管理员权限被拒绝或失败，Win32 = {Marshal.GetLastWin32Error()}");
        }

        if (info.hProcess == IntPtr.Zero) return -1;
        WaitForSingleObject(info.hProcess, (uint)timeoutMs);
        return GetExitCodeProcess(info.hProcess, out var code) ? (int)code : -1;
    }

    private static string Quote(string s) => s.Contains(' ') ? $"\"{s}\"" : s;

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool GetExitCodeProcess(nint handle, out uint exitCode);

    private static string ReadInstanceId(nint set, ref SP_DEVICE_INTERFACE_DATA iface)
    {
        const int SPDRP_DEVINST = 0x0000000E;
        var info = new SP_DEVINFO_DATA { cbSize = Marshal.SizeOf<SP_DEVINFO_DATA>() };
        var buffer = new byte[1024];
        if (!SetupDiGetDeviceRegistryProperty(set, ref info, SPDRP_DEVINST, out _, buffer, (uint)buffer.Length, out var size)
            || size == 0 || size > buffer.Length)
        {
            return string.Empty;
        }

        return System.Text.Encoding.Unicode.GetString(buffer, 0, (int)size).TrimEnd('\0');
    }

    /// <summary>True when a device-interface path belongs to the given USB vendor/product id.</summary>
    internal static bool PathMatches(string path, ushort vendorId, ushort productId)
        => path.Contains($"vid_{vendorId:x4}&pid_{productId:x4}", StringComparison.OrdinalIgnoreCase);

    internal static nint Open(string path)
        => CreateFile(path, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, 0, OPEN_EXISTING, FILE_FLAG_OVERLAPPED, 0);

    /// <summary>One synchronous control transfer; returns the transferred length or throws on failure.</summary>
    internal static uint ControlTransfer(nint interfaceHandle, byte requestType, byte request, ushort value, ushort index, byte[] buffer)
    {
        // The payload goes through unmanaged memory so no array-marshalling behaviour can take part
        // in the transfer, and the whole thing runs synchronously (no OVERLAPPED).
        var pointer = Marshal.AllocHGlobal(Math.Max(buffer.Length, 1));
        try
        {
            Marshal.Copy(buffer, 0, pointer, buffer.Length);

            var setup = new WinUsbSetupPacket
            {
                RequestType = requestType,
                Request = request,
                Value = value,
                Index = index,
                Length = (ushort)buffer.Length,
            };

            if (!WinUsb_ControlTransfer(interfaceHandle, setup, pointer, (uint)buffer.Length, out var moved, 0))
            {
                throw new IOException(
                    $"WinUsb_ControlTransfer 失败: Win32 {Marshal.GetLastWin32Error()} " +
                    $"(bmRequestType=0x{requestType:X2} bRequest=0x{request:X2} wValue=0x{value:X4} wIndex={index} len={buffer.Length})");
            }

            Marshal.Copy(pointer, buffer, 0, buffer.Length);
            return moved;
        }
        finally
        {
            Marshal.FreeHGlobal(pointer);
        }
    }

    /// <summary>Sends the request, then drains whatever the firmware pushes onto the interrupt IN pipe.</summary>
    internal static string RequestThenDrain(string path, byte[] request, byte[] replyBuffer, byte[] pipeIds, uint timeoutMs)
    {
        var device = Open(path);
        if (!IsValid(device)) return $"CreateFile 失败，Win32 = {Marshal.GetLastWin32Error()}";

        try
        {
            if (!WinUsb_Initialize(device, out var usb))
                return $"WinUsb_Initialize 失败，Win32 = {Marshal.GetLastWin32Error()}";

            try
            {
                var sent = new byte[request.Length + 1];
                sent[0] = 0x00;
                Buffer.BlockCopy(request, 0, sent, 1, request.Length);

                var report = string.Empty;
                var sentOk = false;
                for (var attempt = 1; attempt <= 3 && !sentOk; attempt++)
                {
                    try
                    {
                        var moved = ControlTransfer(usb, 0x21, 0x09, 0x0200, 0, sent);
                        report += $"控制传输 OUT 第{attempt}次尝试 -> 传输长度={moved}\r\n";
                        sentOk = true;
                    }
                    catch (IOException ex)
                    {
                        report += $"控制传输 第{attempt}次尝试失败：{ex.Message}\r\n";
                        Thread.Sleep(200);
                    }
                }

                foreach (var pipeId in pipeIds)
                {
                    // A synchronous WinUsb_ReadPipe blocks forever when the device stays silent, so
                    // every drain is an overlapped read with an explicit deadline.
                    var moved = ReadPipeWithDeadline(usb, pipeId, replyBuffer, timeoutMs, out var data, out var how);
                    report += $"读管道 0x{pipeId:X2}: {how}  数据={Convert.ToHexString(data)}\r\n";
                }

                return report.TrimEnd();
            }
            finally
            {
                if (IsValid(usb)) WinUsb_Free(usb);
            }
        }
        catch (Exception ex)
        {
            return $"{ex.GetType().Name}: {ex.Message}";
        }
        finally
        {
            CloseHandle(device);
        }
    }

    /// <summary>One overlapped pipe read with a hard deadline; never blocks past it.</summary>
    private static uint ReadPipeWithDeadline(nint usb, byte pipeId, byte[] buffer, uint timeoutMs, out byte[] data, out string how)
    {
        data = Array.Empty<byte>();
        var event_ = CreateEventW(IntPtr.Zero, true, false, null);
        var overlapped = AllocOverlapped(event_);
        var pointer = Marshal.AllocHGlobal(buffer.Length);
        try
        {
            var ok = WinUsb_ReadPipe(usb, pipeId, pointer, (uint)buffer.Length, out var transferred, overlapped);

            if (!ok && Marshal.GetLastWin32Error() != ErrorIoPending)
            {
                how = $"立即失败 Win32={Marshal.GetLastWin32Error()}";
                return 0;
            }

            if (!ok)
            {
                var wait = WaitForSingleObject(event_, timeoutMs);
                if (wait != WaitObject0)
                {
                    WinUsb_AbortPipe(usb, pipeId);
                    how = $"{timeoutMs} ms 内无数据（管道静默）";
                    return 0;
                }
            }

            // WinUSB never fills ActualLength on the overlapped path; the byte count lives in the
            // OVERLAPPED structure's InternalHigh field.
            var status = Marshal.PtrToStructure<NativeOverlapped>(overlapped);
            var count = (int)Math.Max(0, Math.Min(status.InternalHigh, buffer.Length));
            var copied = new byte[count];
            Marshal.Copy(pointer, copied, 0, count);
            data = copied;
            how = ok ? $"立即完成，收到 {count} 字节" : $"收到 {count} 字节";
            return (uint)count;
        }
        finally
        {
            Marshal.FreeHGlobal(pointer);
            Marshal.FreeHGlobal(overlapped);
            CloseHandle(event_);
        }
    }

    /// <summary>Sends the request, then drains whatever the firmware pushes onto its declared interrupt IN endpoints.</summary>
    internal static List<string> RequestThenDrainEndpoints(string path, byte[] request, byte[] replyBuffer, uint timeoutMs)
    {
        var lines = new List<string>();
        var device = Open(path);
        if (!IsValid(device))
        {
            lines.Add($"CreateFile 失败，Win32 = {Marshal.GetLastWin32Error()}");
            return lines;
        }

        try
        {
            if (!WinUsb_Initialize(device, out var usb))
            {
                lines.Add($"WinUsb_Initialize 失败，Win32 = {Marshal.GetLastWin32Error()}");
                return lines;
            }

            try
            {
                if (!WinUsb_QueryInterfaceSettings(usb, 0, out var iface))
                {
                    lines.Add($"WinUsb_QueryInterfaceSettings 失败，Win32 = {Marshal.GetLastWin32Error()}");
                    return lines;
                }

                lines.Add(DescribeInterface(iface));

                var endpoints = EndpointDescriptors(usb, Math.Max(iface.NumEndpoints, (byte)1));
                lines.Add($"端点描述符共 {endpoints.Count} 条（最后一次查询：{LastEndpointQuery}）：");
                foreach (var (address, attributes, maxPacket) in endpoints)
                {
                    var kind = (attributes & 0x03) switch
                    {
                        0 => "Control",
                        1 => "Iso",
                        2 => "Bulk",
                        3 => "Interrupt",
                        _ => "?",
                    };
                    var dir = (address & 0x80) != 0 ? "IN " : "OUT";
                    lines.Add($"    端点 0x{address:X2} 方向={dir} 类型={kind} 最大包={maxPacket}");
                }

                var interruptIn = InterruptInPipes(usb, iface.NumEndpoints);
                lines.Add($"可读的中断 IN 端点：{(interruptIn.Count == 0 ? "无" : string.Join(", ", interruptIn.Select(a => $"0x{a:X2}")))}");

                var sent = new byte[request.Length + 1];
                sent[0] = 0x00;
                Buffer.BlockCopy(request, 0, sent, 1, request.Length);

                for (var attempt = 1; attempt <= 3; attempt++)
                {
                    try
                    {
                        var moved = ControlTransfer(usb, 0x21, 0x09, 0x0200, (ushort)iface.InterfaceNumber, sent);
                        lines.Add($"控制传输 OUT 第{attempt}次尝试 -> 传输长度={moved}（发送 {Convert.ToHexString(sent)}）");
                        break;
                    }
                    catch (IOException ex)
                    {
                        lines.Add($"控制传输 第{attempt}次尝试失败：{ex.Message}");
                        Thread.Sleep(200);
                    }
                }

                if (interruptIn.Count == 0)
                {
                    lines.Add("固件没有声明中断 IN 端点，无法接收回复。");
                    return lines;
                }

                foreach (var pipeId in interruptIn)
                {
                    ReadPipeWithDeadline(usb, pipeId, replyBuffer, timeoutMs, out var data, out var how);
                    lines.Add($"读端点 0x{pipeId:X2}: {how}  数据={Convert.ToHexString(data)}");
                }

                return lines;
            }
            finally
            {
                if (IsValid(usb)) WinUsb_Free(usb);
            }
        }
        catch (Exception ex)
        {
            lines.Add($"{ex.GetType().Name}: {ex.Message}");
            return lines;
        }
        finally
        {
            CloseHandle(device);
        }
    }

    /// <summary>Every field of the interface descriptor, for diagnostics.</summary>
    internal static string DescribeInterface(UsbInterfaceDescriptor d)
        => $"    bLength={d.Length} bDescriptorType=0x{d.DescriptorType:X2} bInterfaceNumber={d.InterfaceNumber} " +
           $"bAlternateSetting={d.AlternateSetting} bNumEndpoints={d.NumEndpoints} " +
           $"bInterfaceClass=0x{d.InterfaceClass:X2} bInterfaceSubClass=0x{d.InterfaceSubClass:X2} " +
           $"bInterfaceProtocol=0x{d.InterfaceProtocol:X2} iInterface={d.Interface}";

    /// <summary>Every endpoint the firmware declares, straight from the endpoint descriptors.</summary>
    internal static List<string> DescribePipes(nint winUsb, byte numEndpoints)
    {
        var rows = new List<string>();
        var endpoints = EndpointDescriptors(winUsb, Math.Max(numEndpoints, (byte)1));
        if (endpoints.Count == 0)
        {
            rows.Add("    （未能读到任何端点描述符）");
            return rows;
        }

        foreach (var (address, attributes, maxPacket) in endpoints)
        {
            var type = (attributes & 0x03) switch
            {
                0 => "Control",
                1 => "Isochronous",
                2 => "Bulk",
                3 => "Interrupt",
                _ => "未知",
            };
            var direction = (address & 0x80) != 0 ? "IN " : "OUT";
            rows.Add($"    端点 0x{address:X2} 方向={direction} 类型={type} 最大包={maxPacket}");
        }

        return rows;
    }

    /// <summary>
    /// Reproduces the official Android app's read model: claim the interface, take the first IN
    /// endpoint, then keep an overlapped read queued on it at all times while the request is sent
    /// out of band. Sending once and reading once is not the same thing.
    /// </summary>
    internal static List<string> ContinuousDrain(string path, byte[] request, int seconds, int requestEveryMs, bool prefixReportId = true, int readWindowMs = 1500)
    {
        var lines = new List<string>();
        var device = Open(path);
        if (!IsValid(device)) { lines.Add($"CreateFile 失败，Win32 = {Marshal.GetLastWin32Error()}"); return lines; }

        try
        {
            if (!WinUsb_Initialize(device, out var usb))
            { lines.Add($"WinUsb_Initialize 失败，Win32 = {Marshal.GetLastWin32Error()}"); return lines; }

            try
            {
                if (!WinUsb_QueryInterfaceSettings(usb, 0, out var iface))
                { lines.Add($"WinUsb_QueryInterfaceSettings 失败，Win32 = {Marshal.GetLastWin32Error()}"); return lines; }

                lines.Add(DescribeInterface(iface));

                // WinUsb_GetDescriptor times out on this firmware, so the endpoint cannot be read
                // from the device at runtime. The USB specification requires a HID interface to
                // declare an interrupt IN endpoint, and bNumEndpoints == 1, so that endpoint is
                // 0x81 by definition rather than by guesswork.
                const byte HidInterruptIn = 0x81;
                lines.Add($"端点无法运行时枚举（固件对 GET_DESCRIPTOR 超时）；" +
                          $"按 HID 规范 bNumEndpoints={iface.NumEndpoints} 可确定 IN 端点为 0x{HidInterruptIn:X2}");

                var stopAt = Environment.TickCount64 + seconds * 1000L;
                var event_ = CreateEventW(IntPtr.Zero, true, false, null);
                var overlapped = AllocOverlapped(event_);
                var pointer = Marshal.AllocHGlobal(64);
                var received = 0;
                var nextRequest = Environment.TickCount64;
                var readUntil = 0L;
                var requestNumber = 0;

                try
                {
                    // The official app hands Android a raw 16-byte frame with no report-id prefix, while the Windows
                    // HID class driver requires the leading 0x00. Both shapes are worth trying.
                    var sent = prefixReportId
                        ? new byte[request.Length + 1]
                        : (byte[])request.Clone();
                    if (prefixReportId)
                    {
                        sent[0] = 0x00;
                        Buffer.BlockCopy(request, 0, sent, 1, request.Length);
                    }

                    while (Environment.TickCount64 < stopAt)
                    {
                        // Send first, with no read pending. A queued overlapped read shares the
                        // interface's single I/O queue with the synchronous control transfer and was
                        // making roughly half of them time out with ERROR_SEM_TIMEOUT.
                        if (Environment.TickCount64 >= nextRequest)
                        {
                            nextRequest = Environment.TickCount64 + requestEveryMs;
                            requestNumber++;
                            try
                            {
                                var moved = ControlTransfer(usb, 0x21, 0x09, 0x0200, (ushort)iface.InterfaceNumber, sent);
                                lines.Add($"[{DateTime.Now:HH:mm:ss.fff}] 发出读请求 #{requestNumber}，传输长度={moved}");
                                readUntil = Environment.TickCount64 + readWindowMs;
                            }
                            catch (IOException ex)
                            {
                                lines.Add($"[{DateTime.Now:HH:mm:ss.fff}] 读请求 #{requestNumber} 失败：{ex.Message}");
                            }
                        }

                        if (Environment.TickCount64 >= readUntil)
                        {
                            Thread.Sleep(20);
                            continue;
                        }

                        // Then read, and nothing else, until the window closes.
                        for (var i = 0; i < 64; i++) Marshal.WriteByte(pointer, i, 0);
                        ResetEvent(event_);

                        var ok = WinUsb_ReadPipe(usb, HidInterruptIn, pointer, 64, out _, overlapped);
                        if (!ok && Marshal.GetLastWin32Error() != ErrorIoPending)
                        {
                            lines.Add($"[{DateTime.Now:HH:mm:ss.fff}] WinUsb_ReadPipe 立即失败 Win32={Marshal.GetLastWin32Error()}");
                            WinUsb_AbortPipe(usb, HidInterruptIn);
                            Thread.Sleep(50);
                            continue;
                        }

                        var wait = WaitForSingleObject(event_, 200);
                        if (wait != WaitObject0) continue;   // timed out: re-queue and keep going

                        var status = Marshal.PtrToStructure<NativeOverlapped>(overlapped);
                        var count = (int)Math.Max(0, Math.Min(status.InternalHigh, 64));
                        if (count == 0) continue;

                        var data = new byte[count];
                        Marshal.Copy(pointer, data, 0, count);
                        received += count;
                        lines.Add($"[{DateTime.Now:HH:mm:ss.fff}] ★ 收到 {count} 字节：{Convert.ToHexString(data)}");
                    }
                }
                finally
                {
                    WinUsb_AbortPipe(usb, HidInterruptIn);
                    Marshal.FreeHGlobal(pointer);
                    Marshal.FreeHGlobal(overlapped);
                    CloseHandle(event_);
                }

                lines.Add($"监听 {seconds} 秒结束，共收到 {received} 字节，发出 {requestNumber} 个读请求。");
                return lines;
            }
            finally
            {
                if (IsValid(usb)) WinUsb_Free(usb);
            }
        }
        catch (Exception ex)
        {
            lines.Add($"{ex.GetType().Name}: {ex.Message}");
            return lines;
        }
        finally
        {
            CloseHandle(device);
        }
    }

    /// <summary>
    /// The authoritative endpoint list, read straight from the firmware's endpoint descriptors.
    /// WinUsb_QueryPipe could not be trusted on this interface, and guessing endpoint numbers is
    /// exactly how the earlier "every pipe is silent" run ended up reading the wrong pipes.
    /// </summary>
    internal static List<(byte Address, byte Attributes, ushort MaxPacketSize)> EndpointDescriptors(nint usb, byte maxEndpoints = 16)
    {
        const byte UsbDescriptorEndpoint = 0x05;
        var found = new List<(byte, byte, ushort)>();
        var pointer = Marshal.AllocHGlobal(64);
        try
        {
            for (byte index = 0; index < maxEndpoints; index++)
            {
                var ok = WinUsb_GetDescriptor(usb, UsbDescriptorEndpoint, index, 0, pointer, 64, out var length);
                if (!ok || length < 7)
                {
                    LastEndpointQuery = $"索引 {index}: 成功={ok} 返回长度={length} Win32={Marshal.GetLastWin32Error()}";
                    break; // ran off the end of the descriptor list
                }

                var raw = new byte[length];
                Marshal.Copy(pointer, raw, 0, (int)length);
                found.Add((raw[2], raw[3], (ushort)(raw[4] | (raw[5] << 8))));
                LastEndpointQuery = $"索引 {index}: 读到 {length} 字节";
            }
        }
        finally
        {
            Marshal.FreeHGlobal(pointer);
        }

        return found;
    }

    /// <summary>Diagnostic for the last endpoint-descriptor query; empty before the first call.</summary>
    internal static string LastEndpointQuery { get; private set; } = string.Empty;

    /// <summary>Interrupt IN endpoint addresses the firmware actually declares.</summary>
    internal static List<byte> InterruptInPipes(nint winUsb, byte numEndpoints)
    {
        const byte TransferTypeMask = 0x03;
        const byte TransferTypeInterrupt = 0x03;

        return EndpointDescriptors(winUsb, Math.Max(numEndpoints, (byte)1))
            .Where(e => (e.Attributes & TransferTypeMask) == TransferTypeInterrupt)
            .Select(e => e.Address)
            .Where(a => (a & 0x80) != 0)
            .ToList();
    }
}
using System.IO;
using System.Runtime.InteropServices;
using System.Text;

namespace iBassoUAC.Interop;

/// <summary>
/// Minimal Win32 HID access layer. Deliberately dependency-free so the app can be
/// published as a single self-contained executable without any native payload.
///
/// The iBasso UAC firmware (verified against DC03 Pro, VID 262A / PID 187E) matches
/// the official Android app, which drives the device with
///   controlTransfer(0x21, 0x09 /*SET_REPORT*/, 0x0200 /*Output report, id 0*/, 0, data, 16)
/// and receives the reply asynchronously on the interrupt IN endpoint. It exposes no
/// HID Feature reports (SET_REPORT / SET_FEATURE_REPORT both fail with
/// ERROR_INVALID_FUNCTION), so on Windows the equivalent — and only working —
/// path is a WriteFile of an output report.
///
/// The handle must therefore be opened with FILE_FLAG_OVERLAPPED: a synchronous read
/// keeps the file object's I/O lock and serializes every concurrent write, which
/// deadlocks the request/reply loop.
/// </summary>
internal static class HidApi
{
    private const uint DIGCF_PRESENT = 0x02;
    private const uint DIGCF_DEVICEINTERFACE = 0x10;
    private const uint GENERIC_READ = 0x80000000;
    private const uint GENERIC_WRITE = 0x40000000;
    private const uint FILE_SHARE_READ = 0x00000001;
    private const uint FILE_SHARE_WRITE = 0x00000002;
    private const uint OPEN_EXISTING = 3;
    private const uint FILE_FLAG_OVERLAPPED = 0x40000000;
    private const nint INVALID_HANDLE_VALUE = -1;

    // CTL_CODE(FILE_DEVICE_KEYBOARD /*0x0b*/, function, method, FILE_ANY_ACCESS)
    //   = (DeviceType << 16) | (Access << 14) | (Function << 2) | Method
    // METHOD_BUFFERED = 0, METHOD_OUT_DIRECT = 2
    private const uint IOCTL_HID_GET_REPORT = 0x000B040E;   // CTL_CODE(0x0b, 0x0103, OUT_DIRECT, 0)
    private const uint IOCTL_HID_SET_REPORT = 0x000B0412;   // CTL_CODE(0x0b, 0x0104, OUT_DIRECT, 0)
    private const uint IOCTL_HID_SET_FEATURE_REPORT = 0x000B0410; // CTL_CODE(0x0b, 0x0104, BUFFERED, 0)

    internal const int ErrorIoPending = 997;

    /// <summary>Bound on a single output report transfer; the firmware answers well inside it.</summary>
    internal const uint WriteTimeoutMs = 2000;

    internal const int ReportIdLength = 1;

    [StructLayout(LayoutKind.Sequential)]
    internal struct SP_DEVICE_INTERFACE_DATA
    {
        public int cbSize;
        public Guid InterfaceClassGuid;
        public int Flags;
        public nint Reserved;
    }

    [DllImport("hid.dll", SetLastError = true)]
    internal static extern void HidD_GetHidGuid(out Guid guid);

    [DllImport("hid.dll", SetLastError = true)]
    internal static extern bool HidD_GetAttributes(nint hidDeviceObject, out HIDD_ATTRIBUTES attributes);

    [DllImport("hid.dll", SetLastError = true)]
    internal static extern bool HidD_GetPreparsedData(nint hidDeviceObject, out nint preparsedData);

    [DllImport("hid.dll", SetLastError = true)]
    internal static extern bool HidD_FreePreparsedData(nint preparsedData);

    /// <summary>
    /// SET_REPORT(Output) control transfer — the exact primitive the official Android app
    /// reaches for with controlTransfer(0x21, 0x09, 0x0200, 0, data, len). The buffer must
    /// start with the report id (0x00 for this firmware); the stack strips it from the wire.
    /// </summary>
    [DllImport("hid.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool HidD_SetOutputReport(nint hidDeviceObject, byte[] reportBuffer, uint reportBufferLength);

    /// <summary>GET_REPORT(Input) control transfer, used to probe the reply framing.</summary>
    [DllImport("hid.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool HidD_GetInputReport(nint hidDeviceObject, byte[] reportBuffer, uint reportBufferLength);

    [DllImport("hid.dll", SetLastError = true, CharSet = CharSet.Unicode, EntryPoint = "HidD_GetProductString")]
    internal static extern bool HidD_GetProductString(nint hidDeviceObject, [Out] StringBuilder buffer, int bufferLength);

    [DllImport("hid.dll", SetLastError = true, CharSet = CharSet.Unicode, EntryPoint = "HidD_GetManufacturerString")]
    internal static extern bool HidD_GetManufacturerString(nint hidDeviceObject, [Out] StringBuilder buffer, int bufferLength);

    [DllImport("hid.dll", SetLastError = true, CharSet = CharSet.Unicode, EntryPoint = "HidD_GetSerialNumberString")]
    internal static extern bool HidD_GetSerialNumberString(nint hidDeviceObject, [Out] StringBuilder buffer, int bufferLength);

    [DllImport("setupapi.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern nint SetupDiGetClassDevs(ref Guid classGuid, string? enumerator, nint hwndParent, uint flags);

    [DllImport("setupapi.dll", SetLastError = true)]
    internal static extern bool SetupDiEnumDeviceInterfaces(nint deviceInfoSet, uint deviceInfoData, ref Guid interfaceClassGuid, uint memberIndex, ref SP_DEVICE_INTERFACE_DATA deviceInterfaceData);

    [DllImport("setupapi.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern bool SetupDiGetDeviceInterfaceDetail(nint deviceInfoSet, ref SP_DEVICE_INTERFACE_DATA deviceInterfaceData, nint deviceInterfaceDetailData, uint deviceInterfaceDetailDataSize, out uint requiredSize, nint deviceInfoData);

    [DllImport("setupapi.dll", SetLastError = true)]
    internal static extern bool SetupDiDestroyDeviceInfoList(nint deviceInfoSet);

    [DllImport("kernel32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
    internal static extern nint CreateFile(string fileName, uint desiredAccess, uint shareMode, nint securityAttributes, uint creationDisposition, uint flagsAndAttributes, nint templateFile);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool ReadFile(nint handle, byte[] buffer, uint bytesToRead, out uint bytesRead, nint overlapped);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool WriteFile(nint handle, byte[] buffer, uint bytesToWrite, out uint bytesWritten, nint overlapped);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool DeviceIoControl(nint device, uint controlCode, byte[]? inBuffer, uint inBufferSize, byte[]? outBuffer, uint outBufferSize, out uint bytesReturned, nint overlapped);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool CloseHandle(nint handle);

    [StructLayout(LayoutKind.Sequential)]
    internal struct HIDD_ATTRIBUTES
    {
        public int Size;
        public ushort VendorID;
        public ushort ProductID;
        public ushort VersionNumber;
    }

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern bool CancelIo(nint handle);

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern bool CancelIoEx(nint handle, nint overlapped);

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern uint WaitForSingleObject(nint handle, uint milliseconds);

    /// <summary>WAIT_OBJECT_0 — the wait completed and the object is signalled.</summary>
    internal const uint WaitObject0 = 0x00000000;

    /// <summary>WAIT_TIMEOUT — the deadline elapsed first.</summary>
    internal const uint WaitTimeout = 0x00000102;

    /// <summary>WAIT_FAILED — the handle itself is bad; the wait returns immediately.</summary>
    internal const uint WaitFailed = 0xFFFFFFFF;

    internal static bool IsValidHandle(nint handle) => handle != 0 && handle != INVALID_HANDLE_VALUE;

    /// <summary>Enumerates the device interface paths of every currently attached HID device.</summary>
    internal static IReadOnlyList<string> EnumerateHidPaths()
    {
        var paths = new List<string>();
        HidD_GetHidGuid(out var guid);
        var set = SetupDiGetClassDevs(ref guid, null, 0, DIGCF_PRESENT | DIGCF_DEVICEINTERFACE);
        if (set == nint.Zero || set == -1) return paths;

        try
        {
            var iface = new SP_DEVICE_INTERFACE_DATA { cbSize = Marshal.SizeOf<SP_DEVICE_INTERFACE_DATA>() };
            for (uint index = 0; SetupDiEnumDeviceInterfaces(set, 0, ref guid, index, ref iface); index++)
            {
                SetupDiGetDeviceInterfaceDetail(set, ref iface, 0, 0, out var required, 0);
                if (required == 0) continue;

                var detail = Marshal.AllocHGlobal((int)required);
                try
                {
                    // cbSize must be 6 (x86) / 8 (x64); DevicePath itself starts at byte 4
                    // because SP_DEVICE_INTERFACE_DETAIL_DATA_W is { DWORD cbSize; WCHAR[] DevicePath; }.
                    Marshal.WriteInt32(detail, IntPtr.Size == 8 ? 8 : 6);
                    if (!SetupDiGetDeviceInterfaceDetail(set, ref iface, detail, required, out _, 0)) continue;

                    var path = Marshal.PtrToStringUni(detail + 4);
                    if (string.IsNullOrEmpty(path) || !path.StartsWith(@"\\?\", StringComparison.Ordinal))
                    {
                        // Fall back to the (incorrect but common) 8-byte offset before giving up.
                        var alt = Marshal.PtrToStringUni(detail + 8);
                        if (!string.IsNullOrEmpty(alt) && alt.StartsWith(@"\\?\", StringComparison.Ordinal)) path = alt;
                    }

                    if (!string.IsNullOrEmpty(path)) paths.Add(path);
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

        return paths;
    }

    internal static HIDD_ATTRIBUTES GetAttributes(nint handle)
    {
        var attributes = new HIDD_ATTRIBUTES { Size = Marshal.SizeOf<HIDD_ATTRIBUTES>() };
        if (!HidD_GetAttributes(handle, out attributes)) return default;
        return attributes;
    }

    internal static string GetString(nint handle, bool product)
    {
        var buffer = new StringBuilder(512);
        var ok = product ? HidD_GetProductString(handle, buffer, buffer.Capacity) : HidD_GetManufacturerString(handle, buffer, buffer.Capacity);
        return ok ? buffer.ToString().Trim() : string.Empty;
    }

    /// <summary>
    /// Raw HID report descriptor bytes. HidD_GetReportDescriptor takes no length, so the
    /// caller hands over a zeroed buffer and the item stream is walked to its terminating
    /// top-level collection — HID items are self-delimiting, which makes the true length
    /// recoverable without knowing it up front.
    /// </summary>
    internal static byte[]? GetReportDescriptor(nint handle, int capacity = 4096)
    {
        var buffer = new byte[capacity];
        if (!HidD_GetPreparsedData(handle, out var preparsed) || preparsed == IntPtr.Zero) return null;
        try
        {
            // PHIDP_PREPARSED_DATA: { USHORT cbSize; PVOID pReportDescriptor; ... }
            var descriptor = Marshal.ReadIntPtr(preparsed, IntPtr.Size);
            if (descriptor == IntPtr.Zero) return null;

            Marshal.Copy(descriptor, buffer, 0, capacity);

            // Short-item data sizes for prefix 0b00 (Main), 0b01 (Global), 0b10 (Local).
            ReadOnlySpan<byte> dataSize = stackalloc byte[] { 0, 1, 2, 4 };
            var i = 0;
            while (i < capacity)
            {
                var b = buffer[i];

                // 0xFE introduces a long item (0b11 prefix reserved for variable-length data).
                if (b == 0xFE)
                {
                    if (i + 2 >= capacity) break;
                    var len = (buffer[i + 1] << 8) | buffer[i + 2];
                    i += 3 + len;
                    continue;
                }

                // Any other 0b11 item is top-level (End Collection 0xC0): the stream ends here.
                if ((b >> 6) == 0x03) return buffer.AsSpan(0, i + 1).ToArray();

                i += 1 + dataSize[b >> 6];
            }

            return null;
        }
        finally
        {
            HidD_FreePreparsedData(preparsed);
        }
    }

    internal static nint Open(string path)
    {
        return CreateFile(path, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, 0, OPEN_EXISTING, FILE_FLAG_OVERLAPPED, 0);
    }

    [DllImport("kernel32.dll", SetLastError = true)]
    internal static extern nint CreateEventW(nint attrs, bool manualReset, bool initialState, string? name);

    /// <summary>Win32 OVERLAPPED; hEvent is the only field callers must fill in.</summary>
    [StructLayout(LayoutKind.Sequential)]
    internal struct NativeOverlapped
    {
        public nint Internal;
        public nint InternalHigh;
        public nint Offset;
        public nint OffsetHigh;
        public nint Pointer;   // InternalPointer
        public nint Event;     // hEvent
    }

    internal static nint AllocOverlapped(nint eventHandle)
    {
        var p = Marshal.AllocHGlobal(Marshal.SizeOf<NativeOverlapped>());
        // Everything must be zero except InternalPointer(=Status) and hEvent.
        for (var i = 0; i < Marshal.SizeOf<NativeOverlapped>(); i++) Marshal.WriteByte(p, i, 0);
        Marshal.WriteIntPtr(p, (int)Marshal.OffsetOf<NativeOverlapped>(nameof(NativeOverlapped.Event)), eventHandle);
        return p;
    }

    internal enum HidOutMethod { SetOutputReport, WriteFile, SetReport, SetFeatureReport }

    /// <summary>
    /// One overlapped WriteFile of a fully-formed HID output report buffer
    /// (report id byte first). Returns true only once the driver reports the bytes moved.
    /// </summary>
    internal static bool WriteOutput(nint handle, byte[] buffer, out uint written, out string detail)
    {
        written = 0;
        var ev = CreateEventW(IntPtr.Zero, true, false, null);
        if (ev == IntPtr.Zero) { detail = "CreateEvent failed"; return false; }

        var ov = AllocOverlapped(ev);
        try
        {
            if (!WriteFile(handle, buffer, (uint)buffer.Length, out written, ov))
            {
                var err = Marshal.GetLastWin32Error();
                if (err != ErrorIoPending) { detail = $"WriteFile err {err}"; return false; }

                // Pending: the HID stack signals completion, so wait for it before reading.
                if (WaitForSingleObject(ev, WriteTimeoutMs) != WaitObject0)
                {
                    CancelIoEx(handle, ov);
                    WaitForSingleObject(ev, 2000);
                    detail = "WriteFile timed out";
                    return false;
                }
            }

            // Inline completion never signals the event, so the fields are already final.
            written = OverlappedBytesTransferred(ov);
            if (OverlappedStatus(ov) != 0 || written == 0)
            {
                detail = $"WriteFile incomplete (status=0x{OverlappedStatus(ov):X8}, moved={written})";
                return false;
            }

            detail = $"WriteFile ok {written}B";
            return true;
        }
        finally
        {
            CloseHandle(ev);
            Marshal.FreeHGlobal(ov);
        }
    }

    /// <summary>
    /// DeviceIoControl on a FILE_FLAG_OVERLAPPED handle. Passing a null OVERLAPPED to such a
    /// handle fails outright, which is why the Feature-report path has to go through here.
    /// </summary>
    internal static bool IoControl(nint handle, uint code, byte[] inBuffer, out uint moved, out string detail)
    {
        moved = 0;
        var ev = CreateEventW(IntPtr.Zero, true, false, null);
        if (ev == IntPtr.Zero) { detail = "CreateEvent failed"; return false; }

        var ov = AllocOverlapped(ev);
        try
        {
            var input = inBuffer ?? Array.Empty<byte>();
            if (!DeviceIoControl(handle, code, input, (uint)input.Length, input, (uint)input.Length, out moved, ov))
            {
                var err = Marshal.GetLastWin32Error();
                if (err != ErrorIoPending) { detail = $"ioctl err {err}"; return false; }

                if (WaitForSingleObject(ev, WriteTimeoutMs) != WaitObject0)
                {
                    CancelIoEx(handle, ov);
                    WaitForSingleObject(ev, 2000);
                    detail = $"ioctl 超时 {WriteTimeoutMs}ms";
                    return false;
                }
            }

            var status = OverlappedStatus(ov);
            moved = OverlappedBytesTransferred(ov);
            if (status != 0 || moved == 0)
            {
                detail = $"ioctl 不完整 (status=0x{status:X8}, moved={moved})";
                return false;
            }

            detail = $"ioctl ok {moved}B";
            return true;
        }
        finally
        {
            CloseHandle(ev);
            Marshal.FreeHGlobal(ov);
        }
    }

    /// <summary>
    /// SET_REPORT(Output) with a caller-sized buffer, returned by reference so the caller can
    /// inspect whether the firmware used the IN data stage to answer. HidD_SetOutputReport
    /// hides this: it reports only success/failure and keeps the buffer to itself.
    /// </summary>
    internal static bool SetOutputReportRaw(nint handle, byte[] buffer, out string detail)
    {
        if (HidD_SetOutputReport(handle, buffer, (uint)buffer.Length))
        {
            detail = $"ok len={buffer.Length}";
            return true;
        }

        detail = $"err {Marshal.GetLastWin32Error()} (len={buffer.Length})";
        return false;
    }

    /// <summary>Sends using one specific method (used to characterise a device).</summary>
    internal static bool SendByMethod(nint handle, byte[] payload, byte reportId, HidOutMethod method, out string detail)
    {
        var buffer = new byte[payload.Length + ReportIdLength];
        buffer[0] = reportId;
        Buffer.BlockCopy(payload, 0, buffer, ReportIdLength, payload.Length);

        switch (method)
        {
            case HidOutMethod.SetOutputReport:
                if (HidD_SetOutputReport(handle, buffer, (uint)buffer.Length))
                {
                    detail = $"ok {buffer.Length}B";
                    return true;
                }
                detail = $"err {Marshal.GetLastWin32Error()}";
                return false;

            case HidOutMethod.WriteFile:
                return WriteOutput(handle, buffer, out _, out detail);

            case HidOutMethod.SetReport:
                return IoControl(handle, IOCTL_HID_SET_REPORT, buffer, out _, out detail);

            case HidOutMethod.SetFeatureReport:
                return IoControl(handle, IOCTL_HID_SET_FEATURE_REPORT, buffer, out _, out detail);

            default:
                return WriteOutput(handle, buffer, out _, out detail);
        }
    }

    /// <summary>
    /// Sends one protocol packet the way the official app does — an output report
    /// (SET_REPORT with report type 0x02, report id 0x00) carrying the raw payload.
    /// A Feature-report control transfer is kept as a fallback for firmware revisions
    /// that expose one; every attempt is recorded in <paramref name="detail"/> so the
    /// protocol log shows exactly what the device accepted.
    ///
    /// The whole chain runs on a fenced worker thread: HidD_SetOutputReport takes no
    /// timeout, so firmware that has wedged its control endpoint would otherwise block
    /// the caller forever and freeze the UI.
    /// </summary>
    internal static bool SendPacket(nint handle, byte[] payload, byte reportId, out string detail)
    {
        var buffer = new byte[payload.Length + ReportIdLength];
        buffer[0] = reportId;
        Buffer.BlockCopy(payload, 0, buffer, ReportIdLength, payload.Length);

        var attempts = new List<string>();

        // Primary path: SET_REPORT with report type 0x02 (Output), report id 0x00 —
        // byte-for-byte what the official app sends via controlTransfer(0x21, 0x09, 0x0200).
        if (HidD_SetOutputReport(handle, buffer, (uint)buffer.Length))
        {
            detail = $"SET_REPORT(Output) ok {buffer.Length}B";
            return true;
        }
        attempts.Add($"HidD_SetOutputReport=err{Marshal.GetLastWin32Error()}");

        if (WriteOutput(handle, buffer, out _, out var writeDetail))
        {
            detail = writeDetail + "; " + string.Join(", ", attempts);
            return true;
        }

        attempts.Add("WriteFile=" + writeDetail);

        foreach (var (code, name) in new[]
                 {
                     (IOCTL_HID_SET_REPORT, "IOCTL_HID_SET_REPORT"),
                     (IOCTL_HID_SET_FEATURE_REPORT, "IOCTL_HID_SET_FEATURE_REPORT"),
                 })
        {
            if (DeviceIoControl(handle, code, buffer, (uint)buffer.Length, buffer, (uint)buffer.Length, out var moved, 0))
            {
                detail = $"{name} (ok, {moved} bytes); " + string.Join(", ", attempts);
                return true;
            }

            attempts.Add($"{name}=err{Marshal.GetLastWin32Error()}");
        }

        detail = "全部失败: " + string.Join(", ", attempts);
        return false;
    }

    /// <summary>Deadline for one outgoing report. Healthy firmware answers in well under 1 ms.</summary>
    internal const int SendTimeoutMs = 3000;

    /// <summary>Raised when a send could not complete inside <see cref="SendTimeoutMs"/>.</summary>
    public sealed class SendTimeoutException : IOException
    {
        public SendTimeoutException() : base(
            $"设备在 {SendTimeoutMs}ms 内没有响应 USB 控制请求。固件的控制端点可能已卡死，请重新插拔设备。")
        {
        }
    }

    /// <summary>
    /// <see cref="SendPacket"/> with a hard deadline. A wedged device must surface as an
    /// exception the UI can report, never as a frozen application.
    /// </summary>
    internal static bool SendPacketBounded(nint handle, byte[] payload, byte reportId, out string detail)
    {
        var finished = new ManualResetEventSlim(false);
        var ok = false;
        var result = "未执行";

        var worker = new Thread(() =>
        {
            try
            {
                ok = SendPacket(handle, payload, reportId, out result);
            }
            catch (Exception ex)
            {
                result = $"{ex.GetType().Name}: {ex.Message}";
            }
            finally
            {
                finished.Set();
            }
        })
        {
            IsBackground = true,
            Name = "uac-hid-send",
        };

        worker.Start();
        if (!finished.Wait(SendTimeoutMs))
        {
            detail = $"SET_REPORT(Output) 超时 {SendTimeoutMs}ms — 设备控制端点无响应";
            throw new SendTimeoutException();
        }

        detail = result;
        return ok;
    }

    /// <summary>
    /// Reads a completed OVERLAPPED straight out of memory. The I/O manager publishes the
    /// NTSTATUS in Internal[Internal|InternalHigh] and the transferred byte count in
    /// InternalHigh, so this needs no waiting — GetOverlappedResult is avoided because on
    /// the HID stack it never returns, even with bWait = FALSE.
    /// </summary>
    internal static uint OverlappedBytesTransferred(nint overlapped)
        => (uint)Marshal.ReadIntPtr(overlapped, IntPtr.Size);          // InternalHigh

    internal static uint OverlappedStatus(nint overlapped)
        => (uint)(nint)Marshal.ReadIntPtr(overlapped, 0);              // Internal (low dword)

    /// <summary>
    /// Dumps the raw HIDP_PREPARSED_DATA block so the report-descriptor pointer can be
    /// located without guessing the (undocumented, version-dependent) struct layout.
    /// </summary>
    internal static byte[]? DumpPreparsed(nint handle, int length = 128)
    {
        if (!HidD_GetPreparsedData(handle, out var pp) || pp == IntPtr.Zero) return null;
        try
        {
            var bytes = new byte[length];
            Marshal.Copy(pp, bytes, 0, length);
            return bytes;
        }
        finally { HidD_FreePreparsedData(pp); }
    }

    /// <summary>GET_REPORT(Input) control transfer: polls whatever the device has staged.</summary>
    internal static bool PollInputReport(nint handle, out byte[] data, int bufferSize = 64)
    {
        data = Array.Empty<byte>();
        var buffer = new byte[bufferSize];
        if (!HidD_GetInputReport(handle, buffer, (uint)bufferSize)) return false;
        data = buffer;
        return true;
    }

    /// <summary>Drops everything the host has queued on the input pipe.</summary>
    [DllImport("hid.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool HidD_FlushQueue(nint hidDeviceObject);

    /// <summary>Starts one overlapped input read; returns false only on a hard error.</summary>
    internal static bool BeginRead(nint handle, byte[] buffer, nint overlapped, out bool pending)
    {
        if (ReadFile(handle, buffer, (uint)buffer.Length, out _, overlapped))
        {
            // Completed inline — the completion event is never signalled in this case.
            pending = false;
            return true;
        }

        pending = Marshal.GetLastWin32Error() == ErrorIoPending;
        return pending;
    }

    /// <summary>
    /// One-off input read with a deadline, for probing whether a command drew a reply.
    /// Returns false (and cancels the pending read) when nothing arrives in time.
    /// </summary>
    internal static bool TryRead(nint handle, out byte[] data, int timeoutMs = 800, Action<string>? trace = null)
    {
        data = Array.Empty<byte>();
        var buffer = new byte[256];
        var ev = CreateEventW(IntPtr.Zero, true, false, null);
        var ov = AllocOverlapped(ev);
        try
        {
            trace?.Invoke($"BeginRead(hEvent={ev}, ov={ov})");
            if (!BeginRead(handle, buffer, ov, out var pending)) { trace?.Invoke("BeginRead false, err=" + Marshal.GetLastWin32Error()); return false; }
            trace?.Invoke(pending ? "BeginRead ok (pending)" : "BeginRead ok (inline)");

            if (pending && WaitForSingleObject(ev, (uint)timeoutMs) != WaitObject0)
            {
                trace?.Invoke("no completion within deadline -> CancelIoEx");
                CancelIoEx(handle, ov);
                trace?.Invoke("CancelIoEx=" + Marshal.GetLastWin32Error() + ", drain=" + WaitForSingleObject(ev, 2000));
                return false;
            }

            var moved = OverlappedBytesTransferred(ov);
            var status = OverlappedStatus(ov);
            trace?.Invoke($"overlapped: status=0x{status:X8} moved={moved}");
            if (moved == 0 || status != 0)
            {
                trace?.Invoke("read failed or empty");
                return false;
            }
            data = buffer.AsSpan(0, (int)moved).ToArray();
            trace?.Invoke($"read {data.Length} bytes");
            return true;
        }
        finally
        {
            CloseHandle(ev);
            Marshal.FreeHGlobal(ov);
        }
    }

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    internal static extern bool ResetEvent(nint handle);
}

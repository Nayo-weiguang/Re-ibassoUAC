using System.Collections.Concurrent;
using System.IO;
using System.Runtime.InteropServices;
using iBassoUAC.Interop;

namespace iBassoUAC.Protocol;

/// <summary>
/// A live USB conversation over WinUSB, for firmware that answers inside the IN stage of the
/// SET_REPORT control transfer.
///
/// Mirrors what the official Android app gets from <c>UsbDeviceConnection.controlTransfer</c>:
/// SETUP {bmRequestType=0x21, bRequest=0x09 (SET_REPORT), wValue=0x0200 (Output report, id 0),
/// wIndex=interface number} followed by the firmware's answer. WinUSB performs both stages, so
/// a register read finally returns data — which the HID class driver could never deliver.
/// </summary>
public sealed class WinUsbLink : IDisposable
{
    private const byte BmRequestTypeClassOut = 0x21;   // host->device, class, interface
    private const byte BmRequestSetReport = 0x09;
    private const ushort WValueOutputReport0 = 0x0200; // report type 0x02 (Output), report id 0
    // WinUsb_ControlTransfer's TimeoutMs is documented as "reserved for future use, must be zero";
    // passing a real timeout there faults the call on this stack.
    private const uint ControlTimeoutMs = 0;

    /// <summary>HID report id used by the iBasso firmware (wValue 0x0200 → id 0x00).</summary>
    public byte ReportId { get; set; } = 0x00;

    private readonly nint _device;
    private readonly nint _winUsb;
    private readonly WinUsbApi.UsbInterfaceDescriptor _interface;
    private readonly List<byte> _interruptIn;
    private readonly ConcurrentDictionary<byte, List<TaskCompletionSource<UacResponse>>> _pending = new();
    private readonly CancellationTokenSource _cts = new();
    private readonly Thread _reader;
    private volatile bool _disposed;

    public UsbDeviceInfo? Info { get; }
    public bool IsOpen => !_disposed && WinUsbApi.IsValid(_device);
    public string Path { get; }

    /// <summary>True when the firmware declares an interrupt IN pipe (it does not, on DC03 Pro).</summary>
    public bool HasInterruptIn => _interruptIn.Count > 0;

    /// <summary>Interface number the protocol endpoint lives on.</summary>
    public byte InterfaceNumber => _interface.InterfaceNumber;

    /// <summary>Endpoint count the firmware's interface descriptor declares.</summary>
    public byte EndpointCount => _interface.NumEndpoints;

    /// <summary>Raw WinUSB handles, for probes that issue their own control transfers.</summary>
    public nint DeviceHandle => _device;

    public nint InterfaceHandle => _winUsb;

    /// <summary>Raw interface descriptor as the firmware declares it.</summary>
    internal WinUsbApi.UsbInterfaceDescriptor InterfaceDescriptor => _interface;

    public event Action<UacResponse>? ResponseReceived;
    public event Action<ProtocolLogEntry>? Log;

    public WinUsbLink(string path, UsbDeviceInfo? info = null)
    {
        Path = path;
        Info = info;

        _device = WinUsbApi.Open(path);
        if (!WinUsbApi.IsValid(_device))
        {
            throw new IOException(
                $"打开 WinUSB 设备失败，Win32 = {Marshal.GetLastWin32Error()}。请确认该接口已绑定 WinUSB 驱动。");
        }

        if (!WinUsbApi.WinUsb_Initialize(_device, out _winUsb))
        {
            var err = Marshal.GetLastWin32Error();
            WinUsbApi.CloseHandle(_device);
            throw new IOException($"WinUsb_Initialize 失败，Win32 = {err}。该接口可能仍绑定在 HidUsb 上。");
        }

        // Interface 0 is the control protocol; on this device alternate setting 0 is the only one.
        if (!WinUsbApi.WinUsb_QueryInterfaceSettings(_winUsb, 0, out _interface))
        {
            var err = Marshal.GetLastWin32Error();
            Dispose();
            throw new IOException($"WinUsb_QueryInterfaceSettings 失败，Win32 = {err}");
        }

        _interruptIn = WinUsbApi.InterruptInPipes(_winUsb, _interface.NumEndpoints);

        _reader = new Thread(ReadLoop)
        {
            IsBackground = true,
            Name = "winusb-in-reader",
            Priority = ThreadPriority.AboveNormal,
        };
        _reader.Start();
    }

    // ---------------------------------------------------------------- logging

    public void Trace(string direction, ReadOnlySpan<byte> data, string note = "")
        => Log?.Invoke(new ProtocolLogEntry(DateTime.Now, direction, Convert.ToHexString(data), note));

    // ------------------------------------------------------------- transport

    /// <summary>
    /// Issues one SET_REPORT(Output) control transfer and hands back whatever the firmware
    /// returned in the IN stage. WinUSB overwrites the head of the buffer with the IN data, so
    /// the caller's request is snapshotted before the transfer.
    /// </summary>
    public byte[] Exchange(byte[] payload, out uint transferred, int? bufferSize = null)
    {
        // OUT stage = report id + payload; the trailing slack gives the firmware room to answer.
        var buffer = new byte[Math.Max(payload.Length + 1, bufferSize ?? 64)];
        buffer[0] = ReportId;
        Buffer.BlockCopy(payload, 0, buffer, 1, payload.Length);

        var request = payload.ToArray();
        transferred = WinUsbApi.ControlTransfer(
            _winUsb, BmRequestTypeClassOut, BmRequestSetReport,
            WValueOutputReport0, (ushort)_interface.InterfaceNumber, buffer);

        Trace("TX", request, $"SET_REPORT(Output) moved={transferred} IN={Convert.ToHexString(buffer.AsSpan(0, (int)Math.Min(transferred, (uint)buffer.Length)))}");
        return buffer.AsSpan(0, (int)Math.Min(transferred, (uint)buffer.Length)).ToArray();
    }

    /// <summary>Sends a packet and waits for the matching reply.</summary>
    public async Task<UacResponse> RequestAsync(
        byte[] packet,
        byte expectSeq,
        CancellationToken cancellationToken = default)
    {
        using var cts = CancellationTokenSource.CreateLinkedTokenSource(_cts.Token, cancellationToken);
        var tcs = new TaskCompletionSource<UacResponse>(TaskCreationOptions.RunContinuationsAsynchronously);

        var bucket = _pending.GetOrAdd(expectSeq, _ => new List<TaskCompletionSource<UacResponse>>());
        lock (bucket) bucket.Add(tcs);

        try
        {
            var reply = Exchange(packet, out _);
            if (reply.Length > 0) Dispatch(reply);

            using var timeoutCts = CancellationTokenSource.CreateLinkedTokenSource(cts.Token);
            timeoutCts.CancelAfter(TimeSpan.FromMilliseconds(600));
            try
            {
                return await tcs.Task.WaitAsync(timeoutCts.Token).ConfigureAwait(false);
            }
            catch (OperationCanceledException) when (!cancellationToken.IsCancellationRequested && !_cts.IsCancellationRequested)
            {
                throw new TimeoutException($"No reply for {Cmd.Name(expectSeq)} (0x{expectSeq:X2}).");
            }
        }
        finally
        {
            if (_pending.TryGetValue(expectSeq, out var list))
            {
                lock (list) list.Remove(tcs);
            }
        }
    }

    /// <summary>Fire-and-forget send.</summary>
    public void Send(byte[] packet) => Exchange(packet, out _);

    // ----------------------------------------------------------- reader loop

    private void ReadLoop()
    {
        if (_interruptIn.Count == 0) return; // nothing to poll; replies arrive on the control pipe

        var buffer = new byte[256];
        foreach (var pipe in _interruptIn)
        {
            var ev = WinUsbApi.CreateEventW(IntPtr.Zero, true, false, null);
            var ov = WinUsbApi.AllocOverlapped(ev);
            try
            {
                while (!_disposed && !_cts.IsCancellationRequested)
                {
                    WinUsbApi.ResetEvent(ev);
                    if (!WinUsbApi.WinUsb_InterruptTransfer(_winUsb, pipe, buffer, (uint)buffer.Length, out var moved, ov)
                        && Marshal.GetLastWin32Error() != WinUsbApi.ErrorIoPending)
                    {
                        Thread.Sleep(100);
                        continue;
                    }

                    if (WinUsbApi.WaitForSingleObject(ev, 300) != WinUsbApi.WaitObject0) continue;
                    if (moved > 0) Dispatch(buffer.AsSpan(0, (int)moved).ToArray());
                }
            }
            finally
            {
                WinUsbApi.CloseHandle(ev);
                Marshal.FreeHGlobal(ov);
            }
        }
    }

    private void Dispatch(byte[] report)
    {
        if (report.Length > 1 && report[0] == ReportId) report = report[1..];

        var (seq, payload, isNativeVolume) = ResponseParser.Parse(report);
        Trace("RX", report, $"{Cmd.Name(seq)} payload=[{Convert.ToHexString(payload)}]{(isNativeVolume ? " native-volume" : string.Empty)}");

        var response = new UacResponse(seq, report, payload) { IsNativeVolumeChange = isNativeVolume };

        if (_pending.TryGetValue(seq, out var bucket))
        {
            lock (bucket)
            {
                foreach (var t in bucket) t.TrySetResult(response);
            }
        }

        ResponseReceived?.Invoke(response);
    }

    // --------------------------------------------------------------- teardown

    public void Dispose()
    {
        if (_disposed) return;
        _disposed = true;
        _cts.Cancel();
        foreach (var bucket in _pending.Values)
        {
            lock (bucket) foreach (var t in bucket) t.TrySetCanceled();
        }
        _pending.Clear();
        if (_reader.IsAlive) _reader.Join(500);
        if (WinUsbApi.IsValid(_winUsb)) WinUsbApi.WinUsb_Free(_winUsb);
        if (WinUsbApi.IsValid(_device)) WinUsbApi.CloseHandle(_device);
        _cts.Dispose();
    }
}
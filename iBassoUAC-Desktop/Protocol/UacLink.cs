using System.Collections.Concurrent;
using System.IO;
using System.Runtime.InteropServices;
using iBassoUAC.Interop;

namespace iBassoUAC.Protocol;

/// <summary>
/// One live USB conversation with an iBasso DAC.
///
/// Mirrors com.ibasso.volume.TaskManager: commands go out as HID feature reports,
/// replies (and unsolicited button/volume notifications) arrive on the interrupt
/// IN endpoint and are correlated back by command id.
/// </summary>
public sealed class UacLink : IDisposable
{
    private const int ReadBufferSize = 256;

    /// <summary>HID report id used by the iBasso firmware (wValue 0x0200 → id 0x00).</summary>
    public byte ReportId { get; set; } = 0x00;

    private readonly nint _handle;
    private readonly nint _readEvent;
    private readonly ConcurrentDictionary<byte, List<TaskCompletionSource<UacResponse>>> _pending = new();
    private readonly CancellationTokenSource _cts = new();
    private readonly Thread _reader;
    private volatile bool _disposed;

    public UsbDeviceInfo Info { get; }
    public bool IsOpen => !_disposed && HidApi.IsValidHandle(_handle);

    public event Action<UacResponse>? ResponseReceived;
    public event Action<ProtocolLogEntry>? Log;

    public UacLink(nint handle, UsbDeviceInfo info)
    {
        _handle = handle;
        Info = info;
        _readEvent = CreateEventW(IntPtr.Zero, true, false, null);
        _reader = new Thread(ReadLoop)
        {
            IsBackground = true,
            Name = "uac-hid-reader",
            Priority = ThreadPriority.AboveNormal,
        };
        _reader.Start();
    }

    // ---------------------------------------------------------------- logging

    public void Trace(string direction, ReadOnlySpan<byte> data, string note = "")
    {
        Log?.Invoke(new ProtocolLogEntry(
            DateTime.Now,
            direction,
            Convert.ToHexString(data),
            note));
    }

    // ------------------------------------------------------- packet factories

    /// <summary>Builds the 16-byte register-read packet (TaskManager.sendGetRegisterRequest*).</summary>
    public static byte[] BuildRead(byte seqId, byte op, byte deviceAddress, ReadOnlySpan<byte> offset)
    {
        var p = new byte[16];
        p[0] = seqId;
        p[1] = Cmd.SetDigitalFilter2;           // 0x12 marks a register read
        p[2] = op;
        p[3] = deviceAddress;

        switch (op)
        {
            case RegOp.Read4ByteOffset:
                p[6] = 4;
                p[7] = offset[0];
                p[8] = offset[1];
                p[9] = offset[2];
                p[10] = offset[3];
                p[11] = 0xFF;
                break;
            case RegOp.Read2ByteOffset:
                p[4] = offset[0];
                p[5] = offset[1];
                p[6] = 1;
                break;
            default: // Read1ByteOffset
                p[4] = offset[0];
                p[6] = 1;
                break;
        }

        return p;
    }

    /// <summary>Builds the 16-byte register-write packet (TaskManager.writeRegister*).</summary>
    public static byte[] BuildWrite(byte seqId, byte op, byte deviceAddress, ReadOnlySpan<byte> offset, byte value)
    {
        var p = new byte[16];
        p[0] = seqId;
        p[1] = Cmd.SetDigitalFilter1;           // 0x11 marks a register write
        p[2] = op;
        p[3] = deviceAddress;

        switch (op)
        {
            case RegOp.Write4ByteOffset:
                p[6] = 5;
                p[7] = offset[0];
                p[8] = offset[1];
                p[9] = offset[2];
                p[10] = offset[3];
                p[11] = value;
                break;
            case RegOp.Write2ByteOffset:
                p[4] = offset[0];
                p[5] = offset[1];
                p[6] = 1;
                p[7] = value;
                break;
            default: // Write1ByteOffset
                p[4] = offset[0];
                p[6] = 1;
                p[7] = value;
                break;
        }

        return p;
    }

    /// <summary>Builds the 64-byte "custom" packet used by D18 / Macchiato / DC04U firmware.</summary>
    public static byte[] BuildCustom64(byte command, byte group, byte direction, params byte[] payload)
    {
        var p = new byte[64];
        p[0] = Cmd.GetDc03ProDigitalFilter1;     // 0x4B
        p[1] = direction;                         // 0x01 write, 0x80 read (D18 uses 0x00)
        p[2] = 0x3F;                              // okio Utf8.REPLACEMENT_BYTE
        p[3] = 8;
        p[4] = command;
        p[5] = group;
        for (var i = 0; i < payload.Length && i + 6 < p.Length; i++) p[6 + i] = payload[i];
        return p;
    }

    // ------------------------------------------------------------- transport

    private void SendPacket(ReadOnlySpan<byte> payload)
    {
        if (!HidApi.SendPacketBounded(_handle, payload.ToArray(), ReportId, out var method))
        {
            throw new IOException($"发送失败: {method}");
        }

        Trace("TX", payload, $"{Cmd.Describe(payload)} via {method}");
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
            SendPacket(packet);

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

    /// <summary>Fire-and-forget send (used for writes the device does not acknowledge).</summary>
    public void Send(byte[] packet) => SendPacket(packet);

    // ----------------------------------------------------------- reader loop

    private void ReadLoop()
    {
        var buffer = new byte[ReadBufferSize];
        var overlapped = HidApi.AllocOverlapped(_readEvent);
        var errorReported = false;

        // Exactly one read is kept outstanding at a time: re-issuing ReadFile on an
        // OVERLAPPED whose previous request has not completed corrupts the driver state.
        try
        {
            while (!_disposed && !_cts.IsCancellationRequested)
            {
                HidApi.ResetEvent(_readEvent);
                if (!HidApi.BeginRead(_handle, buffer, overlapped, out var pending))
                {
                    if (_disposed) break;
                    if (!errorReported)
                    {
                        errorReported = true;
                        Trace("RX-ERR", ReadOnlySpan<byte>.Empty, $"ReadFile error {Marshal.GetLastWin32Error()}");
                    }

                    Thread.Sleep(100);
                    continue;
                }

                if (pending)
                {
                    // Poll in slices so shutdown stays responsive while no report is arriving.
                    var cancelled = false;
                    while (HidApi.WaitForSingleObject(_readEvent, 300) != HidApi.WaitObject0)
                    {
                        if (_disposed || _cts.IsCancellationRequested)
                        {
                            HidApi.CancelIoEx(_handle, overlapped);
                            HidApi.WaitForSingleObject(_readEvent, 2000);
                            cancelled = true;
                            break;
                        }
                    }

                    if (cancelled) break;
                }

                var moved = HidApi.OverlappedBytesTransferred(overlapped);
                var status = HidApi.OverlappedStatus(overlapped);
                if (status != 0 || moved <= 0)
                {
                    if (!errorReported)
                    {
                        errorReported = true;
                        Trace("RX-ERR", ReadOnlySpan<byte>.Empty, $"ReadFile status=0x{status:X8} moved={moved}");
                    }

                    Thread.Sleep(20);
                    continue;
                }

                errorReported = false;
                var report = buffer.AsSpan(0, (int)moved).ToArray();
                if (report.Length > 1 && report[0] == ReportId) report = report[1..];
                Dispatch(report);
            }
        }
        finally
        {
            Marshal.FreeHGlobal(overlapped);
        }
    }

    private void Dispatch(byte[] report)
    {
        var (seq, payload, isNativeVolume) = ResponseParser.Parse(report);
        Trace("RX", report, $"{Cmd.Name(seq)} payload=[{Convert.ToHexString(payload)}]{(isNativeVolume ? " native-volume" : string.Empty)}");

        var response = new UacResponse(seq, report, payload) { IsNativeVolumeChange = isNativeVolume };

        if (_pending.TryGetValue(seq, out var bucket))
        {
            lock (bucket)
            {
                foreach (var tcs in bucket) tcs.TrySetResult(response);
            }
        }

        ResponseReceived?.Invoke(response);
    }

    // ---------------------------------------------------------------- teardown

    public void Dispose()
    {
        if (_disposed) return;
        _disposed = true;
        _cts.Cancel();
        try { HidApi.CancelIoEx(_handle, IntPtr.Zero); } catch { /* best effort */ }
        foreach (var bucket in _pending.Values)
        {
            lock (bucket) foreach (var tcs in bucket) tcs.TrySetCanceled();
        }
        _pending.Clear();
        if (_reader.IsAlive) _reader.Join(500);
        if (_readEvent != IntPtr.Zero) CloseHandle(_readEvent);
        if (HidApi.IsValidHandle(_handle)) CloseHandle(_handle);
        _cts.Dispose();
    }

    // ------------------------------------------------------------- native P/Invoke

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern nint CreateEventW(nint attrs, bool manualReset, bool initialState, string? name);

    [DllImport("kernel32.dll", SetLastError = true)]
    private static extern bool CloseHandle(nint handle);
}

public sealed record ProtocolLogEntry(DateTime Time, string Direction, string Hex, string Note);

/// <summary>Turns a raw interrupt-IN report into (commandId, payload).</summary>
public static class ResponseParser
{
    public static (byte Seq, byte[] Payload, bool NativeVolume) Parse(byte[] r)
    {
        // Unsolicited "volume changed on the device" notification.
        if (r.Length > 9 && r[4] == 0xFE && r[5] == 0x01)
        {
            return (Cmd.NativeReportVolume, new[] { r[8], r[9] }, true);
        }

        // 64-byte firmware (D18 family).
        if (r.Length == 64)
        {
            var seq = (r[0] == Cmd.GetDc03ProDigitalFilter1 && r[1] == 0x80) ? r[4] : r[5];
            return (seq, r, false);
        }

        // Legacy 8-byte firmware.
        if (r.Length == 8)
        {
            return (r[0], r.Skip(1).ToArray(), false);
        }

        // Standard register replies: [6]=cmd [7]=len [8..]=data
        if (r.Length > 8)
        {
            var seq = r[6];
            var len = r[7];
            var take = (int)Math.Clamp(len, 0, r.Length - 8);
            return (seq, r.Skip(8).Take(take).ToArray(), false);
        }

        return (0, Array.Empty<byte>(), false);
    }
}


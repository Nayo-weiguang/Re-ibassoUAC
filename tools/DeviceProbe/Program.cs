using System.Runtime.InteropServices;
using System.Text;
using iBassoUAC.Devices;
using iBassoUAC.Interop;
using iBassoUAC.Protocol;

namespace iBassoUAC.DeviceProbe;

/// <summary>Live hardware probe: enumerate, identify, and exercise one device.</summary>
internal static class Program
{
    [StructLayout(LayoutKind.Explicit, Size = 12)]
    private struct WinUsbPipeInfo
    {
        [FieldOffset(0)] public int PipeType;
        [FieldOffset(4)] public byte PipeId;
        [FieldOffset(6)] public ushort MaximumPacketSize;
        [FieldOffset(8)] public byte Interval;
    }

    [DllImport("WinUSB.dll", EntryPoint = "WinUsb_QueryPipe", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool WinUsb_QueryPipeNative(nint interfaceHandle, byte altInterface, byte pipeIndex, out WinUsbPipeInfo pipe);

    [DllImport("WinUSB.dll", EntryPoint = "WinUsb_ControlTransfer", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool WinUsb_ControlTransfer_Ulong(nint interfaceHandle, ulong setupPacket, nint buffer, uint bufferLength, out uint lengthTransferred, nint overlapped);

    [DllImport("WinUSB.dll", EntryPoint = "WinUsb_ControlTransfer", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool WinUsb_ControlTransfer_Ref(nint interfaceHandle, in WinUsbApi.WinUsbSetupPacket setupPacket, nint buffer, uint bufferLength, out uint lengthTransferred, nint overlapped);

    [DllImport("WinUSB.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool WinUsb_SetPipePolicy(nint interfaceHandle, byte pipeId, uint policyType, uint valueLength, ref uint value);

    [DllImport("kernel32.dll", SetLastError = true, CharSet = CharSet.Auto)]
    private static extern nint CreateFile(string fileName, uint desiredAccess, uint shareMode, nint securityAttributes, uint creationDisposition, uint flagsAndAttributes, nint templateFile);
    private static string HidItemName(byte b) => b switch
    {
        0x05 => "Usage_Page",
        0x09 => "Usage",
        0x0A => "Collection",
        0x0B => "SubCollection",
        0x0C => "EndCollection",
        0x15 => "Logical_Min",
        0x16 => "Logical_Max",
        0x19 => "Usage_Min",
        0x1A => "Usage_Max",
        0x25 => "Design_Max",
        0x29 => "Design_Max_Physical",
        0x75 => "Report_Size",
        0x76 => "Report_ID",
        0x77 => "Report_Count",
        0x7E => "Unit_Exponent",
        0x81 => "Input",
        0x91 => "Output",
        0xA1 => "Feature",
        0xB0 => "Push",
        0xB1 => "Pop",
        0xC0 => "End_Collection",
        _ => "",
    };
    /// <summary>Pads explicit head bytes out to the firmware's fixed 16-byte frame.</summary>
    private static byte[] P(params byte[] head)
    {
        var p = new byte[16];
        for (var i = 0; i < head.Length && i < 16; i++) p[i] = head[i];
        return p;
    }

    /// <summary>Isolated single-operation runs so a hang can be attributed precisely.</summary>
    private static async Task<int> RunStep(string step, int args2 = -1)
    {
        // Once MI_00 is rebound to WinUSB it stops publishing the HID interface class, so the HID-based
        // lookup below can no longer find it. The WinUSB-only steps must still be able to run.
        var winUsbOnly = step is "winusb" or "wdiag" or "nodes" or "wctl" or "wstd" or "wmin" or "wread" or "wlisten" or "wlisten16" or "wa0" or "wtest" or "wreset" or "cdiag";

        var device = DeviceEnumerator.Enumerate().FirstOrDefault(d => ProductCatalog.Match(d) is not null);
        if (device is null && !winUsbOnly) { Console.WriteLine("no iBasso device"); return 1; }

        var handle = nint.Zero;
        if (device is not null)
        {
            Console.WriteLine($"device: {device.DisplayName} {device.VidPid}");
            handle = HidApi.Open(device.Path);
            if (!HidApi.IsValidHandle(handle)) { Console.WriteLine("open failed"); return 2; }
        }

        if (step == "flush")
        {
            Console.WriteLine("step: 尝试冲开卡住的 HID 管道");
            var probe = new byte[17];
            probe[0] = 0x00;
            Array.Copy(P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01), 0, probe, 1, 16);

            for (var attempt = 1; attempt <= 3; attempt++)
            {
                Console.WriteLine($"  第 {attempt} 次: HidD_FlushQueue={HidApi.HidD_FlushQueue(handle)}");
                var sw = System.Diagnostics.Stopwatch.StartNew();
                var worker = Task.Run(() => HidApi.HidD_SetOutputReport(handle, probe, (uint)probe.Length)
                    ? "ok" : "err " + System.Runtime.InteropServices.Marshal.GetLastWin32Error());
                var done = await Task.WhenAny(worker, Task.Delay(4000)) == worker;
                sw.Stop();
                Console.WriteLine(done
                    ? $"    SetOutputReport -> {await worker}  [{sw.ElapsedMilliseconds} ms]"
                    : $"    SetOutputReport -> *** 仍阻塞 >4000 ms ***");
                if (done && await worker == "ok") { Console.WriteLine("  设备已恢复"); break; }
                await Task.Delay(1000);
            }

            HidApi.CloseHandle(handle);
            return 0;
        }

        if (step == "bindwinusb" || step == "unbindhid")
        {
            var wantWinUsb = step == "bindwinusb";
            var logPath = Path.Combine(Path.GetTempPath(), "ibasso-bind.log");
            var isElevatedChild = args2 == -999;   // internal marker: we were relaunched

            if (!WinUsbApi.IsAdministrator() && !isElevatedChild)
            {
                Console.WriteLine("需要管理员权限，正在请求提权，请在 UAC 弹窗点「是」…");
                var nodes = WinUsbApi.DescribeUsbNodes(0x262A, 0x187E);
                Console.WriteLine($"扫描到 {nodes.Count} 个 VID_262A&PID_187E 设备节点：");
                foreach (var n in nodes) Console.WriteLine("  " + n);
                try
                {
                    var forwarded = new List<string> { $"--step={step}", "--volume=-999" };
                    var code = WinUsbApi.RelaunchElevated(forwarded.ToArray());

                    if (File.Exists(logPath))
                    {
                        Console.WriteLine(File.ReadAllText(logPath, Encoding.UTF8));
                        File.Delete(logPath);
                    }
                    else
                    {
                        Console.WriteLine($"(提权进程未留下日志，子进程退出码 = {code}，可能被取消或启动失败。)");
                    }

                    return code;
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"提权失败：{ex.Message}");
                    return 5;
                }
            }

            // Accumulate into one report so nothing is lost to a later overwrite; flush as we go so the
            // parent can still read progress if this process dies mid-way.
            var report = new StringBuilder();
            void Mark(string text)
            {
                report.AppendLine(text);
                try { File.WriteAllText(logPath, report.ToString(), new UTF8Encoding(false)); } catch { /* best effort */ }
                Console.WriteLine(text);
            }

            var privErr = WinUsbApi.EnableLoadDriverPrivilege();
            Mark($"提权子进程已启动：IsAdministrator={WinUsbApi.IsAdministrator()}，IsUserAnAdmin={WinUsbApi.IsUserAnAdmin()}，启用 SeLoadDriverPrivilege 返回={privErr}" + (privErr == 0 ? "（成功）" : $"（失败 Win32={privErr}）"));

            var ok = false;
            try
            {
                var hwid = WinUsbApi.ResolveInstanceId(0x262A, 0x187E, 0);
                Mark($"目标实例 ID = {(hwid.Length == 0 ? "<未找到>" : hwid)}");

                if (hwid.Length == 0)
                {
                    Mark("在设备树中找不到 MI_00 接口节点。");
                    return 6;
                }

                // Control: push the driver this node is already running. If that also fails the
                // problem is the call or the instance id, not winusb.inf.
                Mark("实例 ID 矩阵（每项都用 winusb.inf [WINUSB.NT] flags=0）：");
                var current = hwid;
                var tried = new List<string> { current, $"USB\\VID_262A&PID_187E&MI_00" };
                foreach (var id in WinUsbApi.CandidateInstanceIds(0x262A, 0x187E))
                {
                    if (!id.Contains("&MI_00", StringComparison.OrdinalIgnoreCase)) continue;
                    tried.Add(id);
                }

                foreach (var id in tried.Distinct())
                {
                    var (idOk, idErr) = WinUsbApi.TryUpdateDriver(id, WinUsbApi.WinUsbInf, "WINUSB.NT", 0);
                    var tag = id == current ? "（当前 MI_00 节点）" : id.Contains("HID\\") ? "（HID 子节点）" : id == "USB\\VID_262A&PID_187E&MI_00" ? "（无尾缀）" : "";
                    Mark($"    {(idOk ? "成功" : $"失败 Win32={idErr}"),-18} {id}{tag}");
                    if (idOk)
                    {
                        ok = true;
                        Mark($"绑定成功：{id} → {WinUsbApi.WinUsbInf} [WINUSB.NT]");
                        Mark("请拔插一次 USB 让驱动完全生效。");
                        return 0;
                    }
                }

                Mark("尝试矩阵（INF 路径 × installSection × flags）：");
                foreach (var inf in WinUsbApi.WinUsbInfCandidates())
                foreach (var section in new[] { "WINUSB.NT", "WinUSB.NT", "WINUSB.NT.HW", "WINUSB.NT.Services" })
                foreach (var flags in new uint[] { 0, 1, 2 })
                {
                    var (attOk, win32) = WinUsbApi.TryUpdateDriver(hwid, inf, section, flags);
                    Mark($"    [{System.IO.Path.GetFileName(System.IO.Path.GetDirectoryName(inf) ?? "-"),-28}] [{section,-18}] flags={flags} -> {(attOk ? "成功" : $"失败 Win32={win32}")}");
                    if (attOk)
                    {
                        ok = true;
                        Mark($"绑定成功：{hwid}");
                        Mark($"    → {inf} [{section}] flags={flags}");
                        Mark("请拔插一次 USB 让驱动完全生效。");
                        return 0;
                    }
                }

                Mark("所有组合都失败。");
            }
            catch (Exception ex)
            {
                Mark($"驱动绑定过程抛出异常：{ex.GetType().Name}: {ex.Message}\r\n{ex.StackTrace}");
            }

            return ok ? 0 : 6;
        }

        if (step == "wread")
        {
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine("发请求 -> 从固件声明的中断 IN 端点收回复");
            foreach (var line in WinUsbApi.RequestThenDrainEndpoints(
                target.Path,
                P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01),
                new byte[64],
                1000))
            {
                Console.WriteLine(line);
            }

            return 0;
        }

        if (step is "wlisten" or "wlisten16")
        {
            var seconds = args2 > 0 ? args2 : 20;
            var prefix = step != "wlisten16";
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"按官方 App 的模型常驻监听 {seconds} 秒（数据阶段 {(prefix ? "17 字节含 report-id" : "16 字节不含 report-id")}）");
            Console.WriteLine("请现在用音量 +/− 按键或旋钮操作设备…");
            foreach (var line in WinUsbApi.ContinuousDrain(
                target.Path, P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01), seconds, 2000, prefix))
            {
                Console.WriteLine(line);
            }

            return 0;
        }

        if (step == "wmin")
        {
            var len = args2 > 0 ? args2 : 2;
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine("最小路径：CreateFile -> WinUsb_Initialize -> GET_STATUS（不查询任何描述符）");
            Console.WriteLine(WinUsbApi.MinimalControlProbe(target.Path, 0x00, 0x00, 0, 0, len));
            return 0;
        }

        if (step == "wstd")
        {
            // A transfer every USB device must answer: GET_STATUS on interface 0. If this faults too,
            // the problem is not our SET_REPORT semantics.
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            using var wlink = new WinUsbLink(target.Path);
            var buf = new byte[2];
            Console.WriteLine("GET_STATUS  bmRequestType=0x00 bRequest=0x00 wValue=0 wIndex=0 len=2");
            try
            {
                var moved = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x00, 0x00, 0, 0, buf);
                Console.WriteLine($"成功：传输长度={moved}  回包={Convert.ToHexString(buf)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"失败：{ex.GetType().Name}: {ex.Message}");
            }

            return 0;
        }

        if (step == "wa0")
        {
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"打开 WinUSB 接口: {target.Path}");
            using var wlink = new WinUsbLink(target.Path);
            Console.WriteLine($"WinUsb_Initialize 成功；interfaceNumber={wlink.InterfaceNumber}，bNumEndpoints={wlink.EndpointCount}，中断IN={wlink.HasInterruptIn}");

            void ProbeControl(string tag, byte reqType, byte req, ushort val, ushort idx, int len)
            {
                var buf = new byte[len];
                Console.Write($"[{tag,-16}] bmReqType=0x{reqType:X2} bReq=0x{req:X2} wVal=0x{val:X4} wIdx=0x{idx:X4} len={len} -> ");
                try
                {
                    var moved = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, reqType, req, val, idx, buf);
                    short val16 = (short)(buf[0] | (buf.Length > 1 ? buf[1] << 8 : 0));
                    Console.WriteLine($"【成功】moved={moved} hex={Convert.ToHexString(buf)} (int16_le={val16})");
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"【失败】{ex.Message}");
                }
            }

            Console.WriteLine("\n--- 1. 官方 APK 基类 UsbDeviceController 原样参数实测 ---");
            ProbeControl("App Left Vol",   0xA0, 0x01, 0x0201, 0x0A01, 2);
            ProbeControl("App Right Vol",  0xA0, 0x01, 0x0202, 0x0A01, 2);
            ProbeControl("App Master Vol", 0xA0, 0x01, 0x0200, 0x0A01, 2);

            Console.WriteLine("\n--- 2. wIndex 实体/接口号穷举变体 (wValue=0x0201) ---");
            ushort[] candidateIndices = { 0x0A00, 0x0101, 0x0100, 0x0001, 0x0000, 0x0201, 0x0200, 0x0501, 0x0500, 0x0D01, 0x0D00 };
            foreach (var idx in candidateIndices)
            {
                ProbeControl($"idx=0x{idx:X4}", 0xA0, 0x01, 0x0201, idx, 2);
            }

            Console.WriteLine("\n--- 3. bmRequestType 接收者/类型穷举变体 (wValue=0x0201, wIndex=0x0A01) ---");
            byte[] candidateReqTypes = { 0xA1, 0xA2, 0xC0, 0xC1, 0xC2, 0x80, 0x81, 0x82 };
            foreach (var rt in candidateReqTypes)
            {
                ProbeControl($"rt=0x{rt:X2}", rt, 0x01, 0x0201, 0x0A01, 2);
                if (rt == 0xA1 || rt == 0xC1)
                {
                    ProbeControl($"rt=0x{rt:X2} if0", rt, 0x01, 0x0201, 0x0000, 2);
                    ProbeControl($"rt=0x{rt:X2} if1", rt, 0x01, 0x0201, 0x0001, 2);
                }
            }

            Console.WriteLine("\n--- 4. bRequest UAC/HID 穷举 (bmReqType=0xA0, wValue=0x0201, wIndex=0x0A01) ---");
            byte[] candidateReqs = { 0x02, 0x03, 0x04, 0x81, 0x82, 0x83 };
            foreach (var r in candidateReqs)
            {
                ProbeControl($"req=0x{r:X2}", 0xA0, r, 0x0201, 0x0A01, 2);
            }

            Console.WriteLine("\n--- 5. 对照检查：标准 GET_STATUS (bmReqType=0x80/0x81) ---");
            ProbeControl("GET_STATUS Dev", 0x80, 0x00, 0x0000, 0x0000, 2);
            ProbeControl("GET_STATUS If0", 0x81, 0x00, 0x0000, 0x0000, 2);

            return 0;
        }

        if (step == "wreset")
        {
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"打开 WinUSB 接口: {target.Path}");
            using var wlink = new WinUsbLink(target.Path);
            Console.WriteLine("正在尝试 WinUsb_ResetDevice...");
            var ok = WinUsbApi.WinUsb_ResetDevice(wlink.InterfaceHandle);
            Console.WriteLine($"WinUsb_ResetDevice 返回: {ok}, Win32={Marshal.GetLastWin32Error()}");
            return ok ? 0 : 1;
        }

        if (step == "wtest")
        {
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"打开 WinUSB 接口: {target.Path}");
            using var wlink = new WinUsbLink(target.Path);
            Console.WriteLine($"WinUsb_Initialize 成功；interfaceNumber={wlink.InterfaceNumber}，bNumEndpoints={wlink.EndpointCount}");

            uint Transfer(byte reqType, byte req, ushort val, ushort idx, byte[] data, out string hex, out short int16)
            {
                var moved = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, reqType, req, val, idx, data);
                hex = Convert.ToHexString(data.AsSpan(0, (int)Math.Min(moved, (uint)data.Length)));
                int16 = (short)(data[0] | (data.Length > 1 ? data[1] << 8 : 0));
                return moved;
            }

            void ReadVol(string label)
            {
                var bufL = new byte[2];
                var bufR = new byte[2];
                var bufM = new byte[2];
                try
                {
                    Transfer(0xA0, 0x01, 0x0201, 0x0A01, bufL, out var hexL, out var sL);
                    Transfer(0xA0, 0x01, 0x0202, 0x0A01, bufR, out var hexR, out var sR);
                    Transfer(0xA0, 0x01, 0x0200, 0x0A01, bufM, out var hexM, out var sM);
                    Console.WriteLine($"  [{label}] L={hexL}({sL,6}, {sL / 256.0:F1}dB)  R={hexR}({sR,6}, {sR / 256.0:F1}dB)  M={hexM}({sM,6}, {sM / 256.0:F1}dB)");
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"  [{label}] 读取失败: {ex.Message}");
                }
            }

            Console.WriteLine("\n=== 1. 读取当前音量基线 (0xA0 控制读) ===");
            ReadVol("基线音量");

            Console.WriteLine("\n=== 2. UAC 同步控制写 (0x20) 闭环测试 ===");
            // 写入 -15 dB (0xF100 = 61696，小端字节 [0x00, 0xF1])
            Console.WriteLine("-> 尝试写入 -15 dB (0xF100) 到左右声道...");
            var set15 = new byte[] { 0x00, 0xF1 };
            try
            {
                var movedR = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0202, 0x0A01, (byte[])set15.Clone());
                Console.WriteLine($"  写 R (0x0202): 成功, moved={movedR}");
            }
            catch (Exception ex) { Console.WriteLine($"  写 R 失败: {ex.Message}"); }

            try
            {
                var movedL = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0201, 0x0A01, (byte[])set15.Clone());
                Console.WriteLine($"  写 L (0x0201): 成功, moved={movedL}");
            }
            catch (Exception ex) { Console.WriteLine($"  写 L 失败: {ex.Message}"); }

            await Task.Delay(100);
            ReadVol("写 -15dB 后回读");

            // 写入 -30 dB (0xE200 = 57856，小端字节 [0x00, 0xE2])
            Console.WriteLine("\n-> 尝试写入 -30 dB (0xE200) 到左右声道...");
            var set30 = new byte[] { 0x00, 0xE2 };
            try
            {
                var movedR = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0202, 0x0A01, (byte[])set30.Clone());
                var movedL = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0201, 0x0A01, (byte[])set30.Clone());
                Console.WriteLine($"  写入命令完成: R_moved={movedR}, L_moved={movedL}");
            }
            catch (Exception ex) { Console.WriteLine($"  写入失败: {ex.Message}"); }

            await Task.Delay(100);
            ReadVol("写 -30dB 后回读");

            // 恢复音量到 0 dB (0x0000)
            Console.WriteLine("\n-> 恢复写入 0 dB (0x0000) 到左右声道...");
            var set0 = new byte[] { 0x00, 0x00 };
            try
            {
                var movedR = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0202, 0x0A01, (byte[])set0.Clone());
                var movedL = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x20, 0x01, 0x0201, 0x0A01, (byte[])set0.Clone());
                Console.WriteLine($"  恢复写入命令完成: R_moved={movedR}, L_moved={movedL}");
            }
            catch (Exception ex) { Console.WriteLine($"  恢复写入失败: {ex.Message}"); }

            await Task.Delay(100);
            ReadVol("恢复 0dB 后回读");

            Console.WriteLine("\n=== 3. bmRequestType=0x21 控制写对比测试 ===");
            try
            {
                var moved21 = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x21, 0x01, 0x0201, 0x0A01, (byte[])set15.Clone());
                Console.WriteLine($"  bmRequestType=0x21, wIndex=0x0A01: 成功, moved={moved21}");
            }
            catch (Exception ex) { Console.WriteLine($"  bmRequestType=0x21, wIndex=0x0A01: 失败 ({ex.Message})"); }

            try
            {
                var moved21_0 = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x21, 0x01, 0x0201, 0x0001, (byte[])set15.Clone());
                Console.WriteLine($"  bmRequestType=0x21, wIndex=0x0001: 成功, moved={moved21_0}");
            }
            catch (Exception ex) { Console.WriteLine($"  bmRequestType=0x21, wIndex=0x0001: 失败 ({ex.Message})"); }

            Console.WriteLine("\n=== 4. 16 字节 HID SET_REPORT 下发与 0xA0 关联测试 ===");
            byte[][] testPackets = {
                P(0x01, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x02), // 滤波模式 2 (Fast Roll-off)
                P(0x01, 0x01, 0x41, 0x09, 0x00, 0x01, 0x00, 0x37), // 音量 L 寄存器包 (55档=0x37)
                P(0x01, 0x03, 0x4B, 0x09, 0x00, 0x01, 0x00, 0x37), // 音量 R 寄存器包 (55档=0x37)
            };

            for (int i = 0; i < testPackets.Length; i++)
            {
                var pkt = testPackets[i];
                Console.WriteLine($"\n-> 下发报文 #{i+1}: {Convert.ToHexString(pkt)}");
                try
                {
                    var movedHid = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0x21, 0x09, 0x0200, 0x0000, (byte[])pkt.Clone());
                    Console.WriteLine($"  SET_REPORT 16字节: 成功, moved={movedHid}");
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"  SET_REPORT 16字节: 失败 ({ex.Message})");
                }
                await Task.Delay(50);
                ReadVol($"下发报文 #{i+1} 后 0xA0 回读");
            }

            Console.WriteLine("\n=== 5. 管道信息与端点精细诊断 ===");
            try
            {
                var pipeCount = wlink.EndpointCount;
                Console.WriteLine($"  接口声明端点数: {pipeCount}");
                for (byte pIdx = 0; pIdx < Math.Max((byte)1, pipeCount); pIdx++)
                {
                    if (WinUsb_QueryPipeNative(wlink.InterfaceHandle, 0, pIdx, out var pInfo))
                    {
                        var pType = pInfo.PipeType switch
                        {
                            0 => "Control",
                            1 => "Isochronous",
                            2 => "Bulk",
                            3 => "Interrupt",
                            _ => $"Unknown({pInfo.PipeType})"
                        };
                        var pDir = (pInfo.PipeId & 0x80) != 0 ? "IN" : "OUT";
                        Console.WriteLine($"  Pipe[{pIdx}]: Type={pType} Id=0x{pInfo.PipeId:X2} (Dir={pDir}) MaxPacket={pInfo.MaximumPacketSize} Interval={pInfo.Interval}");
                    }
                    else
                    {
                        Console.WriteLine($"  Pipe[{pIdx}]: WinUsb_QueryPipe 失败, Win32={Marshal.GetLastWin32Error()}");
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"  管道查询异常: {ex.Message}");
            }

            Console.WriteLine("\n=== 6. UAC 静音属性 (Mute) 控制读测试 ===");
            try
            {
                var bufMute = new byte[1];
                var movedMute1 = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0xA0, 0x01, 0x0100, 0x0101, bufMute);
                Console.WriteLine($"  Mute (wVal=0x0100, wIdx=0x0101): moved={movedMute1}, val=0x{bufMute[0]:X2}");
            }
            catch (Exception ex) { Console.WriteLine($"  Mute wIdx=0x0101 失败: {ex.Message}"); }

            try
            {
                var bufMuteA = new byte[1];
                var movedMuteA = WinUsbApi.ControlTransfer(wlink.InterfaceHandle, 0xA0, 0x01, 0x0100, 0x0A01, bufMuteA);
                Console.WriteLine($"  Mute (wVal=0x0100, wIdx=0x0A01): moved={movedMuteA}, val=0x{bufMuteA[0]:X2}");
            }
            catch (Exception ex) { Console.WriteLine($"  Mute wIdx=0x0A01 失败: {ex.Message}"); }

            return 0;
        }

        if (step == "cdiag")
        {
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"找到目标接口: {target.Path}");

            const uint GENERIC_READ = 0x80000000;
            const uint GENERIC_WRITE = 0x40000000;
            const uint FILE_SHARE_READ = 1;
            const uint FILE_SHARE_WRITE = 2;
            const uint OPEN_EXISTING = 3;
            const uint FILE_FLAG_OVERLAPPED = 0x40000000;

            void RunMode(string modeName, uint flags)
            {
                Console.WriteLine($"\n================== 模式: {modeName} (flags=0x{flags:X8}) ==================");
                var fileHandle = CreateFile(target.Path, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, 0, OPEN_EXISTING, flags, 0);
                if (fileHandle == nint.Zero || fileHandle == (nint)(-1))
                {
                    Console.WriteLine($"CreateFile 失败, Win32={Marshal.GetLastWin32Error()}");
                    return;
                }

                try
                {
                    if (!WinUsbApi.WinUsb_Initialize(fileHandle, out var winUsb))
                    {
                        Console.WriteLine($"WinUsb_Initialize 失败, Win32={Marshal.GetLastWin32Error()}");
                        return;
                    }

                    try
                    {
                        uint timeout = 1000;
                        var policyOk = WinUsb_SetPipePolicy(winUsb, 0x00, 3, 4, ref timeout);
                        Console.WriteLine($"设置 PIPE_TRANSFER_TIMEOUT(ep=0, 1000ms): {policyOk}, Win32={Marshal.GetLastWin32Error()}");

                        void TestProbe(string label, byte reqType, byte req, ushort val, ushort idx, int len, byte[]? outData = null)
                        {
                            var buf = outData is not null ? (byte[])outData.Clone() : new byte[len];
                            var ptr = Marshal.AllocHGlobal(Math.Max(buf.Length, 1));
                            try
                            {
                                Marshal.Copy(buf, 0, ptr, buf.Length);

                                ulong setupUlong = ((ulong)reqType) |
                                                   ((ulong)req << 8) |
                                                   ((ulong)val << 16) |
                                                   ((ulong)idx << 32) |
                                                   ((ulong)buf.Length << 48);

                                var sw = System.Diagnostics.Stopwatch.StartNew();
                                var ok = WinUsb_ControlTransfer_Ulong(winUsb, setupUlong, ptr, (uint)buf.Length, out var moved, 0);
                                sw.Stop();
                                var err = ok ? 0 : Marshal.GetLastWin32Error();

                                if (ok)
                                {
                                    Marshal.Copy(ptr, buf, 0, (int)Math.Min((uint)buf.Length, moved));
                                    Console.WriteLine($"  [{label,-26}] 成功! moved={moved} hex={Convert.ToHexString(buf.AsSpan(0, (int)moved))} ({sw.ElapsedMilliseconds}ms)");
                                }
                                else
                                {
                                    Console.WriteLine($"  [{label,-26}] 失败: Win32={err} ({sw.ElapsedMilliseconds}ms)");
                                }
                            }
                            finally
                            {
                                Marshal.FreeHGlobal(ptr);
                            }
                        }

                        Console.WriteLine("\n--- 标准 USB 请求测试 ---");
                        TestProbe("GET_DESCRIPTOR (Dev)",   0x80, 0x06, 0x0100, 0x0000, 18);
                        TestProbe("GET_DESCRIPTOR (Cfg)",   0x80, 0x06, 0x0200, 0x0000, 9);
                        TestProbe("GET_STATUS (Interface)",  0x81, 0x00, 0x0000, 0x0000, 2);
                        TestProbe("GET_DESCRIPTOR (HID)",   0x81, 0x06, 0x2200, 0x0000, 64);

                        Console.WriteLine("\n--- UAC 官方基类读音量测试 (0xA0) ---");
                        TestProbe("0xA0 Vol L (wIdx=0A01)",  0xA0, 0x01, 0x0201, 0x0A01, 2);
                        TestProbe("0xA0 Vol R (wIdx=0A01)",  0xA0, 0x01, 0x0202, 0x0A01, 2);
                        TestProbe("0xA0 Vol M (wIdx=0A01)",  0xA0, 0x01, 0x0200, 0x0A01, 2);
                        TestProbe("0xA0 Mute  (wIdx=0101)",  0xA0, 0x01, 0x0100, 0x0101, 1);
                        TestProbe("0xA0 Mute  (wIdx=0100)",  0xA0, 0x01, 0x0100, 0x0100, 1);
                        TestProbe("0xA0 Mute  (wIdx=0A01)",  0xA0, 0x01, 0x0100, 0x0A01, 1);

                        Console.WriteLine("\n--- Recipient 变体测试 (0xA1 / 0x80 / 0x81) ---");
                        TestProbe("0xA1 Vol L (wIdx=0A01)",  0xA1, 0x01, 0x0201, 0x0A01, 2);
                        TestProbe("0xA1 Vol L (wIdx=0000)",  0xA1, 0x01, 0x0201, 0x0000, 2);
                        TestProbe("0xA1 Vol L (wIdx=0001)",  0xA1, 0x01, 0x0201, 0x0001, 2);

                        Console.WriteLine("\n--- UAC 控制写测试 (0x20) ---");
                        TestProbe("0x20 Vol L -15dB",        0x20, 0x01, 0x0201, 0x0A01, 2, new byte[] { 0x00, 0xF1 });
                        TestProbe("0x20 Vol L 0dB",          0x20, 0x01, 0x0201, 0x0A01, 2, new byte[] { 0x00, 0x00 });

                        Console.WriteLine("\n--- 16 字节 HID SET_REPORT 测试 (0x21) ---");
                        var hidPkt = P(0x01, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x02);
                        TestProbe("0x21 HID SET_REPORT 16B", 0x21, 0x09, 0x0200, 0x0000, 16, hidPkt);
                    }
                    finally
                    {
                        WinUsbApi.WinUsb_Free(winUsb);
                    }
                }
                finally
                {
                    WinUsbApi.CloseHandle(fileHandle);
                }
            }

            RunMode("同步模式 (无 OVERLAPPED)", 0);
            RunMode("异步模式 (FILE_FLAG_OVERLAPPED)", FILE_FLAG_OVERLAPPED);

            return 0;
        }

        if (step == "wctl")
        {
            // One control transfer per process: an access violation inside WinUSB is fatal and
            // cannot be caught, so the data-stage length has to be swept from the outside.
            var len = args2 > 0 ? args2 : 17;
            var rows = WinUsbApi.Enumerate();
            var target = rows.FirstOrDefault(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E) && r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase));
            if (target is null) { Console.WriteLine("未找到 MI_00 的 WinUSB 接口"); return 4; }

            Console.WriteLine($"接口: {target.Path}");
            using var wlink = new WinUsbLink(target.Path);
            Console.WriteLine($"WinUsb_Initialize 成功；interfaceNumber={wlink.InterfaceNumber}，bNumEndpoints={wlink.EndpointCount}，中断IN={wlink.HasInterruptIn}");
            Console.WriteLine("接口描述符：");
            Console.WriteLine(WinUsbApi.DescribeInterface(wlink.InterfaceDescriptor));
            Console.WriteLine("接口声明的管道：");
            foreach (var row in WinUsbApi.DescribePipes(wlink.InterfaceHandle, wlink.EndpointCount)) Console.WriteLine(row);

            var pkt = P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01);
            Console.WriteLine($"数据阶段长度 = {len}");
            Console.WriteLine($"发送 {Convert.ToHexString(pkt)}");
            try
            {
                var reply = wlink.Exchange(pkt, out var moved, len);
                Console.WriteLine($"成功：传输长度={moved}  回包={Convert.ToHexString(reply)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"失败：{ex.GetType().Name}: {ex.Message}");
            }

            return 0;
        }

        if (step == "nodes")
        {
            Console.WriteLine("=== 设备节点发现路径诊断（无需管理员权限）===");
            Console.WriteLine(WinUsbApi.DescribeDevnodeWalk(0x262A, 0x187E));
            var all = WinUsbApi.DescribeUsbNodes(0x262A, 0x187E);
            Console.WriteLine();
            Console.WriteLine($"多类扫描合并去重后 {all.Count} 个同厂牌硬件 ID：");
            foreach (var a in all) Console.WriteLine("    " + a);
            var mi00 = WinUsbApi.ResolveInstanceId(0x262A, 0x187E, 0);
            Console.WriteLine();
            Console.WriteLine($"MI_00 实例 ID = {(mi00.Length == 0 ? "<未找到>" : mi00)}");
            return 0;
        }

        if (step == "wdiag")
        {
            Console.WriteLine("=== WinUSB 接口原始缓冲区诊断 ===");
            var raw = WinUsbApi.EnumerateRaw();
            Console.WriteLine($"共 {raw.Count} 个");
            for (var i = 0; i < raw.Count; i++)
            {
                Console.WriteLine($"[{i}] raw  = {raw[i].Hex}");
                Console.WriteLine($"    uni  = {raw[i].Unicode}");
            }

            Console.WriteLine("\n=== 对照：HID 接口的路径解码 ===");
            var hid = DeviceEnumerator.Enumerate();
            foreach (var h in hid.Take(2)) Console.WriteLine($"  HID: {h.Path}");

            return 0;
        }

        if (step == "winusb")
        {
            Console.WriteLine("=== WinUSB 接口枚举 ===");
            var rows = WinUsbApi.Enumerate();
            Console.WriteLine($"共 {rows.Count} 个 WinUSB 接口:");
            foreach (var r in rows) Console.WriteLine($"  [{r.Path.Length,3}] {r.Path}\n        {r.InstanceId}");

            // Prefer the MI_00 interface node; fall back to the composite device node only if that is all
// that is present. A parent node cannot be WinUsb_Initialized while a child interface still
// belongs to HidUsb.
            var ours = rows.Where(r => WinUsbApi.PathMatches(r.Path, 0x262A, 0x187E)).ToList();
            var target = ours.FirstOrDefault(r => r.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase))
                      ?? ours.FirstOrDefault();
            if (target is null)
            {
                Console.WriteLine("\n未找到 VID_262A&PID_187E 的 USB 节点。");
                return 4;
            }

            Console.WriteLine($"\n=== 打开 {(target.Path.Contains("&mi_00", StringComparison.OrdinalIgnoreCase) ? "MI_00 接口" : "父设备节点（若未匹配到 MI_00，请检查驱动是否真的换到了接口上）")} ===");
            Console.WriteLine($"  {target.Path}");
            using var wlink = new WinUsbLink(target.Path);
            wlink.Log += e => Console.WriteLine($"  {e.Time:HH:mm:ss.fff} {(e.Direction.StartsWith("TX") ? "->" : "<-")} [{e.Hex}] {e.Note}");
            Console.WriteLine($"  WinUsb_Initialize 成功；中断 IN 端点数量 = {(wlink.HasInterruptIn ? "有" : "无（回包只能走控制传输 IN 阶段）")}");

            Console.WriteLine("\n=== 读音量寄存器（这次应能拿到回包）===");
            var reads = new (string Name, byte[] Pkt)[]
            {
                ("左声道 41 12 E4 A2 00 10", P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01)),
                ("右声道 41 12 E4 A2 00 11", P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x11, 0x01)),
                ("Dc06 式 41 12 DC A2 00 00", P(0x41, 0x12, 0xDC, 0xA2, 0x00, 0x00, 0x01)),
            };

            foreach (var (name, pkt) in reads)
            {
                var reply = wlink.Exchange(pkt, out var moved);
                Console.WriteLine($"  {name}");
                Console.WriteLine($"      发送 {Convert.ToHexString(pkt)}");
                Console.WriteLine($"      传输长度={moved}  回包={Convert.ToHexString(reply)}");
                await Task.Delay(100);
            }

            return 0;
        }

        if (step == "insize")
        {
            // Hypothesis: the firmware has no interrupt IN endpoint and answers inside the IN
            // data stage of the SET_REPORT control transfer. HidD_SetOutputReport normally
            // gives it no IN buffer, so sweep the buffer length and look for a reply being
            // written into the tail of the same buffer.
            var pkt = P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01);
            Console.WriteLine("step: SET_REPORT 缓冲区长度扫描，观察 IN 阶段是否带回回包");
            Console.WriteLine($"  读请求: {Convert.ToHexString(pkt)}");

            foreach (var len in new[] { 17, 18, 20, 24, 32, 33, 48, 64 })
            {
                var buf = new byte[len];
                buf[0] = 0x00;
                Buffer.BlockCopy(pkt, 0, buf, 1, Math.Min(16, len - 1));

                var sw = System.Diagnostics.Stopwatch.StartNew();
                var ok = HidApi.SetOutputReportRaw(handle, buf, out var detail);
                sw.Stop();

                var tail = Convert.ToHexString(buf, 17, Math.Max(0, len - 17));
                var nonZeroTail = tail.Trim('0') != "";
                Console.WriteLine($"  len={len,-3} {detail,-22} {sw.ElapsedMilliseconds,4}ms  尾部回包={(nonZeroTail ? "有: " + tail : "无")}");
            }

            HidApi.CloseHandle(handle);
            return 0;
        }

        if (step == "knob")
        {
            var seconds = args2 > 0 ? args2 : 20;
            using var klink = new UacLink(handle, device);
            var rx = 0;
            klink.ResponseReceived += r =>
            {
                rx++;
                var (seq, payload, native) = ResponseParser.Parse(r.Raw);
                var tag = native
                    ? $"*** 原生音量变化 左=0x{payload.ElementAtOrDefault(0):X2} 右=0x{payload.ElementAtOrDefault(1):X2} ***"
                    : $"seq={seq}({Cmd.Name(seq)}) payload=[{Convert.ToHexString(payload)}]";
                Console.WriteLine($"  {DateTime.Now:HH:mm:ss.fff}  收到 {r.Raw.Length} 字节: {Convert.ToHexString(r.Raw)}  {tag}");
            };

            for (var i = 5; i > 0; i--)
            {
                Console.WriteLine($"  {i} 秒后开始监听…");
                await Task.Delay(1000);
            }

            Console.WriteLine($"  ===== 开始监听 {seconds} 秒，请现在转动音量旋钮 =====");
            var deadline = DateTime.Now.AddSeconds(seconds);
            while (DateTime.Now < deadline)
            {
                await Task.Delay(1000);
                Console.WriteLine($"  {DateTime.Now:HH:mm:ss}  …已监听 {rx} 条");
            }

            Console.WriteLine(rx == 0
                ? $"  ===== {seconds} 秒结束：一条都没收到 ====="
                : $"  ===== {seconds} 秒结束：共收到 {rx} 条 =====");
            return 0;
        }

        if (step == "volset")
        {
            // DC03ProDeviceController.setDc03ProLeftVolume / setDc03ProRightVolume, replayed
            // verbatim: four writeRegister() calls per channel plus one writeRegisterFor2BytesOffset().
            var volStep = args2 >= 0 ? args2 : 55;
            var v = VolumeTables.Step2[volStep];
            Console.WriteLine($"step: 设置音量档位 {volStep}  衰减=0x{v:X2} ({v})");
            Console.WriteLine("step: 左声道 = ID_GET_SPDIF(0x60) / 右声道 = ID_GET_INIT(0x62)");
            Console.WriteLine("step: 读线程持续挂起（对齐官方 App 的 MonitorThread 行为）");

            using var vlink = new UacLink(handle, device);
            vlink.Log += e => Console.WriteLine($"  {e.Time:HH:mm:ss.fff} {(e.Direction.StartsWith("TX") ? "->" : "<-")} [{e.Hex}] {e.Note}");

            await Task.Delay(300);

            void Send(byte[] p, string what) => vlink.Send(p);

            // writeRegister(seq, dev, off1..off4, value)  ->  [0]=seq [1]=0x11 [2]=0x88 [3]=dev [6]=5 [7..10]=off [11]=value
            byte[] W(byte seq, byte dev, byte o1, byte o2, byte o3, byte o4, byte val) =>
                P(seq, 0x11, 0x88, dev, 0x00, 0x00, 0x05, o1, o2, o3, o4, val);

            // writeRegisterFor2BytesOffset(seq, dev, hi, lo, value) -> [0]=seq [1]=0x11 [2]=0xA0 [3]=dev [4]=hi [5]=lo [6]=1 [7]=value
            byte[] W2(byte seq, byte dev, byte hi, byte lo, byte val) =>
                P(seq, 0x11, 0xA0, dev, hi, lo, 0x01, val);

            Send(W(0x01, 0x60, 9, 0, 1, 0, v), "左 L1  vol[09 00 01 00]");
            Send(W(0x02, 0x60, 9, 0, 2, 0, v), "左 L2  vol[09 00 02 00]");
            Send(W(0x09, 0x60, 7, 0, 0, 0, v), "左 L3  vol[07 00 00 00]");
            Send(W(0x0A, 0x60, 7, 0, 1, 0, v), "左 L4  vol[07 00 01 00]");
            Send(W2(0x13, 0xA2, 0x00, 0x10, v), "左 ROOM 提交");

            Send(W(0x03, 0x62, 9, 0, 1, 0, v), "右 R1  vol[09 00 01 00]");
            Send(W(0x04, 0x62, 9, 0, 2, 0, v), "右 R2  vol[09 00 02 00]");
            Send(W(0x0B, 0x62, 7, 0, 0, 0, v), "右 R3  vol[07 00 00 00]");
            Send(W(0x0C, 0x62, 7, 0, 1, 0, v), "右 R4  vol[07 00 01 00]");
            Send(W2(0x14, 0xA2, 0x00, 0x11, v), "右 ROOM 提交");

            Console.WriteLine("  全部 10 步已发送，监听 8 秒 ...");
            await Task.Delay(8000);
            Console.WriteLine("  监听结束");
            return 0;
        }

        if (step == "hid")
        {
            Console.WriteLine("step: HidD_GetInputReport polling");
            for (var i = 0; i < 5; i++)
            {
                var ok = HidApi.PollInputReport(handle, out var d);
                Console.WriteLine($"  poll #{i} size=64 -> {(ok ? Convert.ToHexString(d) : "err " + System.Runtime.InteropServices.Marshal.GetLastWin32Error())}");
                Thread.Sleep(150);
            }

            Console.WriteLine("  先发读请求，再轮询 GetInputReport:");
            var pkt = P(0x41, 0x12, 0xDC, 0xA2, 0x00, 0x00, 0x01);
            var send = new byte[17];
            send[0] = 0;
            Buffer.BlockCopy(pkt, 0, send, 1, 16);
            Console.WriteLine($"  SetOutputReport -> {(HidApi.HidD_SetOutputReport(handle, send, (uint)send.Length) ? "ok" : "err " + System.Runtime.InteropServices.Marshal.GetLastWin32Error())}");
            for (var i = 0; i < 10; i++)
            {
                var ok = HidApi.PollInputReport(handle, out var d);
                if (ok) Console.WriteLine($"  poll #{i} -> {Convert.ToHexString(d)}");
                Thread.Sleep(100);
            }
            Console.WriteLine("  (无输出即 10 次全部失败)");

            HidApi.CloseHandle(handle);
            return 0;
        }

        if (step == "probe")
        {
            // Candidate read requests for DC03 Pro, straight from the decompiled send paths.
            var candidates = new (string Name, byte[] Pkt)[]
            {
                ("Dc06式 op=DC off=00", P(0x41, 0x12, 0xDC, 0xA2, 0x00, 0x00, 0x01)),
                ("Dc06式 op=DC off=10", P(0x41, 0x12, 0xDC, 0xA2, 0x10, 0x00, 0x01)),
                ("productId=1 op=C8 off=09000000", P(0x41, 0x12, 0xC8, 0xA2, 0x00, 0x00, 0x04, 0x09, 0x00, 0x00, 0x00, 0xFF)),
                ("2字节式 op=E4 off=0010 (旧)", P(0x41, 0x12, 0xE4, 0xA2, 0x00, 0x10, 0x01)),
            };

            foreach (var c in candidates)
            {
                var buf = new byte[c.Pkt.Length + 1];
                buf[0] = 0x00;
                Buffer.BlockCopy(c.Pkt, 0, buf, 1, c.Pkt.Length);

                var sendOk = HidApi.HidD_SetOutputReport(handle, buf, (uint)buf.Length);
                Console.WriteLine($"  {c.Name,-34} {Convert.ToHexString(c.Pkt)}  发送={(sendOk ? "ok" : "err" + System.Runtime.InteropServices.Marshal.GetLastWin32Error())}");

                var got = HidApi.TryRead(handle, out var data, 1200)
                    ? Convert.ToHexString(data)
                    : "<无回包>";
                Console.WriteLine($"  {new string(' ', 34)} 收到={got}");
            }

            HidApi.CloseHandle(handle);
            return 0;
        }

        if (step == "matrix")
        {
            // No background reader: for each framing variant, send the read request and then
            // do one deadline-bounded input read to see whether the device answers at all.
            var pkt = UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 });
            var withId = new byte[pkt.Length + 1];
            withId[0] = 0x00;
            Buffer.BlockCopy(pkt, 0, withId, 1, pkt.Length);
            Console.WriteLine($"step: framing matrix, packet={Convert.ToHexString(pkt)}");

            var variants = new (string Name, byte[] Buf, bool UseOutputReport)[]
            {
                ("SetOutputReport +0x00前缀 (17B)", withId, true),
                ("SetOutputReport 裸包 (16B)", pkt, true),
                ("WriteFile +0x00前缀 (17B)", withId, false),
                ("WriteFile 裸包 (16B)", pkt, false),
            };

            foreach (var v in variants)
            {
                Console.WriteLine($"  >>> {v.Name} 发送中 ...");
                var sw = System.Diagnostics.Stopwatch.StartNew();
                string sent = v.UseOutputReport
                    ? (HidApi.HidD_SetOutputReport(handle, v.Buf, (uint)v.Buf.Length)
                        ? $"ok({v.Buf.Length}B)"
                        : $"err{System.Runtime.InteropServices.Marshal.GetLastWin32Error()}")
                    : (HidApi.WriteOutput(handle, v.Buf, out var w, out var wd) ? $"ok({w}B)" : $"FAIL({wd})");
                Console.WriteLine($"      发送完成 {sw.ElapsedMilliseconds}ms: {sent}");

                Console.WriteLine("      读取中 ...");
                var got = HidApi.TryRead(handle, out var data, 800, m => Console.WriteLine($"        | {m}"))
                    ? Convert.ToHexString(data)
                    : "<无回包>";

                Console.WriteLine($"  {v.Name,-34} 发送={sent,-12} 收到={got}");
                Thread.Sleep(200);
            }
        }

        if (step == "desc")
        {
            var d = HidApi.GetReportDescriptor(handle);
            if (d is null) { Console.WriteLine("report descriptor unavailable"); return 3; }
            Console.WriteLine($"step: HID report descriptor, {d.Length} bytes");
            for (var i = 0; i < d.Length; i += 16)
            {
                Console.WriteLine($"  {i:X4}  {Convert.ToHexString(d, i, Math.Min(16, d.Length - i))}");
            }
            Console.WriteLine("  decoded items:");
            for (var i = 0; i < d.Length;)
            {
                var b = d[i];
                if (b == 0xFE)
                {
                    var len = (d[i + 1] << 8) | d[i + 2];
                    Console.WriteLine($"    {i:X4}  LONG  tag=0x{b:X2} len={len}");
                    i += 3 + len;
                    continue;
                }
                var prefix = b >> 6;
                if (prefix == 3)
                {
                    Console.WriteLine($"    {i:X4}  TOP   0x{b:X2} {HidItemName(b)}");
                    i += 1;
                    continue;
                }
                var sz = new[] { 0, 1, 2, 4 }[prefix];
                var avail = Math.Min(sz, d.Length - i - 1);
                var data = Convert.ToHexString(d, i + 1, avail);
                Console.WriteLine($"    {i:X4}  {new[]{"MAIN ","GLOB ","LOCL "}[prefix]} 0x{b:X2} {HidItemName(b)} data=[{data}]");
                i += 1 + sz;
            }
            HidApi.CloseHandle(handle);
            return 0;
        }

        var readPkt = UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 });

        if (step == "solo")
        {
            Console.WriteLine("step: no reader thread at all — time each write method on an idle handle");
            Console.WriteLine($"  packet: {Convert.ToHexString(readPkt)}");

            foreach (var m in new[] { HidApi.HidOutMethod.SetOutputReport, HidApi.HidOutMethod.WriteFile, HidApi.HidOutMethod.SetReport, HidApi.HidOutMethod.SetFeatureReport })
            {
                var sw = System.Diagnostics.Stopwatch.StartNew();
                var worker = Task.Run(() => HidApi.SendByMethod(handle, readPkt, 0x00, m, out var d) ? $"OK ({d})" : $"FAIL ({d})");
                var finished = await Task.WhenAny(worker, Task.Delay(5000)) == worker;
                sw.Stop();
                Console.WriteLine(finished
                    ? $"  {m,-18}: {await worker}  [{sw.ElapsedMilliseconds} ms]"
                    : $"  {m,-18}: *** 阻塞 >5000 ms ***");
            }

            HidApi.CloseHandle(handle);
            return 0;
        }

        using var link = new UacLink(handle, device);
        link.Log += e => Console.WriteLine($"  {e.Time:HH:mm:ss.fff} {(e.Direction.StartsWith("TX") ? "->" : "<-")} [{e.Hex}] {e.Note}");

        if (step == "send")
        {
            Console.WriteLine("step: send one read request, do not wait for a reply");
            var pkt = UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 });
            Console.WriteLine($"  packet: {Convert.ToHexString(pkt)}");
            link.Send(pkt);
            Console.WriteLine("  sent");
            await Task.Delay(2000);
            Console.WriteLine("  waited 2s");
        }
        else if (step == "methods")
        {
            Console.WriteLine("step: NO reader thread — write one read request, then listen for the reply");
            var pkt = UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 });
            Console.WriteLine($"  packet: {Convert.ToHexString(pkt)}");
            var sw = System.Diagnostics.Stopwatch.StartNew();
            link.Send(pkt);
            sw.Stop();
            Console.WriteLine($"  发送返回，耗时 {sw.ElapsedMilliseconds} ms");
            await Task.Delay(2000);
            Console.WriteLine("  已等待 2 秒");
        }
        else if (step == "listen")
        {
            Console.WriteLine("step: listen 5s, send nothing");
            await Task.Delay(5000);
            Console.WriteLine("  done");
        }
        else if (step == "write")
        {
            Console.WriteLine("step: send one volume write (seqId=1, op=0x88, dev=0x60, off=09000100)");
            var value = VolumeTables.Step2[50];
            var pkt = UacLink.BuildWrite(Cmd.SetVolumeLeft1, RegOp.Write4ByteOffset, 0x60, new byte[] { 9, 0, 1, 0 }, value);
            Console.WriteLine($"  packet: {Convert.ToHexString(pkt)}   attenuation=0x{value:X2}");
            link.Send(pkt);
            Console.WriteLine("  sent");
            await Task.Delay(2500);
            Console.WriteLine("  waited 2.5s");
        }

        return 0;
    }

    private static async Task<int> Main(string[] args)
    {
        Console.OutputEncoding = Encoding.UTF8;
        var stdout = new StreamWriter(Console.OpenStandardOutput(), new UTF8Encoding(false)) { AutoFlush = true };
        Console.SetOut(stdout);

        var step = args.FirstOrDefault(a => a.StartsWith("--step=", StringComparison.Ordinal))?.Split('=', 2)[1];
        var volArg = args.FirstOrDefault(a => a.StartsWith("--volume=", StringComparison.Ordinal))?.Split('=', 2)[1];
        if (step is not null) return await RunStep(step, volArg is not null && int.TryParse(volArg, out var vv) ? vv : -1);

        if (args.Contains("--diag"))
        {
            Console.WriteLine(DeviceEnumerator.Diagnose());
            return 0;
        }

        var only = args.FirstOrDefault(a => a.StartsWith("--product=", StringComparison.Ordinal))?.Split('=', 2)[1];
        var doWrite = args.Contains("--write");

        Console.WriteLine("=== HID 设备枚举 ===");
        var devices = DeviceEnumerator.Enumerate();
        Console.WriteLine($"共发现 {devices.Count} 个 HID 设备。\n");

        foreach (var d in devices)
        {
            var match = ProductCatalog.Match(d.ProductName);
            var tag = match is null ? "" : "   <== 识别为 " + match.DisplayName;
            Console.WriteLine($"  {d.VidPid}  {d.Manufacturer,-18} {d.DisplayName}{tag}");
            if (only is not null && match is null) continue;
            Console.WriteLine($"      path: {d.Path}");
        }

        var candidates = new List<(UsbDeviceInfo Dev, ProductProfile Match)>();
        foreach (var d in devices)
        {
            var m = ProductCatalog.Match(d.ProductName);
            if (m is null) continue;
            if (only is not null && !(d.ProductName?.Contains(only, StringComparison.OrdinalIgnoreCase) ?? false)) continue;
            candidates.Add((d, m));
        }

        if (candidates.Count == 0)
        {
            Console.WriteLine("\n没有找到已识别的 iBasso 设备。");
            return 1;
        }

        var (device, productProfile) = candidates[0];

        Console.WriteLine($"\n=== 目标设备 ===");
        Console.WriteLine($"  {device.DisplayName}  {device.VidPid}");
        Console.WriteLine($"  匹配规则: \"{productProfile.Selector}\"");

        Console.WriteLine("\n=== 打开设备 ===");
        var handle = HidApi.Open(device.Path);
        if (!HidApi.IsValidHandle(handle))
        {
            Console.WriteLine($"  打开失败，Win32 = {System.Runtime.InteropServices.Marshal.GetLastWin32Error()}");
            Console.WriteLine("  （常见原因：设备被其他程序占用；某些机型需要以管理员身份运行）");
            return 2;
        }

        Console.WriteLine("  打开成功");
        using var link = new UacLink(handle, device);
        var profile = productProfile.Factory(link, device);
        Console.WriteLine($"  控制器: {profile.DisplayName}   最大音量: {profile.MaxVolume}");

        link.Log += e =>
        {
            var arrow = e.Direction.StartsWith("TX") ? "->" : "<-";
            Console.WriteLine($"  {e.Time:HH:mm:ss.fff} {arrow} [{e.Hex}] {e.Note}");
        };

        try
        {
            Console.WriteLine("\n=== 读取当前状态 (音量/平衡/数字滤波) ===");
            var state = new DeviceState();
            await profile.RefreshAsync(state);
            Console.WriteLine($"\n  音量档位 = {state.Volume}   (dB = {profile.VolumeToDb(state.Volume):0.0})");
            Console.WriteLine($"  平衡     = {state.Balance}");
            Console.WriteLine($"  数字滤波 = {state.DigitalFilter}");

            if (!doWrite)
            {
                Console.WriteLine("\n（加 --write 参数才会真正下发写命令）");
                return 0;
            }

            Console.WriteLine("\n=== 写入测试：设置音量到 55 档 ===");
            await profile.SetVolumeAsync(55);
            await Task.Delay(400);

            Console.WriteLine("\n=== 本地状态跟踪（该机型不回读）===");
            var after = new DeviceState();
            await profile.RefreshAsync(after);
            Console.WriteLine($"\n  音量档位 = {after.Volume}   (dB = {profile.VolumeToDb(after.Volume):0.0})");
            Console.WriteLine($"  平衡     = {after.Balance}");

            Console.WriteLine(after.IsWriteOnly
                ? "\n==> 写入指令已全部下发成功。注意：该机型固件不回读，以上是本机跟踪值，\n    写入是否真正生效需听音或看设备面板确认。"
                : after.Volume == 55
                    ? "\n==> 写入成功：设备已确认档位 55"
                    : $"\n==> 写入未确认（读到 {after.Volume}）");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"\n异常: {ex.GetType().Name}: {ex.Message}");
            return 3;
        }

        return 0;
    }
}

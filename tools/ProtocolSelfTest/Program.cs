using System.Text;
using iBassoUAC.Devices;
using iBassoUAC.Protocol;

namespace iBassoUAC.ProtocolSelfTest;

/// <summary>
/// Verifies the desktop encoder against byte layouts taken verbatim from the
/// decompiled iBasso UAC 2.1.9 sources. No hardware required.
/// </summary>
internal static class Program
{
    private static int _pass;
    private static int _fail;
    private static readonly StringBuilder Report = new();

    private static int Main()
    {
        Header("16-byte register packets (TaskManager.send*/writeRegister*)");

        // writeRegister(seq, addr, 09 00 01 00, value)  — 4-byte offset, opcode 0x88
        Check("writeRegister/0x88",
            UacLink.BuildWrite(Cmd.SetVolumeLeft1, RegOp.Write4ByteOffset, 0x60, new byte[] { 9, 0, 1, 0 }, 0x5A),
            "01 11 88 60 00 00 05 09 00 01 00 5A 00 00 00 00");

        // sendGetRegisterRequest(seq, addr, 09 00 00 00) — read, opcode 0xC8, pkt[11] = -1
        Check("sendGetRegisterRequest/0xC8",
            UacLink.BuildRead(Cmd.GetDc03ProDigitalFilter1, RegOp.Read4ByteOffset, 0x60, new byte[] { 9, 0, 0, 0 }),
            "4B 12 C8 60 00 00 04 09 00 00 00 FF 00 00 00 00");

        // writeRegisterFor2BytesOffset(seq, 0xA2, 0x0010, value) — 2-byte offset, opcode 0xA0
        Check("writeRegisterFor2BytesOffset/0xA0",
            UacLink.BuildWrite(19, RegOp.Write2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 }, 0x3C),
            "13 11 A0 A2 00 10 01 3C 00 00 00 00 00 00 00 00");

        // sendGetRegisterRequestFor2BytesOffset(65, 0xA2, 0x00 0x10) — opcode 0xE4
        Check("sendGetRegisterRequestFor2BytesOffset/0xE4",
            UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read2ByteOffset, 0xA2, new byte[] { 0x00, 0x10 }),
            "41 12 E4 A2 00 10 01 00 00 00 00 00 00 00 00 00");

        // writeRegisterForDc06(1, 0x90, 0x10, value) — 1-byte offset, opcode 0x98
        Check("writeRegisterForDc06/0x98",
            UacLink.BuildWrite(Cmd.SetVolumeLeft1, RegOp.Write1ByteOffset, 0x90, new byte[] { 0x10 }, 0x2C),
            "01 11 98 90 10 00 01 2C 00 00 00 00 00 00 00 00");

        // sendGetRegisterRequestForDc06(65, 0x90, 0x10) — opcode 0xDC
        Check("sendGetRegisterRequestForDc06/0xDC",
            UacLink.BuildRead(Cmd.GetVolumeLeft1, RegOp.Read1ByteOffset, 0x90, new byte[] { 0x10 }),
            "41 12 DC 90 10 00 01 00 00 00 00 00 00 00 00 00");

        Header("64-byte state frames (Macchiato / DC04U / D18)");

        // Macchiato/DC04U header: 4B 80 3F 08 <cmd> <group> ...   (0x80 = read)
        Check("64B get filter group",
            UacLink.BuildCustom64(Cmd.GetFilter, 0x20, direction: 0x80),
            "4B 80 3F 08 59 20" + Repeat("00", 58));

        Check("64B set codec group",
            UacLink.BuildCustom64(Cmd.SetCodecExternal, 0x30, direction: 0x01, 1, 2, 3),
            "4B 01 3F 08 19 30 01 02 03" + Repeat("00", 55));

        // D18 uses direction 0x00 for reads
        Check("64B D18 read uses direction 0x00",
            UacLink.BuildCustom64(Cmd.GetFilter, 0x20, direction: 0x00)[1].ToString(),
            "0");

        Header("EQ band packing (MacchiatoDeviceController.setParametricEqualizer)");

        // 1000 Hz, Q 1.25 (0x0140), +3.5 dB (0x0380), PEAK(2), totalGain -1
        var eq = Raw64Profile.BuildEqBandFrame(
            presetSlot: 0, band: 3, totalGain: -1, dirWrite: 0x01,
            setting: new EqBandSetting { Frequency = 1000, Factor = 1.25, Gain = 3.5, FilterType = EqFilterKind.Peak });

        Check("EQ frame head   -> bytes 0..3", Convert.ToHexString(eq.AsSpan(0, 4)), "4B01091F");
        Check("EQ record tag   -> bytes 2..3", Convert.ToHexString(eq.AsSpan(2, 2)), "091F");
        Check("EQ preset slot  -> byte 4", eq[4].ToString(), "7");
        Check("EQ band index   -> byte 5", eq[5].ToString(), "3");
        Check("EQ blob         -> byte 9", eq[9].ToString("X2"), "8F");
        Check("EQ blob         -> byte 27", eq[27].ToString("X2"), "4A");
        // Frequency / Q / gain are little-endian on the wire (low byte first).
        Check("EQ freq 1000Hz  -> bytes 28..29 LE", $"{(eq[29] << 8) | eq[28]:X4}", "03E8");
        Check("EQ Q 1.25       -> bytes 30..31 LE", $"{(eq[31] << 8) | eq[30]:X4}", "0140");
        Check("EQ gain +3.5dB  -> bytes 32..33 LE", $"{(short)((eq[33] << 8) | eq[32]):X4}", "0380");
        Check("EQ filterType   -> byte 34", eq[34].ToString(), "2");
        Check("EQ totalGain    -> byte 35", ((sbyte)eq[35]).ToString(), "-1");

        Header("Response demultiplexing (TaskManager.onRequestDone)");

        var native = new byte[32];
        native[4] = 0xFE; native[5] = 0x01; native[8] = 0x1E; native[9] = 0x20;
        var (seq0, pay0, isVol) = ResponseParser.Parse(native);
        Check("native volume report seq", seq0.ToString(), "83");
        Check("native volume report flag", isVol.ToString(), "True");
        Check("native volume payload", Convert.ToHexString(pay0), "1E20");

        var reg = new byte[16];
        reg[6] = Cmd.GetVolumeLeft1; reg[7] = 3; reg[8] = 0x5A; reg[9] = 0xC3; reg[10] = 0x01;
        var (seq1, pay1, _) = ResponseParser.Parse(reg);
        Check("register reply seq", seq1.ToString(), "65");
        Check("register reply payload", Convert.ToHexString(pay1), "5AC301");

        var eqReply = new byte[64];
        eqReply[0] = 0x4B; eqReply[1] = 0x80; eqReply[2] = 9; eqReply[3] = 31;
        eqReply[4] = 7; eqReply[5] = 2;
        var (seq2, _, _) = ResponseParser.Parse(eqReply);
        Check("EQ read reply uses byte 4 as seq", seq2.ToString(), "7");

        var stateReply = new byte[64];
        stateReply[0] = 0x4B; stateReply[1] = 0x00; stateReply[5] = 0x30; stateReply[6] = 0x05;
        var (seq3, _, _) = ResponseParser.Parse(stateReply);
        Check("state reply uses byte 5 as seq", seq3.ToString(), "48");

        Header("Volume tables (UsbDeviceController)");

        Check("nVolumeStep length", VolumeTables.Step.Length.ToString(), "64");
        Check("nVolumeStep[0]", $"0x{VolumeTables.Step[0]:X4}", "0xC100");
        Check("nVolumeStep[62]", $"0x{VolumeTables.Step[62]:X4}", "0xFF00");
        Check("nVolumeStep[63]", $"0x{VolumeTables.Step[63]:X4}", "0x0000");
        Check("nVolumeStep2 length", VolumeTables.Step2.Length.ToString(), "101");
        Check("nVolumeStep2[0] (mute)", VolumeTables.Step2[0].ToString(), "255");
        Check("nVolumeStep2[100] (0 dB)", VolumeTables.Step2[100].ToString(), "0");
        Check("nVolumeStep2[1]", VolumeTables.Step2[1].ToString(), "155");

        Header("Biquad math (EQProcessor.calculateCoefficients)");

        var peak = Biquad.Coefficients(EqFilterKind.Peak, 1000, 0, 0.707);
        Check("unity peak @1k centre gain", $"{Biquad.ResponseDb(peak, 1000):F4}", "0.0000");
        Check("unity peak @20Hz", Math.Abs(Biquad.ResponseDb(peak, 20)).ToString("F3"), "0.000");

        var boost = Biquad.Coefficients(EqFilterKind.Peak, 1000, 6, 0.707);
        Check("+6 dB peak @1k", $"{Biquad.ResponseDb(boost, 1000):F2}", "6.00");

        var cut = Biquad.Coefficients(EqFilterKind.Peak, 1000, -6, 0.707);
        Check("-6 dB peak @1k", $"{Biquad.ResponseDb(cut, 1000):F2}", "-6.00");

        var lp = Biquad.Coefficients(EqFilterKind.LowPass, 1000, 0, 0.707);
        Check("low-pass passband @20Hz ~ 0 dB", Math.Abs(Biquad.ResponseDb(lp, 20)).ToString("F2"), "0.00");
        Check("low-pass @10kHz attenuated", (Biquad.ResponseDb(lp, 10000) < -30).ToString(), "True");
        var hp = Biquad.Coefficients(EqFilterKind.HighPass, 1000, 0, 0.707);
        Check("high-pass @20Hz attenuated", (Biquad.ResponseDb(hp, 20) < -30).ToString(), "True");
        Check("high-pass @10kHz ~ 0 dB", Math.Abs(Biquad.ResponseDb(hp, 10000)).ToString("F2"), "0.00");

        Header("Product catalogue");

        foreach (var selector in new[] { "iBasso-DC06", "iBasso-DC06-Pro", "iBasso-DC03-Pro", "iBasso-DC04-Pro",
                                        "Macaron", "iBasso DC-Elite", "iBasso DC-Tonfa", "iBasso DC07 Pro",
                                        "iBasso DC-Nunchaku", "DC04U", "D18", "Macchiato", "CX31993 384Khz HIFI AUDIO" })
        {
            var hit = ProductCatalog.Match(selector);
            Check($"selector \"{selector}\"", hit is null ? "NOT MATCHED" : hit.DisplayName, hit?.DisplayName ?? "NOT MATCHED");
        }

        Check("unknown selector returns null", (ProductCatalog.Match("Some Random DAC") is null).ToString(), "True");

        Console.OutputEncoding = Encoding.UTF8;
        Console.WriteLine(Report.ToString());
        Console.WriteLine($"PASSED {_pass} / FAILED {_fail}");

        var outPath = Path.Combine(AppContext.BaseDirectory, "protocol-selftest.txt");
        File.WriteAllText(outPath, Report.ToString() + $"\nPASSED {_pass} / FAILED {_fail}\n", Encoding.UTF8);
        return _fail == 0 ? 0 : 1;
    }

    private static string Repeat(string s, int n) => string.Concat(Enumerable.Repeat(s, n));

    private static void Header(string title)
    {
        Report.AppendLine();
        Report.AppendLine("=== " + title + " ===");
    }

    private static void Check(string name, byte[] actual, string expectedHex)
        => Check(name, Convert.ToHexString(actual), expectedHex.Replace(" ", string.Empty, StringComparison.Ordinal));

    private static void Check(string name, string actual, string expected)
    {
        var ok = string.Equals(actual, expected, StringComparison.OrdinalIgnoreCase);
        if (ok) _pass++; else _fail++;
        Report.AppendLine($"[{(ok ? "PASS" : "FAIL")}] {name}");
        if (!ok) Report.AppendLine($"        expected: {expected}\n        actual  : {actual}");
    }
}


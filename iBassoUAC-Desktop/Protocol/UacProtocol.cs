namespace iBassoUAC.Protocol;

/// <summary>
/// Command / opcode constants recovered from com.ibasso.volume.IConstants and
/// com.ibasso.volume.TaskManager of iBasso UAC 2.1.9 (com.ibasso.volume).
/// </summary>
public static class Cmd
{
    // ---- register-ish command ids (IConstants) --------------------------------
    public const byte PcmNativeReport = 0;
    public const byte SetVolumeLeft1 = 1;
    public const byte SetVolumeLeft2 = 2;
    public const byte SetVolumeRight1 = 3;
    public const byte SetVolumeRight2 = 4;
    public const byte SetBalanceLeft1 = 5;
    public const byte SetBalanceLeft2 = 6;
    public const byte SetBalanceRight1 = 7;
    public const byte SetBalanceRight2 = 8;
    public const byte SetDsdVolumeLeft1 = 9;
    public const byte SetDsdVolumeLeft2 = 10;
    public const byte SetDsdVolumeRight1 = 11;
    public const byte SetDsdVolumeRight2 = 12;
    public const byte SetDsdBalanceLeft1 = 13;
    public const byte SetDsdBalanceLeft2 = 14;
    public const byte SetDsdBalanceRight1 = 15;
    public const byte SetDsdBalanceRight2 = 16;
    public const byte SetDigitalFilter1 = 17;   // == 0x11, doubles as "write" marker in pkt[1]
    public const byte SetDigitalFilter2 = 18;   // == 0x12, doubles as "read" marker in pkt[1]
    public const byte SetVolumeLeftRoom = 19;
    public const byte SetVolumeRightRoom = 20;
    public const byte SetGain1 = 21;
    public const byte SetGain2 = 22;
    public const byte SetOutput1 = 23;
    public const byte SetOutput2 = 24;
    public const byte SetCodecExternal = 25;
    public const byte SetCodecExternalRoom = 32;
    public const byte SetUac = 33;
    public const byte GetVolumeLeft1 = 65;
    public const byte GetVolumeLeft2 = 66;
    public const byte GetVolumeRight1 = 69;
    public const byte GetVolumeRight2 = 70;
    public const byte GetDigitalFilter1 = 71;
    public const byte GetDigitalFilter2 = 72;
    public const byte GetDigitalFilterInternal1 = 73;
    public const byte GetDigitalFilterInternal2 = 74;
    public const byte GetDc03ProDigitalFilter1 = 75;   // == 0x4B, doubles as 64-byte packet header
    public const byte GetDc03ProDigitalFilter2 = 76;
    public const byte GetDsdVolumeLeft1 = 77;
    public const byte GetDsdVolumeLeft2 = 78;
    public const byte GetDsdVolumeRight1 = 79;
    public const byte GetDsdVolumeRight2 = 80;
    public const byte GetRegisterTest = 81;
    public const byte SetRegisterTest = 82;
    public const byte NativeReportVolume = 83;
    public const byte GetGain1 = 84;
    public const byte GetGain2 = 85;
    public const byte GetOutput1 = 86;
    public const byte GetOutput2 = 87;
    public const byte GetFpgaVersion = 88;
    public const byte GetFilter = 89;
    public const byte GetSpdif = 96;
    public const byte GetVolumeKeyControl = 97;
    public const byte GetInit = 98;
    public const byte GetUac = 99;

    private static readonly Dictionary<byte, string> Names = new()
    {
        [PcmNativeReport] = "ID_PCM_NATIVE_REPORT",
        [SetVolumeLeft1] = "ID_SET_VOLUME_LEFT_1",
        [SetVolumeLeft2] = "ID_SET_VOLUME_LEFT_2",
        [SetVolumeRight1] = "ID_SET_VOLUME_RIGHT_1",
        [SetVolumeRight2] = "ID_SET_VOLUME_RIGHT_2",
        [SetBalanceLeft1] = "ID_SET_BALANCE_LEFT_1",
        [SetBalanceLeft2] = "ID_SET_BALANCE_LEFT_2",
        [SetBalanceRight1] = "ID_SET_BALANCE_RIGHT_1",
        [SetBalanceRight2] = "ID_SET_BALANCE_RIGHT_2",
        [SetDsdVolumeLeft1] = "ID_SET_DSD_VOLUME_LEFT_1",
        [SetDsdVolumeLeft2] = "ID_SET_DSD_VOLUME_LEFT_2",
        [SetDsdVolumeRight1] = "ID_SET_DSD_VOLUME_RIGHT_1",
        [SetDsdVolumeRight2] = "ID_SET_DSD_VOLUME_RIGHT_2",
        [SetDigitalFilter1] = "ID_SET_DIGITAL_FILTER_1",
        [SetVolumeLeftRoom] = "ID_SET_VOLUME_LEFT_ROOM",
        [SetVolumeRightRoom] = "ID_SET_VOLUME_RIGHT_ROOM",
        [SetGain1] = "ID_SET_GAIN_1",
        [SetGain2] = "ID_SET_GAIN_2",
        [SetOutput1] = "ID_SET_OUTPUT_1",
        [SetOutput2] = "ID_SET_OUTPUT_2",
        [SetCodecExternal] = "ID_SET_CODEC_EXTERNAL",
        [SetCodecExternalRoom] = "ID_SET_CODEC_EXTERNAL_ROOM",
        [SetUac] = "ID_SET_UAC",
        [GetVolumeLeft1] = "ID_GET_VOLUME_LEFT_1",
        [GetVolumeLeft2] = "ID_GET_VOLUME_LEFT_2",
        [GetVolumeRight1] = "ID_GET_VOLUME_RIGHT_1",
        [GetVolumeRight2] = "ID_GET_VOLUME_RIGHT_2",
        [GetDigitalFilter1] = "ID_GET_DIGITAL_FILTER_1",
        [GetDigitalFilter2] = "ID_GET_DIGITAL_FILTER_2",
        [GetDigitalFilterInternal1] = "ID_GET_DIGITAL_FILTER_INTERNAL_1",
        [GetDigitalFilterInternal2] = "ID_GET_DIGITAL_FILTER_INTERNAL_2",
        [GetDc03ProDigitalFilter1] = "ID_GET_DC03PRO_DIGITAL_FILTER_1",
        [GetDc03ProDigitalFilter2] = "ID_GET_DC03PRO_DIGITAL_FILTER_2",
        [GetDsdVolumeLeft1] = "ID_GET_DSD_VOLUME_LEFT_1",
        [GetDsdVolumeLeft2] = "ID_GET_DSD_VOLUME_LEFT_2",
        [GetDsdVolumeRight1] = "ID_GET_DSD_VOLUME_RIGHT_1",
        [GetDsdVolumeRight2] = "ID_GET_DSD_VOLUME_RIGHT_2",
        [GetRegisterTest] = "ID_GET_REGISTER_TEST",
        [SetRegisterTest] = "ID_SET_REGISTER_TEST",
        [NativeReportVolume] = "ID_NATIVE_REPORT_VOLUME",
        [GetGain1] = "ID_GET_GAIN_1",
        [GetGain2] = "ID_GET_GAIN_2",
        [GetOutput1] = "ID_GET_OUTPUT_1",
        [GetOutput2] = "ID_GET_OUTPUT_2",
        [GetFpgaVersion] = "ID_GET_FPGA_VERSION",
        [GetFilter] = "ID_GET_FILTER",
        [GetSpdif] = "ID_GET_SPDIF",
        [GetVolumeKeyControl] = "ID_GET_VOLUME_KEY_CONTROL",
        [GetInit] = "ID_GET_INIT",
        [GetUac] = "ID_GET_UAC",
    };

    public static string Name(byte id) => Names.TryGetValue(id, out var n) ? n : $"ID_UNKNOWN_{id}";

    public static string Describe(ReadOnlySpan<byte> packet)
    {
        if (packet.Length >= 2 && packet[0] == GetDc03ProDigitalFilter1)
        {
            var mode = packet[1] == 0 ? "GET " : "SET ";
            return $"64B {mode}{Name(packet[4])}/sub={packet[5]:X2} payload=[{string.Join(' ', packet.Slice(6, 4).ToArray().Select(b => b.ToString("X2")))}]";
        }

        if (packet.Length >= 2)
        {
            var kind = packet[1] == SetDigitalFilter1 ? "REG-WRITE" : packet[1] == SetDigitalFilter2 ? "REG-READ " : "??";
            return $"16B {kind} {Name(packet[0])} op={packet[2]:X2} dev={packet[3]:X2} len={packet[6]}";
        }

        return $"?? {packet.Length}B";
    }
}

/// <summary>Sub-opcode / addressing mode carried in byte 2 of the 16-byte register packet.</summary>
public static class RegOp
{
    public const byte Read4ByteOffset = 0xC8; // productId == 1 flavour
    public const byte Read2ByteOffset = 0xE4;
    public const byte Read1ByteOffset = 0xDC; // DC06 flavour
    public const byte Write4ByteOffset = 0x88;
    public const byte Write2ByteOffset = 0xA0;
    public const byte Write1ByteOffset = 0x98;
}

/// <summary>Hardware identification.</summary>
public static class UsbIds
{
    public const ushort VidIbaso = 0x262A;        // 9770
    public const ushort VidDcElite = 0x2FC6;     // 12230 — iBasso DC-Elite / DC07
    public const ushort VidDc04U = 0x0661;       // 1633  — DC04U
    public const ushort VidCx31993 = 0x3302;      // 13058 — CX31993 based units

    public static readonly ushort[] Known = { VidIbaso, VidDcElite, VidDc04U, VidCx31993 };
}

/// <summary>A decoded device response.</summary>
public sealed record UacResponse(byte SeqId, byte[] Raw, byte[] Payload)
{
    public int Value => Payload.Length > 0 ? Payload[0] : 0;
    public bool IsNativeVolumeChange { get; init; }
}

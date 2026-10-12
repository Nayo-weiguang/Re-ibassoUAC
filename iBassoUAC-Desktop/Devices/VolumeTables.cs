namespace iBassoUAC.Devices;

/// <summary>Hardware volume attenuation tables lifted from UsbDeviceController.</summary>
public static class VolumeTables
{
    /// <summary>
    /// nVolumeStep — 64 entries used by the base/CX31993 path.
    /// 0xC100 + 256*i for i = 0..62, then 0. Entry 0 is replaced by 0x9000
    /// on firmware revisions 3 / &gt;= 6 (device configuration byte 12).
    /// </summary>
    public static readonly ushort[] Step =
    {
        0xC100, 0xC200, 0xC300, 0xC400, 0xC500, 0xC600, 0xC700, 0xC800,
        0xC900, 0xCA00, 0xCB00, 0xCC00, 0xCD00, 0xCE00, 0xCF00, 0xD000,
        0xD100, 0xD200, 0xD300, 0xD400, 0xD500, 0xD600, 0xD700, 0xD800,
        0xD900, 0xDA00, 0xDB00, 0xDC00, 0xDD00, 0xDE00, 0xDF00, 0xE000,
        0xE100, 0xE200, 0xE300, 0xE400, 0xE500, 0xE600, 0xE700, 0xE800,
        0xE900, 0xEA00, 0xEB00, 0xEC00, 0xED00, 0xEE00, 0xEF00, 0xF000,
        0xF100, 0xF200, 0xF300, 0xF400, 0xF500, 0xF600, 0xF700, 0xF800,
        0xF900, 0xFA00, 0xFB00, 0xFC00, 0xFD00, 0xFE00, 0xFF00, 0x0000,
    };

    /// <summary>
    /// nVolumeStep2 — 101 entries used by DC03Pro / DC04Pro / DC06 / DC06Pro /
    /// Macaron / DC-Elite. Index 0 is the mute value (255), index 100 is 0 dB.
    /// </summary>
    public static readonly byte[] Step2 =
    {
        255, 155, 150, 145, 140, 135, 130, 125, 120, 115, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101,
        100,  99,  98,  97,  96,  95,  94,  93,  92,  91,  90,  88,  86,  84,  82,  80,  78,  76,  74,  72,
         70,  68,  66,  64,  62,  60,  58,  56,  54,  52,  50,  49,  48,  47,  46,  45,  44,  43,  42,  41,
         40,  39,  38,  37,  36,  35,  34,  33,  32,  31,  30,  29,  28,  27,  26,  25,  24,  23,  22,  21,
         20,  19,  18,  17,  16,  15,  14,  13,  12,  11,  10,   9,   8,   7,   6,   5,   4,   3,   2,   1,
          0,
    };

    /// <summary>Mirrors parseNativeAndSaveVolume: nearest match on the attenuation curve.</summary>
    public static int NearestIndex(byte[] table, int raw, int maxIndex)
    {
        var result = 0;
        for (var i = 0; i < maxIndex; i++)
        {
            var step = table[i];
            if (step == raw) return i;
            if (step > raw && i + 1 <= maxIndex && table[i + 1] <= raw) return i + 1;
        }

        return result;
    }
}

using System.Text.Json.Serialization;

namespace iBassoUAC.Devices;

public static class EqFilterKind
{
    public const int Disable = 0;
    public const int LowShelf = 1;
    public const int Peak = 2;
    public const int HighShelf = 3;
    public const int LowPass = 4;
    public const int HighPass = 5;
    public const int BandPass = 6;
    public const int AllPass = 7;

    public static readonly string[] Names =
    {
        "关闭", "低架增益", "峰值", "高架增益", "低通", "高通", "带通", "全通",
    };

    public static string Name(int value) =>
        value >= 0 && value < Names.Length ? Names[value] : $"未知({value})";
}

public sealed record FilterTypeOption(string Name, int Value);

public sealed class EqBandSetting
{
    private static readonly IReadOnlyList<FilterTypeOption> Options =
        Enumerable.Range(0, EqFilterKind.Names.Length)
                  .Select(i => new FilterTypeOption(EqFilterKind.Names[i], i))
                  .ToList();


    [JsonIgnore]
    public IReadOnlyList<FilterTypeOption> TypeOptions => Options;

    [JsonPropertyName("filterIndex")] public int FilterIndex { get; set; }
    [JsonPropertyName("filterType")] public int FilterType { get; set; } = EqFilterKind.Peak;

    /// <summary>Combo-friendly view over <see cref="FilterType"/>.</summary>
    [JsonIgnore]
    public FilterTypeOption SelectedType
    {
        get => Options.FirstOrDefault(o => o.Value == this.FilterType) ?? Options[0];
        set { if (value is not null) FilterType = value.Value; }
    }

    [JsonPropertyName("frequency")] public int Frequency { get; set; } = 1000;
    [JsonPropertyName("gain")] public double Gain { get; set; }
    [JsonPropertyName("factor")] public double Factor { get; set; } = 0.71;
}

public sealed class EqPreset
{
    [JsonPropertyName("settingId")] public int SettingId { get; set; }
    [JsonPropertyName("name")] public string Name { get; set; } = "自定义";
    [JsonPropertyName("description")] public string Description { get; set; } = string.Empty;
    [JsonPropertyName("totalGain")] public int TotalGain { get; set; }
    [JsonPropertyName("filterSettings")] public List<EqBandSetting> FilterSettings { get; set; } = new();

    public EqBandSetting[] Bands
    {
        get
        {
            while (FilterSettings.Count < 10) FilterSettings.Add(new EqBandSetting { FilterIndex = FilterSettings.Count });
            return FilterSettings.Take(10).ToArray();
        }
    }

    public static EqPreset Flat()
    {
        var preset = new EqPreset { SettingId = 0, Name = "Flat", TotalGain = 0 };
        var freqs = new[] { 32, 64, 125, 250, 500, 1000, 2000, 4000, 8000, 16000 };
        for (var i = 0; i < 10; i++)
        {
            preset.FilterSettings.Add(new EqBandSetting
            {
                FilterIndex = i,
                FilterType = EqFilterKind.Peak,
                Frequency = freqs[i],
                Gain = 0,
                Factor = 0.71,
            });
        }

        return preset;
    }
}

public sealed record EqBand(int PresetIndex, int BandIndex, int FilterType, int Frequency, double Gain, double Factor, int TotalGain);

/// <summary>
/// RBJ-style biquad coefficients, ported 1:1 from com.ibasso.volume.eq.EQProcessor.
/// Only used to preview a curve locally — the DAC derives its own coefficients.
/// </summary>
public static class Biquad
{
    public const double SampleRate = 48000.0;

    public static double[] Coefficients(int filterType, double frequency, double gainDb, double q)
    {
        var w = frequency * 6.283185307179586 / SampleRate;
        var a = Math.Pow(10.0, gainDb / 40.0);          // "pow"   in EQProcessor
        var s = Math.Sin(w);                             // "sin"
        var c = Math.Cos(w);                             // "cos"
        var alpha = s / (q * 2.0);                       // d8 before reassignment
        var twoA = a * 2.0;                              // d9
        var g = Math.Sqrt(twoA) / q;                     // "sqrt"

        // d2 = a1, d3 = b2, d4 = a0, d5 = a2, d8 = b0 (reused slot), d10 = b1
        double d2, d3, d4, d5, d8, d10;
        d10 = 0.0;
        d8 = alpha;

        switch (filterType)
        {
            case EqFilterKind.LowShelf:
            {
                var d11 = a + 1.0;
                var d12 = a - 1.0;
                var d13 = d12 * c;
                var d14 = d11 - d13;
                var d15 = g * s;
                var d17 = c * d11;
                var d18 = d11 + d13;
                d2 = (d12 + d17) * -2.0;
                d3 = a * (d14 - d15);
                d4 = d18 + d15;
                d8 = (d14 + d15) * a;
                d5 = d18 - d15;
                d10 = twoA * (d12 - d17);
                break;
            }
            case EqFilterKind.HighShelf:
            {
                var d21 = a + 1.0;
                var d22 = a - 1.0;
                var d23 = d22 * c;
                var d24 = d21 + d23;
                var d25 = g * s;
                var d26 = c * d21;
                var d27 = d21 - d23;
                d5 = d27 - d25;
                d8 = (d24 + d25) * a;
                d10 = -2.0 * a * (d22 + d26);
                d2 = (d22 - d26) * 2.0;
                d3 = a * (d24 - d25);
                d4 = d27 + d25;
                break;
            }
            case EqFilterKind.LowPass:
                d10 = 1.0 - c;
                d4 = alpha + 1.0;
                d2 = c * -2.0;
                d5 = 1.0 - alpha;
                d8 = (1.0 - c) / 2.0;
                d3 = d8;
                break;
            case EqFilterKind.HighPass:
            {
                var d30 = c + 1.0;
                d4 = alpha + 1.0;
                d2 = c * -2.0;
                d5 = 1.0 - alpha;
                d8 = d30 / 2.0;
                d10 = -d30;
                d3 = d8;
                break;
            }
            case EqFilterKind.BandPass:
                d5 = 1.0 - alpha;
                d2 = c * -2.0;
                d3 = -alpha;
                d4 = alpha + 1.0;
                d8 = alpha;      // b0 = alpha (d8 was never reassigned in this branch)
                break;
            case EqFilterKind.AllPass:
                d4 = alpha + 1.0;
                d2 = c * -2.0;
                d5 = 1.0 - alpha;
                d8 = d5;
                d10 = d2;
                d3 = d4;
                break;
            case EqFilterKind.Peak:
            {
                var d19 = alpha * a;
                var d20 = alpha / a;
                d4 = d20 + 1.0;
                d5 = 1.0 - d20;
                d8 = d19 + 1.0;
                d2 = c * -2.0;
                d3 = 1.0 - d19;
                d10 = d2;
                break;
            }
            default:
                d3 = 0.0; d2 = 0.0; d5 = 0.0; d4 = 1.0; d8 = 1.0;
                break;
        }

        if (Math.Abs(d4) < 1e-12) d4 = 1e-12;
        // FilterCoefficients: f281b = {d8, d10, d3}, f280a = {1, d2, d5}, all divided by d4
        return new[] { d8 / d4, d10 / d4, d3 / d4, 1.0, d2 / d4, d5 / d4 };
    }

    /// <summary>Magnitude of the filter at a given frequency, in dB.</summary>
    public static double ResponseDb(double[] c, double frequency)
    {
        var w = frequency * 2.0 * Math.PI / SampleRate;
        var cos1 = Math.Cos(w);
        var sin1 = Math.Sin(w);
        var cos2 = Math.Cos(2 * w);
        var sin2 = Math.Sin(2 * w);

        var nr = c[0] + c[1] * cos1 + c[2] * cos2;
        var ni = -(c[1] * sin1 + c[2] * sin2);
        var dr = c[3] + c[4] * cos1 + c[5] * cos2;
        var di = -(c[4] * sin1 + c[5] * sin2);

        var mag = Math.Sqrt(nr * nr + ni * ni) / Math.Max(1e-12, Math.Sqrt(dr * dr + di * di));
        return 20.0 * Math.Log10(Math.Max(1e-12, mag));
    }
}



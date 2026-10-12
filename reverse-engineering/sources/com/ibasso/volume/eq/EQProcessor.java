package com.ibasso.volume.eq;
public class EQProcessor {
    private static final double PI = 3.141592653589793d;
    public static double SAMPLE_RATE = 48000.0d;

    public static FilterCoefficients calculateCoefficients(FilterSetting filterSetting) {
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double gain = filterSetting.getGain();
        double factor = filterSetting.getFactor();
        int filterType = filterSetting.getFilterType();
        double frequency = (filterSetting.getFrequency() * 6.283185307179586d) / SAMPLE_RATE;
        double pow = Math.pow(10.0d, gain / 40.0d);
        double sin = Math.sin(frequency);
        double cos = Math.cos(frequency);
        double d8 = sin / (factor * 2.0d);
        double d9 = pow * 2.0d;
        double sqrt = Math.sqrt(d9) / factor;
        double d10 = 0.0d;
        switch (filterType) {
            case 1:
                double d11 = pow + 1.0d;
                double d12 = pow - 1.0d;
                double d13 = d12 * cos;
                double d14 = d11 - d13;
                double d15 = sqrt * sin;
                double d16 = (d14 + d15) * pow;
                double d17 = cos * d11;
                double d18 = d11 + d13;
                d2 = (d12 + d17) * (-2.0d);
                d3 = pow * (d14 - d15);
                d4 = d18 + d15;
                d8 = d16;
                d5 = d18 - d15;
                d10 = d9 * (d12 - d17);
                break;
            case 2:
                double d19 = d8 * pow;
                double d20 = d8 / pow;
                d4 = d20 + 1.0d;
                d5 = 1.0d - d20;
                d8 = d19 + 1.0d;
                d2 = cos * (-2.0d);
                d3 = 1.0d - d19;
                d10 = d2;
                break;
            case 3:
                double d21 = pow + 1.0d;
                double d22 = pow - 1.0d;
                double d23 = d22 * cos;
                double d24 = d21 + d23;
                double d25 = sqrt * sin;
                double d26 = cos * d21;
                double d27 = d21 - d23;
                double d28 = d27 + d25;
                double d29 = (d22 - d26) * 2.0d;
                d5 = d27 - d25;
                d8 = (d24 + d25) * pow;
                d10 = (-2.0d) * pow * (d22 + d26);
                d2 = d29;
                d3 = pow * (d24 - d25);
                d4 = d28;
                break;
            case 4:
                d10 = 1.0d - cos;
                d6 = d8 + 1.0d;
                d7 = cos * (-2.0d);
                d5 = 1.0d - d8;
                d8 = d10 / 2.0d;
                d4 = d6;
                d2 = d7;
                d3 = d8;
                break;
            case 5:
                double d30 = cos + 1.0d;
                d6 = d8 + 1.0d;
                d7 = cos * (-2.0d);
                d5 = 1.0d - d8;
                d8 = d30 / 2.0d;
                d10 = -d30;
                d4 = d6;
                d2 = d7;
                d3 = d8;
                break;
            case 6:
                d5 = 1.0d - d8;
                d2 = cos * (-2.0d);
                d3 = -d8;
                d4 = d8 + 1.0d;
                break;
            case 7:
                d4 = d8 + 1.0d;
                d2 = cos * (-2.0d);
                d5 = 1.0d - d8;
                d8 = d5;
                d10 = d2;
                d3 = d4;
                break;
            default:
                d3 = 0.0d;
                d2 = 0.0d;
                d5 = 0.0d;
                d4 = 1.0d;
                d8 = 1.0d;
                break;
        }
        return new FilterCoefficients(new double[]{d8 / d4, d10 / d4, d3 / d4}, new double[]{1.0d, d2 / d4, d5 / d4});
    }
}

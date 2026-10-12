package com.ibasso.volume.eq;
public class FilterCoefficients {
    public double[] f280a;
    public double[] f281b;

    public FilterCoefficients(double[] dArr, double[] dArr2) {
        this.f281b = dArr;
        this.f280a = dArr2;
    }
}

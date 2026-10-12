package com.ibasso.volume.eq;
public class ComplexUtils {
    public static Complex evaluateFrequencyResponse(double[] dArr, double[] dArr2, double d2) {
        Complex expj = expj(-d2);
        Complex complex = new Complex(0.0d, 0.0d);
        Complex complex2 = new Complex(0.0d, 0.0d);
        Complex complex3 = new Complex(1.0d, 0.0d);
        for (double d3 : dArr) {
            complex = complex.add(complex3.multiply(new Complex(d3, 0.0d)));
            complex3 = complex3.multiply(expj);
        }
        Complex complex4 = new Complex(1.0d, 0.0d);
        for (double d4 : dArr2) {
            complex2 = complex2.add(complex4.multiply(new Complex(d4, 0.0d)));
            complex4 = complex4.multiply(expj);
        }
        return complex.divide(complex2);
    }

    public static Complex expj(double d2) {
        return new Complex(Math.cos(d2), Math.sin(d2));
    }
}

package com.ibasso.volume.eq;
public class Complex {
    public final double im;
    public final double re;

    public Complex(double d2, double d3) {
        this.re = d2;
        this.im = d3;
    }

    public static Complex fromPolar(double d2, double d3) {
        return new Complex(Math.cos(d3) * d2, d2 * Math.sin(d3));
    }

    public double abs() {
        return Math.hypot(this.re, this.im);
    }

    public Complex add(Complex complex) {
        return new Complex(this.re + complex.re, this.im + complex.im);
    }

    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    public Complex divide(Complex complex) {
        double d2 = complex.re;
        double d3 = complex.im;
        double d4 = (d2 * d2) + (d3 * d3);
        double d5 = this.re;
        double d6 = this.im;
        return new Complex(((d5 * d2) + (d6 * d3)) / d4, ((d6 * d2) - (d5 * d3)) / d4);
    }

    public Complex multiply(Complex complex) {
        double d2 = this.re;
        double d3 = complex.re;
        double d4 = this.im;
        double d5 = complex.im;
        return new Complex((d2 * d3) - (d4 * d5), (d2 * d5) + (d4 * d3));
    }

    public double phase() {
        return Math.atan2(this.im, this.re);
    }

    public Complex subtract(Complex complex) {
        return new Complex(this.re - complex.re, this.im - complex.im);
    }

    public String toString() {
        return String.format("(%f %c %fi)", Double.valueOf(this.re), Character.valueOf(this.im >= 0.0d ? '+' : '-'), Double.valueOf(Math.abs(this.im)));
    }
}

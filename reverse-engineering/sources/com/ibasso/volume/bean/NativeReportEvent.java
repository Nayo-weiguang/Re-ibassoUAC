package com.ibasso.volume.bean;
public class NativeReportEvent {
    public int type;
    public int value;
    public int value1;
    public int value2;
    public int value3;

    public NativeReportEvent(int i, int i2) {
        this.type = i;
        this.value = i2;
    }

    public NativeReportEvent(int i, int i2, int i3) {
        this.type = i;
        this.value = i2;
        this.value1 = i3;
    }

    public NativeReportEvent(int i, int i2, int i3, int i4) {
        this.type = i;
        this.value = i2;
        this.value1 = i3;
        this.value2 = i4;
    }

    public NativeReportEvent(int i, int i2, int i3, int i4, int i5) {
        this.type = i;
        this.value = i2;
        this.value1 = i3;
        this.value2 = i4;
        this.value3 = i5;
    }
}

package com.ibasso.volume;
public class ResponseEvent {
    public byte[] data;
    public int rightVolume;
    public int seqId;
    public int size;
    public int value;

    public ResponseEvent(int i, int i2, int i3) {
        this.seqId = i;
        this.value = i2;
        this.size = i3;
    }

    public ResponseEvent(int i, int i2, int i3, int i4) {
        this.seqId = i;
        this.value = i2;
        this.rightVolume = i3;
        this.size = i4;
    }

    public ResponseEvent(int i, byte[] bArr, int i2) {
        this.seqId = i;
        this.size = i2;
        this.data = bArr;
    }
}

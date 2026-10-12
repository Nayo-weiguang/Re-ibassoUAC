package com.ibasso.volume;

import android.util.Log;
public class Task {
    private static final String TAG = "Task";
    public static final int finished = 2;
    public static final int idle = 0;
    public static final int running = 1;
    public byte bI2CRwOffset;
    public byte bI2CRwOffset2;
    public byte bI2CRwOffset3;
    public byte bI2CRwOffset4;
    public byte bI2cDevAdr;
    public byte bSeqID;
    public byte[] data;
    public int isCustomCommand;
    public boolean isWaited;
    public boolean isWriteTask;
    private final Object lock;
    public boolean needRetry;
    public int productId;
    public int regOffsetBytes;
    public int state;
    public int value;

    public Task(byte b2, byte b3, byte b4) {
        this.productId = 0;
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.isWriteTask = false;
    }

    public Task(byte b2, byte b3, byte b4, byte b5) {
        this.productId = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.regOffsetBytes = 2;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.bI2CRwOffset2 = b5;
        this.isWriteTask = false;
    }

    public Task(byte b2, byte b3, byte b4, byte b5, int i) {
        this.productId = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.regOffsetBytes = 2;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.bI2CRwOffset2 = b5;
        this.value = i;
        this.isWriteTask = true;
    }

    public Task(byte b2, byte b3, byte b4, int i) {
        this.productId = 0;
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.value = i;
        this.isWriteTask = true;
    }

    public Task(byte b2, byte[] bArr) {
        this.productId = 0;
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.needRetry = true;
        this.bSeqID = b2;
        this.isCustomCommand = 1;
        this.data = bArr;
    }

    public Task(int i, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.productId = i;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.bI2CRwOffset2 = b5;
        this.bI2CRwOffset3 = b6;
        this.bI2CRwOffset4 = b7;
        this.isWriteTask = false;
    }

    public Task(int i, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, int i2) {
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.productId = i;
        this.bSeqID = b2;
        this.bI2cDevAdr = b3;
        this.bI2CRwOffset = b4;
        this.bI2CRwOffset2 = b5;
        this.bI2CRwOffset3 = b6;
        this.bI2CRwOffset4 = b7;
        this.value = i2;
        this.isWriteTask = true;
    }

    public Task(byte[] bArr) {
        this.productId = 0;
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.isCustomCommand = -1;
        this.needRetry = true;
        this.bSeqID = bArr[0];
        this.isCustomCommand = 2;
        this.data = bArr;
    }

    public Task(byte[] bArr, int i) {
        this.productId = 0;
        this.regOffsetBytes = 0;
        this.state = 0;
        this.lock = new Object();
        this.needRetry = true;
        this.bSeqID = (byte) i;
        this.isCustomCommand = i;
        this.data = bArr;
    }

    public void notifyFinished() {
        Log.i("task", "notifyFinished " + IConstants.idToString(this.bSeqID));
        if (this.isWaited) {
            notifyAll();
        }
    }

    public void waitForFinish() {
        Log.i("task", "waitForFinish " + IConstants.idToString(this.bSeqID));
        this.isWaited = true;
        try {
            wait(200L);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.isWaited = false;
    }
}

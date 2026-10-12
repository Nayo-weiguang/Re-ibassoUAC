package com.ibasso.volume;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
public class MonitorThread extends Thread implements IConstants {
    private final String TAG = "UacManager";
    private UsbInterface mInterface;
    private final UsbDeviceConnection mUsbConn;
    private final UsbDevice mUsbDevice;
    private final OnRequestCallBackListener onRequestCallBackListener;
    private boolean startMonitor;

    public interface OnRequestCallBackListener {
        void onRequestDone(byte[] bArr);
    }

    public MonitorThread(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection, OnRequestCallBackListener onRequestCallBackListener) {
        this.mUsbDevice = usbDevice;
        this.mUsbConn = usbDeviceConnection;
        this.onRequestCallBackListener = onRequestCallBackListener;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not decompiled: com.ibasso.volume.MonitorThread.run():void");
    }

    @Override
    public synchronized void start() {
        this.startMonitor = true;
        super.start();
    }

    public void stopMonitor() {
        this.startMonitor = false;
    }
}

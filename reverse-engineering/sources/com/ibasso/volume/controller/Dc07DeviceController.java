package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.widget.Toast;
import com.ibasso.volume.DigitalFilterEvent;
import com.ibasso.volume.IConstants;
import com.ibasso.volume.R;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.Task;
import com.ibasso.volume.TaskManager;
import com.ibasso.volume.bean.NativeReportEvent;
import org.greenrobot.eventbus.EventBus;
public class Dc07DeviceController extends UsbDeviceController {
    public Dc07DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getDc03ProBalance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getDc07SpdifMode();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getDc07DigitalFilter();
    }

    @Override
    public int getDsdFilter() {
        return SpStorage.getInstance().getDc07DsdFilter();
    }

    @Override
    public void getFpgaVersion() {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_FPGA_VERSION, -15, IConstants.ID_SET_DSD_BALANCE_LEFT_2, 0, 0, 0, 0, 0}));
    }

    @Override
    public int getKeyControlStatus() {
        return SpStorage.getInstance().getDc07KeyControlStatus();
    }

    @Override
    public int getMaxVolume() {
        return this.nVolumeStep2.length - 1;
    }

    @Override
    public int getPcmTrim() {
        return SpStorage.getInstance().getDc07PcmTrim();
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc03ProVolume();
    }

    @Override
    public void initDevice() {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_FILTER, IConstants.ID_SET_UAC, -34, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_INIT, 49, -50, 0, 0, 0, 0, 0}));
        getFpgaVersion();
    }

    @Override
    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
        SpStorage.getInstance().setDc07SpdifMode(nativeReportEvent.value);
        SpStorage.getInstance().setDc07PcmTrim(nativeReportEvent.value1);
        SpStorage.getInstance().setDc07DsdVolumeCompensation(nativeReportEvent.value2);
        SpStorage.getInstance().setDc07KeyControlStatus(nativeReportEvent.value3);
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        return 0;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    public void sendGetDc07DigitalFilterRegisterRequest() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_DIGITAL_FILTER_1, IConstants.ID_SET_UAC, -34, 0, 0, 0, 0, 0}));
    }

    @Override
    public void setBalanceVolume(int i) {
    }

    @Override
    public void setCodecExternal(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) i, (byte) SpStorage.getInstance().getDc07PcmTrim(), (byte) SpStorage.getInstance().getDc07DsdVolumeCompensation(), (byte) SpStorage.getInstance().getDc07KeyControlStatus()}));
        SpStorage.getInstance().setDc07SpdifMode(i);
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, -33, 0, (byte) i, (byte) SpStorage.getInstance().getDc07DsdFilter(), 0, 0}));
        SpStorage.getInstance().setDc07DigitalFilter(i);
    }

    @Override
    public void setDsdFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, -33, 0, (byte) SpStorage.getInstance().getDc07DigitalFilter(), (byte) i, 0, 0}));
        SpStorage.getInstance().setDc07DsdFilter(i);
    }

    @Override
    public void setGain(int i) {
    }

    @Override
    public void setKeyControlStatus(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getDc07SpdifMode(), (byte) SpStorage.getInstance().getDc07PcmTrim(), (byte) SpStorage.getInstance().getDc07DsdVolumeCompensation(), (byte) i}));
        SpStorage.getInstance().setDc07KeyControlStatus(i);
    }

    @Override
    public void setOutput(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getDc07SpdifMode(), (byte) SpStorage.getInstance().getDc07PcmTrim(), (byte) i, (byte) SpStorage.getInstance().getDc07KeyControlStatus()}));
        SpStorage.getInstance().setDc07DsdVolumeCompensation(i);
    }

    @Override
    public void setPcmTrim(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getDc07SpdifMode(), (byte) i, (byte) SpStorage.getInstance().getDc07DsdVolumeCompensation(), (byte) SpStorage.getInstance().getDc07KeyControlStatus()}));
        SpStorage.getInstance().setDc07PcmTrim(i);
    }

    @Override
    public void setVolume(int i) {
    }
}

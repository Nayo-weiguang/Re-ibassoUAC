package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.widget.Toast;
import com.google.android.material.timepicker.TimeModel;
import com.ibasso.volume.DigitalFilterEvent;
import com.ibasso.volume.IConstants;
import com.ibasso.volume.R;
import com.ibasso.volume.ResponseEvent;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.Task;
import com.ibasso.volume.TaskManager;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.bean.NativeReportEvent;
import kotlin.UByte;
import org.greenrobot.eventbus.EventBus;
public class Dc09DeviceController extends UsbDeviceController {
    public Dc09DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getDc09Balance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getDc09SpdifMode();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getDc09DigitalFilter();
    }

    @Override
    public void getFpgaVersion() {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_FPGA_VERSION, -15, IConstants.ID_SET_DSD_BALANCE_LEFT_2, 0, 0, 0, 0, 0}));
    }

    @Override
    public int getKeyControlStatus() {
        return SpStorage.getInstance().getDc09KeyControlStatus();
    }

    @Override
    public int getMaxVolume() {
        return 100;
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc09Volume();
    }

    @Override
    public void initDevice() {
        getFpgaVersion();
        getFpgaVersion();
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_FILTER, IConstants.ID_SET_UAC, -34, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_VOLUME_LEFT_2, IConstants.ID_SET_DIGITAL_FILTER_1, -18, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_SPDIF, 49, -50, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_VOLUME_KEY_CONTROL, IConstants.ID_GET_VOLUME_LEFT_1, -66, 0, 0, 0, 0, 0}));
    }

    @Override
    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
        int i = nativeReportEvent.type;
        if (89 == i) {
            SpStorage.getInstance().setDc09DigitalFilter(nativeReportEvent.value);
            SpStorage.getInstance().setDc09HpFilter(nativeReportEvent.value1);
        } else if (96 == i) {
            SpStorage.getInstance().setDc09SpdifMode(nativeReportEvent.value);
            SpStorage.getInstance().setDc09Balance(nativeReportEvent.value1);
            SpStorage.getInstance().setDc09Gain(nativeReportEvent.value2);
        } else if (97 == i) {
            SpStorage.getInstance().setDc09ScreenRotation(nativeReportEvent.value);
            SpStorage.getInstance().setDc09KeyControlStatus(nativeReportEvent.value1);
            SpStorage.getInstance().setDc09ScreenTime(nativeReportEvent.value2);
            SpStorage.getInstance().setDc09Output(nativeReportEvent.value3);
        }
    }

    @Override
    public boolean parseCallBackValue(ResponseEvent responseEvent) {
        if (88 == responseEvent.seqId) {
            EventBus eventBus = EventBus.getDefault();
            eventBus.postSticky(new FpgaVersionEvent((responseEvent.data[4] & UByte.MAX_VALUE) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(responseEvent.data[5] & UByte.MAX_VALUE)) + "." + Integer.toHexString(responseEvent.data[6] & UByte.MAX_VALUE)));
            return true;
        }
        return false;
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        SpStorage.getInstance().setDc09Volume(i);
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    @Override
    public void setBalanceVolume(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getDc09SpdifMode(), (byte) i, (byte) SpStorage.getInstance().getDc09Gain(), 0}));
        SpStorage.getInstance().setDc09Balance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) i, (byte) SpStorage.getInstance().getDc09Balance(), (byte) SpStorage.getInstance().getDc09Gain(), 0}));
        SpStorage.getInstance().setDc09SpdifMode(i);
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, -33, 0, (byte) i, (byte) SpStorage.getInstance().getDc09HpFilter(), 0, 0}));
        SpStorage.getInstance().setDc09DigitalFilter(i);
    }

    @Override
    public void setGain(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getDc09SpdifMode(), (byte) SpStorage.getInstance().getDc09Balance(), (byte) i, 0}));
        SpStorage.getInstance().setDc09Gain(i);
    }

    @Override
    public void setHpFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, -33, 0, (byte) SpStorage.getInstance().getDc09DigitalFilter(), (byte) i, 0, 0}));
        SpStorage.getInstance().setDc09HpFilter(i);
    }

    @Override
    public void setKeyControlStatus(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) SpStorage.getInstance().getDc09ScreenRotation(), (byte) i, (byte) SpStorage.getInstance().getDc09ScreenTime(), (byte) SpStorage.getInstance().getDc09Output()}));
        SpStorage.getInstance().setDc09KeyControlStatus(i);
    }

    @Override
    public void setOutput(int i) {
        byte b2 = (byte) i;
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) SpStorage.getInstance().getDc09ScreenRotation(), (byte) SpStorage.getInstance().getDc09KeyControlStatus(), (byte) SpStorage.getInstance().getDc09ScreenTime(), b2}));
        SpStorage.getInstance().setDc09Output(b2);
    }

    @Override
    public void setScreenRotation(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) i, (byte) SpStorage.getInstance().getDc09KeyControlStatus(), (byte) SpStorage.getInstance().getDc09ScreenTime(), (byte) SpStorage.getInstance().getDc09Output()}));
        SpStorage.getInstance().setDc09ScreenRotation(i);
    }

    @Override
    public void setScreenTime(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) SpStorage.getInstance().getDc09ScreenRotation(), (byte) SpStorage.getInstance().getDc09KeyControlStatus(), (byte) i, (byte) SpStorage.getInstance().getDc09Output()}));
        SpStorage.getInstance().setDc09ScreenTime(i);
    }

    @Override
    public void setVolume(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte b2 = (byte) i;
        this.mTaskManager.postTask(new Task(new byte[]{1, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, -17, b2, b2, 0, 0, 0}));
    }
}

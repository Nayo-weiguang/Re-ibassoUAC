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
public class TonfaDeviceController extends UsbDeviceController {
    public TonfaDeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getTonfaBalance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getTonfaSpdifMode();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getTonfaDigitalFilter();
    }

    @Override
    public void getFpgaVersion() {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_GET_FPGA_VERSION, -15, IConstants.ID_SET_DSD_BALANCE_LEFT_2, 0, 0, 0, 0, 0}));
    }

    @Override
    public int getKeyControlStatus() {
        return SpStorage.getInstance().getTonfaKeyControlStatus();
    }

    @Override
    public int getMaxVolume() {
        return 100;
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getTonfaVolume();
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
            SpStorage.getInstance().setTonfaDigitalFilter(nativeReportEvent.value);
        } else if (96 == i) {
            SpStorage.getInstance().setTonfaSpdifMode(nativeReportEvent.value);
            SpStorage.getInstance().setTonfaBalance(nativeReportEvent.value1);
            SpStorage.getInstance().setTonfaGain(nativeReportEvent.value2);
        } else if (97 == i) {
            SpStorage.getInstance().setTonfaScreenRotation(nativeReportEvent.value);
            SpStorage.getInstance().setTonfaKeyControlStatus(nativeReportEvent.value1);
            SpStorage.getInstance().setTonfaScreenTime(nativeReportEvent.value2);
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
        SpStorage.getInstance().setTonfaVolume(i);
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    @Override
    public void setBalanceVolume(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getTonfaSpdifMode(), (byte) i, (byte) SpStorage.getInstance().getTonfaGain(), 0}));
        SpStorage.getInstance().setTonfaBalance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) i, (byte) SpStorage.getInstance().getTonfaBalance(), (byte) SpStorage.getInstance().getTonfaGain(), 0}));
        SpStorage.getInstance().setTonfaSpdifMode(i);
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, -33, 0, (byte) i, 0, 0, 0}));
        SpStorage.getInstance().setTonfaDigitalFilter(i);
    }

    @Override
    public void setGain(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 48, -49, 0, (byte) SpStorage.getInstance().getTonfaSpdifMode(), (byte) SpStorage.getInstance().getTonfaBalance(), (byte) i, 0}));
        SpStorage.getInstance().setTonfaGain(i);
    }

    @Override
    public void setKeyControlStatus(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) SpStorage.getInstance().getTonfaScreenRotation(), (byte) i, (byte) SpStorage.getInstance().getTonfaScreenTime(), 0}));
        SpStorage.getInstance().setTonfaKeyControlStatus(i);
    }

    @Override
    public void setScreenRotation(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) i, (byte) SpStorage.getInstance().getTonfaKeyControlStatus(), (byte) SpStorage.getInstance().getTonfaScreenTime(), 0}));
        SpStorage.getInstance().setTonfaScreenRotation(i);
    }

    @Override
    public void setScreenTime(int i) {
        this.mTaskManager.postTask(new Task(new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, 64, -65, 0, (byte) SpStorage.getInstance().getTonfaScreenRotation(), (byte) SpStorage.getInstance().getTonfaKeyControlStatus(), (byte) i, 0}));
        SpStorage.getInstance().setTonfaScreenTime(i);
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

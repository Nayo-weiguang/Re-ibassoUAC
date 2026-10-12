package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;
import android.widget.Toast;
import com.ibasso.volume.NormalVolumeEvent;
import com.ibasso.volume.R;
import com.ibasso.volume.ResponseEvent;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.Task;
import com.ibasso.volume.TaskManager;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.eq.ParametricEqualizer;
import org.greenrobot.eventbus.EventBus;
public class CX31993DeviceController extends UsbDeviceController {
    protected TaskManager mTaskManager;

    public CX31993DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = null;
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    @Override
    public void dumpFlash() {
    }

    @Override
    public void eraseFlash() {
    }

    @Override
    public int getAmpMode() {
        return 0;
    }

    @Override
    public int getBalanceVolume() {
        return 0;
    }

    @Override
    public int getCodecExternal() {
        return 0;
    }

    @Override
    public int getDigitalFilter() {
        return 0;
    }

    @Override
    public int getDsdFilter() {
        return 0;
    }

    @Override
    public int getEqualizer() {
        return 0;
    }

    @Override
    public void getFpgaVersion() {
    }

    @Override
    public int getGain() {
        return 0;
    }

    @Override
    public int getHpFilter() {
        return 0;
    }

    @Override
    public int getKeyControlStatus() {
        return 0;
    }

    @Override
    public int getLanguage() {
        return 0;
    }

    @Override
    public int getMaxVolume() {
        return UsbDeviceController.nVolumeStep.length - 1;
    }

    @Override
    public int getMicMode() {
        return 0;
    }

    @Override
    public int getOpVoltage() {
        return 0;
    }

    @Override
    public int getPcmTrim() {
        return 0;
    }

    @Override
    public int getUacVersion() {
        return 0;
    }

    @Override
    public int getVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        return -1;
    }

    @Override
    public void initDevice() {
        if (!SpStorage.getInstance().getResetLastValue()) {
            setVolume(getMaxVolume());
            return;
        }
        int volume = SpStorage.getInstance().getVolume();
        if (volume != -1) {
            setVolume(volume);
        }
    }

    @Override
    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
    }

    @Override
    public boolean parseCallBackValue(ResponseEvent responseEvent) {
        return true;
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
    }

    @Override
    public void setAmpMode(int i) {
    }

    @Override
    public void setBalanceVolume(int i) {
    }

    @Override
    public void setCodecExternal(int i) {
    }

    @Override
    public void setDigitalFilter(int i) {
    }

    @Override
    public void setDsdFilter(int i) {
    }

    @Override
    public void setEqualizer(int i) {
    }

    @Override
    public void setGain(int i) {
    }

    @Override
    public void setHpFilter(int i) {
    }

    @Override
    public void setKeyControlStatus(int i) {
    }

    @Override
    public void setLanguage(int i) {
    }

    @Override
    public void setMicMode(int i) {
    }

    @Override
    public void setOpVoltage(int i) {
    }

    @Override
    public void setOutput(int i) {
    }

    @Override
    public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
    }

    @Override
    public void setPcmTrim(int i) {
    }

    @Override
    public void setScreenRotation(int i) {
    }

    @Override
    public void setScreenTime(int i) {
    }

    @Override
    public void setUacVersion(int i) {
    }

    @Override
    public void setVolume(int i) {
        if (this.mUsbDevice == null) {
            Log.i("UacManager", "[Set RVolume]Open Device Fail");
        } else if (i > getMaxVolume() || i < 0) {
            Toast.makeText(this.mContext, (int) R.string.setting_invalid, 0).show();
        } else {
            this.mTaskManager.postTask(new Task(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)}, 3));
            EventBus.getDefault().postSticky(new NormalVolumeEvent(i));
        }
    }

    @Override
    public void stopTaskManager() {
        this.mUsbDevice = null;
        TaskManager taskManager = this.mTaskManager;
        if (taskManager != null) {
            taskManager.stopTaskManager();
        }
    }
}

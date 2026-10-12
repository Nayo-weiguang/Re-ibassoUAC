package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;
import android.widget.Toast;
import com.ibasso.volume.DigitalFilterEvent;
import com.ibasso.volume.IConstants;
import com.ibasso.volume.R;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.Task;
import com.ibasso.volume.TaskManager;
import org.greenrobot.eventbus.EventBus;
public class Dc08DeviceController extends UsbDeviceController {
    public Dc08DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    private void getDC03ProLeftVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task((byte) IConstants.ID_GET_VOLUME_LEFT_1, (byte) -94, (byte) 0, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_2));
    }

    private void getDC03ProRightVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task((byte) IConstants.ID_GET_VOLUME_LEFT_1, (byte) -94, (byte) 0, (byte) IConstants.ID_SET_DIGITAL_FILTER_1));
    }

    private void setDc03ProLeftVolume(byte b2, byte b3, byte b4, byte b5, int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        byte b6 = (byte) i;
        this.mTaskManager.postTask(new Task(1, b2, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 1, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b3, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 2, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b4, IConstants.ID_GET_SPDIF, (byte) 7, (byte) 0, (byte) 0, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b5, IConstants.ID_GET_SPDIF, (byte) 7, (byte) 0, (byte) 1, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_LEFT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
    }

    private void setDc03ProLeftVolume(int i) {
        setDc03ProLeftVolume((byte) 1, (byte) 2, (byte) 9, (byte) 10, i);
    }

    private void setDc03ProRightVolume(byte b2, byte b3, byte b4, byte b5, int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        byte b6 = (byte) i;
        this.mTaskManager.postTask(new Task(1, b2, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 1, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b3, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 2, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b4, IConstants.ID_GET_INIT, (byte) 7, (byte) 0, (byte) 0, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(1, b5, IConstants.ID_GET_INIT, (byte) 7, (byte) 0, (byte) 1, (byte) 0, b6));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_RIGHT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_1, i));
    }

    private void setDc03ProRightVolume(int i) {
        setDc03ProRightVolume((byte) 3, (byte) 4, IConstants.ID_SET_DSD_VOLUME_RIGHT_1, IConstants.ID_SET_DSD_VOLUME_RIGHT_2, i);
    }

    private void setDc03ProVolume(byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, int i, int i2) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        byte b10 = (byte) i;
        this.mTaskManager.postTask(new Task(1, b2, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 1, (byte) 0, b10));
        this.mTaskManager.postTask(new Task(1, b3, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 2, (byte) 0, b10));
        byte b11 = (byte) i2;
        this.mTaskManager.postTask(new Task(1, b6, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 1, (byte) 0, b11));
        this.mTaskManager.postTask(new Task(1, b7, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 2, (byte) 0, b11));
        this.mTaskManager.postTask(new Task(1, b4, IConstants.ID_GET_SPDIF, (byte) 7, (byte) 0, (byte) 0, (byte) 0, b10));
        this.mTaskManager.postTask(new Task(1, b5, IConstants.ID_GET_SPDIF, (byte) 7, (byte) 0, (byte) 1, (byte) 0, b10));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_LEFT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
        this.mTaskManager.postTask(new Task(1, b8, IConstants.ID_GET_INIT, (byte) 7, (byte) 0, (byte) 0, (byte) 0, b11));
        this.mTaskManager.postTask(new Task(1, b9, IConstants.ID_GET_INIT, (byte) 7, (byte) 0, (byte) 1, (byte) 0, b11));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_RIGHT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_1, i2));
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getDc08Balance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getDc08CodecExternal();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getDc08DigitalFilter();
    }

    @Override
    public void getFpgaVersion() {
    }

    @Override
    public int getMaxVolume() {
        return this.nVolumeStep2.length - 1;
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc08Volume();
    }

    @Override
    public void initDevice() {
        int dc08Volume = SpStorage.getInstance().getDc08Volume();
        this.mVolume = dc08Volume;
        if (dc08Volume != -1) {
            setVolume(dc08Volume);
        }
        int dc08DigitalFilter = SpStorage.getInstance().getDc08DigitalFilter();
        if (dc08DigitalFilter != -1) {
            setDigitalFilter(dc08DigitalFilter);
        }
        setGain(SpStorage.getInstance().getDc08Gain());
        setOutput(SpStorage.getInstance().getDc08Output());
        int dc08Balance = SpStorage.getInstance().getDc08Balance();
        if (dc08Balance != 0) {
            setBalanceVolume(dc08Balance);
        }
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= getMaxVolume()) {
                break;
            }
            int[] iArr = this.nVolumeStep2;
            int i4 = iArr[i3];
            if (i4 == i) {
                i2 = i3;
                break;
            }
            if (i4 > i) {
                int i5 = i3 + 1;
                if (iArr[i5] <= i) {
                    i2 = i5;
                    break;
                }
            }
            i3++;
        }
        SpStorage.getInstance().setDc08Volume(i2);
        return i2;
    }

    public void sendGetDc03ProDigitalFilterRegisterRequest() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(1, IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 0, (byte) 0));
        this.mTaskManager.postTask(new Task(1, IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_2, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 0, (byte) 0));
    }

    @Override
    public void setBalanceVolume(int i) {
        int volume = getVolume();
        this.mVolume = volume;
        int i2 = this.nVolumeStep2[volume];
        this.mBalanceVolume = i;
        if (i >= 0) {
            int i3 = i2 - i;
            if (i2 == 255) {
                i3 = i2;
            }
            setDc03ProVolume((byte) 5, (byte) 6, IConstants.ID_SET_DSD_BALANCE_LEFT_1, IConstants.ID_SET_DSD_BALANCE_LEFT_2, (byte) 7, (byte) 8, IConstants.ID_SET_DSD_BALANCE_RIGHT_1, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i2, Math.max(0, i3));
        } else {
            int i4 = i2 + i;
            if (i2 == 255) {
                i4 = i2;
            }
            setDc03ProVolume((byte) 5, (byte) 6, IConstants.ID_SET_DSD_BALANCE_LEFT_1, IConstants.ID_SET_DSD_BALANCE_LEFT_2, (byte) 7, (byte) 8, IConstants.ID_SET_DSD_BALANCE_RIGHT_1, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, Math.max(0, i4), i2);
        }
        SpStorage.getInstance().setDc08Balance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        SpStorage.getInstance().setDc08CodecExternal(i);
        if (i == 1) {
            this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_CODEC_EXTERNAL, new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, IConstants.ID_SET_DIGITAL_FILTER_1, -104, 44, 2, 0, 1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, 0, 0, 0, 0, 0, 0, 0, 0}));
            this.mTaskManager.postTask(new Task(IConstants.ID_SET_CODEC_EXTERNAL_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_2, 32));
            return;
        }
        this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_CODEC_EXTERNAL, new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, IConstants.ID_SET_DIGITAL_FILTER_1, -104, 44, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_CODEC_EXTERNAL_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_2, 0));
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        SpStorage.getInstance().setDc08DigitalFilter(i);
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
        byte b2 = (byte) i;
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_DIGITAL_FILTER_1, IConstants.ID_GET_SPDIF, (byte) 9, (byte) 0, (byte) 0, (byte) 0, b2));
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_DIGITAL_FILTER_2, IConstants.ID_GET_INIT, (byte) 9, (byte) 0, (byte) 0, (byte) 0, b2));
    }

    @Override
    public void setGain(int i) {
        int i2 = 0;
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        SpStorage.getInstance().setDc08Gain(i);
        if (i == 2) {
            i2 = 49;
        } else if (i != 0) {
            i2 = 32;
        }
        byte b2 = (byte) i2;
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_GAIN_1, IConstants.ID_GET_SPDIF, (byte) 8, (byte) 0, (byte) 0, (byte) 0, b2));
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_GAIN_2, IConstants.ID_GET_INIT, (byte) 8, (byte) 0, (byte) 0, (byte) 0, b2));
    }

    @Override
    public void setOutput(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        SpStorage.getInstance().setDc08Output(i);
        byte b2 = (byte) (i == 1 ? 30 : 28);
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_OUTPUT_1, IConstants.ID_GET_SPDIF, IConstants.ID_SET_DSD_VOLUME_RIGHT_1, (byte) 0, (byte) 0, (byte) 0, b2));
        this.mTaskManager.postTask(new Task(1, IConstants.ID_SET_OUTPUT_2, IConstants.ID_GET_INIT, IConstants.ID_SET_DSD_VOLUME_RIGHT_1, (byte) 0, (byte) 0, (byte) 0, b2));
    }

    @Override
    public void setVolume(int i) {
        this.mVolume = i;
        this.mBalanceVolume = getBalanceVolume();
        int i2 = this.nVolumeStep2[i];
        Log.i("UacManager", "-----------------------------------setVolumeForDc03Pro " + String.format("deviceVolume %02x", Integer.valueOf(i2)));
        int i3 = this.mBalanceVolume;
        if (i3 == 0) {
            setDc03ProVolume((byte) 1, (byte) 2, (byte) 9, (byte) 10, (byte) 3, (byte) 4, IConstants.ID_SET_DSD_VOLUME_RIGHT_1, IConstants.ID_SET_DSD_VOLUME_RIGHT_2, i2, i2);
            getDC03ProRightVolume();
        } else if (i3 >= 0) {
            int i4 = i2 - i3;
            if (i2 == 255) {
                i4 = i2;
            }
            setDc03ProVolume((byte) 1, (byte) 2, (byte) 9, (byte) 10, (byte) 7, (byte) 8, IConstants.ID_SET_DSD_BALANCE_RIGHT_1, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i2, Math.max(0, i4));
            getDC03ProLeftVolume();
        } else {
            int i5 = i3 + i2;
            if (i2 == 255) {
                i5 = i2;
            }
            setDc03ProVolume((byte) 5, (byte) 6, IConstants.ID_SET_DSD_BALANCE_LEFT_1, IConstants.ID_SET_DSD_BALANCE_LEFT_2, (byte) 3, (byte) 4, IConstants.ID_SET_DSD_VOLUME_RIGHT_1, IConstants.ID_SET_DSD_VOLUME_RIGHT_2, Math.max(0, i5), i2);
            getDC03ProRightVolume();
        }
    }
}

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
public class Dc06ProDeviceController extends UsbDeviceController {
    public Dc06ProDeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    private void getDc06ProLeftVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_LEFT_1, (byte) -112, IConstants.ID_SET_DSD_BALANCE_RIGHT_2));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_LEFT_2, (byte) -112, IConstants.ID_SET_DSD_BALANCE_RIGHT_1));
    }

    private void getDc06ProRightVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_RIGHT_1, (byte) -110, IConstants.ID_SET_DSD_BALANCE_RIGHT_2));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_RIGHT_2, (byte) -110, IConstants.ID_SET_DSD_BALANCE_RIGHT_1));
    }

    private void setVolume(byte b2, byte b3, byte b4, byte b5, int i, int i2) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        Log.i("UacManager", "-----------setVolume leftVolume: " + String.format("value 0x%02x", Integer.valueOf(i)) + " rightVolume: " + String.format("value 0x%02x", Integer.valueOf(i2)));
        this.mTaskManager.postTask(new Task(b2, (byte) -112, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
        this.mTaskManager.postTask(new Task(b3, (byte) -112, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_1, i));
        this.mTaskManager.postTask(new Task(b4, (byte) -110, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i2));
        this.mTaskManager.postTask(new Task(b5, (byte) -110, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_1, i2));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_LEFT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_VOLUME_RIGHT_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_1, i2));
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getDc06ProBalance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getDc06ProCodecExternal();
    }

    public int getDc06ProDigitalFilter() {
        return SpStorage.getInstance().getDc06ProDigitalFilter();
    }

    @Override
    public void getFpgaVersion() {
        this.mTaskManager.postTask(new Task((byte) IConstants.ID_GET_FPGA_VERSION, new byte[]{IConstants.ID_GET_FPGA_VERSION, IConstants.ID_SET_DIGITAL_FILTER_2, -36, 44, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }

    @Override
    public int getMaxVolume() {
        return this.nVolumeStep2.length - 1;
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc06ProVolume();
    }

    @Override
    public void initDevice() {
        int dc06ProVolume = SpStorage.getInstance().getDc06ProVolume();
        this.mVolume = dc06ProVolume;
        if (dc06ProVolume != -1) {
            setVolume(dc06ProVolume);
        }
        int dc06ProDigitalFilter = SpStorage.getInstance().getDc06ProDigitalFilter();
        if (dc06ProDigitalFilter != -1) {
            setDigitalFilter((byte) dc06ProDigitalFilter);
        }
        int dc06ProBalance = SpStorage.getInstance().getDc06ProBalance();
        if (dc06ProBalance != 0) {
            setBalanceVolume(dc06ProBalance);
        }
        setCodecExternal(SpStorage.getInstance().getDc06ProCodecExternal());
        getFpgaVersion();
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
        SpStorage.getInstance().setDc06ProVolume(i2);
        return i2;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        setDc06ProDigitalFilterInternal(b2, i);
    }

    public void sendgetDc06ProDigitalFilterRegisterRequest() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_DIGITAL_FILTER_1, (byte) -112, (byte) 7));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_DIGITAL_FILTER_2, (byte) -110, (byte) 7));
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
            setVolume((byte) 5, (byte) 6, (byte) 7, (byte) 8, i2, Math.max(i3, 0));
        } else {
            int i4 = i2 + i;
            if (i2 == 255) {
                i4 = i2;
            }
            setVolume((byte) 5, (byte) 6, (byte) 7, (byte) 8, Math.max(i4, 0), i2);
        }
        SpStorage.getInstance().setDc06ProBalance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        SpStorage.getInstance().setDc06ProCodecExternal(i);
        if (i == 0) {
            this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_CODEC_EXTERNAL, new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, IConstants.ID_SET_DIGITAL_FILTER_1, -104, 44, 2, 0, 1, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, 0, 0, 0, 0, 0, 0, 0, 0}));
            this.mTaskManager.postTask(new Task(IConstants.ID_SET_CODEC_EXTERNAL_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_2, 32));
            return;
        }
        this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_CODEC_EXTERNAL, new byte[]{IConstants.ID_SET_CODEC_EXTERNAL, IConstants.ID_SET_DIGITAL_FILTER_1, -104, 44, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        this.mTaskManager.postTask(new Task(IConstants.ID_SET_CODEC_EXTERNAL_ROOM, (byte) -94, (byte) 0, IConstants.ID_SET_DIGITAL_FILTER_2, 0));
    }

    public void setDc06ProDigitalFilterInternal(int i, int i2) {
        if (this.mUsbDevice == null) {
            return;
        }
        int dc06ProDigitalFilter = SpStorage.getInstance().getDc06ProDigitalFilter();
        StringBuilder sb = new StringBuilder();
        sb.append(IConstants.idToString((byte) i));
        sb.append(" ");
        sb.append(i2);
        sb.append(" | ");
        sb.append(dc06ProDigitalFilter);
        sb.append(" -> ");
        int i3 = (i2 & 31) | dc06ProDigitalFilter;
        sb.append(i3);
        Log.i("UacManager", sb.toString());
        if (71 == i) {
            this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_DIGITAL_FILTER_1, (byte) -112, (byte) 7, (byte) i3));
        } else {
            this.mTaskManager.postTask(new Task((byte) IConstants.ID_SET_DIGITAL_FILTER_2, (byte) -110, (byte) 7, (byte) i3));
        }
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        SpStorage.getInstance().setDc06ProDigitalFilter(i);
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
        sendgetDc06ProDigitalFilterRegisterRequest();
    }

    @Override
    public void setVolume(int i) {
        this.mVolume = i;
        int balanceVolume = getBalanceVolume();
        this.mBalanceVolume = balanceVolume;
        int i2 = this.nVolumeStep2[i];
        if (balanceVolume == 0) {
            setVolume((byte) 1, (byte) 2, (byte) 3, (byte) 4, i2, i2);
            getDc06ProRightVolume();
        } else if (balanceVolume >= 0) {
            int i3 = i2 - balanceVolume;
            if (i2 == 255) {
                i3 = i2;
            }
            setVolume((byte) 1, (byte) 2, (byte) 7, (byte) 8, i2, Math.max(i3, 0));
            getDc06ProLeftVolume();
        } else {
            int i4 = balanceVolume + i2;
            if (i2 == 255) {
                i4 = i2;
            }
            setVolume((byte) 5, (byte) 6, (byte) 3, (byte) 4, Math.max(i4, 0), i2);
            getDc06ProRightVolume();
        }
    }
}

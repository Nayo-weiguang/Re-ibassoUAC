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
public class Dc06DeviceController extends UsbDeviceController {
    public Dc06DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        initDevice();
    }

    private void getDc06LeftVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_LEFT_1, (byte) -112, IConstants.ID_SET_DSD_BALANCE_RIGHT_2));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_LEFT_2, (byte) -112, IConstants.ID_SET_DSD_BALANCE_RIGHT_1));
    }

    private void getDc06RightVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_RIGHT_1, (byte) -110, IConstants.ID_SET_DSD_BALANCE_RIGHT_2));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_VOLUME_RIGHT_2, (byte) -110, IConstants.ID_SET_DSD_BALANCE_RIGHT_1));
    }

    private void setDc06LeftVolume(byte b2, byte b3, int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(b2, (byte) -112, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
        this.mTaskManager.postTask(new Task(b3, (byte) -112, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_1, i));
    }

    private void setDc06LeftVolume(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        setDc06LeftVolume((byte) 1, (byte) 2, i);
    }

    private void setDc06RightVolume(byte b2, byte b3, int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(b2, (byte) -110, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_2, i));
        this.mTaskManager.postTask(new Task(b3, (byte) -110, (byte) IConstants.ID_SET_DSD_BALANCE_RIGHT_1, i));
    }

    private void setDc06RightVolume(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        setDc06RightVolume((byte) 3, (byte) 4, i);
    }

    public int getDc06BalanceVolume() {
        return SpStorage.getInstance().getDc06Balance();
    }

    public int getDc06DigitalFilter() {
        return SpStorage.getInstance().getDc06DigitalFilter();
    }

    public int getDc06Volume() {
        return SpStorage.getInstance().getDc06Volume();
    }

    @Override
    public int getMaxVolume() {
        return this.nVolumeStep2.length - 1;
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc06Volume();
    }

    @Override
    public void initDevice() {
        int dc06Volume = SpStorage.getInstance().getDc06Volume();
        this.mVolume = dc06Volume;
        if (dc06Volume != -1) {
            setVolume(dc06Volume);
        }
        int dc06DigitalFilter = SpStorage.getInstance().getDc06DigitalFilter();
        if (dc06DigitalFilter != -1) {
            setDigitalFilter((byte) dc06DigitalFilter);
        }
        int dc06Balance = SpStorage.getInstance().getDc06Balance();
        if (dc06Balance != 0) {
            setBalanceVolume(dc06Balance);
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
        SpStorage.getInstance().setDc06Volume(i2);
        return i2;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        setDc06DigitalFilterInternal(b2, i);
    }

    public void sendGetDc06DigitalFilterRegisterRequest() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
        }
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_DIGITAL_FILTER_1, (byte) -112, (byte) 7));
        this.mTaskManager.postTask(new Task(IConstants.ID_GET_DIGITAL_FILTER_2, (byte) -110, (byte) 7));
    }

    @Override
    public void setBalanceVolume(int i) {
        int dc06Volume = getDc06Volume();
        this.mVolume = dc06Volume;
        int i2 = this.nVolumeStep2[dc06Volume];
        this.mBalanceVolume = i;
        if (i >= 0) {
            int i3 = i2 - i;
            if (i2 == 255) {
                i3 = i2;
            }
            setDc06RightVolume((byte) 7, (byte) 8, Math.max(i3, 0));
            setDc06LeftVolume((byte) 5, (byte) 6, i2);
        } else {
            int i4 = i2 + i;
            if (i2 == 255) {
                i4 = i2;
            }
            setDc06LeftVolume((byte) 5, (byte) 6, Math.max(i4, 0));
            setDc06RightVolume((byte) 7, (byte) 8, i2);
        }
        SpStorage.getInstance().setDc06Balance(i);
    }

    public void setDc06DigitalFilterInternal(int i, int i2) {
        if (this.mUsbDevice == null) {
            return;
        }
        int dc06DigitalFilter = SpStorage.getInstance().getDc06DigitalFilter();
        StringBuilder sb = new StringBuilder();
        sb.append(IConstants.idToString((byte) i));
        sb.append(" ");
        sb.append(i2);
        sb.append(" | ");
        sb.append(dc06DigitalFilter);
        sb.append(" -> ");
        int i3 = (i2 & 31) | dc06DigitalFilter;
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
        SpStorage.getInstance().setDc06DigitalFilter(i);
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
        sendGetDc06DigitalFilterRegisterRequest();
    }

    @Override
    public void setVolume(int i) {
        this.mVolume = i;
        int dc06BalanceVolume = getDc06BalanceVolume();
        this.mBalanceVolume = dc06BalanceVolume;
        int i2 = this.nVolumeStep2[i];
        if (dc06BalanceVolume == 0) {
            setDc06LeftVolume(i2);
            setDc06RightVolume(i2);
            getDc06RightVolume();
        } else if (dc06BalanceVolume >= 0) {
            int i3 = i2 - dc06BalanceVolume;
            if (i2 == 255) {
                i3 = i2;
            }
            setDc06RightVolume((byte) 7, (byte) 8, Math.max(i3, 0));
            setDc06LeftVolume(i2);
            getDc06LeftVolume();
        } else {
            int i4 = dc06BalanceVolume + i2;
            if (i2 == 255) {
                i4 = i2;
            }
            setDc06LeftVolume((byte) 5, (byte) 6, Math.max(i4, 0));
            setDc06RightVolume(i2);
            getDc06RightVolume();
        }
    }
}

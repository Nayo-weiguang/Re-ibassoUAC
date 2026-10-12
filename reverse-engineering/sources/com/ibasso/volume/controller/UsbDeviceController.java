package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.ibasso.volume.IConstants;
import com.ibasso.volume.NormalVolumeEvent;
import com.ibasso.volume.R;
import com.ibasso.volume.ResponseEvent;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.TaskManager;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.eq.ParametricEqualizer;
import java.util.Arrays;
import kotlin.UByte;
import okio.Utf8;
import org.greenrobot.eventbus.EventBus;
public class UsbDeviceController implements IConstants {
    public static final String TAG = "UacManager";
    public static final int[] nVolumeStep = {49408, 49664, 49920, 50176, 50432, 50688, 50944, 51200, 51456, 51712, 51968, 52224, 52480, 52736, 52992, 53248, 53504, 53760, 54016, 54272, 54528, 54784, 55040, 55296, 55552, 55808, 56064, Utf8.LOG_SURROGATE_HEADER, 56576, 56832, 57088, 57344, 57600, 57856, 58112, 58368, 58624, 58880, 59136, 59392, 59648, 59904, 60160, 60416, 60672, 60928, 61184, 61440, 61696, 61952, 62208, 62464, 62720, 62976, 63232, 63488, 63744, 64000, 64256, 64512, 64768, 65024, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 0};
    protected Context mContext;
    protected UsbDevice mUsbDevice;
    protected UsbManager mUsbManager;
    protected final int[] nVolumeStep2 = {255, 155, 150, 145, 140, 135, 130, 125, 120, 115, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 88, 86, 84, 82, 80, 78, 76, 74, 72, 70, 68, 66, 64, 62, 60, 58, 56, 54, 52, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
    protected int mVolume = 0;
    protected int mBalanceVolume = 0;
    protected final int mBalanceStep = 1;
    protected TaskManager mTaskManager = null;

    public UsbDeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        this.mUsbManager = (UsbManager) context.getSystemService("usb");
        this.mContext = context;
        this.mUsbDevice = usbDevice;
    }

    private int getFirmwareVersion(UsbDeviceConnection usbDeviceConnection) {
        if (usbDeviceConnection == null) {
            return 0;
        }
        return usbDeviceConnection.getRawDescriptors()[12] & UByte.MAX_VALUE;
    }

    public void dumpFlash() {
    }

    public void eraseFlash() {
    }

    public int getAmpMode() {
        return 0;
    }

    public int getBalanceVolume() {
        return 0;
    }

    public int getCodecExternal() {
        return 0;
    }

    public int getDRE() {
        return 0;
    }

    public int getDacGain() {
        return 0;
    }

    public int getDigitalFilter() {
        return 0;
    }

    public int getDsdFilter() {
        return 0;
    }

    public int getEqualizer() {
        return 0;
    }

    public void getFpgaVersion() {
    }

    public int getGain() {
        return 0;
    }

    public int getHpFilter() {
        return 0;
    }

    public int getKeyControlStatus() {
        return 0;
    }

    public int getLanguage() {
        return 0;
    }

    public int getMaxVolume() {
        return nVolumeStep.length - 1;
    }

    public int getMicMode() {
        return 0;
    }

    public int getOpVoltage() {
        return 0;
    }

    public int getPcmTrim() {
        return 0;
    }

    public int getUacVersion() {
        return 0;
    }

    public int getVolume() {
        int i;
        UsbDevice usbDevice = this.mUsbDevice;
        int i2 = -1;
        if (usbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return -1;
        }
        UsbDeviceConnection openDevice = this.mUsbManager.openDevice(usbDevice);
        if (getFirmwareVersion(openDevice) == 3) {
            nVolumeStep[0] = 36864;
        }
        if (openDevice == null) {
            Toast.makeText(this.mContext, "Open Device Fail", 0).show();
            return -1;
        }
        byte[] bArr = new byte[2];
        byte[] bArr2 = new byte[2];
        Arrays.fill(bArr, (byte) -1);
        Arrays.fill(bArr2, (byte) -1);
        int controlTransfer = openDevice.controlTransfer(160, 1, InputDeviceCompat.SOURCE_DPAD, 2561, bArr, 2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        if (controlTransfer != 2) {
            Log.i("UacManager", String.format("Get L Volume command error, error code = %d", Integer.valueOf(controlTransfer)));
            openDevice.close();
            return -1;
        }
        int i3 = ((bArr[1] & UByte.MAX_VALUE) << 8) | (bArr[0] & UByte.MAX_VALUE);
        int i4 = 0;
        while (true) {
            int[] iArr = nVolumeStep;
            int i5 = i2;
            if (i4 >= iArr.length) {
                i = i5;
                break;
            } else if (i3 == iArr[i4]) {
                i = i4;
                break;
            } else {
                i4++;
                i2 = i5;
            }
        }
        int controlTransfer2 = openDevice.controlTransfer(160, 1, 514, 2561, bArr2, 2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        if (controlTransfer2 != 2) {
            Log.i("UacManager", String.format("Get R Volume command error, error code = %d", Integer.valueOf(controlTransfer2)));
            openDevice.close();
            return i;
        }
        int i6 = ((bArr2[1] & UByte.MAX_VALUE) << 8) | (bArr2[0] & UByte.MAX_VALUE);
        int i7 = 0;
        while (true) {
            int[] iArr2 = nVolumeStep;
            if (i7 >= iArr2.length) {
                break;
            } else if (i6 == iArr2[i7]) {
                i = i7;
                break;
            } else {
                i7++;
            }
        }
        Log.i("UacManager", String.format("(L, R) = (%ddB, %ddB)", Integer.valueOf(i - getMaxVolume()), Integer.valueOf(i - getMaxVolume())));
        Log.i("UacManager", String.format("Get Current Volume (L,R):(%ddB, %ddB), Data(L, R):(0x%02X%02X, 0x%02X%02X)\t", Integer.valueOf(i - getMaxVolume()), Integer.valueOf(i - getMaxVolume()), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0]), Byte.valueOf(bArr2[1]), Byte.valueOf(bArr2[0])));
        openDevice.close();
        return i;
    }

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

    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
    }

    public boolean parseCallBackValue(ResponseEvent responseEvent) {
        return false;
    }

    public int parseNativeAndSaveVolume(int i) {
        return i;
    }

    public void parseNativeDigitalFilter(byte b2, int i) {
    }

    public void setAmpMode(int i) {
    }

    public void setBalanceVolume(int i) {
    }

    public void setCodecExternal(int i) {
    }

    public void setDRE(int i) {
    }

    public void setDacGain(int i) {
    }

    public void setDigitalFilter(int i) {
    }

    public void setDsdFilter(int i) {
    }

    public void setEqualizer(int i) {
    }

    public void setGain(int i) {
    }

    public void setHpFilter(int i) {
    }

    public void setKeyControlStatus(int i) {
    }

    public void setLanguage(int i) {
    }

    public void setMicMode(int i) {
    }

    public void setOpVoltage(int i) {
    }

    public void setOutput(int i) {
    }

    public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
    }

    public void setPcmTrim(int i) {
    }

    public void setScreenRotation(int i) {
    }

    public void setScreenTime(int i) {
    }

    public void setUacVersion(int i) {
    }

    public void setVolume(int i) {
        if (this.mUsbDevice == null) {
            Log.i("UacManager", "[Set RVolume]Open Device Fail");
        } else if (i > getMaxVolume() || i < 0) {
            Toast.makeText(this.mContext, (int) R.string.setting_invalid, 0).show();
        } else {
            UsbDeviceConnection openDevice = this.mUsbManager.openDevice(this.mUsbDevice);
            int firmwareVersion = getFirmwareVersion(openDevice);
            if (firmwareVersion == 3 || firmwareVersion >= 6) {
                nVolumeStep[0] = 36864;
            }
            Log.i("UacManager", "getFirmwareVersion:" + firmwareVersion + " volume:" + i);
            if (openDevice != null) {
                int i2 = nVolumeStep[i];
                byte[] bArr = {(byte) (i2 & 255), (byte) ((i2 >> 8) & 255)};
                if (openDevice.controlTransfer(32, 1, 514, 2561, bArr, 2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) != 2) {
                    Toast.makeText(this.mContext, "Set Right Channel Fail", 0).show();
                    Log.i("UacManager", "[Set RVolume] Set Right Channel Fail");
                    openDevice.close();
                    return;
                } else if (openDevice.controlTransfer(32, 1, InputDeviceCompat.SOURCE_DPAD, 2561, bArr, 2, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) != 2) {
                    Toast.makeText(this.mContext, "Set Left Channel Fail", 0).show();
                    Log.i("UacManager", "Set Left Channel Fail");
                    openDevice.close();
                    return;
                } else {
                    SpStorage.getInstance().setVolume(i);
                    Log.i("UacManager", String.format("Right Channel Volume(dB) = %d", Integer.valueOf(i - getMaxVolume())));
                    Log.i("UacManager", String.format("set Right Channel Volume OK,Volume(dB) = %d(0x%02X%02X)\t", Integer.valueOf(i - getMaxVolume()), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])));
                    openDevice.close();
                }
            } else {
                Toast.makeText(this.mContext, (int) R.string.volume_set_fail, 0).show();
            }
            EventBus.getDefault().postSticky(new NormalVolumeEvent(i));
        }
    }

    public void stopTaskManager() {
        this.mUsbDevice = null;
        TaskManager taskManager = this.mTaskManager;
        if (taskManager != null) {
            taskManager.stopTaskManager();
        }
    }
}

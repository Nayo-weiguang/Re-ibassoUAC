package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.android.material.timepicker.TimeModel;
import com.ibasso.volume.Dc06VolumeEvent;
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
import kotlin.jvm.internal.ByteCompanionObject;
import okio.Utf8;
import org.greenrobot.eventbus.EventBus;
public class D18DeviceController extends UsbDeviceController {
    private int lastFilterType;
    private final Handler mHandler;
    private final Runnable mRunnable;

    public D18DeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        Handler handler = new Handler();
        this.mHandler = handler;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                D18DeviceController d18DeviceController = D18DeviceController.this;
                if (d18DeviceController.mUsbDevice == null) {
                    return;
                }
                d18DeviceController.initDevice();
            }
        };
        this.mRunnable = runnable;
        this.lastFilterType = -2;
        this.mTaskManager = new TaskManager(usbDevice, usbDeviceConnection);
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 500L);
    }

    private void getNativeFilter() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_FILTER;
        bArr[5] = IConstants.ID_SET_CODEC_EXTERNAL_ROOM;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    private void getNativeScreenRotation() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_VOLUME_KEY_CONTROL;
        bArr[5] = 64;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    private void getNativeSpdif() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_SPDIF;
        bArr[5] = 48;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    private void getNativeUacVersion() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    private void getNativeVolume() {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_VOLUME_LEFT_2;
        bArr[5] = IConstants.ID_SET_DSD_BALANCE_RIGHT_2;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    @Override
    public int getAmpMode() {
        return SpStorage.getInstance().getD18AmpMode();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getD18Balance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getD18SpdifMode();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getD18DigitalFilter();
    }

    @Override
    public void getFpgaVersion() {
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = ByteCompanionObject.MIN_VALUE;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_GET_FPGA_VERSION;
        bArr[5] = -16;
        Task task = new Task(bArr);
        task.bSeqID = bArr[4];
        this.mTaskManager.postTask(task);
    }

    @Override
    public int getLanguage() {
        return SpStorage.getInstance().getD18Language();
    }

    @Override
    public int getMaxVolume() {
        return 100;
    }

    @Override
    public int getMicMode() {
        return SpStorage.getInstance().getD18MicMode();
    }

    @Override
    public int getUacVersion() {
        return SpStorage.getInstance().getD18UacVersion();
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getD18Volume();
    }

    @Override
    public void initDevice() {
        getFpgaVersion();
        getNativeVolume();
        getNativeFilter();
        getNativeSpdif();
        getNativeScreenRotation();
        getNativeUacVersion();
    }

    @Override
    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
    }

    @Override
    public boolean parseCallBackValue(ResponseEvent responseEvent) {
        int i;
        Log.d("UacManager", "###parseCallBackValue ID:" + IConstants.idToString((byte) responseEvent.seqId));
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            byte[] bArr = responseEvent.data;
            if (i2 >= bArr.length) {
                break;
            }
            sb.append(String.format("%02x,", Byte.valueOf(bArr[i2])));
            i2++;
        }
        Log.d("UacManager", "###data[] = {" + ((Object) sb) + "}");
        int i3 = responseEvent.seqId;
        byte[] bArr2 = responseEvent.data;
        if (bArr2[0] == 75 && bArr2[1] == Byte.MIN_VALUE && bArr2[2] == 9 && bArr2[3] == 31 && i3 < 11) {
            int i4 = ((bArr2[29] & UByte.MAX_VALUE) << 8) | (bArr2[28] & UByte.MAX_VALUE);
            int i5 = bArr2[30] & UByte.MAX_VALUE;
            byte[] bArr3 = responseEvent.data;
            double pow = ((short) ((bArr3[32] & UByte.MAX_VALUE) | ((bArr3[33] & UByte.MAX_VALUE) << 8))) / Math.pow(2.0d, 8.0d);
            byte b2 = responseEvent.data[34];
            Log.d("UacManager", "seqId: " + i3 + " index: " + ((int) responseEvent.data[5]) + " filterType:" + ((int) b2) + " freq: " + i4 + " gain:" + String.format("%.2f", Double.valueOf(pow)) + " Q Factor:" + String.format("%.2f", Double.valueOf((i5 | ((bArr2[31] & UByte.MAX_VALUE) << 8)) / Math.pow(2.0d, 8.0d))));
            if (b2 == -1 && this.lastFilterType != b2) {
                this.lastFilterType = b2;
                EventBus.getDefault().postSticky(new NativeReportEvent(-1, -1));
            } else if (b2 != -1 && (i = this.lastFilterType) != b2 && i != -2) {
                this.lastFilterType = b2;
                EventBus.getDefault().postSticky(new NativeReportEvent(-1, 0));
            }
            return true;
        } else if (88 == i3) {
            if ((bArr2[8] & UByte.MAX_VALUE) == 0) {
                EventBus eventBus = EventBus.getDefault();
                eventBus.postSticky(new FpgaVersionEvent((responseEvent.data[6] & UByte.MAX_VALUE) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(responseEvent.data[7] & UByte.MAX_VALUE)) + ".30"));
            } else {
                EventBus eventBus2 = EventBus.getDefault();
                eventBus2.postSticky(new FpgaVersionEvent((responseEvent.data[6] & UByte.MAX_VALUE) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(responseEvent.data[7] & UByte.MAX_VALUE)) + "." + Integer.toHexString(responseEvent.data[8] & UByte.MAX_VALUE)));
            }
            return true;
        } else {
            byte b3 = bArr2[5];
            if (16 == b3) {
                SpStorage.getInstance().setD18Volume(responseEvent.data[6]);
                EventBus.getDefault().postSticky(new Dc06VolumeEvent(responseEvent.data[6]));
                return true;
            }
            if (32 == b3) {
                SpStorage.getInstance().setD18DigitalFilter(responseEvent.data[6]);
                i3 = 89;
            } else if (48 == b3) {
                SpStorage.getInstance().setD18SpdifMode(responseEvent.data[6]);
                SpStorage.getInstance().setD18Balance(responseEvent.data[7]);
                SpStorage.getInstance().setD18DacGain(responseEvent.data[8]);
                SpStorage.getInstance().setD18Gain(responseEvent.data[9]);
                i3 = 96;
            } else if (64 == b3) {
                SpStorage.getInstance().setD18ScreenRotation(responseEvent.data[6]);
                SpStorage.getInstance().setD18OInputMode(responseEvent.data[7]);
                SpStorage.getInstance().setD18ScreenTime(responseEvent.data[8]);
                SpStorage.getInstance().setD18Language(responseEvent.data[9]);
                i3 = 97;
            } else if (80 == b3) {
                SpStorage.getInstance().setD18UacVersion(responseEvent.data[6]);
                SpStorage.getInstance().setD18AmpMode(responseEvent.data[7]);
                SpStorage.getInstance().setD18MicMode(responseEvent.data[8]);
                SpStorage.getInstance().setD18Theme(responseEvent.data[9]);
                i3 = 99;
            }
            int i6 = i3;
            byte[] bArr4 = responseEvent.data;
            EventBus.getDefault().postSticky(new NativeReportEvent(i6, bArr4[6], bArr4[7], bArr4[8], bArr4[9]));
            return true;
        }
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        SpStorage.getInstance().setD18Volume(i);
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    @Override
    public void setAmpMode(int i) {
        byte d18UacVersion = (byte) SpStorage.getInstance().getD18UacVersion();
        byte b2 = (byte) i;
        byte d18MicMode = (byte) SpStorage.getInstance().getD18MicMode();
        byte d18Theme = (byte) SpStorage.getInstance().getD18Theme();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        bArr[6] = d18UacVersion;
        bArr[7] = b2;
        bArr[8] = d18MicMode;
        bArr[9] = d18Theme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18AmpMode(i);
    }

    @Override
    public void setBalanceVolume(int i) {
        byte d18SpdifMode = (byte) SpStorage.getInstance().getD18SpdifMode();
        byte b2 = (byte) i;
        byte d18DacGain = (byte) SpStorage.getInstance().getD18DacGain();
        byte d18Gain = (byte) SpStorage.getInstance().getD18Gain();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 48;
        bArr[6] = d18SpdifMode;
        bArr[7] = b2;
        bArr[8] = d18DacGain;
        bArr[9] = d18Gain;
        Log.d("UacManager", "setBalanceVolume: " + ((int) b2));
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18Balance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        byte b2 = (byte) i;
        byte d18Balance = (byte) SpStorage.getInstance().getD18Balance();
        byte d18DacGain = (byte) SpStorage.getInstance().getD18DacGain();
        byte d18Gain = (byte) SpStorage.getInstance().getD18Gain();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 48;
        bArr[6] = b2;
        bArr[7] = d18Balance;
        bArr[8] = d18DacGain;
        bArr[9] = d18Gain;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18SpdifMode(i);
    }

    @Override
    public void setDacGain(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 48;
        bArr[6] = (byte) SpStorage.getInstance().getD18SpdifMode();
        bArr[7] = (byte) SpStorage.getInstance().getD18Balance();
        bArr[8] = (byte) i;
        bArr[9] = (byte) SpStorage.getInstance().getD18Gain();
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18DacGain(i);
    }

    @Override
    public void setDigitalFilter(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_DIGITAL_FILTER_1;
        bArr[5] = IConstants.ID_SET_CODEC_EXTERNAL_ROOM;
        bArr[6] = (byte) i;
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18DigitalFilter(i);
    }

    @Override
    public void setGain(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 48;
        bArr[6] = (byte) SpStorage.getInstance().getD18SpdifMode();
        bArr[7] = (byte) SpStorage.getInstance().getD18Balance();
        bArr[8] = (byte) SpStorage.getInstance().getD18DacGain();
        bArr[9] = (byte) i;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18Gain(i);
    }

    @Override
    public void setLanguage(int i) {
        byte d18ScreenRotation = (byte) SpStorage.getInstance().getD18ScreenRotation();
        byte d18OInputMode = (byte) SpStorage.getInstance().getD18OInputMode();
        byte d18ScreenTime = (byte) SpStorage.getInstance().getD18ScreenTime();
        byte b2 = (byte) i;
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 64;
        bArr[6] = d18ScreenRotation;
        bArr[7] = d18OInputMode;
        bArr[8] = d18ScreenTime;
        bArr[9] = b2;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18Language(i);
    }

    @Override
    public void setMicMode(int i) {
        byte d18UacVersion = (byte) SpStorage.getInstance().getD18UacVersion();
        byte d18AmpMode = (byte) SpStorage.getInstance().getD18AmpMode();
        byte b2 = (byte) i;
        byte d18Theme = (byte) SpStorage.getInstance().getD18Theme();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        if (b2 != SpStorage.getInstance().getD18MicMode()) {
            Toast.makeText(this.mContext, (int) R.string.usb_desc_config_update_d18, 1).show();
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        bArr[6] = d18UacVersion;
        bArr[7] = d18AmpMode;
        bArr[8] = b2;
        bArr[9] = d18Theme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18MicMode(i);
    }

    @Override
    public void setScreenRotation(int i) {
        byte b2 = (byte) i;
        byte d18OInputMode = (byte) SpStorage.getInstance().getD18OInputMode();
        byte d18ScreenTime = (byte) SpStorage.getInstance().getD18ScreenTime();
        byte d18Language = (byte) SpStorage.getInstance().getD18Language();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 64;
        bArr[6] = b2;
        bArr[7] = d18OInputMode;
        bArr[8] = d18ScreenTime;
        bArr[9] = d18Language;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18ScreenRotation(i);
    }

    @Override
    public void setScreenTime(int i) {
        byte d18ScreenRotation = (byte) SpStorage.getInstance().getD18ScreenRotation();
        byte d18OInputMode = (byte) SpStorage.getInstance().getD18OInputMode();
        byte b2 = (byte) i;
        byte d18Language = (byte) SpStorage.getInstance().getD18Language();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_CODEC_EXTERNAL;
        bArr[5] = 64;
        bArr[6] = d18ScreenRotation;
        bArr[7] = d18OInputMode;
        bArr[8] = b2;
        bArr[9] = d18Language;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18ScreenTime(i);
    }

    @Override
    public void setUacVersion(int i) {
        byte b2 = (byte) i;
        byte d18AmpMode = (byte) SpStorage.getInstance().getD18AmpMode();
        byte d18MicMode = (byte) SpStorage.getInstance().getD18MicMode();
        byte dc04uTheme = (byte) SpStorage.getInstance().getDc04uTheme();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        bArr[6] = b2;
        bArr[7] = d18AmpMode;
        bArr[8] = d18MicMode;
        bArr[9] = dc04uTheme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setD18UacVersion(i);
    }

    @Override
    public void setVolume(int i) {
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = 1;
        bArr[5] = IConstants.ID_SET_DSD_BALANCE_RIGHT_2;
        byte b2 = (byte) i;
        bArr[6] = b2;
        bArr[7] = b2;
        Log.d("UacManager", "setVolume: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
    }
}

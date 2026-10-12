package com.ibasso.volume.controller;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
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
import com.ibasso.volume.eq.EqualizerManager;
import com.ibasso.volume.eq.FilterSetting;
import com.ibasso.volume.eq.ParametricEqualizer;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import okio.Utf8;
import org.greenrobot.eventbus.EventBus;
public class Dc04uDeviceController extends UsbDeviceController {
    private int lastFilterType;
    private final Handler mHandler;
    private final Runnable mRunnable;
    private ParametricEqualizer[] parametricEqualizers;

    public Dc04uDeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.parametricEqualizers = new ParametricEqualizer[3];
        Handler handler = new Handler();
        this.mHandler = handler;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Dc04uDeviceController dc04uDeviceController = Dc04uDeviceController.this;
                if (dc04uDeviceController.mUsbDevice == null) {
                    return;
                }
                dc04uDeviceController.initDevice();
            }
        };
        this.mRunnable = runnable;
        this.lastFilterType = -2;
        this.parametricEqualizers[0] = new ParametricEqualizer();
        this.parametricEqualizers[0].setSettingId(0);
        this.parametricEqualizers[1] = new ParametricEqualizer();
        this.parametricEqualizers[1].setSettingId(1);
        this.parametricEqualizers[2] = new ParametricEqualizer();
        this.parametricEqualizers[2].setSettingId(2);
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
    public void dumpFlash() {
        Log.d("UacManager", "seqId###getAllEQ############");
        for (int i = 7; i < 10; i++) {
            getParametricEqualizer(i);
            try {
                Thread.sleep(100L);
            } catch (Exception unused) {
            }
        }
    }

    @Override
    public void eraseFlash() {
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = 5;
        bArr[3] = 1;
        bArr[4] = 4;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        task.needRetry = false;
        this.mTaskManager.postTask(task);
    }

    @Override
    public int getAmpMode() {
        return SpStorage.getInstance().getDc04uAmpMode();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getDc04uBalance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getDc04uSpdifMode();
    }

    @Override
    public int getDRE() {
        return SpStorage.getInstance().getDc04uDRE();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getDc04uDigitalFilter();
    }

    @Override
    public int getEqualizer() {
        return SpStorage.getInstance().getDc04uEQ();
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
        return SpStorage.getInstance().getDc04uLanguage();
    }

    @Override
    public int getMaxVolume() {
        return 100;
    }

    @Override
    public int getMicMode() {
        return SpStorage.getInstance().getDc04uMicMode();
    }

    @Override
    public int getOpVoltage() {
        return SpStorage.getInstance().getDc04uOpVoltage();
    }

    public void getParametricEqualizer(int i) {
        for (int i2 = 0; i2 < 10; i2++) {
            byte[] bArr = new byte[64];
            bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
            bArr[1] = ByteCompanionObject.MIN_VALUE;
            bArr[2] = 9;
            bArr[3] = 31;
            bArr[4] = (byte) (i & 255);
            bArr[5] = (byte) (i2 & 255);
            Task task = new Task(bArr);
            task.bSeqID = bArr[4];
            task.needRetry = true;
            this.mTaskManager.postTask(task);
        }
    }

    @Override
    public int getUacVersion() {
        return SpStorage.getInstance().getDc04uUacVersion();
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getDc04uVolume();
    }

    @Override
    public void initDevice() {
        getFpgaVersion();
        getNativeVolume();
        getNativeFilter();
        getNativeSpdif();
        getNativeScreenRotation();
        getNativeUacVersion();
        dumpFlash();
    }

    @Override
    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
    }

    @Override
    public boolean parseCallBackValue(ResponseEvent responseEvent) {
        Log.d("UacManager", "###parseCallBackValue ID:" + IConstants.idToString((byte) responseEvent.seqId));
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            byte[] bArr = responseEvent.data;
            if (i >= bArr.length) {
                break;
            }
            sb.append(String.format("%02x,", Byte.valueOf(bArr[i])));
            i++;
        }
        Log.d("UacManager", "###data[] = {" + ((Object) sb) + "}");
        int i2 = responseEvent.seqId;
        byte[] bArr2 = responseEvent.data;
        if (bArr2[0] == 75 && bArr2[1] == Byte.MIN_VALUE && bArr2[2] == 9 && bArr2[3] == 31 && i2 < 11) {
            byte b2 = bArr2[5];
            int i3 = (bArr2[28] & UByte.MAX_VALUE) | ((bArr2[29] & UByte.MAX_VALUE) << 8);
            double round = Math.round((((bArr2[30] & UByte.MAX_VALUE) | ((bArr2[31] & UByte.MAX_VALUE) << 8)) / Math.pow(2.0d, 8.0d)) * 100.0d) / 100.0d;
            byte[] bArr3 = responseEvent.data;
            double round2 = Math.round((((short) ((bArr3[32] & UByte.MAX_VALUE) | ((bArr3[33] & UByte.MAX_VALUE) << 8))) / Math.pow(2.0d, 8.0d)) * 100.0d) / 100.0d;
            int i4 = responseEvent.data[34] & UByte.MAX_VALUE;
            Log.d("UacManager", "seqId: " + i2 + " index: " + ((int) responseEvent.data[5]) + " filterType:" + i4 + " freq: " + i3 + " gain:" + String.format("%.2f", Double.valueOf(round2)) + " Q Factor:" + String.format("%.2f", Double.valueOf(round)));
            int i5 = this.lastFilterType;
            if (i5 != i4 && i5 != -2) {
                this.lastFilterType = i4;
                EventBus.getDefault().postSticky(new NativeReportEvent(-1, 0));
            }
            if (i2 < 7) {
                return true;
            }
            int i6 = i2 - 7;
            int i7 = responseEvent.data[35] & UByte.MAX_VALUE;
            if (i7 > 127) {
                i7 += InputDeviceCompat.SOURCE_ANY;
            }
            this.parametricEqualizers[i6].setTotalGain(i7);
            if (b2 >= 0 && b2 < 10) {
                this.parametricEqualizers[i6].getFilterSettings().get(b2).setFilterSetting(b2, true, i4, i3, round2, round);
                if (b2 == 9) {
                    Log.d("UacManager", "update PEQ: " + this.parametricEqualizers[i6].toString());
                    EqualizerManager.getInstance().updateDeviceEqToCache(i6, this.parametricEqualizers[i6]);
                }
            }
            return true;
        } else if (88 == i2) {
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
                SpStorage.getInstance().setDc04uVolume(responseEvent.data[6]);
                EventBus.getDefault().postSticky(new Dc06VolumeEvent(responseEvent.data[6]));
                return true;
            }
            if (32 == b3) {
                SpStorage.getInstance().setDc04uDigitalFilter(responseEvent.data[6]);
                SpStorage.getInstance().setDc04uEQ(responseEvent.data[7]);
                SpStorage.getInstance().setDc04uDRE(responseEvent.data[8]);
                i2 = 89;
            } else if (48 == b3) {
                SpStorage.getInstance().setDc04uSpdifMode(responseEvent.data[6]);
                SpStorage.getInstance().setDc04uBalance(responseEvent.data[7]);
                SpStorage.getInstance().setDc04uGain(responseEvent.data[8]);
                i2 = 96;
            } else if (64 == b3) {
                SpStorage.getInstance().setDc04uScreenRotation(responseEvent.data[6]);
                SpStorage.getInstance().setDc04uOpVoltage(responseEvent.data[7]);
                SpStorage.getInstance().setDc04uScreenTime(responseEvent.data[8]);
                SpStorage.getInstance().setDc04uLanguage(responseEvent.data[9]);
                SpStorage.getInstance().setDc04uThemeColor(responseEvent.data[10]);
                i2 = 97;
            } else if (80 == b3) {
                SpStorage.getInstance().setDc04uUacVersion(responseEvent.data[6]);
                SpStorage.getInstance().setDc04uAmpMode(responseEvent.data[7]);
                SpStorage.getInstance().setDc04uMicMode(responseEvent.data[8]);
                SpStorage.getInstance().setDc04uTheme(responseEvent.data[9]);
                SpStorage.getInstance().setDc04uBrightness(responseEvent.data[10]);
                i2 = 99;
            }
            int i8 = i2;
            byte[] bArr4 = responseEvent.data;
            EventBus.getDefault().postSticky(new NativeReportEvent(i8, bArr4[6], bArr4[7], bArr4[8], bArr4[9]));
            return true;
        }
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        SpStorage.getInstance().setDc04uVolume(i);
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    @Override
    public void setAmpMode(int i) {
        byte dc04uUacVersion = (byte) SpStorage.getInstance().getDc04uUacVersion();
        byte b2 = (byte) i;
        byte dc04uMicMode = (byte) SpStorage.getInstance().getDc04uMicMode();
        byte dc04uTheme = (byte) SpStorage.getInstance().getDc04uTheme();
        byte dc04uBrightness = (byte) SpStorage.getInstance().getDc04uBrightness();
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
        bArr[6] = dc04uUacVersion;
        bArr[7] = b2;
        bArr[8] = dc04uMicMode;
        bArr[9] = dc04uTheme;
        bArr[10] = dc04uBrightness;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uAmpMode(i);
    }

    @Override
    public void setBalanceVolume(int i) {
        byte dc04uSpdifMode = (byte) SpStorage.getInstance().getDc04uSpdifMode();
        byte b2 = (byte) i;
        byte dc04uGain = (byte) SpStorage.getInstance().getDc04uGain();
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
        bArr[6] = dc04uSpdifMode;
        bArr[7] = b2;
        bArr[8] = dc04uGain;
        Log.d("UacManager", "setBalanceVolume: " + ((int) b2));
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uBalance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        byte b2 = (byte) i;
        byte dc04uBalance = (byte) SpStorage.getInstance().getDc04uBalance();
        byte dc04uGain = (byte) SpStorage.getInstance().getDc04uGain();
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
        bArr[7] = dc04uBalance;
        bArr[8] = dc04uGain;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uSpdifMode(i);
    }

    @Override
    public void setDRE(int i) {
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
        bArr[6] = (byte) SpStorage.getInstance().getDc04uDigitalFilter();
        bArr[7] = (byte) SpStorage.getInstance().getDc04uEQ();
        bArr[8] = (byte) i;
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uDRE(i);
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
        bArr[7] = (byte) SpStorage.getInstance().getDc04uEQ();
        bArr[8] = (byte) SpStorage.getInstance().getDc04uDRE();
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uDigitalFilter(i);
    }

    @Override
    public void setEqualizer(int i) {
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
        bArr[6] = (byte) SpStorage.getInstance().getDc04uDigitalFilter();
        bArr[7] = (byte) i;
        bArr[8] = (byte) SpStorage.getInstance().getDc04uDRE();
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uEQ(i);
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
        bArr[6] = (byte) SpStorage.getInstance().getDc04uSpdifMode();
        bArr[7] = (byte) SpStorage.getInstance().getDc04uBalance();
        bArr[8] = (byte) i;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uGain(i);
    }

    @Override
    public void setLanguage(int i) {
        byte dc04uScreenRotation = (byte) SpStorage.getInstance().getDc04uScreenRotation();
        byte dc04uOpVoltage = (byte) SpStorage.getInstance().getDc04uOpVoltage();
        byte dc04uScreenTime = (byte) SpStorage.getInstance().getDc04uScreenTime();
        byte b2 = (byte) i;
        byte dc04uThemeColor = (byte) SpStorage.getInstance().getDc04uThemeColor();
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
        bArr[6] = dc04uScreenRotation;
        bArr[7] = dc04uOpVoltage;
        bArr[8] = dc04uScreenTime;
        bArr[9] = b2;
        bArr[10] = dc04uThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uLanguage(i);
    }

    @Override
    public void setMicMode(int i) {
        byte dc04uUacVersion = (byte) SpStorage.getInstance().getDc04uUacVersion();
        byte dc04uAmpMode = (byte) SpStorage.getInstance().getDc04uAmpMode();
        byte b2 = (byte) i;
        byte dc04uTheme = (byte) SpStorage.getInstance().getDc04uTheme();
        byte dc04uBrightness = (byte) SpStorage.getInstance().getDc04uBrightness();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        if (b2 != SpStorage.getInstance().getDc04uMicMode()) {
            Toast.makeText(this.mContext, (int) R.string.usb_desc_config_update, 1).show();
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        bArr[6] = dc04uUacVersion;
        bArr[7] = dc04uAmpMode;
        bArr[8] = b2;
        bArr[9] = dc04uTheme;
        bArr[10] = dc04uBrightness;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uMicMode(i);
    }

    @Override
    public void setOpVoltage(int i) {
        byte dc04uScreenRotation = (byte) SpStorage.getInstance().getDc04uScreenRotation();
        byte b2 = (byte) i;
        byte dc04uScreenTime = (byte) SpStorage.getInstance().getDc04uScreenTime();
        byte dc04uLanguage = (byte) SpStorage.getInstance().getDc04uLanguage();
        byte dc04uThemeColor = (byte) SpStorage.getInstance().getDc04uThemeColor();
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
        bArr[6] = dc04uScreenRotation;
        bArr[7] = b2;
        bArr[8] = dc04uScreenTime;
        bArr[9] = dc04uLanguage;
        bArr[10] = dc04uThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uOpVoltage(i);
    }

    @Override
    public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
        byte b2 = 0;
        for (int i = 0; i < parametricEqualizer.getFilterSettings().size(); i++) {
            byte round = (byte) Math.round(parametricEqualizer.getFilterSettings().get(i).getGain());
            if (b2 < round) {
                b2 = round;
            }
        }
        for (int i2 = 0; i2 < parametricEqualizer.getFilterSettings().size(); i2++) {
            byte[] bArr = {IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1, 1, 9, 31, (byte) (parametricEqualizer.getSettingId() + 7), 0, 0, 0, -113, IConstants.ID_GET_FILTER, IConstants.ID_SET_DSD_BALANCE_RIGHT_1, 64, -107, -81, IConstants.ID_GET_DIGITAL_FILTER_INTERNAL_2, ByteCompanionObject.MIN_VALUE, -60, 59, -90, Utf8.REPLACEMENT_BYTE, 107, IConstants.ID_GET_DSD_VOLUME_RIGHT_2, -75, ByteCompanionObject.MAX_VALUE, -83, 106, IConstants.ID_GET_DIGITAL_FILTER_INTERNAL_2, -64, 31, 0, -64, 0, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            bArr[5] = (byte) i2;
            FilterSetting filterSetting = parametricEqualizer.getFilterSettings().get(i2);
            bArr[28] = (byte) (filterSetting.getFrequency() & 255);
            bArr[29] = (byte) ((filterSetting.getFrequency() >> 8) & 255);
            short round2 = (short) Math.round(filterSetting.getFactor() * 256.0d);
            bArr[30] = (byte) (round2 & 255);
            bArr[31] = (byte) ((round2 >> 8) & 255);
            short round3 = (short) Math.round(filterSetting.getGain() * 256.0d);
            bArr[32] = (byte) (round3 & 255);
            bArr[33] = (byte) ((round3 >> 8) & 255);
            bArr[34] = (byte) filterSetting.getFilterType();
            bArr[35] = (byte) parametricEqualizer.getTotalGain();
            Task task = new Task(bArr);
            task.bSeqID = bArr[5];
            task.needRetry = false;
            this.mTaskManager.postTask(task);
        }
        byte[] bArr2 = {IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1, 1, 10, 4, (byte) (parametricEqualizer.getSettingId() + 7), 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Task task2 = new Task(bArr2);
        task2.bSeqID = bArr2[5];
        task2.needRetry = false;
        this.mTaskManager.postTask(task2);
        try {
            Thread.sleep(100L);
        } catch (Exception unused) {
        }
        byte[] bArr3 = {IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1, 1, 1, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Task task3 = new Task(bArr3);
        task3.bSeqID = bArr3[5];
        task3.needRetry = false;
        this.mTaskManager.postTask(task3);
    }

    @Override
    public void setScreenRotation(int i) {
        byte b2 = (byte) i;
        byte dc04uOpVoltage = (byte) SpStorage.getInstance().getDc04uOpVoltage();
        byte dc04uScreenTime = (byte) SpStorage.getInstance().getDc04uScreenTime();
        byte dc04uLanguage = (byte) SpStorage.getInstance().getDc04uLanguage();
        byte dc04uThemeColor = (byte) SpStorage.getInstance().getDc04uThemeColor();
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
        bArr[7] = dc04uOpVoltage;
        bArr[8] = dc04uScreenTime;
        bArr[9] = dc04uLanguage;
        bArr[10] = dc04uThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uScreenRotation(i);
    }

    @Override
    public void setScreenTime(int i) {
        byte dc04uScreenRotation = (byte) SpStorage.getInstance().getDc04uScreenRotation();
        byte dc04uOpVoltage = (byte) SpStorage.getInstance().getDc04uOpVoltage();
        byte b2 = (byte) i;
        byte dc04uLanguage = (byte) SpStorage.getInstance().getDc04uLanguage();
        byte dc04uThemeColor = (byte) SpStorage.getInstance().getDc04uThemeColor();
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
        bArr[6] = dc04uScreenRotation;
        bArr[7] = dc04uOpVoltage;
        bArr[8] = b2;
        bArr[9] = dc04uLanguage;
        bArr[10] = dc04uThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uScreenTime(i);
    }

    @Override
    public void setUacVersion(int i) {
        byte b2 = (byte) i;
        byte dc04uAmpMode = (byte) SpStorage.getInstance().getDc04uAmpMode();
        byte dc04uMicMode = (byte) SpStorage.getInstance().getDc04uMicMode();
        byte dc04uTheme = (byte) SpStorage.getInstance().getDc04uTheme();
        byte dc04uBrightness = (byte) SpStorage.getInstance().getDc04uBrightness();
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
        bArr[7] = dc04uAmpMode;
        bArr[8] = dc04uMicMode;
        bArr[9] = dc04uTheme;
        bArr[10] = dc04uBrightness;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setDc04uUacVersion(i);
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

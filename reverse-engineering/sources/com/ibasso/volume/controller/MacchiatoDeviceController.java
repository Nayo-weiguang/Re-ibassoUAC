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
public class MacchiatoDeviceController extends UsbDeviceController {
    private int lastFilterType;
    private final Handler mHandler;
    private final Runnable mRunnable;
    private ParametricEqualizer[] parametricEqualizers;

    public MacchiatoDeviceController(Context context, UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        super(context, usbDevice, usbDeviceConnection);
        this.parametricEqualizers = new ParametricEqualizer[3];
        Handler handler = new Handler();
        this.mHandler = handler;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MacchiatoDeviceController macchiatoDeviceController = MacchiatoDeviceController.this;
                if (macchiatoDeviceController.mUsbDevice == null) {
                    return;
                }
                macchiatoDeviceController.initDevice();
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
        return SpStorage.getInstance().getMacchiatoAmpMode();
    }

    @Override
    public int getBalanceVolume() {
        return SpStorage.getInstance().getMacchiatoBalance();
    }

    @Override
    public int getCodecExternal() {
        return SpStorage.getInstance().getMacchiatoSpdifMode();
    }

    @Override
    public int getDRE() {
        return SpStorage.getInstance().getMacchiatoDRE();
    }

    @Override
    public int getDigitalFilter() {
        return SpStorage.getInstance().getMacchiatoDigitalFilter();
    }

    @Override
    public int getEqualizer() {
        return SpStorage.getInstance().getMacchiatoEQ();
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
        return SpStorage.getInstance().getMacchiatoLanguage();
    }

    @Override
    public int getMaxVolume() {
        return 100;
    }

    @Override
    public int getMicMode() {
        return SpStorage.getInstance().getMacchiatoMicMode();
    }

    @Override
    public int getOpVoltage() {
        return SpStorage.getInstance().getMacchiatoOpVoltage();
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
        return SpStorage.getInstance().getMacchiatoUacVersion();
    }

    @Override
    public int getVolume() {
        return SpStorage.getInstance().getMacchiatoVolume();
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
        char c2;
        int i;
        int i2;
        Log.d("UacManager", "###parseCallBackValue ID:" + IConstants.idToString((byte) responseEvent.seqId));
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (true) {
            byte[] bArr = responseEvent.data;
            if (i3 >= bArr.length) {
                break;
            }
            sb.append(String.format("%02x,", Byte.valueOf(bArr[i3])));
            i3++;
        }
        Log.d("UacManager", "###data[] = {" + ((Object) sb) + "}");
        int i4 = responseEvent.seqId;
        byte[] bArr2 = responseEvent.data;
        if (bArr2[0] == 75 && bArr2[1] == Byte.MIN_VALUE && bArr2[2] == 9 && bArr2[3] == 31 && i4 < 11) {
            byte b2 = bArr2[5];
            int i5 = (bArr2[28] & UByte.MAX_VALUE) | ((bArr2[29] & UByte.MAX_VALUE) << 8);
            double round = Math.round((((bArr2[30] & UByte.MAX_VALUE) | ((bArr2[31] & UByte.MAX_VALUE) << 8)) / Math.pow(2.0d, 8.0d)) * 100.0d) / 100.0d;
            byte[] bArr3 = responseEvent.data;
            double round2 = Math.round((((short) ((bArr3[32] & UByte.MAX_VALUE) | ((bArr3[33] & UByte.MAX_VALUE) << 8))) / Math.pow(2.0d, 8.0d)) * 100.0d) / 100.0d;
            int i6 = responseEvent.data[34] & UByte.MAX_VALUE;
            Log.d("UacManager", "seqId: " + i4 + " index: " + ((int) responseEvent.data[5]) + " filterType:" + i6 + " freq: " + i5 + " gain:" + String.format("%.2f", Double.valueOf(round2)) + " Q Factor:" + String.format("%.2f", Double.valueOf(round)));
            int i7 = this.lastFilterType;
            if (i7 != i6 && i7 != -2) {
                this.lastFilterType = i6;
                EventBus.getDefault().postSticky(new NativeReportEvent(-1, 0));
            }
            if (i4 < 7) {
                return true;
            }
            int i8 = i4 - 7;
            int i9 = responseEvent.data[35] & UByte.MAX_VALUE;
            if (i9 > 127) {
                i9 += InputDeviceCompat.SOURCE_ANY;
            }
            this.parametricEqualizers[i8].setTotalGain(i9);
            if (b2 >= 0 && b2 < 10) {
                this.parametricEqualizers[i8].getFilterSettings().get(b2).setFilterSetting(b2, true, i6, i5, round2, round);
                if (b2 == 9) {
                    Log.d("UacManager", "update PEQ: " + this.parametricEqualizers[i8].toString());
                    EqualizerManager.getInstance().updateDeviceEqToCache(i8, this.parametricEqualizers[i8]);
                }
            }
            return true;
        } else if (88 == i4) {
            EventBus eventBus = EventBus.getDefault();
            eventBus.postSticky(new FpgaVersionEvent((responseEvent.data[6] & UByte.MAX_VALUE) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(responseEvent.data[7] & UByte.MAX_VALUE))));
            return true;
        } else {
            byte b3 = bArr2[5];
            if (16 == b3) {
                SpStorage.getInstance().setMacchiatoVolume(responseEvent.data[6]);
                EventBus.getDefault().postSticky(new Dc06VolumeEvent(responseEvent.data[6]));
                return true;
            }
            if (32 == b3) {
                SpStorage.getInstance().setMacchiatoDigitalFilter(responseEvent.data[6]);
                SpStorage.getInstance().setMacchiatoEQ(responseEvent.data[7]);
                SpStorage.getInstance().setMacchiatoDRE(responseEvent.data[8]);
                i2 = 89;
            } else if (48 == b3) {
                SpStorage.getInstance().setMacchiatoSpdifMode(responseEvent.data[6]);
                SpStorage.getInstance().setMacchiatoBalance(responseEvent.data[7]);
                SpStorage.getInstance().setMacchiatoGain(responseEvent.data[8]);
                i2 = 96;
            } else if (64 != b3) {
                if (80 == b3) {
                    SpStorage.getInstance().setMacchiatoUacVersion(responseEvent.data[6]);
                    SpStorage.getInstance().setMacchiatoAmpMode(responseEvent.data[7]);
                    SpStorage.getInstance().setMacchiatoMicMode(responseEvent.data[8]);
                    c2 = '\t';
                    SpStorage.getInstance().setMacchiatoTheme(responseEvent.data[9]);
                    i4 = 99;
                } else {
                    c2 = '\t';
                }
                i = i4;
                byte[] bArr4 = responseEvent.data;
                EventBus.getDefault().postSticky(new NativeReportEvent(i, bArr4[6], bArr4[7], bArr4[8], bArr4[c2]));
                return true;
            } else {
                SpStorage.getInstance().setMacchiatoScreenRotation(responseEvent.data[6]);
                SpStorage.getInstance().setMacchiatoOpVoltage(responseEvent.data[7]);
                SpStorage.getInstance().setMacchiatoScreenTime(responseEvent.data[8]);
                SpStorage.getInstance().setMacchiatoLanguage(responseEvent.data[9]);
                SpStorage.getInstance().setMacchiatoThemeColor(responseEvent.data[10]);
                i2 = 97;
            }
            i = i2;
            c2 = '\t';
            byte[] bArr42 = responseEvent.data;
            EventBus.getDefault().postSticky(new NativeReportEvent(i, bArr42[6], bArr42[7], bArr42[8], bArr42[c2]));
            return true;
        }
    }

    @Override
    public int parseNativeAndSaveVolume(int i) {
        SpStorage.getInstance().setMacchiatoVolume(i);
        return i;
    }

    @Override
    public void parseNativeDigitalFilter(byte b2, int i) {
        EventBus.getDefault().postSticky(new DigitalFilterEvent(i));
    }

    @Override
    public void setAmpMode(int i) {
        byte macchiatoUacVersion = (byte) SpStorage.getInstance().getMacchiatoUacVersion();
        byte b2 = (byte) i;
        byte macchiatoMicMode = (byte) SpStorage.getInstance().getMacchiatoMicMode();
        byte macchiatoTheme = (byte) SpStorage.getInstance().getMacchiatoTheme();
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
        bArr[6] = macchiatoUacVersion;
        bArr[7] = b2;
        bArr[8] = macchiatoMicMode;
        bArr[9] = macchiatoTheme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoAmpMode(i);
    }

    @Override
    public void setBalanceVolume(int i) {
        byte macchiatoSpdifMode = (byte) SpStorage.getInstance().getMacchiatoSpdifMode();
        byte b2 = (byte) i;
        byte macchiatoGain = (byte) SpStorage.getInstance().getMacchiatoGain();
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
        bArr[6] = macchiatoSpdifMode;
        bArr[7] = b2;
        bArr[8] = macchiatoGain;
        Log.d("UacManager", "setBalanceVolume: " + ((int) b2));
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoBalance(i);
    }

    @Override
    public void setCodecExternal(int i) {
        byte b2 = (byte) i;
        byte macchiatoBalance = (byte) SpStorage.getInstance().getMacchiatoBalance();
        byte macchiatoGain = (byte) SpStorage.getInstance().getMacchiatoGain();
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
        bArr[7] = macchiatoBalance;
        bArr[8] = macchiatoGain;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoSpdifMode(i);
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
        bArr[6] = (byte) SpStorage.getInstance().getMacchiatoDigitalFilter();
        bArr[7] = (byte) SpStorage.getInstance().getMacchiatoEQ();
        bArr[8] = (byte) i;
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoDRE(i);
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
        bArr[7] = (byte) SpStorage.getInstance().getMacchiatoEQ();
        bArr[8] = (byte) SpStorage.getInstance().getMacchiatoDRE();
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoDigitalFilter(i);
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
        bArr[6] = (byte) SpStorage.getInstance().getMacchiatoDigitalFilter();
        bArr[7] = (byte) i;
        bArr[8] = (byte) SpStorage.getInstance().getMacchiatoDRE();
        Log.d("UacManager", "setDigitalFilter: " + i);
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoEQ(i);
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
        bArr[6] = (byte) SpStorage.getInstance().getMacchiatoSpdifMode();
        bArr[7] = (byte) SpStorage.getInstance().getMacchiatoBalance();
        bArr[8] = (byte) i;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoGain(i);
    }

    @Override
    public void setLanguage(int i) {
        byte macchiatoScreenRotation = (byte) SpStorage.getInstance().getMacchiatoScreenRotation();
        byte macchiatoOpVoltage = (byte) SpStorage.getInstance().getMacchiatoOpVoltage();
        byte macchiatoScreenTime = (byte) SpStorage.getInstance().getMacchiatoScreenTime();
        byte b2 = (byte) i;
        byte macchiatoThemeColor = (byte) SpStorage.getInstance().getMacchiatoThemeColor();
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
        bArr[6] = macchiatoScreenRotation;
        bArr[7] = macchiatoOpVoltage;
        bArr[8] = macchiatoScreenTime;
        bArr[9] = b2;
        bArr[10] = macchiatoThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoLanguage(i);
    }

    @Override
    public void setMicMode(int i) {
        byte macchiatoUacVersion = (byte) SpStorage.getInstance().getMacchiatoUacVersion();
        byte macchiatoAmpMode = (byte) SpStorage.getInstance().getMacchiatoAmpMode();
        byte b2 = (byte) i;
        byte macchiatoTheme = (byte) SpStorage.getInstance().getMacchiatoTheme();
        if (this.mUsbDevice == null) {
            Toast.makeText(this.mContext, (int) R.string.no_device, 0).show();
            return;
        }
        if (b2 != SpStorage.getInstance().getMacchiatoMicMode()) {
            Toast.makeText(this.mContext, (int) R.string.usb_desc_config_update, 1).show();
        }
        byte[] bArr = new byte[64];
        bArr[0] = IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1;
        bArr[1] = 1;
        bArr[2] = Utf8.REPLACEMENT_BYTE;
        bArr[3] = 8;
        bArr[4] = IConstants.ID_SET_UAC;
        bArr[5] = IConstants.ID_GET_DSD_VOLUME_RIGHT_2;
        bArr[6] = macchiatoUacVersion;
        bArr[7] = macchiatoAmpMode;
        bArr[8] = b2;
        bArr[9] = macchiatoTheme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoMicMode(i);
    }

    @Override
    public void setOpVoltage(int i) {
        byte macchiatoScreenRotation = (byte) SpStorage.getInstance().getMacchiatoScreenRotation();
        byte b2 = (byte) i;
        byte macchiatoScreenTime = (byte) SpStorage.getInstance().getMacchiatoScreenTime();
        byte macchiatoLanguage = (byte) SpStorage.getInstance().getMacchiatoLanguage();
        byte macchiatoThemeColor = (byte) SpStorage.getInstance().getMacchiatoThemeColor();
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
        bArr[6] = macchiatoScreenRotation;
        bArr[7] = b2;
        bArr[8] = macchiatoScreenTime;
        bArr[9] = macchiatoLanguage;
        bArr[10] = macchiatoThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoOpVoltage(i);
    }

    @Override
    public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
        for (int i = 0; i < parametricEqualizer.getFilterSettings().size(); i++) {
            byte[] bArr = {IConstants.ID_GET_DC03PRO_DIGITAL_FILTER_1, 1, 9, 31, (byte) (parametricEqualizer.getSettingId() + 7), 0, 0, 0, -113, IConstants.ID_GET_FILTER, IConstants.ID_SET_DSD_BALANCE_RIGHT_1, 64, -107, -81, IConstants.ID_GET_DIGITAL_FILTER_INTERNAL_2, ByteCompanionObject.MIN_VALUE, -60, 59, -90, Utf8.REPLACEMENT_BYTE, 107, IConstants.ID_GET_DSD_VOLUME_RIGHT_2, -75, ByteCompanionObject.MAX_VALUE, -83, 106, IConstants.ID_GET_DIGITAL_FILTER_INTERNAL_2, -64, 31, 0, -64, 0, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            bArr[5] = (byte) i;
            FilterSetting filterSetting = parametricEqualizer.getFilterSettings().get(i);
            bArr[28] = (byte) (filterSetting.getFrequency() & 255);
            bArr[29] = (byte) ((filterSetting.getFrequency() >> 8) & 255);
            short round = (short) Math.round(filterSetting.getFactor() * 256.0d);
            bArr[30] = (byte) (round & 255);
            bArr[31] = (byte) ((round >> 8) & 255);
            short round2 = (short) Math.round(filterSetting.getGain() * 256.0d);
            bArr[32] = (byte) (round2 & 255);
            bArr[33] = (byte) ((round2 >> 8) & 255);
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
        byte macchiatoOpVoltage = (byte) SpStorage.getInstance().getMacchiatoOpVoltage();
        byte macchiatoScreenTime = (byte) SpStorage.getInstance().getMacchiatoScreenTime();
        byte macchiatoLanguage = (byte) SpStorage.getInstance().getMacchiatoLanguage();
        byte macchiatoThemeColor = (byte) SpStorage.getInstance().getMacchiatoThemeColor();
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
        bArr[7] = macchiatoOpVoltage;
        bArr[8] = macchiatoScreenTime;
        bArr[9] = macchiatoLanguage;
        bArr[10] = macchiatoThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoScreenRotation(i);
    }

    @Override
    public void setScreenTime(int i) {
        byte macchiatoScreenRotation = (byte) SpStorage.getInstance().getMacchiatoScreenRotation();
        byte macchiatoOpVoltage = (byte) SpStorage.getInstance().getMacchiatoOpVoltage();
        byte b2 = (byte) i;
        byte macchiatoLanguage = (byte) SpStorage.getInstance().getMacchiatoLanguage();
        byte macchiatoThemeColor = (byte) SpStorage.getInstance().getMacchiatoThemeColor();
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
        bArr[6] = macchiatoScreenRotation;
        bArr[7] = macchiatoOpVoltage;
        bArr[8] = b2;
        bArr[9] = macchiatoLanguage;
        bArr[10] = macchiatoThemeColor;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoScreenTime(i);
    }

    @Override
    public void setUacVersion(int i) {
        byte b2 = (byte) i;
        byte macchiatoAmpMode = (byte) SpStorage.getInstance().getMacchiatoAmpMode();
        byte macchiatoMicMode = (byte) SpStorage.getInstance().getMacchiatoMicMode();
        byte macchiatoTheme = (byte) SpStorage.getInstance().getMacchiatoTheme();
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
        bArr[7] = macchiatoAmpMode;
        bArr[8] = macchiatoMicMode;
        bArr[9] = macchiatoTheme;
        Task task = new Task(bArr);
        task.bSeqID = bArr[5];
        this.mTaskManager.postTask(task);
        SpStorage.getInstance().setMacchiatoUacVersion(i);
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

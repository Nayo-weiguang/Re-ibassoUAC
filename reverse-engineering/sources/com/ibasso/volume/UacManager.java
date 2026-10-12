package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.controller.CX31993DeviceController;
import com.ibasso.volume.controller.D18DeviceController;
import com.ibasso.volume.controller.Dc03ProDeviceController;
import com.ibasso.volume.controller.Dc04ProDeviceController;
import com.ibasso.volume.controller.Dc04uDeviceController;
import com.ibasso.volume.controller.Dc06DeviceController;
import com.ibasso.volume.controller.Dc06ProDeviceController;
import com.ibasso.volume.controller.Dc07DeviceController;
import com.ibasso.volume.controller.Dc07ProDeviceController;
import com.ibasso.volume.controller.Dc08DeviceController;
import com.ibasso.volume.controller.Dc09DeviceController;
import com.ibasso.volume.controller.MacchiatoDeviceController;
import com.ibasso.volume.controller.TonfaDeviceController;
import com.ibasso.volume.controller.UsbDeviceController;
import com.ibasso.volume.eq.ParametricEqualizer;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.UByte;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
public class UacManager implements IConstants {
    private static final String ACTION_USB_PERMISSION = "com.ibasso.USB_PERMISSION";
    private static final int CX31993_VID = 13058;
    public static final String D18 = "D18";
    public static final String DC03PRO = "iBasso-DC03-Pro";
    public static final String DC04PRO = "iBasso-DC04-Pro";
    public static final String DC04U = "DC04U";
    private static final int DC04U_VID = 1633;
    public static final String DC06 = "iBasso-DC06";
    public static final String DC06PRO = "iBasso-DC06-Pro";
    public static final String DC07 = "iBasso DC-Elite";
    public static final String DC07PRO = "iBasso DC07 Pro";
    private static final int DC07_VID = 12230;
    public static final String DC08 = "Macaron";
    public static final String DC09 = "iBasso DC-Nunchaku";
    public static final int MAX_DC06_VOLUME = 100;
    public static final String Macchiato = "Macchiato";
    public static final String TAG = "UacManager";
    public static final String TONFA = "iBasso DC-Tonfa";
    private static final int VID = 9770;
    private static UacManager mUacManager;
    private Context mContext;
    private IntentFilter mFilterAttachedAndDetached;
    private PendingIntent mPermissionIntent;
    private PowerManager mPowerManager;
    private UsbDeviceConnection mUsbConn;
    private UsbDevice mUsbDevice;
    private UsbDeviceController mUsbDeviceController;
    private UsbManager mUsbManager;
    private final BroadcastReceiver mUsbReceiver;
    private PowerManager.WakeLock mWakeLock;
    private int MSG_SET_VOLUME = 0;
    private int MSG_SET_BALANCE = 1;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
            if (UacManager.this.mUsbDevice == null) {
                return;
            }
            if (message.what == UacManager.this.MSG_SET_VOLUME) {
                if (UacManager.this.mUsbDeviceController != null) {
                    UacManager.this.mUsbDeviceController.setVolume(message.arg1);
                }
            } else if (message.what != UacManager.this.MSG_SET_BALANCE || UacManager.this.mUsbDeviceController == null) {
            } else {
                UacManager.this.mUsbDeviceController.setBalanceVolume(message.arg1);
            }
        }
    };

    private UacManager(Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                    UacManager.this.disconnectDevice();
                    Log.i("UacManager", "(ACTION_USB_DEVICE_DETACHED)");
                    Toast.makeText(context2, (int) R.string.device_disconnect, 0).show();
                } else if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (!intent.getBooleanExtra("permission", false)) {
                        UacManager.this.enumerationUsb();
                    } else if (usbDevice != null) {
                        UacManager.this.onDcDevicePermissionRequired();
                    }
                } else if (UacManager.ACTION_USB_PERMISSION.equals(action)) {
                    UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                    if (!intent.getBooleanExtra("permission", false) || usbDevice2 == null) {
                        return;
                    }
                    UacManager.this.onDcDevicePermissionRequired();
                }
            }
        };
        this.mUsbReceiver = broadcastReceiver;
        this.mContext = context;
        this.mUsbManager = (UsbManager) context.getSystemService("usb");
        this.mPermissionIntent = PendingIntent.getActivity(context, 0, new Intent(ACTION_USB_PERMISSION), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0);
        IntentFilter intentFilter = new IntentFilter();
        this.mFilterAttachedAndDetached = intentFilter;
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mFilterAttachedAndDetached.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        this.mFilterAttachedAndDetached.addAction(ACTION_USB_PERMISSION);
        ContextCompat.registerReceiver(this.mContext, broadcastReceiver, this.mFilterAttachedAndDetached, 2);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
    }

    private void acquireWakeLock() {
        if (this.mWakeLock == null) {
            PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "MangoPlayer:scanning");
            this.mWakeLock = newWakeLock;
            newWakeLock.acquire();
        }
    }

    public void disconnectDevice() {
        this.mUsbDevice = null;
        this.mUsbConn = null;
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.stopTaskManager();
        }
        releaseWakeLock();
        EventBus.getDefault().postSticky(new NormalVolumeEvent(-1));
        EventBus.getDefault().postSticky(new ProductChangedEvent(HttpUrl.FRAGMENT_ENCODE_SET));
    }

    public static UacManager getUacManager() {
        return mUacManager;
    }

    public static synchronized void init(Context context) {
        synchronized (UacManager.class) {
            if (mUacManager == null) {
                mUacManager = new UacManager(context);
            }
        }
    }

    public void onDcDevicePermissionRequired() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.stopTaskManager();
        }
        if (Build.VERSION.SDK_INT >= 26) {
            ContextCompat.startForegroundService(this.mContext, new Intent(this.mContext, UacServices.class));
        } else {
            this.mContext.startService(new Intent(this.mContext, UacServices.class));
        }
        this.mUsbConn = this.mUsbManager.openDevice(this.mUsbDevice);
        EventBus.getDefault().postSticky(new ProductChangedEvent(getProductName()));
        String productName = getProductName();
        Log.i("UacManager", "productName:" + productName);
        if (DC06.equalsIgnoreCase(productName)) {
            this.mUsbDeviceController = new Dc06DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC06PRO.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc06ProDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC03PRO.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc03ProDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC04PRO.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc04ProDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC08.equals(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc08DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC07.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc07DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (TONFA.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new TonfaDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC07PRO.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc07ProDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC09.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc09DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (DC04U.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new Dc04uDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (D18.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new D18DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if (Macchiato.equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new MacchiatoDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else if ("CX31993 384Khz HIFI AUDIO".equalsIgnoreCase(productName)) {
            acquireWakeLock();
            this.mUsbDeviceController = new CX31993DeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
        } else {
            UsbDeviceController usbDeviceController2 = new UsbDeviceController(this.mContext, this.mUsbDevice, this.mUsbConn);
            this.mUsbDeviceController = usbDeviceController2;
            usbDeviceController2.initDevice();
            EventBus.getDefault().postSticky(new NormalVolumeEvent(this.mUsbDeviceController.getVolume()));
        }
    }

    private void parseNativeDigitalFilter(byte b2, int i) {
        this.mUsbDeviceController.parseNativeDigitalFilter(b2, i);
    }

    private void parseVolume(int i) {
        if (this.mUsbDevice == null) {
            return;
        }
        EventBus.getDefault().postSticky(new Dc06VolumeEvent(this.mUsbDeviceController.parseNativeAndSaveVolume(i)));
    }

    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
        }
        this.mWakeLock = null;
    }

    public void dumpFlash() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.dumpFlash();
        }
    }

    public void enumerationUsb() {
        UsbManager usbManager = this.mUsbManager;
        if (usbManager != null) {
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (deviceList.isEmpty()) {
                disconnectDevice();
                this.mUsbDevice = null;
                return;
            }
            Iterator<UsbDevice> it = deviceList.values().iterator();
            boolean z = false;
            while (it.hasNext() && !z) {
                UsbDevice next = it.next();
                int vendorId = next.getVendorId();
                next.getProductId();
                Log.i("UacManager", "mUsbDevice:==" + Integer.toHexString(next.getVendorId()) + ":" + Integer.toHexString(next.getProductId()) + ":" + next.getDeviceName() + ":" + next.getProductName());
                if (vendorId == VID || vendorId == DC07_VID || vendorId == DC04U_VID) {
                    this.mUsbDevice = next;
                    if (this.mUsbManager.hasPermission(next)) {
                        onDcDevicePermissionRequired();
                    } else {
                        this.mUsbManager.requestPermission(this.mUsbDevice, this.mPermissionIntent);
                    }
                    z = true;
                } else {
                    this.mUsbDevice = null;
                }
            }
            if (z) {
                return;
            }
            disconnectDevice();
        }
    }

    public void eraseFlash() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.eraseFlash();
        }
    }

    public int getAmpMode() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getAmpMode();
        }
        return 0;
    }

    public int getBalanceVolume() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getBalanceVolume();
        }
        return 0;
    }

    public int getCodecExternal() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getCodecExternal();
        }
        return 0;
    }

    public int getDRE() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getDRE();
        }
        return 0;
    }

    public int getDacGain() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getDacGain();
        }
        return 0;
    }

    public int getDigitalFilter() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getDigitalFilter();
        }
        return 0;
    }

    public int getDsdFilter() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getDsdFilter();
        }
        return 0;
    }

    public int getEqualizer() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getEqualizer();
        }
        return 0;
    }

    public int getGain() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getGain();
        }
        return 0;
    }

    public int getHpFilter() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getHpFilter();
        }
        return 0;
    }

    public int getKeyControlStatus() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getKeyControlStatus();
        }
        return 0;
    }

    public int getLanguage() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getLanguage();
        }
        return 0;
    }

    public int getMaxVolume() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        return usbDeviceController != null ? usbDeviceController.getMaxVolume() : UsbDeviceController.nVolumeStep.length - 1;
    }

    public int getMicMode() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getMicMode();
        }
        return 0;
    }

    public int getOpVoltage() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getOpVoltage();
        }
        return 0;
    }

    public int getPcmTrim() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getPcmTrim();
        }
        return 0;
    }

    public String getProductName() {
        UsbDevice usbDevice = this.mUsbDevice;
        return (usbDevice == null || usbDevice.getProductName() == null) ? HttpUrl.FRAGMENT_ENCODE_SET : this.mUsbDevice.getProductName().trim();
    }

    public int getUacVersion() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getUacVersion();
        }
        return 0;
    }

    public int getVolume() {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            return usbDeviceController.getVolume();
        }
        return 0;
    }

    public void initDeviceStatus(NativeReportEvent nativeReportEvent) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.initDeviceStatus(nativeReportEvent);
        }
    }

    public boolean isConnected() {
        return this.mUsbDevice != null;
    }

    public void parseCallBackValue(ResponseEvent responseEvent) {
        byte[] bArr;
        char c2;
        if (responseEvent.data == null || !this.mUsbDeviceController.parseCallBackValue(responseEvent)) {
            if (responseEvent.size != 8 || (bArr = responseEvent.data) == null) {
                int i = responseEvent.seqId;
                if (71 == i || 72 == i) {
                    parseNativeDigitalFilter((byte) i, responseEvent.value);
                    return;
                } else if (69 == i || 70 == i || 65 == i || 66 == i) {
                    parseVolume(responseEvent.value);
                    return;
                } else if (73 == i || 74 == i) {
                    EventBus.getDefault().postSticky(new TestObject(HttpUrl.FRAGMENT_ENCODE_SET + responseEvent.value));
                    return;
                } else if (83 == i) {
                    if (this.mUsbDeviceController.getBalanceVolume() >= 0) {
                        parseVolume(responseEvent.value);
                        return;
                    } else {
                        parseVolume(responseEvent.rightVolume);
                        return;
                    }
                } else if (88 == i) {
                    Log.i("UacManager", "FPGA Version: 0x" + Integer.toHexString(responseEvent.value));
                    EventBus.getDefault().postSticky(new FpgaVersionEvent("1." + Integer.toHexString(responseEvent.value & 255)));
                    return;
                } else {
                    return;
                }
            }
            int i2 = responseEvent.seqId;
            if (98 == i2) {
                NativeReportEvent nativeReportEvent = new NativeReportEvent(0, bArr[4], bArr[5], bArr[6], bArr[7]);
                this.mUsbDeviceController.initDeviceStatus(nativeReportEvent);
                EventBus.getDefault().postSticky(nativeReportEvent);
                return;
            }
            if (89 != i2) {
                c2 = 5;
                if (i2 != 0 || bArr[1] != 34) {
                    if (96 == i2 || (i2 == 0 && bArr[1] == 50)) {
                        NativeReportEvent nativeReportEvent2 = new NativeReportEvent(96, bArr[4], bArr[5], bArr[6], bArr[7]);
                        this.mUsbDeviceController.initDeviceStatus(nativeReportEvent2);
                        EventBus.getDefault().postSticky(nativeReportEvent2);
                        return;
                    } else if (97 == i2 || (i2 == 0 && bArr[1] == 66)) {
                        NativeReportEvent nativeReportEvent3 = new NativeReportEvent(97, bArr[4], bArr[5], bArr[6], bArr[7]);
                        this.mUsbDeviceController.initDeviceStatus(nativeReportEvent3);
                        EventBus.getDefault().postSticky(nativeReportEvent3);
                        return;
                    } else if (88 != i2) {
                        if (65 == i2 || 66 == i2 || 69 == i2 || 70 == i2 || 3 == i2 || 4 == i2 || 1 == i2 || 2 == i2 || (i2 == 0 && bArr[1] == 18)) {
                            parseVolume(bArr[4]);
                            return;
                        }
                        return;
                    } else {
                        Log.i("UacManager", "FPGA Version: 0x" + Integer.toHexString(responseEvent.value));
                        if (((responseEvent.data[4] >> 4) & 255) == 0) {
                            EventBus.getDefault().postSticky(new FpgaVersionEvent("1." + ((int) responseEvent.data[5]) + "." + Integer.toHexString(responseEvent.data[6] & UByte.MAX_VALUE)));
                            return;
                        }
                        EventBus.getDefault().postSticky(new FpgaVersionEvent(Integer.toHexString((responseEvent.data[4] >> 4) & 255) + "." + Integer.toHexString(responseEvent.data[5] & UByte.MAX_VALUE) + "." + Integer.toHexString(responseEvent.data[6] & UByte.MAX_VALUE)));
                        return;
                    }
                }
            } else {
                c2 = 5;
            }
            NativeReportEvent nativeReportEvent4 = new NativeReportEvent(89, bArr[4], bArr[c2]);
            this.mUsbDeviceController.initDeviceStatus(nativeReportEvent4);
            EventBus.getDefault().postSticky(nativeReportEvent4);
        }
    }

    public void setAmpMode(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setAmpMode(i);
        }
    }

    public void setBalanceVolume(int i) {
        if (this.mUsbDeviceController != null) {
            this.mHandler.removeMessages(this.MSG_SET_BALANCE);
            Message obtain = Message.obtain();
            obtain.what = this.MSG_SET_BALANCE;
            obtain.arg1 = i;
            this.mHandler.sendMessageDelayed(obtain, 100L);
        }
    }

    public void setCodecExternal(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setCodecExternal(i);
        }
    }

    public void setDRE(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setDRE(i);
        }
    }

    public void setDacGain(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setDacGain(i);
        }
    }

    public void setDigitalFilter(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setDigitalFilter(i);
        }
    }

    public void setDsdFilter(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setDsdFilter(i);
        }
    }

    public void setEqualizer(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setEqualizer(i);
        }
    }

    public void setGain(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setGain(i);
        }
    }

    public void setHpFilter(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setHpFilter(i);
        }
    }

    public void setKeyControlStatus(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setKeyControlStatus(i);
        }
    }

    public void setLanguage(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setLanguage(i);
        }
    }

    public void setMicMode(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setMicMode(i);
        }
    }

    public void setOpVoltage(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setOpVoltage(i);
        }
    }

    public void setOutput(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setOutput(i);
        }
    }

    public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setParametricEqualizer(parametricEqualizer);
        }
    }

    public void setPcmTrim(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setPcmTrim(i);
        }
    }

    public void setScreenRotation(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setScreenRotation(i);
        }
    }

    public void setScreenTime(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setScreenTime(i);
        }
    }

    public void setUacVersion(int i) {
        UsbDeviceController usbDeviceController = this.mUsbDeviceController;
        if (usbDeviceController != null) {
            usbDeviceController.setUacVersion(i);
        }
    }

    public void setVolume(int i) {
        if (this.mUsbDeviceController.getMaxVolume() == 63) {
            this.mUsbDeviceController.setVolume(i);
            return;
        }
        this.mHandler.removeMessages(this.MSG_SET_VOLUME);
        Message obtain = Message.obtain();
        obtain.what = this.MSG_SET_VOLUME;
        obtain.arg1 = i;
        this.mHandler.sendMessageDelayed(obtain, 100L);
    }

    public void setVolume(boolean z) {
        if (this.mUsbDevice == null) {
            return;
        }
        int volume = getVolume();
        if (z) {
            int i = volume + 1;
            if (i > getMaxVolume()) {
                i = getMaxVolume();
            }
            setVolume(i);
            return;
        }
        int i2 = volume - 1;
        if (i2 < 0) {
            i2 = 0;
        }
        setVolume(i2);
    }
}

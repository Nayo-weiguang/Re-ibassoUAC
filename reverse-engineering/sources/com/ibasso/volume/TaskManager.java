package com.ibasso.volume;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.ibasso.volume.MonitorThread;
import java.util.concurrent.LinkedBlockingDeque;
import kotlin.UByte;
import org.greenrobot.eventbus.EventBus;
public class TaskManager implements IConstants, MonitorThread.OnRequestCallBackListener {
    private static final String TAG = "UacManager";
    private volatile Task currentTask;
    private UsbInterface mInterface;
    private UsbEndpoint mOutEndpointIn;
    private UsbDeviceConnection mUsbConn;
    private MonitorThread monitorThread;
    private boolean startWork;
    private final LinkedBlockingDeque<Task> taskDeque = new LinkedBlockingDeque<>();
    private final Object lockObject = new Object();
    public Thread workThread = new Thread() {
        private void doWork(Task task) {
            int i = task.isCustomCommand;
            if (i != -1) {
                if (i == 2) {
                    TaskManager.this.sendCustomData2(task.data);
                } else if (i == 3) {
                    TaskManager.this.sendCustomData3(task.data);
                } else {
                    TaskManager.this.sendCustomData1(task.bSeqID, task.data);
                }
            } else if (task.isWriteTask) {
                if (1 == task.productId) {
                    TaskManager.this.writeRegister(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset, task.bI2CRwOffset2, task.bI2CRwOffset3, task.bI2CRwOffset4, (byte) task.value);
                } else if (task.regOffsetBytes == 2) {
                    TaskManager.this.writeRegisterFor2BytesOffset(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset, task.bI2CRwOffset2, (byte) task.value);
                } else {
                    TaskManager.this.writeRegisterForDc06(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset, (byte) task.value);
                }
            } else if (1 == task.productId) {
                TaskManager.this.sendGetRegisterRequest(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset, task.bI2CRwOffset2, task.bI2CRwOffset3, task.bI2CRwOffset4);
            } else if (task.regOffsetBytes == 2) {
                TaskManager.this.sendGetRegisterRequestFor2BytesOffset(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset, task.bI2CRwOffset2);
            } else {
                TaskManager.this.sendGetRegisterRequestForDc06(task.bSeqID, task.bI2cDevAdr, task.bI2CRwOffset);
            }
        }

        @Override
        public void run() {
            while (TaskManager.this.startWork) {
                try {
                    Task task = (Task) TaskManager.this.taskDeque.take();
                    synchronized (TaskManager.this.lockObject) {
                        Log.i("UacManager", "task====== " + IConstants.idToString(task.bSeqID));
                        TaskManager.this.currentTask = task;
                        doWork(TaskManager.this.currentTask);
                        if (TaskManager.this.currentTask.isCustomCommand == 3) {
                            TaskManager.this.currentTask.state = 2;
                        }
                        if (TaskManager.this.currentTask.state != 2) {
                            if (TaskManager.this.currentTask.needRetry) {
                                TaskManager.this.lockObject.wait(200L);
                            } else {
                                TaskManager.this.lockObject.wait(30L);
                                TaskManager.this.currentTask.state = 2;
                            }
                        }
                        if (TaskManager.this.currentTask.state != 2) {
                            Log.e("UacManager", "***retry again***");
                            doWork(TaskManager.this.currentTask);
                            TaskManager.this.lockObject.wait(200L);
                        }
                        TaskManager.this.currentTask = null;
                    }
                } catch (InterruptedException unused) {
                    Log.e("UacManager", "TaskManager.workThread.run() InterruptedException");
                }
            }
            Log.e("UacManager", "TaskManager.workThread.run() End");
        }
    };

    public TaskManager(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        this.mUsbConn = usbDeviceConnection;
        if (usbDevice != null) {
            int i = 0;
            while (true) {
                if (i >= usbDevice.getInterfaceCount()) {
                    break;
                }
                UsbInterface usbInterface = usbDevice.getInterface(i);
                if (usbInterface.getInterfaceClass() == 3 && usbInterface.getInterfaceSubclass() == 0 && usbInterface.getInterfaceProtocol() == 0) {
                    this.mInterface = usbInterface;
                    for (int i2 = 0; i2 < this.mInterface.getEndpointCount(); i2++) {
                        UsbEndpoint endpoint = this.mInterface.getEndpoint(i2);
                        if (endpoint != null) {
                            if (endpoint.getDirection() == 128) {
                                Log.i("UacManager", "[isUsbPermission] Endpoint " + i2 + " is USB_DIR_IN");
                            } else {
                                Log.i("UacManager", "[isUsbPermission] Endpoint " + i2 + " is USB_DIR_OUT");
                                this.mOutEndpointIn = endpoint;
                            }
                        }
                    }
                } else {
                    i++;
                }
            }
        }
        MonitorThread monitorThread = new MonitorThread(usbDevice, usbDeviceConnection, this);
        this.monitorThread = monitorThread;
        this.startWork = true;
        monitorThread.start();
        this.workThread.start();
    }

    private void processOldDevice(byte[] bArr) {
        byte b2;
        byte b3 = bArr[0];
        int length = bArr.length;
        if (b3 == 0 && ((b2 = bArr[1]) == 50 || b2 == 34 || b2 == 18 || b2 == 66)) {
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[0], bArr, length));
            return;
        }
        synchronized (this.lockObject) {
            try {
                if (this.currentTask != null) {
                    Log.d("UacManager", "Read Data length " + length + " **expect**" + IConstants.idToString(this.currentTask.bSeqID) + "**real**" + IConstants.idToString(b3) + "*****");
                    for (int i = 0; i < length; i++) {
                        Log.d("UacManager", IConstants.idToString(b3) + "----->" + String.format("Data[%d] = %02x", Integer.valueOf(i), Byte.valueOf(bArr[i])));
                    }
                    if (this.currentTask.bSeqID == b3 && this.currentTask.state != 2) {
                        this.currentTask.state = 2;
                        this.lockObject.notifyAll();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (b3 == 1) {
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[0], bArr, length));
        } else if (b3 >= 65) {
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[0], bArr, length));
        } else {
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[0], -1, length));
            Log.d("UacManager", "====Write Data OK");
        }
    }

    public void sendCustomData1(byte b2, byte[] bArr) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    byte[] bArr2 = bArr;
                    int controlTransfer = usbDeviceConnection.controlTransfer(33, 9, 512, 0, bArr2, bArr.length, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "custom1 " + IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                    }
                    i = controlTransfer;
                    bArr = bArr2;
                }
            }
        }
    }

    public void sendCustomData2(byte[] bArr) {
        UsbEndpoint usbEndpoint;
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null && (usbEndpoint = this.mOutEndpointIn) != null) {
                    i = usbDeviceConnection.bulkTransfer(usbEndpoint, bArr, bArr.length, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "custom2 " + IConstants.idToString(bArr[4]) + " write controlTransfer ret : " + i);
                    if (i > 0) {
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                    }
                }
            }
        }
    }

    public void sendCustomData3(byte[] bArr) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    byte[] bArr2 = bArr;
                    Log.d("UacManager", "custom3  write controlTransfer ret : " + usbDeviceConnection.controlTransfer(33, 1, InputDeviceCompat.SOURCE_DPAD, 0, bArr2, bArr.length, 500));
                    int controlTransfer = this.mUsbConn.controlTransfer(33, 1, 514, 0, bArr2, bArr2.length, 500);
                    Log.d("UacManager", "custom3  write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                    }
                    i = controlTransfer;
                    bArr = bArr2;
                }
            }
        }
    }

    public void sendGetRegisterRequest(byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        if (this.mUsbConn != null) {
            int i = 3;
            int i2 = -1;
            while (i2 < 0 && i > 0) {
                byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                bArr[0] = b2;
                bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_2;
                bArr[2] = -56;
                bArr[3] = b3;
                bArr[6] = 4;
                bArr[7] = b4;
                bArr[8] = b5;
                bArr[9] = b6;
                bArr[10] = b7;
                bArr[11] = -1;
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    int controlTransfer = usbDeviceConnection.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "bI2CRwOffset1 " + ((int) b4) + " " + IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i2 = controlTransfer;
                        i = 0;
                    } else {
                        try {
                            Thread.sleep(2L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i--;
                        i2 = controlTransfer;
                    }
                }
            }
        }
    }

    public void sendGetRegisterRequestFor2BytesOffset(byte b2, byte b3, byte b4, byte b5) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    bArr[0] = b2;
                    bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_2;
                    bArr[2] = -28;
                    bArr[3] = b3;
                    bArr[4] = b4;
                    bArr[5] = b5;
                    bArr[6] = 1;
                    int controlTransfer = usbDeviceConnection.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "bI2CRwOffset1 " + ((int) b4) + " " + IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(2L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                    }
                    i = controlTransfer;
                }
            }
        }
    }

    public void sendGetRegisterRequestForDc06(byte b2, byte b3, byte b4) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                bArr[0] = b2;
                bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_2;
                bArr[2] = -36;
                bArr[3] = b3;
                bArr[4] = b4;
                bArr[6] = 1;
                int controlTransfer = this.mUsbConn.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                Log.d("UacManager", IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                if (controlTransfer > 0) {
                    i2 = 0;
                } else {
                    try {
                        Thread.sleep(2L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    i2--;
                }
                i = controlTransfer;
            }
        }
    }

    public void writeRegister(byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                bArr[0] = b2;
                bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_1;
                bArr[2] = -120;
                bArr[3] = b3;
                bArr[6] = 5;
                bArr[7] = b4;
                bArr[8] = b5;
                bArr[9] = b6;
                bArr[10] = b7;
                bArr[11] = b8;
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    int controlTransfer = usbDeviceConnection.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "bI2CRwOffset1 " + ((int) b4) + " " + IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i = controlTransfer;
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(2L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                        i = controlTransfer;
                    }
                }
            }
        }
    }

    public void writeRegisterFor2BytesOffset(byte b2, byte b3, byte b4, byte b5, byte b6) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                bArr[0] = b2;
                bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_1;
                bArr[2] = -96;
                bArr[3] = b3;
                bArr[4] = b4;
                bArr[5] = b5;
                bArr[6] = 1;
                bArr[7] = b6;
                UsbDeviceConnection usbDeviceConnection = this.mUsbConn;
                if (usbDeviceConnection != null) {
                    int controlTransfer = usbDeviceConnection.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    Log.d("UacManager", "bI2CRwOffset1 " + ((int) b4) + " " + IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                    if (controlTransfer > 0) {
                        i = controlTransfer;
                        i2 = 0;
                    } else {
                        try {
                            Thread.sleep(2L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        i2--;
                        i = controlTransfer;
                    }
                }
            }
        }
    }

    public void writeRegisterForDc06(byte b2, byte b3, byte b4, byte b5) {
        if (this.mUsbConn != null) {
            int i = -1;
            int i2 = 3;
            while (i < 0 && i2 > 0) {
                byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                bArr[0] = b2;
                bArr[1] = IConstants.ID_SET_DIGITAL_FILTER_1;
                bArr[2] = -104;
                bArr[3] = b3;
                bArr[4] = b4;
                bArr[6] = 1;
                bArr[7] = b5;
                int controlTransfer = this.mUsbConn.controlTransfer(33, 9, 512, 0, bArr, 16, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                Log.d("UacManager", IConstants.idToString(b2) + " write controlTransfer ret : " + controlTransfer);
                if (controlTransfer > 0) {
                    i2 = 0;
                } else {
                    try {
                        Thread.sleep(2L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    i2--;
                }
                i = controlTransfer;
            }
        }
    }

    @Override
    public void onRequestDone(byte[] bArr) {
        Log.d("UacManager", "Read Data length " + bArr.length);
        int i = 0;
        if (bArr.length == 64) {
            byte b2 = bArr[5];
            if ((bArr[0] & UByte.MAX_VALUE) == 75 && (bArr[1] & UByte.MAX_VALUE) == 128) {
                b2 = bArr[4];
            }
            int length = bArr.length;
            synchronized (this.lockObject) {
                try {
                    if (this.currentTask != null) {
                        Log.d("UacManager", "Read Data length " + length + " **expect**" + IConstants.idToString(this.currentTask.bSeqID) + "**real**" + IConstants.idToString(b2) + "*****");
                        StringBuilder sb = new StringBuilder();
                        int length2 = bArr.length;
                        while (i < length2) {
                            sb.append(String.format("%02x,", Byte.valueOf(bArr[i])));
                            i++;
                        }
                        Log.d("UacManager", "###data[] = {" + ((Object) sb) + "}");
                        if (this.currentTask.bSeqID == b2 && this.currentTask.state != 2) {
                            this.currentTask.state = 2;
                            this.lockObject.notifyAll();
                        }
                    }
                } finally {
                }
            }
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[4], bArr, 64));
        } else if (bArr.length <= 8) {
            if (bArr.length == 8) {
                processOldDevice(bArr);
            }
        } else if ((bArr[4] & UByte.MAX_VALUE) == 254 && bArr[5] == 1) {
            Log.d("UacManager", String.format("native volume changed: left 0x%02x right 0x%02x", Integer.valueOf(bArr[8] & UByte.MAX_VALUE), Integer.valueOf(bArr[9] & UByte.MAX_VALUE)));
            EventBus.getDefault().post(new ResponseEvent(83, bArr[8] & UByte.MAX_VALUE, bArr[9] & UByte.MAX_VALUE, 32));
        } else {
            byte b3 = bArr[6];
            byte b4 = bArr[7];
            synchronized (this.lockObject) {
                try {
                    if (this.currentTask != null) {
                        Log.d("UacManager", "Read Data length " + ((int) b4) + " **expect**" + IConstants.idToString(this.currentTask.bSeqID) + "**real**" + IConstants.idToString(b3) + "*****");
                        while (i < b4) {
                            Log.d("UacManager", IConstants.idToString(this.currentTask.bSeqID) + "----->" + String.format("Data[%d] = %02x", Integer.valueOf(i), Byte.valueOf(bArr[i + 8])));
                            i++;
                        }
                        if (this.currentTask.bSeqID == b3 && this.currentTask.state != 2) {
                            this.currentTask.state = 2;
                            this.lockObject.notifyAll();
                        }
                    }
                } finally {
                }
            }
            if (b3 >= 65) {
                EventBus.getDefault().postSticky(new ResponseEvent(bArr[6], bArr[8] & UByte.MAX_VALUE, b4));
                return;
            }
            EventBus.getDefault().postSticky(new ResponseEvent(bArr[6], -1, b4));
            Log.d("UacManager", "====Write Data OK");
        }
    }

    public void postTask(Task task) {
        this.taskDeque.add(task);
    }

    public void stopTaskManager() {
        this.startWork = false;
        this.mUsbConn = null;
        this.workThread.interrupt();
        this.monitorThread.stopMonitor();
        this.monitorThread.interrupt();
    }
}

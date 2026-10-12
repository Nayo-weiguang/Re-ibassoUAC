### private constructor <init>(Landroid/content/Context;)V
    invoke-direct v6, Ljava/lang/Object;-><init>()V
    const/4 v0, 0
    iput v0, v6, Lcom/ibasso/volume/UacManager;->MSG_SET_VOLUME I
    const/4 v1, 1
    iput v1, v6, Lcom/ibasso/volume/UacManager;->MSG_SET_BALANCE I
    new-instance v1, Lcom/ibasso/volume/UacManager$1;
    invoke-direct v1, v6, Lcom/ibasso/volume/UacManager$1;-><init>(Lcom/ibasso/volume/UacManager;)V
    iput-object v1, v6, Lcom/ibasso/volume/UacManager;->mHandler Landroid/os/Handler;
    new-instance v1, Lcom/ibasso/volume/UacManager$2;
    invoke-direct v1, v6, Lcom/ibasso/volume/UacManager$2;-><init>(Lcom/ibasso/volume/UacManager;)V
    iput-object v1, v6, Lcom/ibasso/volume/UacManager;->mUsbReceiver Landroid/content/BroadcastReceiver;
    iput-object v7, v6, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    const-string v2, "usb"
    invoke-virtual v7, v2, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;
    move-result-object v2
    check-cast v2, Landroid/hardware/usb/UsbManager;
    iput-object v2, v6, Lcom/ibasso/volume/UacManager;->mUsbManager Landroid/hardware/usb/UsbManager;
    new-instance v2, Landroid/content/Intent;
    const-string v3, "com.ibasso.USB_PERMISSION"
    invoke-direct v2, v3, Landroid/content/Intent;-><init>(Ljava/lang/String;)V
    sget v4, Landroid/os/Build$VERSION;->SDK_INT I
    const/16 v5, 31
    if-lt v4, v5, +005h
    const/high16 v4, 67108864
    goto +2h
    move v4, v0
    invoke-static v7, v0, v2, v4, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context; I Landroid/content/Intent; I)Landroid/app/PendingIntent;
    move-result-object v0
    iput-object v0, v6, Lcom/ibasso/volume/UacManager;->mPermissionIntent Landroid/app/PendingIntent;
    new-instance v0, Landroid/content/IntentFilter;
    invoke-direct v0, Landroid/content/IntentFilter;-><init>()V
    iput-object v0, v6, Lcom/ibasso/volume/UacManager;->mFilterAttachedAndDetached Landroid/content/IntentFilter;
    const-string v2, "android.hardware.usb.action.USB_DEVICE_DETACHED"
    invoke-virtual v0, v2, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V
    iget-object v0, v6, Lcom/ibasso/volume/UacManager;->mFilterAttachedAndDetached Landroid/content/IntentFilter;
    const-string v2, "android.hardware.usb.action.USB_DEVICE_ATTACHED"
    invoke-virtual v0, v2, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V
    iget-object v0, v6, Lcom/ibasso/volume/UacManager;->mFilterAttachedAndDetached Landroid/content/IntentFilter;
    invoke-virtual v0, v3, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V
    iget-object v0, v6, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v6, Lcom/ibasso/volume/UacManager;->mFilterAttachedAndDetached Landroid/content/IntentFilter;
    const/4 v3, 2
    invoke-static v0, v1, v2, v3, Landroidx/core/content/ContextCompat;->registerReceiver(Landroid/content/Context; Landroid/content/BroadcastReceiver; Landroid/content/IntentFilter; I)Landroid/content/Intent;
    const-string v0, "power"
    invoke-virtual v7, v0, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;
    move-result-object v7
    check-cast v7, Landroid/os/PowerManager;
    iput-object v7, v6, Lcom/ibasso/volume/UacManager;->mPowerManager Landroid/os/PowerManager;
    return-void 
### public static synthetic access$000(Lcom/ibasso/volume/UacManager;)Landroid/hardware/usb/UsbDevice;
    iget-object v0, v0, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    return-object v0
### public static synthetic access$100(Lcom/ibasso/volume/UacManager;)I
    iget v0, v0, Lcom/ibasso/volume/UacManager;->MSG_SET_VOLUME I
    return v0
### public static synthetic access$200(Lcom/ibasso/volume/UacManager;)Lcom/ibasso/volume/controller/UsbDeviceController;
    iget-object v0, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-object v0
### public static synthetic access$300(Lcom/ibasso/volume/UacManager;)I
    iget v0, v0, Lcom/ibasso/volume/UacManager;->MSG_SET_BALANCE I
    return v0
### public static synthetic access$400(Lcom/ibasso/volume/UacManager;)V
    invoke-direct v0, Lcom/ibasso/volume/UacManager;->disconnectDevice()V
    return-void 
### public static synthetic access$500(Lcom/ibasso/volume/UacManager;)V
    invoke-direct v0, Lcom/ibasso/volume/UacManager;->onDcDevicePermissionRequired()V
    return-void 
### private acquireWakeLock()V
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mWakeLock Landroid/os/PowerManager$WakeLock;
    if-nez v0, +010h
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mPowerManager Landroid/os/PowerManager;
    const/4 v1, 1
    const-string v2, "MangoPlayer:scanning"
    invoke-virtual v0, v1, v2, Landroid/os/PowerManager;->newWakeLock(I Ljava/lang/String;)Landroid/os/PowerManager$WakeLock;
    move-result-object v0
    iput-object v0, v3, Lcom/ibasso/volume/UacManager;->mWakeLock Landroid/os/PowerManager$WakeLock;
    invoke-virtual v0, Landroid/os/PowerManager$WakeLock;->acquire()V
    return-void 
### private disconnectDevice()V
    const/4 v0, 0
    iput-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iput-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->stopTaskManager()V
    invoke-direct v3, Lcom/ibasso/volume/UacManager;->releaseWakeLock()V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/NormalVolumeEvent;
    const/4 v2, -1
    invoke-direct v1, v2, Lcom/ibasso/volume/NormalVolumeEvent;-><init>(I)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/ProductChangedEvent;
    const-string v2, ""
    invoke-direct v1, v2, Lcom/ibasso/volume/ProductChangedEvent;-><init>(Ljava/lang/String;)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
### public static getUacManager()Lcom/ibasso/volume/UacManager;
    sget-object v0, Lcom/ibasso/volume/UacManager;->mUacManager Lcom/ibasso/volume/UacManager;
    return-object v0
### public static synchronized init(Landroid/content/Context;)V
    const-class v0, Lcom/ibasso/volume/UacManager;
    monitor-enter v0
    sget-object v1, Lcom/ibasso/volume/UacManager;->mUacManager Lcom/ibasso/volume/UacManager;
    if-nez v1, +00ch
    new-instance v1, Lcom/ibasso/volume/UacManager;
    invoke-direct v1, v2, Lcom/ibasso/volume/UacManager;-><init>(Landroid/content/Context;)V
    sput-object v1, Lcom/ibasso/volume/UacManager;->mUacManager Lcom/ibasso/volume/UacManager;
    goto +3h
    move-exception v2
    goto +3h
    monitor-exit v0
    return-void 
    monitor-exit v0
    throw v2
### private onDcDevicePermissionRequired()V
    iget-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->stopTaskManager()V
    sget v0, Landroid/os/Build$VERSION;->SDK_INT I
    const/16 v1, 26
    const-class v2, Lcom/ibasso/volume/UacServices;
    if-lt v0, v1, +00fh
    iget-object v0, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    new-instance v1, Landroid/content/Intent;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    invoke-direct v1, v3, v2, Landroid/content/Intent;-><init>(Landroid/content/Context; Ljava/lang/Class;)V
    invoke-static v0, v1, Landroidx/core/content/ContextCompat;->startForegroundService(Landroid/content/Context; Landroid/content/Intent;)V
    goto +dh
    iget-object v0, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    new-instance v1, Landroid/content/Intent;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    invoke-direct v1, v3, v2, Landroid/content/Intent;-><init>(Landroid/content/Context; Ljava/lang/Class;)V
    invoke-virtual v0, v1, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;
    iget-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbManager Landroid/hardware/usb/UsbManager;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v0, v1, Landroid/hardware/usb/UsbManager;->openDevice(Landroid/hardware/usb/UsbDevice;)Landroid/hardware/usb/UsbDeviceConnection;
    move-result-object v0
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/ProductChangedEvent;
    invoke-virtual v4, Lcom/ibasso/volume/UacManager;->getProductName()Ljava/lang/String;
    move-result-object v2
    invoke-direct v1, v2, Lcom/ibasso/volume/ProductChangedEvent;-><init>(Ljava/lang/String;)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    invoke-virtual v4, Lcom/ibasso/volume/UacManager;->getProductName()Ljava/lang/String;
    move-result-object v0
    new-instance v1, Ljava/lang/StringBuilder;
    invoke-direct v1, Ljava/lang/StringBuilder;-><init>()V
    const-string v2, "productName:"
    invoke-virtual v1, v2, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v1, v0, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v1, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v1
    const-string v2, "UacManager"
    invoke-static v2, v1, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    const-string v1, "iBasso-DC06"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +010h
    new-instance v0, Lcom/ibasso/volume/controller/Dc06DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc06DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso-DC06-Pro"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc06ProDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc06ProDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso-DC03-Pro"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc03ProDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc03ProDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso-DC04-Pro"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc04ProDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc04ProDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "Macaron"
    invoke-virtual v1, v0, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc08DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc08DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso DC-Elite"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc07DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc07DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso DC-Tonfa"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/TonfaDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/TonfaDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso DC07 Pro"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc07ProDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc07ProDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "iBasso DC-Nunchaku"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc09DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc09DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "DC04U"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/Dc04uDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/Dc04uDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "D18"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/D18DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/D18DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "Macchiato"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v1
    if-eqz v1, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/MacchiatoDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/MacchiatoDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    const-string v1, "CX31993 384Khz HIFI AUDIO"
    invoke-virtual v1, v0, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z
    move-result v0
    if-eqz v0, +013h
    invoke-direct v4, Lcom/ibasso/volume/UacManager;->acquireWakeLock()V
    new-instance v0, Lcom/ibasso/volume/controller/CX31993DeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/CX31993DeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    return-void 
    new-instance v0, Lcom/ibasso/volume/controller/UsbDeviceController;
    iget-object v1, v4, Lcom/ibasso/volume/UacManager;->mContext Landroid/content/Context;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v3, v4, Lcom/ibasso/volume/UacManager;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-direct v0, v1, v2, v3, Lcom/ibasso/volume/controller/UsbDeviceController;-><init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    iput-object v0, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->initDevice()V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/NormalVolumeEvent;
    iget-object v2, v4, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v2, Lcom/ibasso/volume/controller/UsbDeviceController;->getVolume()I
    move-result v2
    invoke-direct v1, v2, Lcom/ibasso/volume/NormalVolumeEvent;-><init>(I)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
### private parseNativeDigitalFilter(B I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v0, v2, v3, Lcom/ibasso/volume/controller/UsbDeviceController;->parseNativeDigitalFilter(B I)V
    return-void 
### private parseVolume(I)V
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    if-nez v0, +003h
    return-void 
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/Dc06VolumeEvent;
    iget-object v2, v3, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v2, v4, Lcom/ibasso/volume/controller/UsbDeviceController;->parseNativeAndSaveVolume(I)I
    move-result v4
    invoke-direct v1, v4, Lcom/ibasso/volume/Dc06VolumeEvent;-><init>(I)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
### private releaseWakeLock()V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mWakeLock Landroid/os/PowerManager$WakeLock;
    if-eqz v0, +005h
    invoke-virtual v0, Landroid/os/PowerManager$WakeLock;->release()V
    const/4 v0, 0
    iput-object v0, v1, Lcom/ibasso/volume/UacManager;->mWakeLock Landroid/os/PowerManager$WakeLock;
    return-void 
### public dumpFlash()V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->dumpFlash()V
    return-void 
### public enumerationUsb()V
    iget-object v0, v8, Lcom/ibasso/volume/UacManager;->mUsbManager Landroid/hardware/usb/UsbManager;
    if-eqz v0, +0a0h
    invoke-virtual v0, Landroid/hardware/usb/UsbManager;->getDeviceList()Ljava/util/HashMap;
    move-result-object v0
    invoke-virtual v0, Ljava/util/HashMap;->isEmpty()Z
    move-result v1
    const/4 v2, 0
    if-eqz v1, +008h
    invoke-direct v8, Lcom/ibasso/volume/UacManager;->disconnectDevice()V
    iput-object v2, v8, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    return-void 
    invoke-virtual v0, Ljava/util/HashMap;->values()Ljava/util/Collection;
    move-result-object v0
    invoke-interface v0, Ljava/util/Collection;->iterator()Ljava/util/Iterator;
    move-result-object v0
    const/4 v1, 0
    invoke-interface v0, Ljava/util/Iterator;->hasNext()Z
    move-result v3
    if-eqz v3, +07bh
    if-nez v1, +079h
    invoke-interface v0, Ljava/util/Iterator;->next()Ljava/lang/Object;
    move-result-object v3
    check-cast v3, Landroid/hardware/usb/UsbDevice;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getVendorId()I
    move-result v4
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getProductId()I
    new-instance v5, Ljava/lang/StringBuilder;
    invoke-direct v5, Ljava/lang/StringBuilder;-><init>()V
    const-string v6, "mUsbDevice:=="
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getVendorId()I
    move-result v6
    invoke-static v6, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v6
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    const-string v6, ":"
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getProductId()I
    move-result v7
    invoke-static v7, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v7
    invoke-virtual v5, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getDeviceName()Ljava/lang/String;
    move-result-object v7
    invoke-virtual v5, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getProductName()Ljava/lang/String;
    move-result-object v6
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v5
    const-string v6, "UacManager"
    invoke-static v6, v5, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    const/16 v5, 9770
    if-eq v4, v5, +00dh
    const/16 v5, 12230
    if-eq v4, v5, +009h
    const/16 v5, 1633
    if-eq v4, v5, +005h
    iput-object v2, v8, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    goto -65h
    iput-object v3, v8, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v1, v8, Lcom/ibasso/volume/UacManager;->mUsbManager Landroid/hardware/usb/UsbManager;
    invoke-virtual v1, v3, Landroid/hardware/usb/UsbManager;->hasPermission(Landroid/hardware/usb/UsbDevice;)Z
    move-result v1
    if-nez v1, +00ch
    iget-object v1, v8, Lcom/ibasso/volume/UacManager;->mUsbManager Landroid/hardware/usb/UsbManager;
    iget-object v3, v8, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v4, v8, Lcom/ibasso/volume/UacManager;->mPermissionIntent Landroid/app/PendingIntent;
    invoke-virtual v1, v3, v4, Landroid/hardware/usb/UsbManager;->requestPermission(Landroid/hardware/usb/UsbDevice; Landroid/app/PendingIntent;)V
    goto +4h
    invoke-direct v8, Lcom/ibasso/volume/UacManager;->onDcDevicePermissionRequired()V
    const/4 v1, 1
    goto -7eh
    if-nez v1, +005h
    invoke-direct v8, Lcom/ibasso/volume/UacManager;->disconnectDevice()V
    return-void 
### public eraseFlash()V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->eraseFlash()V
    return-void 
### public getAmpMode()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getAmpMode()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getBalanceVolume()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getBalanceVolume()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getCodecExternal()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getCodecExternal()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getDRE()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getDRE()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getDacGain()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getDacGain()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getDigitalFilter()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getDigitalFilter()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getDsdFilter()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getDsdFilter()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getEqualizer()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getEqualizer()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getGain()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getGain()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getHpFilter()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getHpFilter()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getKeyControlStatus()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getKeyControlStatus()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getLanguage()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getLanguage()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getMaxVolume()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    return v0
    sget-object v0, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    array-length v0, v0
    add-int/lit8 v0, v0, -1
    return v0
### public getMicMode()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMicMode()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getOpVoltage()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getOpVoltage()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getPcmTrim()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getPcmTrim()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getProductName()Ljava/lang/String;
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    if-eqz v0, +013h
    invoke-virtual v0, Landroid/hardware/usb/UsbDevice;->getProductName()Ljava/lang/String;
    move-result-object v0
    if-eqz v0, +00dh
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v0, Landroid/hardware/usb/UsbDevice;->getProductName()Ljava/lang/String;
    move-result-object v0
    invoke-virtual v0, Ljava/lang/String;->trim()Ljava/lang/String;
    move-result-object v0
    return-object v0
    const-string v0, ""
    return-object v0
### public getUacVersion()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getUacVersion()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public getVolume()I
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +007h
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getVolume()I
    move-result v0
    return v0
    const/4 v0, 0
    return v0
### public initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    return-void 
### public isConnected()Z
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    if-eqz v0, +004h
    const/4 v0, 1
    return v0
    const/4 v0, 0
    return v0
### public parseCallBackValue(Lcom/ibasso/volume/ResponseEvent;)V
    move-object/from16 v0, v23
    move-object/from16 v1, v24
    iget-object v2, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    if-eqz v2, +00ch
    iget-object v2, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v2, v1, Lcom/ibasso/volume/controller/UsbDeviceController;->parseCallBackValue(Lcom/ibasso/volume/ResponseEvent;)Z
    move-result v2
    if-eqz v2, +004h
    goto/16 +1f6h
    iget v2, v1, Lcom/ibasso/volume/ResponseEvent;->size I
    const/16 v3, 8
    const/16 v4, 70
    const/16 v5, 69
    const/16 v6, 65
    const-string v7, "1."
    const-string v8, "FPGA Version: 0x"
    const-string v9, "UacManager"
    const/16 v10, 88
    const/16 v11, 66
    if-ne v2, v3, +16ch
    iget-object v2, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    if-eqz v2, +168h
    iget v3, v1, Lcom/ibasso/volume/ResponseEvent;->seqId I
    const/4 v13, 6
    const/4 v14, 5
    const/4 v15, 4
    const/16 v16, 7
    const/16 v12, 98
    if-ne v12, v3, +020h
    new-instance v17, Lcom/ibasso/volume/bean/NativeReportEvent;
    aget-byte v19, v2, v15
    aget-byte v20, v2, v14
    aget-byte v21, v2, v13
    aget-byte v22, v2, v16
    const/16 v18, 0
    invoke-direct/range v17 ... v22, Lcom/ibasso/volume/bean/NativeReportEvent;-><init>(I I I I I)V
    move-object/from16 v1, v17
    iget-object v2, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v2, v1, Lcom/ibasso/volume/controller/UsbDeviceController;->initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v2
    invoke-virtual v2, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    const/16 v12, 89
    if-eq v12, v3, +11fh
    move/from16 v17, v13
    const/4 v13, 1
    move/from16 v18, v14
    if-nez v3, +00bh
    aget-byte v14, v2, v13
    const/16 v12, 34
    if-ne v14, v12, +005h
    move-object v1, v2
    goto/16 +113h
    const/16 v12, 96
    if-eq v12, v3, +00ah
    if-nez v3, +00bh
    aget-byte v12, v2, v13
    const/16 v14, 50
    if-ne v12, v14, +005h
    move-object v1, v2
    goto/16 +0e4h
    const/16 v12, 97
    if-eq v12, v3, +008h
    if-nez v3, +009h
    aget-byte v12, v2, v13
    if-ne v12, v11, +005h
    move-object v1, v2
    goto/16 +0bbh
    if-ne v10, v3, +099h
    new-instance v2, Ljava/lang/StringBuilder;
    invoke-direct v2, Ljava/lang/StringBuilder;-><init>()V
    invoke-virtual v2, v8, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget v3, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-static v3, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v3
    invoke-virtual v2, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v2, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v2
    invoke-static v9, v2, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    iget-object v2, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v2, v2, v15
    shr-int/2addr v2, v15
    and-int/lit16 v2, v2, 255
    const-string v3, "."
    if-nez v2, +032h
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v2
    new-instance v4, Lcom/ibasso/volume/bean/FpgaVersionEvent;
    new-instance v5, Ljava/lang/StringBuilder;
    invoke-direct v5, Ljava/lang/StringBuilder;-><init>()V
    invoke-virtual v5, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v6, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v6, v6, v18
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v5, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v1, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v1, v1, v17
    and-int/lit16 v1, v1, 255
    invoke-static v1, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v1
    invoke-virtual v5, v1, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v1
    invoke-direct v4, v1, Lcom/ibasso/volume/bean/FpgaVersionEvent;-><init>(Ljava/lang/String;)V
    invoke-virtual v2, v4, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v2
    new-instance v4, Lcom/ibasso/volume/bean/FpgaVersionEvent;
    new-instance v5, Ljava/lang/StringBuilder;
    invoke-direct v5, Ljava/lang/StringBuilder;-><init>()V
    iget-object v6, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v6, v6, v15
    shr-int/2addr v6, v15
    and-int/lit16 v6, v6, 255
    invoke-static v6, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v6
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v6, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v6, v6, v18
    and-int/lit16 v6, v6, 255
    invoke-static v6, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v6
    invoke-virtual v5, v6, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v1, v1, Lcom/ibasso/volume/ResponseEvent;->data [B
    aget-byte v1, v1, v17
    and-int/lit16 v1, v1, 255
    invoke-static v1, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v1
    invoke-virtual v5, v1, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v5, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v1
    invoke-direct v4, v1, Lcom/ibasso/volume/bean/FpgaVersionEvent;-><init>(Ljava/lang/String;)V
    invoke-virtual v2, v4, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    if-eq v6, v3, +01ah
    if-eq v11, v3, +018h
    if-eq v5, v3, +016h
    if-eq v4, v3, +014h
    const/4 v1, 3
    if-eq v1, v3, +011h
    if-eq v15, v3, +00fh
    if-eq v13, v3, +00dh
    const/4 v1, 2
    if-eq v1, v3, +00ah
    if-nez v3, +0d6h
    aget-byte v1, v2, v13
    const/16 v3, 18
    if-ne v1, v3, +0d0h
    aget-byte v1, v2, v15
    invoke-direct v0, v1, Lcom/ibasso/volume/UacManager;->parseVolume(I)V
    return-void 
    new-instance v2, Lcom/ibasso/volume/bean/NativeReportEvent;
    aget-byte v4, v1, v15
    aget-byte v5, v1, v18
    aget-byte v6, v1, v17
    aget-byte v7, v1, v16
    const/16 v3, 97
    invoke-direct/range v2 ... v7, Lcom/ibasso/volume/bean/NativeReportEvent;-><init>(I I I I I)V
    iget-object v1, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v1, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v1
    invoke-virtual v1, v2, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    new-instance v3, Lcom/ibasso/volume/bean/NativeReportEvent;
    aget-byte v5, v1, v15
    aget-byte v6, v1, v18
    aget-byte v7, v1, v17
    aget-byte v8, v1, v16
    const/16 v4, 96
    invoke-direct/range v3 ... v8, Lcom/ibasso/volume/bean/NativeReportEvent;-><init>(I I I I I)V
    iget-object v1, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v1, v3, Lcom/ibasso/volume/controller/UsbDeviceController;->initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v1
    invoke-virtual v1, v3, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    move/from16 v18, v14
    goto/16 -112h
    new-instance v2, Lcom/ibasso/volume/bean/NativeReportEvent;
    aget-byte v3, v1, v15
    aget-byte v1, v1, v18
    const/16 v4, 89
    invoke-direct v2, v4, v3, v1, Lcom/ibasso/volume/bean/NativeReportEvent;-><init>(I I I)V
    iget-object v1, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v1, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v1
    invoke-virtual v1, v2, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    iget v2, v1, Lcom/ibasso/volume/ResponseEvent;->seqId I
    const/16 v3, 71
    if-eq v3, v2, +097h
    const/16 v3, 72
    if-ne v3, v2, +004h
    goto/16 +091h
    if-eq v5, v2, +089h
    if-eq v4, v2, +087h
    if-eq v6, v2, +085h
    if-ne v11, v2, +004h
    goto/16 +081h
    const/16 v3, 73
    if-eq v3, v2, +05dh
    const/16 v3, 74
    if-ne v3, v2, +003h
    goto +57h
    const/16 v3, 83
    if-ne v3, v2, +016h
    iget-object v2, v0, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v2, Lcom/ibasso/volume/controller/UsbDeviceController;->getBalanceVolume()I
    move-result v2
    if-ltz v2, +008h
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-direct v0, v1, Lcom/ibasso/volume/UacManager;->parseVolume(I)V
    return-void 
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->rightVolume I
    invoke-direct v0, v1, Lcom/ibasso/volume/UacManager;->parseVolume(I)V
    return-void 
    if-ne v10, v2, +03dh
    new-instance v2, Ljava/lang/StringBuilder;
    invoke-direct v2, Ljava/lang/StringBuilder;-><init>()V
    invoke-virtual v2, v8, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget v3, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-static v3, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v3
    invoke-virtual v2, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v2, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v2
    invoke-static v9, v2, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v2
    new-instance v3, Lcom/ibasso/volume/bean/FpgaVersionEvent;
    new-instance v4, Ljava/lang/StringBuilder;
    invoke-direct v4, Ljava/lang/StringBuilder;-><init>()V
    invoke-virtual v4, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    and-int/lit16 v1, v1, 255
    invoke-static v1, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;
    move-result-object v1
    invoke-virtual v4, v1, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v4, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v1
    invoke-direct v3, v1, Lcom/ibasso/volume/bean/FpgaVersionEvent;-><init>(Ljava/lang/String;)V
    invoke-virtual v2, v3, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v2
    new-instance v3, Lcom/ibasso/volume/TestObject;
    new-instance v4, Ljava/lang/StringBuilder;
    invoke-direct v4, Ljava/lang/StringBuilder;-><init>()V
    const-string v5, ""
    invoke-virtual v4, v5, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-virtual v4, v1, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v4, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v1
    invoke-direct v3, v1, Lcom/ibasso/volume/TestObject;-><init>(Ljava/lang/String;)V
    invoke-virtual v2, v3, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-direct v0, v1, Lcom/ibasso/volume/UacManager;->parseVolume(I)V
    return-void 
    int-to-byte v2, v2
    iget v1, v1, Lcom/ibasso/volume/ResponseEvent;->value I
    invoke-direct v0, v2, v1, Lcom/ibasso/volume/UacManager;->parseNativeDigitalFilter(B I)V
    return-void 
### public setAmpMode(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setAmpMode(I)V
    return-void 
### public setBalanceVolume(I)V
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +01ah
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mHandler Landroid/os/Handler;
    iget v1, v3, Lcom/ibasso/volume/UacManager;->MSG_SET_BALANCE I
    invoke-virtual v0, v1, Landroid/os/Handler;->removeMessages(I)V
    invoke-static Landroid/os/Message;->obtain()Landroid/os/Message;
    move-result-object v0
    iget v1, v3, Lcom/ibasso/volume/UacManager;->MSG_SET_BALANCE I
    iput v1, v0, Landroid/os/Message;->what I
    iput v4, v0, Landroid/os/Message;->arg1 I
    iget-object v4, v3, Lcom/ibasso/volume/UacManager;->mHandler Landroid/os/Handler;
    const-wide/16 v1, 100
    invoke-virtual v4, v0, v1, v2, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message; J)Z
    return-void 
### public setCodecExternal(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setCodecExternal(I)V
    return-void 
### public setDRE(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setDRE(I)V
    return-void 
### public setDacGain(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setDacGain(I)V
    return-void 
### public setDigitalFilter(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setDigitalFilter(I)V
    return-void 
### public setDsdFilter(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setDsdFilter(I)V
    return-void 
### public setEqualizer(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setEqualizer(I)V
    return-void 
### public setGain(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setGain(I)V
    return-void 
### public setHpFilter(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setHpFilter(I)V
    return-void 
### public setKeyControlStatus(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setKeyControlStatus(I)V
    return-void 
### public setLanguage(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setLanguage(I)V
    return-void 
### public setMicMode(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setMicMode(I)V
    return-void 
### public setOpVoltage(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setOpVoltage(I)V
    return-void 
### public setOutput(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setOutput(I)V
    return-void 
### public setParametricEqualizer(Lcom/ibasso/volume/eq/ParametricEqualizer;)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setParametricEqualizer(Lcom/ibasso/volume/eq/ParametricEqualizer;)V
    return-void 
### public setPcmTrim(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setPcmTrim(I)V
    return-void 
### public setScreenRotation(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setScreenRotation(I)V
    return-void 
### public setScreenTime(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setScreenTime(I)V
    return-void 
### public setUacVersion(I)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    if-eqz v0, +005h
    invoke-virtual v0, v2, Lcom/ibasso/volume/controller/UsbDeviceController;->setUacVersion(I)V
    return-void 
### public setVolume(I)V
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    const/16 v1, 63
    if-ne v0, v1, +008h
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mUsbDeviceController Lcom/ibasso/volume/controller/UsbDeviceController;
    invoke-virtual v0, v4, Lcom/ibasso/volume/controller/UsbDeviceController;->setVolume(I)V
    return-void 
    iget-object v0, v3, Lcom/ibasso/volume/UacManager;->mHandler Landroid/os/Handler;
    iget v1, v3, Lcom/ibasso/volume/UacManager;->MSG_SET_VOLUME I
    invoke-virtual v0, v1, Landroid/os/Handler;->removeMessages(I)V
    invoke-static Landroid/os/Message;->obtain()Landroid/os/Message;
    move-result-object v0
    iget v1, v3, Lcom/ibasso/volume/UacManager;->MSG_SET_VOLUME I
    iput v1, v0, Landroid/os/Message;->what I
    iput v4, v0, Landroid/os/Message;->arg1 I
    iget-object v4, v3, Lcom/ibasso/volume/UacManager;->mHandler Landroid/os/Handler;
    const-wide/16 v1, 100
    invoke-virtual v4, v0, v1, v2, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message; J)Z
    return-void 
### public setVolume(Z)V
    iget-object v0, v1, Lcom/ibasso/volume/UacManager;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    if-nez v0, +003h
    return-void 
    invoke-virtual v1, Lcom/ibasso/volume/UacManager;->getVolume()I
    move-result v0
    if-eqz v2, +012h
    add-int/lit8 v0, v0, 1
    invoke-virtual v1, Lcom/ibasso/volume/UacManager;->getMaxVolume()I
    move-result v2
    if-le v0, v2, +006h
    invoke-virtual v1, Lcom/ibasso/volume/UacManager;->getMaxVolume()I
    move-result v0
    invoke-virtual v1, v0, Lcom/ibasso/volume/UacManager;->setVolume(I)V
    return-void 
    add-int/lit8 v0, v0, -1
    if-gez v0, +003h
    const/4 v0, 0
    invoke-virtual v1, v0, Lcom/ibasso/volume/UacManager;->setVolume(I)V
    return-void 
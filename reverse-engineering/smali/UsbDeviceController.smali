### static constructor <clinit>()V
    const/16 v0, 64
    new-array v0, v0, [I
    fill-array-data v0, +0000006h
    sput-object v0, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    return-void 
    fill-array-data-payload b'\x00\xc1\x00\x00\x00\xc2\x00\x00\x00\xc3\x00\x00\x00\xc4\x00\x00\x00\xc5\x00\x00\x00\xc6\x00\x00\x00\xc7\x00\x00\x00\xc8\x00\x00\x00\xc9\x00\x00\x00\xca\x00\x00\x00\xcb\x00\x00\x00\xcc\x00\x00\x00\xcd\x00\x00\x00\xce\x00\x00\x00\xcf\x00\x00\x00\xd0\x00\x00\x00\xd1\x00\x00\x00\xd2\x00\x00\x00\xd3\x00\x00\x00\xd4\x00\x00\x00\xd5\x00\x00\x00\xd6\x00\x00\x00\xd7\x00\x00\x00\xd8\x00\x00\x00\xd9\x00\x00\x00\xda\x00\x00\x00\xdb\x00\x00\x00\xdc\x00\x00\x00\xdd\x00\x00\x00\xde\x00\x00\x00\xdf\x00\x00\x00\xe0\x00\x00\x00\xe1\x00\x00\x00\xe2\x00\x00\x00\xe3\x00\x00\x00\xe4\x00\x00\x00\xe5\x00\x00\x00\xe6\x00\x00\x00\xe7\x00\x00\x00\xe8\x00\x00\x00\xe9\x00\x00\x00\xea\x00\x00\x00\xeb\x00\x00\x00\xec\x00\x00\x00\xed\x00\x00\x00\xee\x00\x00\x00\xef\x00\x00\x00\xf0\x00\x00\x00\xf1\x00\x00\x00\xf2\x00\x00\x00\xf3\x00\x00\x00\xf4\x00\x00\x00\xf5\x00\x00\x00\xf6\x00\x00\x00\xf7\x00\x00\x00\xf8\x00\x00\x00\xf9\x00\x00\x00\xfa\x00\x00\x00\xfb\x00\x00\x00\xfc\x00\x00\x00\xfd\x00\x00\x00\xfe\x00\x00\x00\xff\x00\x00\x00\x00\x00\x00' | \x00\xc1\x00\x00\x00\xc2\x00\x00\x00\xc3\x00\x00\x00\xc4\x00\x00\x00\xc5\x00\x00\x00\xc6\x00\x00\x00\xc7\x00\x00\x00\xc8\x00\x00\x00\xc9\x00\x00\x00\xca\x00\x00\x00\xcb\x00\x00\x00\xcc\x00\x00\x00\xcd\x00\x00\x00\xce\x00\x00\x00\xcf\x00\x00\x00\xd0\x00\x00\x00\xd1\x00\x00\x00\xd2\x00\x00\x00\xd3\x00\x00\x00\xd4\x00\x00\x00\xd5\x00\x00\x00\xd6\x00\x00\x00\xd7\x00\x00\x00\xd8\x00\x00\x00\xd9\x00\x00\x00\xda\x00\x00\x00\xdb\x00\x00\x00\xdc\x00\x00\x00\xdd\x00\x00\x00\xde\x00\x00\x00\xdf\x00\x00\x00\xe0\x00\x00\x00\xe1\x00\x00\x00\xe2\x00\x00\x00\xe3\x00\x00\x00\xe4\x00\x00\x00\xe5\x00\x00\x00\xe6\x00\x00\x00\xe7\x00\x00\x00\xe8\x00\x00\x00\xe9\x00\x00\x00\xea\x00\x00\x00\xeb\x00\x00\x00\xec\x00\x00\x00\xed\x00\x00\x00\xee\x00\x00\x00\xef\x00\x00\x00\xf0\x00\x00\x00\xf1\x00\x00\x00\xf2\x00\x00\x00\xf3\x00\x00\x00\xf4\x00\x00\x00\xf5\x00\x00\x00\xf6\x00\x00\x00\xf7\x00\x00\x00\xf8\x00\x00\x00\xf9\x00\x00\x00\xfa\x00\x00\x00\xfb\x00\x00\x00\xfc\x00\x00\x00\xfd\x00\x00\x00\xfe\x00\x00\x00\xff\x00\x00\x00\x00\x00\x00
### public constructor <init>(Landroid/content/Context; Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection;)V
    invoke-direct v0, Ljava/lang/Object;-><init>()V
    const/16 v3, 101
    new-array v3, v3, [I
    fill-array-data v3, +000001fh
    iput-object v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep2 [I
    const/4 v3, 0
    iput v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mVolume I
    iput v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mBalanceVolume I
    const/4 v3, 1
    iput v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mBalanceStep I
    const/4 v3, 0
    iput-object v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mTaskManager Lcom/ibasso/volume/TaskManager;
    const-string v3, "usb"
    invoke-virtual v1, v3, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;
    move-result-object v3
    check-cast v3, Landroid/hardware/usb/UsbManager;
    iput-object v3, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbManager Landroid/hardware/usb/UsbManager;
    iput-object v1, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    iput-object v2, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    return-void 
    fill-array-data-payload b'\xff\x00\x00\x00\x9b\x00\x00\x00\x96\x00\x00\x00\x91\x00\x00\x00\x8c\x00\x00\x00\x87\x00\x00\x00\x82\x00\x00\x00}\x00\x00\x00x\x00\x00\x00s\x00\x00\x00n\x00\x00\x00m\x00\x00\x00l\x00\x00\x00k\x00\x00\x00j\x00\x00\x00i\x00\x00\x00h\x00\x00\x00g\x00\x00\x00f\x00\x00\x00e\x00\x00\x00d\x00\x00\x00c\x00\x00\x00b\x00\x00\x00a\x00\x00\x00`\x00\x00\x00_\x00\x00\x00^\x00\x00\x00]\x00\x00\x00\\\x00\x00\x00[\x00\x00\x00Z\x00\x00\x00X\x00\x00\x00V\x00\x00\x00T\x00\x00\x00R\x00\x00\x00P\x00\x00\x00N\x00\x00\x00L\x00\x00\x00J\x00\x00\x00H\x00\x00\x00F\x00\x00\x00D\x00\x00\x00B\x00\x00\x00@\x00\x00\x00>\x00\x00\x00<\x00\x00\x00:\x00\x00\x008\x00\x00\x006\x00\x00\x004\x00\x00\x002\x00\x00\x001\x00\x00\x000\x00\x00\x00/\x00\x00\x00.\x00\x00\x00-\x00\x00\x00,\x00\x00\x00+\x00\x00\x00*\x00\x00\x00)\x00\x00\x00(\x00\x00\x00\'\x00\x00\x00&\x00\x00\x00%\x00\x00\x00$\x00\x00\x00#\x00\x00\x00"\x00\x00\x00!\x00\x00\x00 \x00\x00\x00\x1f\x00\x00\x00\x1e\x00\x00\x00\x1d\x00\x00\x00\x1c\x00\x00\x00\x1b\x00\x00\x00\x1a\x00\x00\x00\x19\x00\x00\x00\x18\x00\x00\x00\x17\x00\x00\x00\x16\x00\x00\x00\x15\x00\x00\x00\x14\x00\x00\x00\x13\x00\x00\x00\x12\x00\x00\x00\x11\x00\x00\x00\x10\x00\x00\x00\x0f\x00\x00\x00\x0e\x00\x00\x00\r\x00\x00\x00\x0c\x00\x00\x00\x0b\x00\x00\x00\n\x00\x00\x00\t\x00\x00\x00\x08\x00\x00\x00\x07\x00\x00\x00\x06\x00\x00\x00\x05\x00\x00\x00\x04\x00\x00\x00\x03\x00\x00\x00\x02\x00\x00\x00\x01\x00\x00\x00\x00\x00\x00\x00' | \xff\x00\x00\x00\x9b\x00\x00\x00\x96\x00\x00\x00\x91\x00\x00\x00\x8c\x00\x00\x00\x87\x00\x00\x00\x82\x00\x00\x00\x7d\x00\x00\x00\x78\x00\x00\x00\x73\x00\x00\x00\x6e\x00\x00\x00\x6d\x00\x00\x00\x6c\x00\x00\x00\x6b\x00\x00\x00\x6a\x00\x00\x00\x69\x00\x00\x00\x68\x00\x00\x00\x67\x00\x00\x00\x66\x00\x00\x00\x65\x00\x00\x00\x64\x00\x00\x00\x63\x00\x00\x00\x62\x00\x00\x00\x61\x00\x00\x00\x60\x00\x00\x00\x5f\x00\x00\x00\x5e\x00\x00\x00\x5d\x00\x00\x00\x5c\x00\x00\x00\x5b\x00\x00\x00\x5a\x00\x00\x00\x58\x00\x00\x00\x56\x00\x00\x00\x54\x00\x00\x00\x52\x00\x00\x00\x50\x00\x00\x00\x4e\x00\x00\x00\x4c\x00\x00\x00\x4a\x00\x00\x00\x48\x00\x00\x00\x46\x00\x00\x00\x44\x00\x00\x00\x42\x00\x00\x00\x40\x00\x00\x00\x3e\x00\x00\x00\x3c\x00\x00\x00\x3a\x00\x00\x00\x38\x00\x00\x00\x36\x00\x00\x00\x34\x00\x00\x00\x32\x00\x00\x00\x31\x00\x00\x00\x30\x00\x00\x00\x2f\x00\x00\x00\x2e\x00\x00\x00\x2d\x00\x00\x00\x2c\x00\x00\x00\x2b\x00\x00\x00\x2a\x00\x00\x00\x29\x00\x00\x00\x28\x00\x00\x00\x27\x00\x00\x00\x26\x00\x00\x00\x25\x00\x00\x00\x24\x00\x00\x00\x23\x00\x00\x00\x22\x00\x00\x00\x21\x00\x00\x00\x20\x00\x00\x00\x1f\x00\x00\x00\x1e\x00\x00\x00\x1d\x00\x00\x00\x1c\x00\x00\x00\x1b\x00\x00\x00\x1a\x00\x00\x00\x19\x00\x00\x00\x18\x00\x00\x00\x17\x00\x00\x00\x16\x00\x00\x00\x15\x00\x00\x00\x14\x00\x00\x00\x13\x00\x00\x00\x12\x00\x00\x00\x11\x00\x00\x00\x10\x00\x00\x00\x0f\x00\x00\x00\x0e\x00\x00\x00\x0d\x00\x00\x00\x0c\x00\x00\x00\x0b\x00\x00\x00\x0a\x00\x00\x00\x09\x00\x00\x00\x08\x00\x00\x00\x07\x00\x00\x00\x06\x00\x00\x00\x05\x00\x00\x00\x04\x00\x00\x00\x03\x00\x00\x00\x02\x00\x00\x00\x01\x00\x00\x00\x00\x00\x00\x00
### private getFirmwareVersion(Landroid/hardware/usb/UsbDeviceConnection;)I
    if-nez v2, +004h
    const/4 v2, 0
    return v2
    invoke-virtual v2, Landroid/hardware/usb/UsbDeviceConnection;->getRawDescriptors()[B
    move-result-object v2
    const/16 v0, 12
    aget-byte v2, v2, v0
    and-int/lit16 v2, v2, 255
    return v2
### public dumpFlash()V
    return-void 
### public eraseFlash()V
    return-void 
### public getAmpMode()I
    const/4 v0, 0
    return v0
### public getBalanceVolume()I
    const/4 v0, 0
    return v0
### public getCodecExternal()I
    const/4 v0, 0
    return v0
### public getDRE()I
    const/4 v0, 0
    return v0
### public getDacGain()I
    const/4 v0, 0
    return v0
### public getDigitalFilter()I
    const/4 v0, 0
    return v0
### public getDsdFilter()I
    const/4 v0, 0
    return v0
### public getEqualizer()I
    const/4 v0, 0
    return v0
### public getFpgaVersion()V
    return-void 
### public getGain()I
    const/4 v0, 0
    return v0
### public getHpFilter()I
    const/4 v0, 0
    return v0
### public getKeyControlStatus()I
    const/4 v0, 0
    return v0
### public getLanguage()I
    const/4 v0, 0
    return v0
### public getMaxVolume()I
    sget-object v0, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    array-length v0, v0
    add-int/lit8 v0, v0, -1
    return v0
### public getMicMode()I
    const/4 v0, 0
    return v0
### public getOpVoltage()I
    const/4 v0, 0
    return v0
### public getPcmTrim()I
    const/4 v0, 0
    return v0
### public getUacVersion()I
    const/4 v0, 0
    return v0
### public getVolume()I
    move-object/from16 v0, v21
    iget-object v1, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    const/4 v2, 0
    const/4 v3, -1
    if-nez v1, +00fh
    iget-object v1, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const v4, 2131820755
    invoke-static v1, v4, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; I I)Landroid/widget/Toast;
    move-result-object v1
    invoke-virtual v1, Landroid/widget/Toast;->show()V
    return v3
    iget-object v4, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbManager Landroid/hardware/usb/UsbManager;
    invoke-virtual v4, v1, Landroid/hardware/usb/UsbManager;->openDevice(Landroid/hardware/usb/UsbDevice;)Landroid/hardware/usb/UsbDeviceConnection;
    move-result-object v5
    invoke-direct v0, v5, Lcom/ibasso/volume/controller/UsbDeviceController;->getFirmwareVersion(Landroid/hardware/usb/UsbDeviceConnection;)I
    move-result v1
    const/4 v4, 3
    if-ne v1, v4, +009h
    sget-object v1, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    const v4, 36864
    aput v4, v1, v2
    if-eqz v5, +0ech
    const/4 v1, 2
    new-array v10, v1, [B
    new-array v1, v1, [B
    invoke-static v10, v3, Ljava/util/Arrays;->fill([B B)V
    invoke-static v1, v3, Ljava/util/Arrays;->fill([B B)V
    const/16 v6, 160
    const/4 v7, 1
    const/16 v8, 513
    const/16 v9, 2561
    const/4 v11, 2
    const/16 v12, 200
    invoke-virtual/range v5 ... v12, Landroid/hardware/usb/UsbDeviceConnection;->controlTransfer(I I I I [B I I)I
    move-result v4
    move-object v13, v10
    const-string v14, "UacManager"
    if-eq v4, v11, +017h
    invoke-static v4, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v1
    filled-new-array v1, [Ljava/lang/Object;
    move-result-object v1
    const-string v2, "Get L Volume command error, error code = %d"
    invoke-static v2, v1, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v1
    invoke-static v14, v1, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v5, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    return v3
    const/4 v4, 1
    aget-byte v8, v13, v4
    and-int/lit16 v8, v8, 255
    shl-int/lit8 v8, v8, 8
    aget-byte v10, v13, v2
    and-int/lit16 v10, v10, 255
    or-int/2addr v8, v10
    move v10, v2
    sget-object v15, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    move/from16 v16, v3
    array-length v3, v15
    if-ge v10, v3, +00dh
    aget v3, v15, v10
    if-ne v8, v3, +004h
    move v3, v10
    goto +8h
    add-int/lit8 v10, v10, 1
    move/from16 v3, v16
    goto -11h
    move/from16 v3, v16
    const/16 v8, 514
    move-object v10, v1
    invoke-virtual/range v5 ... v12, Landroid/hardware/usb/UsbDeviceConnection;->controlTransfer(I I I I [B I I)I
    move-result v1
    if-eq v1, v11, +017h
    invoke-static v1, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v1
    filled-new-array v1, [Ljava/lang/Object;
    move-result-object v1
    const-string v2, "Get R Volume command error, error code = %d"
    invoke-static v2, v1, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v1
    invoke-static v14, v1, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v5, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    return v3
    aget-byte v1, v10, v4
    and-int/lit16 v1, v1, 255
    shl-int/lit8 v1, v1, 8
    aget-byte v6, v10, v2
    and-int/lit16 v6, v6, 255
    or-int/2addr v1, v6
    move v6, v2
    sget-object v7, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    array-length v8, v7
    if-ge v6, v8, +00bh
    aget v7, v7, v6
    if-ne v1, v7, +004h
    move v3, v6
    goto +4h
    add-int/lit8 v6, v6, 1
    goto -dh
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v1
    sub-int v1, v3, v1
    invoke-static v1, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v1
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v6
    sub-int v6, v3, v6
    invoke-static v6, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v6
    filled-new-array v1, v6, [Ljava/lang/Object;
    move-result-object v1
    const-string v6, "(L, R) = (%ddB, %ddB)"
    invoke-static v6, v1, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v1
    invoke-static v14, v1, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v1
    sub-int v1, v3, v1
    invoke-static v1, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v15
    invoke-virtual v0, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v1
    sub-int v1, v3, v1
    invoke-static v1, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v16
    aget-byte v1, v13, v4
    invoke-static v1, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v17
    aget-byte v1, v13, v2
    invoke-static v1, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v18
    aget-byte v1, v10, v4
    invoke-static v1, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v19
    aget-byte v1, v10, v2
    invoke-static v1, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v20
    filled-new-array/range v15 ... v20, [Ljava/lang/Object;
    move-result-object v1
    const-string v2, "Get Current Volume (L,R):(%ddB, %ddB), Data(L, R):(0x%02X%02X, 0x%02X%02X)	"
    invoke-static v2, v1, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v1
    invoke-static v14, v1, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v5, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    return v3
    move/from16 v16, v3
    iget-object v1, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const-string v3, "Open Device Fail"
    invoke-static v1, v3, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; Ljava/lang/CharSequence; I)Landroid/widget/Toast;
    move-result-object v1
    invoke-virtual v1, Landroid/widget/Toast;->show()V
    return v16
### public initDevice()V
    invoke-static Lcom/ibasso/volume/SpStorage;->getInstance()Lcom/ibasso/volume/SpStorage;
    move-result-object v0
    invoke-virtual v0, Lcom/ibasso/volume/SpStorage;->getResetLastValue()Z
    move-result v0
    if-eqz v0, +011h
    invoke-static Lcom/ibasso/volume/SpStorage;->getInstance()Lcom/ibasso/volume/SpStorage;
    move-result-object v0
    invoke-virtual v0, Lcom/ibasso/volume/SpStorage;->getVolume()I
    move-result v0
    const/4 v1, -1
    if-eq v0, v1, +005h
    invoke-virtual v2, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->setVolume(I)V
    return-void 
    invoke-virtual v2, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    invoke-virtual v2, v0, Lcom/ibasso/volume/controller/UsbDeviceController;->setVolume(I)V
    return-void 
### public initDeviceStatus(Lcom/ibasso/volume/bean/NativeReportEvent;)V
    return-void 
### public parseCallBackValue(Lcom/ibasso/volume/ResponseEvent;)Z
    const/4 v1, 0
    return v1
### public parseNativeAndSaveVolume(I)I
    return v1
### public parseNativeDigitalFilter(B I)V
    return-void 
### public setAmpMode(I)V
    return-void 
### public setBalanceVolume(I)V
    return-void 
### public setCodecExternal(I)V
    return-void 
### public setDRE(I)V
    return-void 
### public setDacGain(I)V
    return-void 
### public setDigitalFilter(I)V
    return-void 
### public setDsdFilter(I)V
    return-void 
### public setEqualizer(I)V
    return-void 
### public setGain(I)V
    return-void 
### public setHpFilter(I)V
    return-void 
### public setKeyControlStatus(I)V
    return-void 
### public setLanguage(I)V
    return-void 
### public setMicMode(I)V
    return-void 
### public setOpVoltage(I)V
    return-void 
### public setOutput(I)V
    return-void 
### public setParametricEqualizer(Lcom/ibasso/volume/eq/ParametricEqualizer;)V
    return-void 
### public setPcmTrim(I)V
    return-void 
### public setScreenRotation(I)V
    return-void 
### public setScreenTime(I)V
    return-void 
### public setUacVersion(I)V
    return-void 
### public setVolume(I)V
    iget-object v0, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    const-string v1, "UacManager"
    if-nez v0, +008h
    const-string v13, "[Set RVolume]Open Device Fail"
    invoke-static v1, v13, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    return-void 
    invoke-virtual v12, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    const/4 v2, 0
    if-gt v13, v0, +0edh
    if-gez v13, +004h
    goto/16 +0e9h
    iget-object v0, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbManager Landroid/hardware/usb/UsbManager;
    iget-object v3, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v0, v3, Landroid/hardware/usb/UsbManager;->openDevice(Landroid/hardware/usb/UsbDevice;)Landroid/hardware/usb/UsbDeviceConnection;
    move-result-object v4
    invoke-direct v12, v4, Lcom/ibasso/volume/controller/UsbDeviceController;->getFirmwareVersion(Landroid/hardware/usb/UsbDeviceConnection;)I
    move-result v0
    const/4 v3, 3
    if-eq v0, v3, +005h
    const/4 v3, 6
    if-lt v0, v3, +009h
    sget-object v3, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    const v5, 36864
    aput v5, v3, v2
    new-instance v3, Ljava/lang/StringBuilder;
    invoke-direct v3, Ljava/lang/StringBuilder;-><init>()V
    const-string v5, "getFirmwareVersion:"
    invoke-virtual v3, v5, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, v0, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    const-string v0, " volume:"
    invoke-virtual v3, v0, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, v13, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v3, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v0
    invoke-static v1, v0, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    if-eqz v4, +099h
    sget-object v0, Lcom/ibasso/volume/controller/UsbDeviceController;->nVolumeStep [I
    aget v0, v0, v13
    and-int/lit16 v3, v0, 255
    int-to-byte v3, v3
    shr-int/lit8 v0, v0, 8
    and-int/lit16 v0, v0, 255
    int-to-byte v0, v0
    const/4 v5, 2
    new-array v9, v5, [B
    aput-byte v3, v9, v2
    const/4 v3, 1
    aput-byte v0, v9, v3
    const/16 v7, 514
    const/16 v5, 32
    const/4 v6, 1
    const/16 v8, 2561
    const/4 v10, 2
    const/16 v11, 200
    invoke-virtual/range v4 ... v11, Landroid/hardware/usb/UsbDeviceConnection;->controlTransfer(I I I I [B I I)I
    move-result v0
    if-eq v0, v10, +016h
    iget-object v13, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const-string v0, "Set Right Channel Fail"
    invoke-static v13, v0, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; Ljava/lang/CharSequence; I)Landroid/widget/Toast;
    move-result-object v13
    invoke-virtual v13, Landroid/widget/Toast;->show()V
    const-string v13, "[Set RVolume] Set Right Channel Fail"
    invoke-static v1, v13, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v4, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    return-void 
    const/16 v7, 513
    invoke-virtual/range v4 ... v11, Landroid/hardware/usb/UsbDeviceConnection;->controlTransfer(I I I I [B I I)I
    move-result v0
    if-eq v0, v10, +014h
    iget-object v13, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const-string v0, "Set Left Channel Fail"
    invoke-static v13, v0, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; Ljava/lang/CharSequence; I)Landroid/widget/Toast;
    move-result-object v13
    invoke-virtual v13, Landroid/widget/Toast;->show()V
    invoke-static v1, v0, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v4, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    return-void 
    invoke-static Lcom/ibasso/volume/SpStorage;->getInstance()Lcom/ibasso/volume/SpStorage;
    move-result-object v0
    invoke-virtual v0, v13, Lcom/ibasso/volume/SpStorage;->setVolume(I)V
    invoke-virtual v12, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    sub-int v0, v13, v0
    invoke-static v0, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v0
    filled-new-array v0, [Ljava/lang/Object;
    move-result-object v0
    const-string v5, "Right Channel Volume(dB) = %d"
    invoke-static v5, v0, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v0
    invoke-static v1, v0, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v12, Lcom/ibasso/volume/controller/UsbDeviceController;->getMaxVolume()I
    move-result v0
    sub-int v0, v13, v0
    invoke-static v0, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;
    move-result-object v0
    aget-byte v3, v9, v3
    invoke-static v3, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v3
    aget-byte v2, v9, v2
    invoke-static v2, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v2
    filled-new-array v0, v3, v2, [Ljava/lang/Object;
    move-result-object v0
    const-string v2, "set Right Channel Volume OK,Volume(dB) = %d(0x%02X%02X)	"
    invoke-static v2, v0, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v0
    invoke-static v1, v0, Landroid/util/Log;->i(Ljava/lang/String; Ljava/lang/String;)I
    invoke-virtual v4, Landroid/hardware/usb/UsbDeviceConnection;->close()V
    goto +dh
    iget-object v0, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const v1, 2131820842
    invoke-static v0, v1, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; I I)Landroid/widget/Toast;
    move-result-object v0
    invoke-virtual v0, Landroid/widget/Toast;->show()V
    invoke-static Lorg/greenrobot/eventbus/EventBus;->getDefault()Lorg/greenrobot/eventbus/EventBus;
    move-result-object v0
    new-instance v1, Lcom/ibasso/volume/NormalVolumeEvent;
    invoke-direct v1, v13, Lcom/ibasso/volume/NormalVolumeEvent;-><init>(I)V
    invoke-virtual v0, v1, Lorg/greenrobot/eventbus/EventBus;->postSticky(Ljava/lang/Object;)V
    return-void 
    iget-object v13, v12, Lcom/ibasso/volume/controller/UsbDeviceController;->mContext Landroid/content/Context;
    const v0, 2131820801
    invoke-static v13, v0, v2, Landroid/widget/Toast;->makeText(Landroid/content/Context; I I)Landroid/widget/Toast;
    move-result-object v13
    invoke-virtual v13, Landroid/widget/Toast;->show()V
    return-void 
### public stopTaskManager()V
    const/4 v0, 0
    iput-object v0, v1, Lcom/ibasso/volume/controller/UsbDeviceController;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iget-object v0, v1, Lcom/ibasso/volume/controller/UsbDeviceController;->mTaskManager Lcom/ibasso/volume/TaskManager;
    if-eqz v0, +005h
    invoke-virtual v0, Lcom/ibasso/volume/TaskManager;->stopTaskManager()V
    return-void 
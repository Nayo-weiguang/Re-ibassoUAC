### public constructor <init>(Landroid/hardware/usb/UsbDevice; Landroid/hardware/usb/UsbDeviceConnection; Lcom/ibasso/volume/MonitorThread$OnRequestCallBackListener;)V
    invoke-direct v1, Ljava/lang/Thread;-><init>()V
    const-string v0, "UacManager"
    iput-object v0, v1, Lcom/ibasso/volume/MonitorThread;->TAG Ljava/lang/String;
    iput-object v2, v1, Lcom/ibasso/volume/MonitorThread;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    iput-object v3, v1, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    iput-object v4, v1, Lcom/ibasso/volume/MonitorThread;->onRequestCallBackListener Lcom/ibasso/volume/MonitorThread$OnRequestCallBackListener;
    return-void 
### public run()V
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    const/4 v1, 0
    const-string v2, "UacManager"
    if-eqz v0, +066h
    new-instance v0, Ljava/lang/StringBuilder;
    invoke-direct v0, Ljava/lang/StringBuilder;-><init>()V
    const-string v3, "interfaceCounts : "
    invoke-virtual v0, v3, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getInterfaceCount()I
    move-result v3
    invoke-virtual v0, v3, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v0, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v0
    invoke-static v2, v0, Landroid/util/Log;->d(Ljava/lang/String; Ljava/lang/String;)I
    move v0, v1
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v3, Landroid/hardware/usb/UsbDevice;->getInterfaceCount()I
    move-result v3
    if-ge v0, v3, +043h
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mUsbDevice Landroid/hardware/usb/UsbDevice;
    invoke-virtual v3, v0, Landroid/hardware/usb/UsbDevice;->getInterface(I)Landroid/hardware/usb/UsbInterface;
    move-result-object v3
    invoke-virtual v3, Landroid/hardware/usb/UsbInterface;->getInterfaceClass()I
    move-result v4
    const/4 v5, 3
    if-ne v4, v5, +033h
    invoke-virtual v3, Landroid/hardware/usb/UsbInterface;->getInterfaceSubclass()I
    move-result v4
    if-nez v4, +02dh
    invoke-virtual v3, Landroid/hardware/usb/UsbInterface;->getInterfaceProtocol()I
    move-result v4
    if-nez v4, +027h
    iput-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    new-instance v3, Ljava/lang/StringBuilder;
    invoke-direct v3, Ljava/lang/StringBuilder;-><init>()V
    const-string v4, "interfaceCount is found "
    invoke-virtual v3, v4, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, v0, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    const-string v0, " ---"
    invoke-virtual v3, v0, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    invoke-virtual v0, Landroid/hardware/usb/UsbInterface;->getId()I
    move-result v0
    invoke-virtual v3, v0, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v3, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v0
    invoke-static v2, v0, Landroid/util/Log;->d(Ljava/lang/String; Ljava/lang/String;)I
    goto +4h
    add-int/lit8 v0, v0, 1
    goto -48h
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    if-eqz v0, +0e8h
    move v0, v1
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    invoke-virtual v3, Landroid/hardware/usb/UsbInterface;->getEndpointCount()I
    move-result v3
    const/4 v4, 0
    if-ge v0, v3, +016h
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    invoke-virtual v3, v0, Landroid/hardware/usb/UsbInterface;->getEndpoint(I)Landroid/hardware/usb/UsbEndpoint;
    move-result-object v3
    if-eqz v3, +00bh
    invoke-virtual v3, Landroid/hardware/usb/UsbEndpoint;->getDirection()I
    move-result v5
    const/16 v6, 128
    if-ne v5, v6, +003h
    goto +5h
    add-int/lit8 v0, v0, 1
    goto -1ch
    move-object v3, v4
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    iget-object v5, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    const/4 v6, 1
    invoke-virtual v0, v5, v6, Landroid/hardware/usb/UsbDeviceConnection;->claimInterface(Landroid/hardware/usb/UsbInterface; Z)Z
    move-result v0
    const-string v5, "usbRequest close & releaseInterface"
    if-eqz v0, +027h
    if-eqz v3, +025h
    new-instance v0, Landroid/hardware/usb/UsbRequest;
    invoke-direct v0, Landroid/hardware/usb/UsbRequest;-><init>()V
    iget-object v4, v12, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-virtual v0, v4, v3, Landroid/hardware/usb/UsbRequest;->initialize(Landroid/hardware/usb/UsbDeviceConnection; Landroid/hardware/usb/UsbEndpoint;)Z
    invoke-virtual v3, Landroid/hardware/usb/UsbEndpoint;->getMaxPacketSize()I
    move-result v3
    invoke-static v3, Ljava/nio/ByteBuffer;->allocate(I)Ljava/nio/ByteBuffer;
    move-result-object v4
    move-object v11, v4
    move-object v4, v0
    move-object v0, v11
    goto +fh
    move-exception v1
    move-object v4, v0
    goto/16 +08dh
    move-exception v1
    move-object v4, v0
    goto/16 +074h
    move-exception v1
    goto/16 +086h
    move-exception v1
    goto +6eh
    move-object v0, v4
    if-eqz v4, +091h
    iget-boolean v3, v12, Lcom/ibasso/volume/MonitorThread;->startMonitor Z
    if-eqz v3, +08dh
    invoke-virtual v0, Ljava/nio/ByteBuffer;->clear()Ljava/nio/Buffer;
    invoke-virtual v0, Ljava/nio/Buffer;->capacity()I
    move-result v3
    invoke-virtual v4, v0, v3, Landroid/hardware/usb/UsbRequest;->queue(Ljava/nio/ByteBuffer; I)Z
    move-result v3
    if-eqz v3, -011h
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    invoke-virtual v3, Landroid/hardware/usb/UsbDeviceConnection;->requestWait()Landroid/hardware/usb/UsbRequest;
    move-result-object v3
    if-ne v3, v4, -019h
    invoke-virtual v0, Ljava/nio/Buffer;->position()I
    move-result v3
    if-lez v3, -01fh
    invoke-virtual v0, Ljava/nio/Buffer;->position()I
    move-result v3
    new-array v6, v3, [B
    invoke-virtual v0, Ljava/nio/ByteBuffer;->flip()Ljava/nio/Buffer;
    invoke-virtual v0, v6, Ljava/nio/ByteBuffer;->get([B)Ljava/nio/ByteBuffer;
    new-instance v7, Ljava/lang/StringBuilder;
    invoke-direct v7, Ljava/lang/StringBuilder;-><init>()V
    move v8, v1
    if-ge v8, v3, +018h
    const-string v9, "%02x,"
    aget-byte v10, v6, v8
    invoke-static v10, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;
    move-result-object v10
    filled-new-array v10, [Ljava/lang/Object;
    move-result-object v10
    invoke-static v9, v10, Ljava/lang/String;->format(Ljava/lang/String; [Ljava/lang/Object;)Ljava/lang/String;
    move-result-object v9
    invoke-virtual v7, v9, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    add-int/lit8 v8, v8, 1
    goto -17h
    new-instance v3, Ljava/lang/StringBuilder;
    invoke-direct v3, Ljava/lang/StringBuilder;-><init>()V
    const-string v8, "MonitorThread: native###data[] = {"
    invoke-virtual v3, v8, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    const-string v7, "}"
    invoke-virtual v3, v7, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v3, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v3
    invoke-static v2, v3, Landroid/util/Log;->d(Ljava/lang/String; Ljava/lang/String;)I
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->onRequestCallBackListener Lcom/ibasso/volume/MonitorThread$OnRequestCallBackListener;
    if-eqz v3, -066h
    invoke-interface v3, v6, Lcom/ibasso/volume/MonitorThread$OnRequestCallBackListener;->onRequestDone([B)V
    goto -6bh
    const-string v0, "Error in USB communication"
    invoke-static v2, v0, v1, Landroid/util/Log;->e(Ljava/lang/String; Ljava/lang/String; Ljava/lang/Throwable;)I
    if-eqz v4, +005h
    invoke-virtual v4, Landroid/hardware/usb/UsbRequest;->close()V
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    iget-object v1, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    invoke-virtual v0, v1, Landroid/hardware/usb/UsbDeviceConnection;->releaseInterface(Landroid/hardware/usb/UsbInterface;)Z
    invoke-static v2, v5, Landroid/util/Log;->e(Ljava/lang/String; Ljava/lang/String;)I
    return-void 
    if-eqz v4, +005h
    invoke-virtual v4, Landroid/hardware/usb/UsbRequest;->close()V
    iget-object v0, v12, Lcom/ibasso/volume/MonitorThread;->mUsbConn Landroid/hardware/usb/UsbDeviceConnection;
    iget-object v3, v12, Lcom/ibasso/volume/MonitorThread;->mInterface Landroid/hardware/usb/UsbInterface;
    invoke-virtual v0, v3, Landroid/hardware/usb/UsbDeviceConnection;->releaseInterface(Landroid/hardware/usb/UsbInterface;)Z
    invoke-static v2, v5, Landroid/util/Log;->e(Ljava/lang/String; Ljava/lang/String;)I
    throw v1
    if-eqz v4, -01bh
    goto -20h
    return-void 
### public synchronized start()V
    monitor-enter v1
    const/4 v0, 1
    iput-boolean v0, v1, Lcom/ibasso/volume/MonitorThread;->startMonitor Z
    invoke-super v1, Ljava/lang/Thread;->start()V
    monitor-exit v1
    return-void 
    move-exception v0
    monitor-exit v1
    throw v0
### public stopMonitor()V
    const/4 v0, 0
    iput-boolean v0, v1, Lcom/ibasso/volume/MonitorThread;->startMonitor Z
    return-void 
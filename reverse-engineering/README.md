# iBasso UAC 官方 App 核心逆向源码与分析资产

本目录收录了从 iBasso 官方 Android 配套控制应用 **iBasso UAC 2.1.9**（`com.ibasso.volume`）中逆向提取、反编译及还原的核心通信协议、硬件控制器模型与音频算法代码。

---

## 目录结构

```
reverse-engineering/
├── sources/com/ibasso/             # 反编译 Java 源码（全量 131 个业务类）
│   ├── controller/                 # 13 款设备硬件控制器实现类
│   │   ├── UsbDeviceController.java   # 设备控制器核心抽象基类（通信与状态调度）
│   │   ├── Dc03ProDeviceController.java
│   │   ├── Dc04ProDeviceController.java
│   │   ├── Dc06DeviceController.java
│   │   ├── Dc06ProDeviceController.java
│   │   ├── Dc07DeviceController.java
│   │   ├── Dc07ProDeviceController.java
│   │   ├── Dc08DeviceController.java
│   │   ├── Dc09DeviceController.java
│   │   ├── D18DeviceController.java
│   │   ├── CX31993DeviceController.java
│   │   ├── MacchiatoDeviceController.java
│   │   └── TonfaDeviceController.java
│   ├── eq/                         # 硬件 EQ 参数计算与二阶 IIR 双二阶滤波器
│   │   ├── EQProcessor.java        # 滤波器系数生成与频响计算核心算法
│   │   ├── FilterCoefficients.java # 双二阶滤波器 b0/b1/b2/a1/a2 换算
│   │   ├── EqualizerManager.java   # EQ 配置状态管理
│   │   └── FilterType.java         # 滤波类型枚举（Peak, LowShelf, HighShelf 等）
│   ├── bean/                       # 设备配置实体与事件定义
│   │   ├── Device.java             # 设备元数据定义
│   │   ├── NativeReportEvent.java  # 底层硬件事件回包
│   │   └── FpgaVersionEvent.java   # FPGA / 固件版本事件
│   ├── UacManager.java             # USB 传输调度与生命周期管理
│   ├── MonitorThread.java          # 异步通信监控与读写轮询线程
│   ├── IConstants.java             # 协议常量、控制字、HID Report ID 与寄存器地址字典
│   └── ...                         # 对应 UI 绑定与业务逻辑
│
├── smali/                          # 核心调度类的 Smali 原生汇编指令
│   ├── UsbDeviceController.smali
│   ├── UacManager.smali
│   ├── MonitorThread.smali
│   ├── IConstants.smali
│   └── Device.smali
│
├── AndroidManifest.xml             # 官方应用清单（USB 权限过滤、Activity 声明）
└── all_strings.txt                 # 官方应用提取的全量多语言本地化字符串与配置文本
```

---

## 核心逆向成果与技术对照

1. **双通道通信架构**：
   - **UAC 标准控制通道**（基类 `UsbDeviceController`）：通过端点 0 发起 `bmRequestType = 0x20`（SET_CUR）与 `0xA0`（GET_CUR）的标准音频类请求。
   - **MCU 专用扩展通道**（具体子类控制器）：通过 HID SET_REPORT（Report ID 0x00，16 字节 / 64 字节载荷）写入，结合中断 IN 端点（0x81）进行状态回传。

2. **完整协议规格解析**：
   详见上级目录 [`docs/iBasso协议逆向规格.md`](../docs/iBasso协议逆向规格.md)。

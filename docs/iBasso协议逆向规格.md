# iBasso USB 音频设备协议逆向规格与真机验证规范

> **版本**：v1.0 (最终定稿)  
> **逆向对象**：iBasso 官方 Android App `iBassoUAC_2.1.9.apk`（包名 `com.ibasso.volume`，版本号 120）  
> **实机验证设备**：iBasso DC03 Pro（USB VID: `0x262A`, PID: `0x187E`, 接口: `MI_00` 控制 / `MI_01` 音频）  
> **测试环境**：Windows 11 x64 (Build 26200), .NET 8.0 Windows Desktop Runtime, WinUSB (libwdi / oem175.inf), 原生 hidusb  

---

## 目录

1. [协议架构与通信分层](#一协议架构与通信分层)
2. [控制通道与寄存器通道详解](#二控制通道与寄存器通道详解)
3. [报文封装与编解码规范](#三报文封装与编解码规范)
4. [全机型协议矩阵与芯片方案](#四全机型协议矩阵与芯片方案)
5. [Windows vs Android 底层通信语义差异](#五windows-vs-android-底层通信语义差异)
6. [实机验证结论（证据等级分类）](#六实机验证结论证据等级分类)
7. [硬件防死锁与驱动还原规范](#七硬件防死锁与驱动还原规范)

---

## 一、协议架构与通信分层

在 iBasso 官方驱动与 Android App 的设计中，音频控制系统在逻辑上分为**两大完全独立的通道**：

```
                      +---------------------------------------+
                      |       iBasso 官方 Android / 桌面端     |
                      +---------------------------------------+
                                     /         \
       [通道 A: 标准 UAC 控制通道]   /           \  [通道 B: 厂商特化 MCU 寄存器通道]
                                   /             \
                  +---------------------+   +---------------------+
                  | UsbDeviceController |   |Dc03ProDeviceContr...|
                  +---------------------+   +---------------------+
                             |                         |
              bmRequestType: 0x20 / 0xA0  bmRequestType: 0x21 (SET_REPORT)
              端点 0 (Control Endpoint)   端点 0 (OUT) + 中断端点 0x81 (IN)
                             |                         |
              +-------------------------+   +-------------------------+
              |    USB UAC 固件层       |   |  Comtrue/Savitech MCU   |
              | (UAC Feature Unit 0x0A) |   | (内部 I2C 寄存器状态机)  |
              +-------------------------+   +-------------------------+
                             |                         |
              数字 PCM 流衰减控制 (dB)       物理 DAC 芯片 (CS43131/ES9219)
                                            55/64/100 档硬件增益与数字滤波
```

### 1.1 通道 A：标准 UAC 控制通道（基类 `UsbDeviceController`）
* **定位**：面向符合 USB Audio Class (UAC) 1.0/2.0 标准规范的固件 Feature Unit。
* **机制**：通过 USB 默认控制端点 0 执行同步控制传输（Control Transfer）。
* **控制对象**：UAC 固件层内部的 PCM 软件衰减与静音逻辑。

### 1.2 通道 B：厂商特化 MCU 寄存器通道（子类 `Dc03ProDeviceController` 等）
* **定位**：面向板载协处理器 MCU（如 Comtrue CT7601 / Savitech 等）直接挂载的物理 DAC 芯片（Cirrus Logic CS43131 / CS43198 / ESS Sabre 等）。
* **机制**：
  * **下发（Host -> Device）**：通过 USB 接口 `MI_00`（HID 类接口）发送 HID 类特异请求 `SET_REPORT`（`bmRequestType = 0x21, bRequest = 0x09, wValue = 0x0200, wIndex = 0x0000`），数据负载为**精确 16 字节**（或 8/64 字节，依机型而定）。
  * **回传（Device -> Host）**：MCU 处理完 I2C 寄存器读写后，将结果异步写入 USB 中断 IN 端点（Pipe `0x81`）；主机通过循环轮询中断端点接收**定长 8 字节事件帧**。

---

## 二、控制通道与寄存器通道详解

### 2.1 UAC 控制通道规格（基类 `0x20` / `0xA0`）

基类 `com.ibasso.volume.controller.UsbDeviceController` 中实现的 UAC 标准音量读写规范如下：

| 操作 | `bmRequestType` | `bRequest` | `wValue` | `wIndex` | 数据长度 (`wLength`) | 数据格式 |
|---|---|---|---|---|---|---|
| **读左声道音量** | `0xA0` (Class/Device/D2H) | `0x01` (GET_CUR) | `0x0201` (Volume / Ch 1) | `0x0A01` (FU 0x0A / If 0x01) | 2 字节 | 16 位有符号小端 (1/256 dB) |
| **读右声道音量** | `0xA0` (Class/Device/D2H) | `0x01` (GET_CUR) | `0x0202` (Volume / Ch 2) | `0x0A01` (FU 0x0A / If 0x01) | 2 字节 | 16 位有符号小端 (1/256 dB) |
| **读主音量** | `0xA0` (Class/Device/D2H) | `0x01` (GET_CUR) | `0x0200` (Volume / Master) | `0x0A01` (FU 0x0A / If 0x01) | 2 字节 | 16 位有符号小端 (1/256 dB) |
| **写左声道音量** | `0x20` (Class/Device/H2D) | `0x01` (SET_CUR) | `0x0201` (Volume / Ch 1) | `0x0A01` (FU 0x0A / If 0x01) | 2 字节 | 16 位有符号小端 (1/256 dB) |
| **写右声道音量** | `0x20` (Class/Device/H2D) | `0x01` (SET_CUR) | `0x0202` (Volume / Ch 2) | `0x0A01` (FU 0x0A / If 0x01) | 2 字节 | 16 位有符号小端 (1/256 dB) |
| **读静音状态** | `0xA0` (Class/Device/D2H) | `0x01` (GET_CUR) | `0x0100` (Mute / Master) | `0x0A01` (或 `0x0101`) | 1 字节 | `0x00`: 未静音, `0x01`: 静音 |

* **数值含义**：小端 16 位整型。`0x0000` = 0.0 dB（最大无衰减）；`0xF100` (-3840) = -15.0 dB；`0xE200` (-7680) = -30.0 dB。
* **反查表**：APK 内置 `nVolumeStep[]` 静态数组（包含 64 或 100 项），将读回的 16 位衰减值二分反查为 UI 档位。

### 2.2 MCU 寄存器通道规格（特化 16 字节 HID 报文）

在 `Dc03ProDeviceController` 中，所有硬件参数变更通过构造任务（`Task`）并由 `TaskManager` 压入工作队列执行：

#### 下发控制请求（HID SET_REPORT）
```c
WINUSB_SETUP_PACKET:
  bmRequestType = 0x21  (Host-to-Device / Class / Interface)
  bRequest      = 0x09  (HID SET_REPORT)
  wValue        = 0x0200 (Report Type: Output 0x02, Report ID: 0x00)
  wIndex        = 0x0000 (Interface Number: 0)
  wLength       = 0x0010 (16 字节，严禁传入其他长度)
```

#### 报文结构（固定 16 字节）：
```
Byte 0       : seqId (任务序列号，由 TaskManager 递增分配，1 ~ 254，用于回显匹配)
Byte 1       : opCode (寄存器操作码，见下表)
Byte 2       : devAddr (目标设备地址，如 0x60 或 0x62)
Byte 3..N    : 寄存器内部偏移与写入数据载荷
(尾部字节补零至满 16 字节)
```

#### 关键操作码（`opCode`）：
| opCode | 含义 | 典型应用 |
|---|---|---|
| `0x11` | 写单字节寄存器 | 配置硬件控制标志、采样率锁相环 |
| `0x12` | 读 2 字节寄存器偏移 | 请求读出指定偏移处的寄存器状态 |
| `0x88` | 连写多字节寄存器序列 | DC03 Pro 调节音量时的多寄存器联动写入 |
| `0xA0` | 读 2 字节偏移寄存器 | 读取滤波模式与特定硬件配置 |
| `0xA2` | 读寄存器特定状态 | 读取左右声道当前硬件实际增益 |

#### 中断 IN 端点异步回包格式（固定 8 字节）：
MCU 执行完毕后，向 Pipe `0x81` 压入 8 字节响应帧：
```
Byte 0       : seqId (与下发请求的 Byte 0 完全一致，用于回显匹配)
Byte 1       : opCode 或响应状态标志
Byte 2..3    : 回包附加参数
Byte 4       : 核心数据字节（音量档位 / 滤波编号 / 增益值）
Byte 5..7    : 扩展数据（如 FPGA 版本号、硬件状态字）
```

---

## 三、报文封装与编解码规范

### 3.1 DC03 Pro 音量控制报文链路

DC03 Pro 具有 55 级物理硬件音量控制（0 ~ 55 档）。
当用户设定音量档位 `step`（0..55）时，驱动通过查表获取衰减值 `V = VolumeTables.Step2[step]`（档位 0 为 `0xFF` 静音，档位 55 为 `0x2D`），并依次下发 5 条 16 字节报文：

```
// 1. 左声道 4 条寄存器配置序列
[01] 11 88 60 00 00 05 09 00 01 00 V 00 00 00 00
[02] 11 88 60 00 00 05 09 00 02 00 V 00 00 00 00
[09] 11 88 60 00 00 05 07 00 00 00 V 00 00 00 00
[0A] 11 88 60 00 00 05 07 00 01 00 V 00 00 00 00
// 2. 左声道最终提交
[13] 11 A0 A2 00 10 01 V 00 00 00 00 00 00 00 00

// 3. 右声道对应镜像下发（devAddr 变为 0x62，命令字相应偏移）
[03] 11 88 62 00 00 05 09 00 01 00 V 00 00 00 00
[04] 11 88 62 00 00 05 09 00 02 00 V 00 00 00 00
[0B] 11 88 62 00 00 05 07 00 00 00 V 00 00 00 00
[0C] 11 88 62 00 00 05 07 00 01 00 V 00 00 00 00
[14] 11 A0 A2 00 11 01 V 00 00 00 00 00 00 00 00
```

### 3.2 数字滤波器选择报文（CS43131 模式）

DC03 Pro 支持 5 种数字滤波模式（模式 1 ~ 5）：
```
下发报文格式：
[seqId] 12 E4 A2 00 10 [filterMode] 00 00 00 00 00 00 00 00 00
其中 filterMode:
  0x01: Fast Roll-off (快速滚降)
  0x02: Short Delay Fast Roll-off (短延迟快速滚降)
  0x03: Slow Roll-off (慢速滚降)
  0x04: Short Delay Slow Roll-off (短延迟慢速滚降)
  0x05: Non-oversampling (NOS 无超采样)
```

### 3.3 状态回读请求报文（读音量寄存器）

官方 App 中构造的异步读音量请求：
```
左声道：[seqId] 12 E4 A2 00 10 01 00 00 00 00 00 00 00 00 00 00
右声道：[seqId] 12 E4 A2 00 11 01 00 00 00 00 00 00 00 00 00 00
```
响应从中断 IN 端点回传，格式为 8 字节，`data[4]` 即当前硬件衰减值 `V`。

---

## 四、全机型协议矩阵与芯片方案

通过 REA 反编译 `com.ibasso.volume` 全量 38 个控制器类，提取出官方支持的全部产品矩阵：

| USB Product Name | 控制器类名 | 协议架构类别 | 帧长 | 芯片方案特征 | 硬件级特性 |
|---|---|---|---|---|---|
| `iBasso-DC03-Pro` | `Dc03ProDeviceController` | 寄存器 4 字节偏移 | 16 B | Cirrus CS43131 ×2 | 55 级硬件音量、5 种滤波器、功耗优化 |
| `iBasso-DC04-Pro` | `Dc04ProDeviceController` | 寄存器 4 字节偏移 | 16 B | Cirrus CS43131 ×2 | 100 级音量、5 种滤波、平衡输出独立增益 |
| `iBasso-DC06` | `Dc06DeviceController` | 寄存器 1 字节偏移 | 16 B | ESS ES9219C ×2 | 硬件 MQA 渲染、64 级音量 |
| `iBasso-DC06-Pro` | `Dc06ProDeviceController` | 寄存器 1 字节偏移+0xA2 | 16 B | ESS ES9219C ×2 | 100 级音量、NDK 滤波计算 |
| `Macaron` | `Dc08DeviceController` | 寄存器 4 字节偏移 | 16 B | Cirrus CS43198 | 甜品系列小尾巴 |
| `iBasso DC-Elite` | `Dc07DeviceController` | 8 字节裸帧 | 8 B | ROHM BD34301EKV | 旗舰电流型 DAC、阶梯步进电位器 |
| `iBasso DC07 Pro` | `Dc07ProDeviceController` | 8 字节裸帧 | 8 B | Cirrus CS43131 ×4 | OLED 屏幕控制联动、多通道滤波 |
| `iBasso DC-Tonfa` | `TonfaDeviceController` | 8 字节裸帧 | 8 B | 专用 MCU 方案 | 电子管/晶体管双音色切换 |
| `iBasso DC-Nunchaku`| `Dc09DeviceController` | 8 字节裸帧 | 8 B | 专用 MCU 方案 | 定制旋钮控制联动 |
| `DC04U` | `Dc04uDeviceController` | 64 字节裸帧 | 64 B | 硬件 DSP 引擎 | 支持 5 段参量均衡器 (PEQ) |
| `Macchiato` | `MacchiatoDeviceController`| 64 字节裸帧 | 64 B | 硬件 DSP 引擎 | 支持 5 段参量均衡器 (PEQ) |
| `D18` | `D18DeviceController` | 64 字节裸帧 | 64 B | 高级 DSP 架构 | 桌面/便携一体解码放 |
| `CX31993...` | `CX31993DeviceController` | 2 字节直接写入 | 2 B | Conexant CX31993 | 通用低成本小尾巴方案 |
| *(其他标准机型)* | `UsbDeviceController` (基类) | UAC 0x20/0xA0 控制传输 | 2 B | 标准 UAC 固件 | 标准 UAC 1.0/2.0 Feature Unit 控制 |

---

## 五、Windows vs Android 底层通信语义差异

在跨平台实现协议时，以下操作系统级差异是导致“Android 通而 Windows 卡死”的根本原因：

### 5.1 USB 控制传输权限与过滤机制

| 维度 | Android (Linux `usbfs`) | Windows (`WinUSB.sys`) | Windows (`hidusb.sys`) |
|---|---|---|---|
| **端点 0 访问接口** | `UsbDeviceConnection.controlTransfer()` | `WinUsb_ControlTransfer()` | 操作系统完全隐藏，无公开 API |
| **Recipient 范围** | **全开放**：Linux 内核不校验 Recipient 是否匹配当前认领的接口，可自由发 `0xA0`(Device) | **强隔离**：仅允许发往自身认领接口的控制请求，Device 目标受主机驱动过滤限制 | **不支持**（仅限 HID 规范的 Report） |
| **数据阶段长度校验** | 允许驱动按最大长度收发，宽松容忍 | **严格对齐**：长度不匹配时驱动直接挂起等待或返回 Win32 87/121 | 驱动限制为报文描述符声明的静态大小 |
| **并发与重叠 I/O** | 纯同步阻塞 / 线程池轮询 | **强制要求** `FILE_FLAG_OVERLAPPED` 打开句柄，否则 `WinUsb_Initialize` 报 Win32 6 | 驱动内部托管，应用层透明 |

### 5.2 端点 0 死锁机制与为什么会超时 (Win32 121)

1. **畸形包导致硬件死锁**：DC03 Pro 板载 MCU 固件的 USB 状态机极度精简，若收到主机发来的**长度不符报文**（如向只接收 16 字节的 HID SET_REPORT 发送了 64 字节缓冲区），MCU 固件会停在等待接收后续数据阶段，或拒绝回复 ACK，导致端点 0 的事务握手永久挂起。
2. **连锁挂起**：端点 0 一旦挂起，主机 USB 控制器对该设备端点 0 的所有后续事务全部返回 `ERROR_SEM_TIMEOUT` (Win32 121)。
3. **免驱动 DAC 的硬件特性**：DC03 Pro 依靠 USB 5V VBUS 供电，板上无独立复位按键，固件未实现 USB 软复位命令。**一旦端点 0 死锁，必须物理断开 USB 线彻底掉电**，使板上滤波电容完全放电，方能清除 MCU 内部硬件状态机挂起。

---

## 六、实机验证结论（证据等级分类）

根据本工程在真实硬件（iBasso DC03 Pro, VID `0x262A` / PID `0x187E`）上的多轮独立探测与逆向核对，结论按严格的证据等级分类如下：

### 6.1 【已证实】（具有实机硬件运行日志与确定性代码支持）

1. **HID 接口拓扑与管道属性**：
   * MI_00 声明为标准 HID 类接口（`InterfaceClass = 0x03, SubClass = 0x00, Protocol = 0x00`）。
   * `WinUsb_QueryPipe` 证实其物理端点配置为：**`Pipe[0]: Type=Interrupt, Id=0x81 (Dir=IN), MaxPacket=32, Interval=6`**。
   * 固件**确实存在**中断 IN 端点（0x81），用于向主机异步回送寄存器响应帧。
2. **WinUSB 底层互操作参数规范**：
   * WinUSB 要求文件句柄必须以 `FILE_FLAG_OVERLAPPED` (0x40000000) 打开；以纯同步模式 (flags=0) 打开时 `WinUsb_Initialize` 确定性返回 `Win32 6 (ERROR_INVALID_HANDLE)`。
   * 在 x64 架构下，`WINUSB_SETUP_PACKET` 必须严格对齐为 8 字节（`RequestType: 1B, Request: 1B, Value: 2B, Index: 2B, Length: 2B`），不可声明为 7 字节。
   * WinUSB 允许对端点 0 设置 `PIPE_TRANSFER_TIMEOUT`（策略 0x03），设置后控制请求在指定超时（如 1000ms）后安全退出并返回 Win32 121，不再导致整个应用程序主线程永久冻结。
3. **官方 Android App 的运行时真实行为**：
   * 官方 Android App 针对 DC03 Pro 时，其 `getVolume()` 方法被重写为 `return this.volume;`（直接返回本地内存/SpStorage 缓存值），在业务逻辑中**从未调用基类的 `0xA0` 控制传输读取 DC03 Pro 的硬件音量**。
   * 官方 Android App 针对 DC03 Pro 的所有状态变更均构造 16 字节报文通过 `SET_REPORT` 下发，并由后台 `MonitorThread` 循环读取中断 IN 端点。

### 6.2 【已证伪】（实验与反编译推翻了早期假设）

1. **证伪「DC03 Pro 固件没有中断 IN 端点、只写不读」**：
   * 早期由于 `WinUsb_QueryPipe` 结构体偏移错误（原定义 5 字节溢出破坏栈），导致查询管道返回垃圾值并误以为无中断端点。修复 12 字节显式布局后，已物理证实存在 `Pipe 0x81 (Interrupt IN)`。
2. **证伪「0x21 控制传输在 IN 阶段返回数据」**：
   * `bmRequestType = 0x21` 的 bit7 为 0，在 USB 规范中严格定义为 **Host-to-Device (OUT)**。它根本没有 IN 数据阶段，返回的缓冲区内容纯属主机发送的“回声”，绝非固件数据回包。

### 6.3 【静态代码推导】（来自完整反编译分析，具有代码完整性，但非运行时抓包）

1. **基类 `0xA0` 同步读通道的设计意图**：
   * 静态分析证实基类 `UsbDeviceController` 包含了 `bmRequestType=0xA0, bRequest=0x01, wValue=0x0201/0x0202, wIndex=0x0A01, len=2` 的完整同步读取逻辑。该逻辑是厂商为使用通用 UAC 固件方案的设备设计的。
   * 但在 DC03 Pro 这款特定双 CS43131 独立 MCU 架构的小尾巴上，硬件寄存器状态完全脱离 UAC 固件控制，因此子类将其完全绕过。
2. **全机型 13 种设备控制器的协议常量与帧结构**：
   * 完整提取自 `IConstants.java` 与各控制器源码，参数映射逻辑完备自洽。

### 6.4 【尚未找到 / 硬件限制】

1. **原生 `hidusb.sys` 下的双向通信通路**：
   * 在 Windows 原生自带的 `hidusb.sys` 驱动下，Windows 内核禁止用户态程序直接向免驱 HID 设备发送非标控制请求或裸读中断端点。
   * 调用 `HidD_GetInputReport` 会强行发出非标控制读，导致 DC03 Pro 硬件总线立即死锁。因此在原生免驱 HID 模式下，目前尚无法安全实现对 MCU 内部寄存器的回读。

---

## 七、硬件防死锁与驱动还原规范

### 7.1 硬件防死锁操作守则
1. **绝不调用 `HidD_GetInputReport`**：对 DC03 Pro，此 API 会引发即时总线挂起。
2. **严禁下发超过 16 字节的 HID SET_REPORT 报文**：必须严格分配 16 字节数组下发，任何填充或多余的缓冲区长度均会导致端点 0 状态机卡死。
3. **配置传输超时**：在 WinUSB 下操作前，必须通过 `WinUsb_SetPipePolicy(handle, 0x00, 3, 4, ref timeoutMs)` 将超时限制在合理范围（如 1000ms），防止进程被死锁挂起。

### 7.2 驱动安全还原规范（唯一标准）
若为测试目的将 `MI_00` 改绑为了 WinUSB，在测试完成后必须使用 Windows 官方 PnP 工具将其彻底卸载，还原为系统的原生 HID 驱动，确保耳机媒体按键恢复正常：

```powershell
# 必须使用管理员权限运行：
pnputil /delete-driver oem175.inf /uninstall /force
```

执行后重新拔插 USB 设备，系统将自动重新装载 `input.inf`（`hidusb.sys`），且 `MI_01` 音频流接口不受任何影响。

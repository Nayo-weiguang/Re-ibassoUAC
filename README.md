# iBasso UAC 桌面控制台与全机型协议逆向工程 (Re-ibassoUAC)

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![.NET 8.0](https://img.shields.io/badge/.NET-8.0%20WPF-purple.svg)](https://dotnet.microsoft.com/)
[![Protocol Version](https://img.shields.io/badge/Protocol-iBasso%20UAC%202.1.9-green.svg)](docs/iBasso协议逆向规格.md)
[![Supported Devices](https://img.shields.io/badge/Devices-13%20Models-orange.svg)](docs/iBasso协议逆向规格.md)

Windows 桌面版 **iBasso USB DAC 控制台** 与 **硬件通信协议逆向规格体系**。  
本项目通过对 iBasso 官方 Android 配套控制应用 **iBasso UAC 2.1.9**（`com.ibasso.volume`）的深度反编译与协议逆向剖析，完整还原了解耦的 USB 通信机制，并在 Windows 11 下通过纯原生 C# / .NET 8 WPF 实现了无驱动依赖的桌面端硬件控制台。

---

## 核心技术与架构解密

通过逆向分析官方应用全部 38 个控制器类与底层调度逻辑，证实 iBasso 设备采用**双通道分流架构**：

```
                ┌─────────────────────────────────────────────────────┐
                │             iBasso USB 控制流解耦架构               │
                └─────────────────────────┬───────────────────────────┘
                                          │
            ┌─────────────────────────────┴─────────────────────────────┐
            ▼                                                           ▼
   【UAC 标准控制通道】                                         【MCU 专用扩展通道】
   (基类 UsbDeviceController)                                   (13 款子类具体设备控制器)
   • 传输介质: 端点 0 控制传输 (Control Transfer)                • 传输介质: HID SET_REPORT / 中断 IN 端点
   • 请求编码: bmRequestType = 0x20 / 0xA0 (Audio)              • 报文规格: 16 字节 (DC03Pro等) / 64 字节
   • 核心功能: 基础音量、声道静音、硬件版本读取                 • 核心功能: 细分增益、数模滤波、二阶硬件 EQ、
                                                                          输出模式、DAC 供电电压、声道平衡
```

- **UAC 标准控制通道**：基于 USB Audio Class 1.0/2.0 标准规范，通过默认端点 0 发起 `0x20`（SET_CUR）与 `0xA0`（GET_CUR）控制请求。
- **MCU 自定义扩展通道**：子类控制器通过专属 HID 报文（Report ID 0x00）与板载 MCU 通信。Windows 下直接使用系统内置 `hidusb.sys`，无需用户安装任何第三方驱动。

---

## 13 款全机型协议支持矩阵

| 系列 / 型号代号 | 代表机型 | 传输报文载荷 | 核心特性支持 | 硬件控制实现类 |
| :--- | :--- | :---: | :--- | :--- |
| **DC03Pro 系列** | DC03 Pro | 16 字节 | 100档音量、高低增益、5种滤波、声道平衡 | `Dc03ProDeviceController.java` |
| **DC04Pro 系列** | DC04 Pro | 16 字节 | 100档音量、高低增益、5种滤波、声道平衡 | `Dc04ProDeviceController.java` |
| **DC06 系列** | DC06 / DC06 Pro | 16 字节 | 64档/100档音量衰减表切换、数模滤波 | `Dc06DeviceController.java` |
| **DC07 系列** | DC07 / DC07 Pro | 8 字节 | 音量控制、独立数模滤波模式 | `Dc07DeviceController.java` |
| **DC08 / DC09** | DC08 / DC09 | 8 字节 | 基础硬件音量、增益控制 | `Dc08DeviceController.java` |
| **DC04u 系列** | DC04U | 64 字节 | 100档音量、增益、10段硬件参数 EQ | `Dc04uDeviceController.java` |
| **D18 系列** | D18 | 64 字节 | 参数 EQ、增益模式、屏幕配置 | `D18DeviceController.java` |
| **Macchiato** | Macchiato | 64 字节 | 参数 EQ、DAC 运放电压模式调节 | `MacchiatoDeviceController.java` |
| **Tonfa 系列** | DC-Tonfa | 8 字节 | 音量控制、增益档位 | `TonfaDeviceController.java` |
| **CX31993 系列** | 兼容第三方芯片 | 8 字节 | 简易音量控制与增益配置 | `CX31993DeviceController.java` |

---

## 核心文档导航

本项目产出了工业级逆向规格标准与真机验证记录，建议按需查阅：

1. 📖 **[iBasso 协议逆向全量规格文档](docs/iBasso协议逆向规格.md)**  
   *含双通道报文格式、13 款机型寄存器映射字典、音量衰减双对数计算公式、参数 EQ 二阶 IIR 滤波器频响换算等。*
2. 🔬 **[DC03 Pro Windows 真机验证报告](docs/真机验证报告.md)**  
   *实机验证 WinUSB 与 HID 双通道、端点 0 超时防死锁机制、媒体键响应差异与驱动还原指令。*
3. 📑 **[原始逆向过程报告](docs/逆向报告.md)**  
   *记录反编译初期的分析轨迹与技术推演。*
4. 📂 **[官方逆向核心源码与分析资产](reverse-engineering/)**  
   *收录从官方 App 反编译提取的全部 131 个 Java 核心控制器、EQ 计算类及 Smali 原生汇编。*

---

## 避坑与真机安全准则（必读）

在 Windows 平台进行底层硬件控制时，请严格遵守以下验证结论：

1. **防硬件死锁**：
   - 严禁对 DC03 Pro 调用 `HidD_GetInputReport`，实测会诱发 MCU 端点 0 挂起死锁，导致设备停止响应，**必须物理拔插方可复原**。
   - 针对 MCU 的 16 字节 HID SET_REPORT 必须严格对齐为 16 字节，严禁下发未对齐的 64 字节缓冲区。
2. **WinUSB 驱动还原标准**：
   若使用 Zadig 临时改绑 `MI_00` 测试，请使用以下标准管理员命令强制还原系统内置 HID 驱动（切勿通过设备管理器反复扫描）：
   ```powershell
   pnputil /delete-driver oem175.inf /uninstall /force
   ```
   *(其中 `oem175.inf` 为 Zadig 注入的第三方驱动编号，可通过 `Get-CimInstance Win32_PnPSignedDriver` 查询确认)*

---

## 仓库工程结构

```
Re-ibassoUAC/
├── docs/                           # 核心逆向与协议规格工程文档
│   ├── iBasso协议逆向规格.md          # 13 款机型全量协议规范、寄存器表与算法公式
│   ├── 真机验证报告.md               # DC03 Pro 真机抓包实测与 Windows 驱动避坑指南
│   └── 逆向报告.md                   # 初始逆向分析汇总
│
├── iBassoUAC-Desktop/              # Windows 桌面控制客户端源码 (.NET 8 WPF)
│   ├── Devices/                    # 各种机型 Profile（音量表、增益、滤波、EQ 模型）
│   ├── Interop/                    # Win32 原生 P/Invoke（HidApi, WinUsbApi）
│   ├── Protocol/                   # 通信协议栈（UacLink, WinUsbLink, DeviceEnumerator）
│   ├── ViewModels/                 # MVVM 视图模型与响应式绑定
│   ├── Services/                   # 设备连接与会话生命周期管理
│   ├── MainWindow.xaml             # 现代 WPF 交互界面
│   └── iBassoUAC.Desktop.csproj    # 工程定义文件
│
├── tools/                          # 辅助诊断与自动化测试工具链
│   ├── DeviceProbe/                # 硬件端点诊断与 WinUSB 控制传输探测工具
│   └── ProtocolSelfTest/           # 协议编解码脱机单元测试套件（57 项校验）
│
├── reverse-engineering/            # 官方 Android App 逆向工程核心资产
│   ├── sources/com/ibasso/         # 提取的 131 个核心 Java 逆向源文件
│   ├── smali/                      # 关键调度类的 Smali 汇编
│   ├── AndroidManifest.xml         # 官方 App 清单
│   └── all_strings.txt             # 提取的全量字符资源
│
├── .gitignore                      # 规范的排除规则
├── LICENSE                         # MIT 开源许可证
└── README.md                       # 项目主文档
```

---

## 构建与运行

### 环境要求
- Windows 10 / 11 (x64)
- [.NET 8.0 SDK](https://dotnet.microsoft.com/download/dotnet/8.0)

### 从源码编译桌面客户端

```powershell
# 1. 编译 Release 版本
dotnet build iBassoUAC-Desktop\iBassoUAC.Desktop.csproj -c Release

# 2. 发布为独立单文件可执行程序
dotnet publish iBassoUAC-Desktop\iBassoUAC.Desktop.csproj -c Release -r win-x64 `
    --self-contained false -p:PublishSingleFile=true -o dist\iBassoUAC

# 3. 运行脱机协议测试套件（无需插入硬件，自动验证 57 项报文编解码断言）
dotnet run --project tools\ProtocolSelfTest\ProtocolSelfTest.csproj
```

---

## 协议与免责声明

本项目遵循 [MIT License](LICENSE) 开源。  
本项目所有协议逆向成果均源于对官方公开配套软件的兼容性研究与学习目的，不包含任何绕过数字版权管理（DRM）或固件保护的技术手段。相关商标归原权利人所有。

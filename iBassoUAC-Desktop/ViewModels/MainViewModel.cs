using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using iBassoUAC.Devices;
using iBassoUAC.Protocol;
using iBassoUAC.Services;

namespace iBassoUAC.ViewModels;

public sealed class RelayCommand : ICommand
{
    private readonly Func<object?, Task> _execute;
    private readonly Func<object?, bool>? _can;

    public RelayCommand(Func<object?, Task> execute, Func<object?, bool>? canExecute = null)
    {
        _execute = execute;
        _can = canExecute;
    }

    public event EventHandler? CanExecuteChanged;
    public bool CanExecute(object? parameter) => _can?.Invoke(parameter) ?? true;
    public void Execute(object? parameter) => _ = _execute(parameter);
    public void RaiseCanExecuteChanged() => CanExecuteChanged?.Invoke(this, EventArgs.Empty);
}

/// <summary>用户友好的硬件参数选项数据模型</summary>
public sealed record SettingOption(int Value, string Title, string Description)
{
    public string DisplayText => $"{Title}  ·  {Description}";
    public override string ToString() => Title;
}

public sealed class MainViewModel : INotifyPropertyChanged
{
    private readonly DeviceSession _session = new();
    private UsbDeviceInfo? _selectedDevice;
    private string _status = "等待连接";
    private DeviceState _state = new();
    private string _logText = string.Empty;
    private bool _isConnected;
    private bool _showAllDevices;
    private EqPreset _eq = EqPreset.Flat();
    private int _eqPresetSlot;

    public MainViewModel()
    {
        RefreshCommand = new RelayCommand(_ => { LoadDevices(); return Task.CompletedTask; });
        ConnectCommand = new RelayCommand(_ => { Connect(); return Task.CompletedTask; }, _ => SelectedDevice is not null);
        DisconnectCommand = new RelayCommand(_ => { _session.Disconnect(); Sync(); return Task.CompletedTask; }, _ => IsConnected);
        RefreshStateCommand = new RelayCommand(_ => RefreshState(), _ => IsConnected);
        MuteCommand = new RelayCommand(_ => Mute(), _ => IsConnected);
        SafeVolumeCommand = new RelayCommand(_ => SetVolume(60));

        VolumeStepUpCommand = new RelayCommand(_ => SetVolume(Math.Min(MaxVolume, VolumeIndex + 1)), _ => IsConnected);
        VolumeStepDownCommand = new RelayCommand(_ => SetVolume(Math.Max(0, VolumeIndex - 1)), _ => IsConnected);
        SetPresetVolumeCommand = new RelayCommand(p =>
        {
            if (p is string s && int.TryParse(s, out var pct))
            {
                var target = (int)Math.Round(MaxVolume * (pct / 100.0));
                return SetVolume(target);
            }
            return Task.CompletedTask;
        }, _ => IsConnected);
        ResetBalanceCommand = new RelayCommand(_ => SetBalance(0), _ => IsConnected);

        ResetEqCommand = new RelayCommand(_ => { Eq = EqPreset.Flat(); return Task.CompletedTask; });
        WriteEqCommand = new RelayCommand(_ => WriteEq(), _ => IsConnected && Profile?.SupportsParametricEq == true);
        ReadEqCommand = new RelayCommand(_ => ReadEq(), _ => IsConnected && Profile?.SupportsParametricEq == true);

        _session.StatusChanged += s => { Status = s; };
        _session.StateChanged += Sync;
        _session.Log += Append;
        _session.NativeStateChanged += _ => Sync();

        InitOptions();
        LoadDevices();
    }

    // ------------------------------------------------------------- options

    public ObservableCollection<SettingOption> DigitalFilterOptions { get; } = new();
    public ObservableCollection<SettingOption> GainOptions { get; } = new();
    public ObservableCollection<SettingOption> OutputOptions { get; } = new();
    public ObservableCollection<SettingOption> SpdifOptions { get; } = new();
    public ObservableCollection<SettingOption> AmpModeOptions { get; } = new();

    private void InitOptions()
    {
        DigitalFilterOptions.Clear();
        DigitalFilterOptions.Add(new(0, "快速滚降滤波", "Fast roll-off · 标准平直高频延伸"));
        DigitalFilterOptions.Add(new(1, "低延迟快速滚降滤波", "Short delay fast roll-off · 低群延迟紧凑瞬态"));
        DigitalFilterOptions.Add(new(2, "慢速滚降滤波", "Slow roll-off · 柔和平缓耐听"));
        DigitalFilterOptions.Add(new(3, "低延迟慢速滚降滤波", "Short delay slow roll-off · 声场温润松弛"));
        DigitalFilterOptions.Add(new(4, "非过采样", "Non oversampling (NOS) · 还原模拟韵味"));

        GainOptions.Clear();
        GainOptions.Add(new(0, "低", "Low · 适用高灵敏入耳耳塞"));
        GainOptions.Add(new(1, "中", "Middle · 平衡推力模式"));
        GainOptions.Add(new(2, "高", "High · 强劲推力适合高阻大耳"));

        OutputOptions.Clear();
        OutputOptions.Add(new(0, "普通", "Normal · 标准耳机放大输出"));
        OutputOptions.Add(new(1, "增压", "Turbo · 增强推力电压输出"));

        SpdifOptions.Clear();
        SpdifOptions.Add(new(0, "关", "Off · 正常 USB DAC 模拟输出"));
        SpdifOptions.Add(new(1, "开", "On · 启用同轴输出"));

        AmpModeOptions.Clear();
        AmpModeOptions.Add(new(0, "普通", "Normal · 标准工作模式"));
        AmpModeOptions.Add(new(1, "省电模式", "Power Saving · 优化低功耗"));
        AmpModeOptions.Add(new(2, "超级", "Super · 满幅高动态模式"));
    }

    // ------------------------------------------------------------- properties

    public ObservableCollection<UsbDeviceInfo> Devices { get; } = new();

    public bool ShowAllDevices
    {
        get => _showAllDevices;
        set
        {
            if (_showAllDevices != value)
            {
                _showAllDevices = value;
                Notify();
                LoadDevices();
            }
        }
    }

    public UsbDeviceInfo? SelectedDevice
    {
        get => _selectedDevice;
        set
        {
            _selectedDevice = value;
            Notify();
            (ConnectCommand as RelayCommand)?.RaiseCanExecuteChanged();
            NotifyHardwareInfo();
        }
    }

    public string Status
    {
        get => _status;
        private set { _status = value; Notify(); }
    }

    public DeviceState State
    {
        get => _state;
        private set { _state = value; Notify(); }
    }

    public bool IsConnected
    {
        get => _isConnected;
        private set
        {
            _isConnected = value;
            Notify();
            Notify(nameof(ConnectionBadgeText));
            (DisconnectCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (RefreshStateCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (WriteEqCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (ReadEqCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (VolumeStepUpCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (VolumeStepDownCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (SetPresetVolumeCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (ResetBalanceCommand as RelayCommand)?.RaiseCanExecuteChanged();
            NotifyHardwareInfo();
        }
    }

    public string ConnectionBadgeText => IsConnected ? "🟢 已建立控制连接" : "⚪ 未连接设备";

    public DeviceProfile? Profile => _session.Profile;

    public int MaxVolume => Profile?.MaxVolume ?? 100;

    public double VolumeDb => State.Volume < 0 ? 0 : (Profile?.VolumeToDb(State.Volume) ?? 0);

    public int VolumeIndex
    {
        get => State.Volume < 0 ? 60 : State.Volume;
        set { if (value != State.Volume) { State.Volume = value; _ = SetVolume(value); } Notify(); NotifyVolume(); }
    }

    public int Balance
    {
        get => State.Balance;
        set { if (value != State.Balance) { State.Balance = value; _ = SetBalance(value); } Notify(); }
    }

    public SettingOption? SelectedDigitalFilterOption
    {
        get => DigitalFilterOptions.FirstOrDefault(o => o.Value == State.DigitalFilter) ?? DigitalFilterOptions.FirstOrDefault();
        set
        {
            if (value is not null && value.Value != State.DigitalFilter)
            {
                DigitalFilter = value.Value;
            }
        }
    }

    public int DigitalFilter
    {
        get => State.DigitalFilter;
        set
        {
            if (value != State.DigitalFilter)
            {
                State.DigitalFilter = value;
                _ = SetDigitalFilter(value);
            }
            Notify();
            Notify(nameof(SelectedDigitalFilterOption));
        }
    }

    public SettingOption? SelectedGainOption
    {
        get => GainOptions.FirstOrDefault(o => o.Value == State.Gain) ?? GainOptions.FirstOrDefault();
        set
        {
            if (value is not null && value.Value != State.Gain)
            {
                Gain = value.Value;
            }
        }
    }

    public int Gain
    {
        get => State.Gain;
        set
        {
            if (value != State.Gain)
            {
                State.Gain = value;
                _ = Run(p => p.SetGainAsync(value));
            }
            Notify();
            Notify(nameof(SelectedGainOption));
        }
    }

    public SettingOption? SelectedOutputOption
    {
        get => OutputOptions.FirstOrDefault(o => o.Value == State.Output) ?? OutputOptions.FirstOrDefault();
        set
        {
            if (value is not null && value.Value != State.Output)
            {
                Output = value.Value;
            }
        }
    }

    public int Output
    {
        get => State.Output;
        set
        {
            if (value != State.Output)
            {
                State.Output = value;
                _ = Run(p => p.SetOutputAsync(value));
            }
            Notify();
            Notify(nameof(SelectedOutputOption));
        }
    }

    public int DsdFilter
    {
        get => State.DsdFilter;
        set { if (value != State.DsdFilter) { State.DsdFilter = value; _ = SetDsdFilter(value); } Notify(); }
    }

    public int Spdif
    {
        get => State.Spdif;
        set { if (value != State.Spdif) { State.Spdif = value; _ = Run(p => p.SetSpdifAsync(value)); } Notify(); }
    }

    public int AmpMode
    {
        get => State.AmpMode;
        set { if (value != State.AmpMode) { State.AmpMode = value; _ = Run(p => p.SetAmpModeAsync(value)); } Notify(); }
    }

    // ------------------------------------------------------------- hardware info

    public string HardwareChipSummary
    {
        get
        {
            if (SelectedDevice is null) return "未选择设备";
            var name = SelectedDevice.DisplayName;
            if (name.Contains("DC03", StringComparison.OrdinalIgnoreCase)) return "Cirrus Logic CS43131 ×2 (MasterHIFI™ Dual DAC)";
            if (name.Contains("DC04", StringComparison.OrdinalIgnoreCase)) return "Cirrus Logic CS43131 ×2 (4.4mm 平衡+3.5mm 单端)";
            if (name.Contains("DC06", StringComparison.OrdinalIgnoreCase)) return "ESS Technology ES9219C ×2 (SABRE HiFi DAC)";
            if (name.Contains("DC07", StringComparison.OrdinalIgnoreCase)) return "Cirrus Logic CS43131 ×4 (Quad DAC 矩阵架构)";
            if (name.Contains("Elite", StringComparison.OrdinalIgnoreCase)) return "ROHM BD34301EKV (MUS-IC™ 电流输出旗舰 DAC)";
            if (name.Contains("Macaron", StringComparison.OrdinalIgnoreCase) || name.Contains("DC08", StringComparison.OrdinalIgnoreCase)) return "Cirrus Logic CS43198 (低功耗高解析解码)";
            return SelectedDevice.IsIbasoFamily ? "iBasso 专用音频主控方案" : "标准通用 USB HID 控制器";
        }
    }

    public string HardwareInterfaceSummary
    {
        get
        {
            if (SelectedDevice is null) return "--";
            return $"控制通道: HID MI_00 (Report ID 0x00)  |  驱动: 系统内置 hidusb.sys  |  硬件ID: {SelectedDevice.VidPid}";
        }
    }

    private void NotifyHardwareInfo()
    {
        Notify(nameof(HardwareChipSummary));
        Notify(nameof(HardwareInterfaceSummary));
    }

    // ------------------------------------------------------------- EQ & logs

    public EqPreset Eq
    {
        get => _eq;
        set { _eq = value; Notify(); Notify(nameof(EqCurve)); }
    }

    public int EqPresetSlot
    {
        get => _eqPresetSlot;
        set { _eqPresetSlot = value; Notify(); }
    }

    public string LogText
    {
        get => _logText;
        private set { _logText = value; Notify(); }
    }

    public string EqCurve => BuildEqCurve();

    private string BuildEqCurve()
    {
        var freqs = new[] { 20.0, 31.5, 63, 125, 250, 500, 1000, 2000, 4000, 8000, 16000, 20000 };
        var lines = new List<string>();
        foreach (var f in freqs)
        {
            var db = 0.0;
            foreach (var band in Eq.Bands)
            {
                if (band.FilterType == EqFilterKind.Disable) continue;
                db += Biquad.ResponseDb(Biquad.Coefficients(band.FilterType, band.Frequency, band.Gain, band.Factor), f);
            }
            lines.Add($"{f,7:0} Hz  {db,7:+0.00;-0.00; 0.00} dB");
        }
        return string.Join(Environment.NewLine, lines);
    }

    // --------------------------------------------------------------- commands

    public RelayCommand RefreshCommand { get; }
    public RelayCommand ConnectCommand { get; }
    public RelayCommand DisconnectCommand { get; }
    public RelayCommand RefreshStateCommand { get; }
    public RelayCommand MuteCommand { get; }
    public RelayCommand SafeVolumeCommand { get; }
    public RelayCommand VolumeStepUpCommand { get; }
    public RelayCommand VolumeStepDownCommand { get; }
    public RelayCommand SetPresetVolumeCommand { get; }
    public RelayCommand ResetBalanceCommand { get; }
    public RelayCommand ResetEqCommand { get; }
    public RelayCommand WriteEqCommand { get; }
    public RelayCommand ReadEqCommand { get; }

    private void LoadDevices()
    {
        Devices.Clear();
        // 默认只显示音频/iBasso家族设备，勾选后才列出所有键盘/鼠标等HID外设
        var list = DeviceEnumerator.Enumerate(onlyAudioFamily: !_showAllDevices);
        foreach (var d in list) Devices.Add(d);

        if (SelectedDevice is null || !Devices.Contains(SelectedDevice))
        {
            SelectedDevice = Devices.FirstOrDefault(d => d.IsIbasoFamily) ?? Devices.FirstOrDefault();
        }
    }

    private void Connect()
    {
        if (SelectedDevice is null) return;
        _session.Connect(SelectedDevice);
        Sync();
        if (IsConnected) _ = RefreshState();
    }

    private async Task RefreshState()
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await profile.RefreshAsync(State);
            Sync();
            if (State.IsWriteOnly)
            {
                Status = "已连接（写入已生效，固件无回读通道）";
            }
            else
            {
                Status = "已连接并同步状态";
            }
        }
        catch (Exception ex)
        {
            Status = $"读取状态失败: {ex.Message}";
        }
    }

    private async Task SetVolume(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await profile.SetVolumeAsync(value);
            Sync();
        }
        catch (Exception ex)
        {
            Status = $"设置音量失败: {ex.Message}";
        }
    }

    private async Task SetBalance(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await profile.SetBalanceAsync(value);
            Notify(nameof(Balance));
        }
        catch (Exception ex)
        {
            Status = $"设置平衡失败: {ex.Message}";
        }
    }

    private async Task SetDigitalFilter(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await profile.SetDigitalFilterAsync(value);
            Status = $"数字滤波已切换: {SelectedDigitalFilterOption?.Title ?? value.ToString()}";
        }
        catch (Exception ex)
        {
            Status = $"设置滤波失败: {ex.Message}";
        }
    }

    private async Task SetDsdFilter(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await profile.SetDsdFilterAsync(value);
        }
        catch (Exception ex)
        {
            Status = $"设置 DSD 滤波失败: {ex.Message}";
        }
    }

    private Task Mute() => SetVolume(0);

    private async Task Run(Func<DeviceProfile, Task> action)
    {
        var profile = Profile;
        if (profile is null) return;
        try
        {
            await action(profile);
        }
        catch (Exception ex)
        {
            Status = $"操作失败: {ex.Message}";
        }
    }

    private async Task WriteEq()
    {
        if (Profile is not Raw64Profile p) return;
        try
        {
            await p.WriteEqAsync(EqPresetSlot, Eq);
            Status = $"参数 EQ 预设 {EqPresetSlot} 已写入设备。";
        }
        catch (Exception ex) { Status = $"写入 EQ 失败: {ex.Message}"; }
    }

    private async Task ReadEq()
    {
        if (Profile is not Raw64Profile p) return;
        p.EqRead -= OnEqBand;
        p.EqRead += OnEqBand;
        try
        {
            await p.ReadEqAsync();
            Status = "参数 EQ 回读完成。";
        }
        catch (Exception ex) { Status = $"读取 EQ 失败: {ex.Message}"; }
    }

    private void OnEqBand(EqBand band)
    {
        if (band.PresetIndex != EqPresetSlot) return;
        var setting = Eq.Bands[band.BandIndex];
        setting.FilterType = band.FilterType;
        setting.Frequency = band.Frequency;
        setting.Gain = band.Gain;
        setting.Factor = band.Factor;
        Notify(nameof(Eq));
        Notify(nameof(EqCurve));
    }

    // ---------------------------------------------------------------- plumbing

    private void Sync()
    {
        IsConnected = _session.IsConnected;
        State = _session.Profile is null ? new DeviceState() : State;
        NotifyVolume();
        Notify(nameof(SelectedDigitalFilterOption));
        Notify(nameof(SelectedGainOption));
        Notify(nameof(SelectedOutputOption));
        Notify();
    }

    private void NotifyVolume()
    {
        Notify(nameof(VolumeIndex));
        Notify(nameof(VolumeDb));
        Notify(nameof(MaxVolume));
    }

    private void Append(ProtocolLogEntry entry)
    {
        var line = $"{entry.Time:HH:mm:ss.fff}  {entry.Direction,-5}  {entry.Hex}";
        if (!string.IsNullOrEmpty(entry.Note)) line += $"   {entry.Note}";
        LogText = (LogText + Environment.NewLine + line).Split('\n').TakeLast(400).Aggregate((a, b) => a + Environment.NewLine + b);
    }

    public event PropertyChangedEventHandler? PropertyChanged;

    private void Notify([CallerMemberName] string? name = null)
        => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));
}

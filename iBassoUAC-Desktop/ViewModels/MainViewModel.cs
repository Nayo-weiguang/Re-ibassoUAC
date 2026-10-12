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

public sealed class MainViewModel : INotifyPropertyChanged
{
    private readonly DeviceSession _session = new();
    private UsbDeviceInfo? _selectedDevice;
    private string _status = "未连接";
    private DeviceState _state = new();
    private string _logText = string.Empty;
    private bool _isConnected;
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
        ResetEqCommand = new RelayCommand(_ => { Eq = EqPreset.Flat(); return Task.CompletedTask; });
        WriteEqCommand = new RelayCommand(_ => WriteEq(), _ => IsConnected && Profile?.SupportsParametricEq == true);
        ReadEqCommand = new RelayCommand(_ => ReadEq(), _ => IsConnected && Profile?.SupportsParametricEq == true);

        _session.StatusChanged += s => { Status = s; };
        _session.StateChanged += Sync;
        _session.Log += Append;
        _session.NativeStateChanged += _ => Sync();

        LoadDevices();
    }

    // ------------------------------------------------------------- properties

    public ObservableCollection<UsbDeviceInfo> Devices { get; } = new();

    public UsbDeviceInfo? SelectedDevice
    {
        get => _selectedDevice;
        set { _selectedDevice = value; Notify(); (ConnectCommand as RelayCommand)?.RaiseCanExecuteChanged(); }
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
            (DisconnectCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (RefreshStateCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (WriteEqCommand as RelayCommand)?.RaiseCanExecuteChanged();
            (ReadEqCommand as RelayCommand)?.RaiseCanExecuteChanged();
        }
    }

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

    public int DigitalFilter
    {
        get => State.DigitalFilter;
        set { if (value != State.DigitalFilter) { State.DigitalFilter = value; _ = SetDigitalFilter(value); } Notify(); }
    }

    public int DsdFilter
    {
        get => State.DsdFilter;
        set { if (value != State.DsdFilter) { State.DsdFilter = value; _ = SetDsdFilter(value); } Notify(); }
    }

    public int Gain
    {
        get => State.Gain;
        set { if (value != State.Gain) { State.Gain = value; _ = Run(p => p.SetGainAsync(value)); } Notify(); }
    }

    public int Output
    {
        get => State.Output;
        set { if (value != State.Output) { State.Output = value; _ = Run(p => p.SetOutputAsync(value)); } Notify(); }
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

    /// <summary>Preview curve: frequency → dB, computed with the app's own biquad math.</summary>
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
    public RelayCommand ResetEqCommand { get; }
    public RelayCommand WriteEqCommand { get; }
    public RelayCommand ReadEqCommand { get; }

    private void LoadDevices()
    {
        Devices.Clear();
        foreach (var d in DeviceEnumerator.Enumerate()) Devices.Add(d);
        if (SelectedDevice is null && Devices.Count > 0) SelectedDevice = Devices[0];
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
                Status = "已连接，但该机型固件不提供回读通道：显示的是本机写入的值，不是设备读数。控制写入已实测生效。";
            }
        }
        catch (Exception ex)
        {
            Status = $"读取设备状态失败: {ex.Message}";
        }
    }

    private async Task SetVolume(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try { await profile.SetVolumeAsync(value); Sync(); }
        catch (Exception ex) { Status = $"设置音量失败: {ex.Message}"; }
    }

    private async Task SetBalance(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try { await profile.SetBalanceAsync(value); }
        catch (Exception ex) { Status = $"设置平衡失败: {ex.Message}"; }
    }

    private async Task SetDigitalFilter(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try { await profile.SetDigitalFilterAsync(value); }
        catch (Exception ex) { Status = $"设置数字滤波失败: {ex.Message}"; }
    }

    private async Task SetDsdFilter(int value)
    {
        var profile = Profile;
        if (profile is null) return;
        try { await profile.SetDsdFilterAsync(value); }
        catch (Exception ex) { Status = $"设置 DSD 滤波失败: {ex.Message}"; }
    }

    private Task Mute() => SetVolume(0);

    private async Task Run(Func<DeviceProfile, Task> action)
    {
        var profile = Profile;
        if (profile is null) return;
        try { await action(profile); }
        catch (Exception ex) { Status = $"操作失败: {ex.Message}"; }
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
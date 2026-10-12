# 反编译一个 APK 方法并直接打印源码
# 用法: .\rea-method.ps1 <ClassFQN> <MethodName> [overloadIndex]
param(
    [Parameter(Mandatory = $true)][string]$Class,
    [Parameter(Mandatory = $true)][string]$Method,
    [int]$Overload = 0,
    [switch]$Quiet
)

$ErrorActionPreference = 'Continue'
$env:NO_COLOR = '1'
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot'
$env:REA_JADX_MCP_JAR = 'C:\Users\18785\.minimax\sessions\mvs_0b44d98cf2df4f4badd4c444d8a329da\workspace\tools\jadx-headless-mcp\jadx-headless-mcp-0.7.1-all.jar'
$apk = 'C:\Users\18785\.minimax\sessions\mvs_0b44d98cf2df4f4badd4c444d8a329da\workspace\apk_rebuilt\ibasso-ua.apk'

$tmp = [System.IO.Path]::GetTempFileName()
$argList = @('inspect-android-method', $apk, $Class, $Method, '--format', 'json')
if ($Overload -gt 0) { $argList += @('--overload-index', "$Overload") }

& rea @argList 2>&1 | Out-File -FilePath $tmp -Encoding UTF8
$raw = Get-Content $tmp -Raw -Encoding UTF8

try {
    $r = $raw | ConvertFrom-Json
} catch {
    Write-Output "解析失败，原始输出："
    Write-Output $raw
    exit 1
}

if ($r.code) {
    Write-Output "错误: $($r.code) / $($r.message)"
    exit 1
}

$n = $r.normalized_result
Write-Output "### $($n.class_name) :: $($n.method.reported_signature)"
Write-Output "representation=$($n.representation) body_status=$($n.body_status) fell_back=$($n.fell_back) overload_count=$($n.overload_count)"
if ($n.markers) { Write-Output "markers: $($n.markers -join ', ')" }
Write-Output '----------------------------------------'
if ($n.source -is [string]) { Write-Output $n.source } else { Write-Output $n.source.text }

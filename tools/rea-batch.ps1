# Batch-decompile many methods into a directory, one JVM per method.
# Usage: .\tools\rea-batch.ps1 <OutDir> <spec1> <spec2> ...
#   spec = ClassFQN#MethodName#OverloadIndex
param(
    [Parameter(Mandatory = $true)][string]$OutDir,
    [Parameter(Mandatory = $true, ValueFromRemainingArguments = $true)][string[]]$Specs
)

$ErrorActionPreference = 'Continue'
$env:NO_COLOR = '1'
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot'
$env:REA_JADX_MCP_JAR = 'C:\Users\18785\.minimax\sessions\mvs_0b44d98cf2df4f4badd4c444d8a329da\workspace\tools\jadx-headless-mcp\jadx-headless-mcp-0.7.1-all.jar'
$apk = 'C:\Users\18785\.minimax\sessions\mvs_0b44d98cf2df4f4badd4c444d8a329da\workspace\apk_rebuilt\ibasso-ua.apk'

if (-not (Test-Path $OutDir)) { New-Item -ItemType Directory -Path $OutDir -Force | Out-Null }

foreach ($spec in $Specs) {
    $parts = $spec.Split('#')
    $cls = $parts[0]
    $meth = $parts[1]
    $ovl = 0
    if ($parts.Count -ge 3 -and $parts[2] -ne '') { $ovl = [int]$parts[2] }
    $safe = ($cls + '__' + $meth + '_' + $ovl).Replace('/', '.')
    $out = Join-Path $OutDir ($safe + '.txt')
    if (Test-Path $out) { Write-Output "SKIP $safe"; continue }

    $argList = @('inspect-android-method', $apk, $cls, $meth, '--format', 'json')
    if ($ovl -gt 0) { $argList += @('--overload-index', "$ovl") }

    $tmp = [System.IO.Path]::GetTempFileName()
    & rea @argList 2>&1 | Out-File -FilePath $tmp -Encoding UTF8
    $raw = Get-Content $tmp -Raw -Encoding UTF8

    $sb = [System.Text.StringBuilder]::new()
    try {
        $r = $raw | ConvertFrom-Json
        if ($r.code) {
            [void]$sb.AppendLine("ERROR $($r.code) / $($r.message)")
        } else {
            $n = $r.normalized_result
            [void]$sb.AppendLine("### $($n.class_name) :: $($n.method.reported_signature)")
            [void]$sb.AppendLine("representation=$($n.representation) body_status=$($n.body_status) fell_back=$($n.fell_back) overload_count=$($n.overload_count)")
            if ($n.markers) { [void]$sb.AppendLine("markers: $($n.markers -join ', ')") }
            [void]$sb.AppendLine('----------------------------------------')
            if ($n.source -is [string]) { [void]$sb.Append($n.source) } else { [void]$sb.Append($n.source.text) }
        }
    } catch {
        [void]$sb.AppendLine('PARSE-FAIL raw:')
        [void]$sb.Append($raw)
    }
    [System.IO.File]::WriteAllText($out, $sb.ToString(), (New-Object System.Text.UTF8Encoding($false)))
    Write-Output "WROTE $safe ($($sb.Length) chars)"
}
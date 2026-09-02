$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

$GitHubMcpVersion = '1.11.0'
$GitHubToolsets = 'context,repos,issues,pull_requests,users,actions,code_quality,code_security,dependabot,secret_protection'
$UserAgent = 'neoforge-rpg-skilltree-cursor-bootstrap'

function Refresh-ProcessPath {
    $machinePath = [Environment]::GetEnvironmentVariable('Path', 'Machine')
    $userPath = [Environment]::GetEnvironmentVariable('Path', 'User')
    $env:Path = "$machinePath;$userPath"
}

function Invoke-NativeChecked {
    param([Parameter(Mandatory)][string]$FilePath, [string[]]$Arguments = @())
    & $FilePath @Arguments
    if ($LASTEXITCODE -ne 0) {
        throw "Command failed with exit code $LASTEXITCODE: $FilePath $($Arguments -join ' ')"
    }
}

if ($env:OS -ne 'Windows_NT') {
    throw 'This bootstrap is intentionally Windows-only.'
}

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$mcpConfigPath = Join-Path $repoRoot '.cursor\mcp.json'
if (-not (Test-Path $mcpConfigPath)) {
    throw "Missing project MCP config: $mcpConfigPath"
}
Get-Content -Raw $mcpConfigPath | ConvertFrom-Json | Out-Null

$arch = [System.Runtime.InteropServices.RuntimeInformation]::OSArchitecture.ToString()
switch ($arch) {
    'X64'   { $assetName = 'github-mcp-server_Windows_x86_64.zip' }
    'Arm64' { $assetName = 'github-mcp-server_Windows_arm64.zip' }
    default { throw "Unsupported Windows architecture for GitHub MCP: $arch" }
}

$tempRoot = Join-Path ([IO.Path]::GetTempPath()) ('rpg-mcp-' + [guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $tempRoot -Force | Out-Null
try {
    Write-Host "[1/4] Installing official GitHub MCP Server v$GitHubMcpVersion..."
    $headers = @{ 'User-Agent' = $UserAgent; 'Accept' = 'application/vnd.github+json' }
    $releaseUrl = "https://api.github.com/repos/github/github-mcp-server/releases/tags/v$GitHubMcpVersion"
    $release = Invoke-RestMethod -Uri $releaseUrl -Headers $headers
    $asset = $release.assets | Where-Object { $_.name -eq $assetName } | Select-Object -First 1
    if (-not $asset) { throw "Release asset not found: $assetName" }

    $zipPath = Join-Path $tempRoot $assetName
    Invoke-WebRequest -Uri $asset.browser_download_url -Headers @{ 'User-Agent' = $UserAgent } -OutFile $zipPath

    $expectedHash = $null
    if ($asset.PSObject.Properties.Name -contains 'digest' -and $asset.digest) {
        $expectedHash = ([string]$asset.digest -replace '^sha256:', '').ToLowerInvariant()
    }
    if (-not $expectedHash) {
        $checksumsAsset = $release.assets | Where-Object { $_.name -eq "github-mcp-server_${GitHubMcpVersion}_checksums.txt" } | Select-Object -First 1
        if (-not $checksumsAsset) { throw 'GitHub MCP checksum metadata is unavailable.' }
        $checksumsPath = Join-Path $tempRoot 'checksums.txt'
        Invoke-WebRequest -Uri $checksumsAsset.browser_download_url -Headers @{ 'User-Agent' = $UserAgent } -OutFile $checksumsPath
        $checksumLine = Get-Content $checksumsPath | Where-Object { $_ -match [regex]::Escape($assetName) } | Select-Object -First 1
        if (-not $checksumLine) { throw "Checksum not found for $assetName" }
        $expectedHash = (($checksumLine -split '\s+')[0]).ToLowerInvariant()
    }

    $actualHash = (Get-FileHash -Path $zipPath -Algorithm SHA256).Hash.ToLowerInvariant()
    if ($actualHash -ne $expectedHash) {
        throw "GitHub MCP checksum mismatch. Expected $expectedHash, got $actualHash"
    }

    $extractDir = Join-Path $tempRoot 'github-mcp'
    Expand-Archive -Path $zipPath -DestinationPath $extractDir -Force
    $sourceExe = Get-ChildItem -Path $extractDir -Filter 'github-mcp-server.exe' -File -Recurse | Select-Object -First 1
    if (-not $sourceExe) { throw 'github-mcp-server.exe was not found in the verified archive.' }

    $installDir = Join-Path $HOME '.local\share\github-mcp-server'
    New-Item -ItemType Directory -Path $installDir -Force | Out-Null
    $githubExe = Join-Path $installDir 'github-mcp-server.exe'
    Copy-Item -Path $sourceExe.FullName -Destination $githubExe -Force
    Invoke-NativeChecked -FilePath $githubExe -Arguments @('list-scopes', "--toolsets=$GitHubToolsets", '--output=summary')

    Write-Host '[2/4] Ensuring uv is available for BetterMemory...'
    $uvCommand = Get-Command uv -ErrorAction SilentlyContinue
    if (-not $uvCommand) {
        $winget = Get-Command winget -ErrorAction SilentlyContinue
        if ($winget) {
            Invoke-NativeChecked -FilePath $winget.Source -Arguments @('install', '--id=astral-sh.uv', '-e', '--source', 'winget', '--accept-source-agreements', '--accept-package-agreements')
        } else {
            $uvInstaller = Join-Path $tempRoot 'install-uv.ps1'
            Invoke-WebRequest -Uri 'https://astral.sh/uv/0.12.9/install.ps1' -OutFile $uvInstaller
            Invoke-NativeChecked -FilePath 'powershell.exe' -Arguments @('-NoProfile', '-ExecutionPolicy', 'Bypass', '-File', $uvInstaller)
        }
        Refresh-ProcessPath
        $uvCommand = Get-Command uv -ErrorAction SilentlyContinue
        if (-not $uvCommand) {
            $uvFallback = Join-Path $HOME '.local\bin\uv.exe'
            if (Test-Path $uvFallback) { $uvCommand = Get-Item $uvFallback }
        }
    }
    if (-not $uvCommand) { throw 'uv installation completed but uv.exe could not be located.' }
    $uvExe = if ($uvCommand.PSObject.Properties.Name -contains 'Source') { $uvCommand.Source } else { $uvCommand.FullName }

    Write-Host '[3/4] Installing BetterMemory and registering it with Cursor...'
    Invoke-NativeChecked -FilePath $uvExe -Arguments @('python', 'install', '3.13')
    Invoke-NativeChecked -FilePath $uvExe -Arguments @('tool', 'install', '--python', '3.13', 'bettermemory')
    $toolBinOutput = & $uvExe tool dir --bin
    if ($LASTEXITCODE -ne 0) { throw 'Unable to resolve uv tool binary directory.' }
    $toolBin = ([string]($toolBinOutput | Select-Object -Last 1)).Trim()
    $betterMemoryExe = Join-Path $toolBin 'bettermemory.exe'
    if (-not (Test-Path $betterMemoryExe)) {
        Refresh-ProcessPath
        $bmCommand = Get-Command bettermemory -ErrorAction SilentlyContinue
        if ($bmCommand) { $betterMemoryExe = $bmCommand.Source }
    }
    if (-not (Test-Path $betterMemoryExe)) { throw 'BetterMemory installed but bettermemory.exe could not be located.' }
    Invoke-NativeChecked -FilePath $betterMemoryExe -Arguments @('init', '--client', 'cursor')

    Write-Host '[4/4] Validating BetterMemory registration...'
    & $betterMemoryExe doctor
    if ($LASTEXITCODE -ne 0) {
        Write-Warning 'BetterMemory doctor reported a remaining client-side step. Restart Cursor and run the bootstrap again if needed.'
    }

    Write-Host ''
    Write-Host 'SUCCESS: Cursor MCP tooling is installed for this project.'
    Write-Host 'Restart Cursor. The first GitHub MCP action will open GitHub in your browser once for authorization.'
    Write-Host 'Context7 and DeepWiki are already configured by the project file; no token is stored in the repository.'
}
finally {
    if (Test-Path $tempRoot) { Remove-Item -Path $tempRoot -Recurse -Force -ErrorAction SilentlyContinue }
}

# Borra la base H2 local para que el seed de demo se cargue al reiniciar el backend.
# Ejecutar desde la raíz del repo:
#   .\scripts\reset-seed.ps1
# Desde backend también funciona:
#   ..\scripts\reset-seed.ps1

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path $PSScriptRoot -Parent
$dataDir = Join-Path $projectRoot "backend\data"
$backendDir = Join-Path $projectRoot "backend"

Write-Host "Proyecto: $projectRoot"
Write-Host "Deteniendo procesos en puerto 8080 (si hay)..."
$pids = @()
foreach ($match in (netstat -ano | Select-String ":8080\s+.*LISTENING")) {
    $procId = ($match.Line -split "\s+")[-1]
    if ($procId -match "^\d+$" -and $procId -ne "0") {
        $pids += [int]$procId
    }
}
foreach ($procId in ($pids | Select-Object -Unique)) {
    try {
        Stop-Process -Id $procId -Force -ErrorAction Stop
        Write-Host "Proceso $procId detenido."
    } catch {
        Write-Host "Proceso $procId ya no estaba en ejecución."
    }
}

if (Test-Path $dataDir) {
    Remove-Item (Join-Path $dataDir "escrims.*") -Force -ErrorAction SilentlyContinue
    Write-Host "Base H2 eliminada en $dataDir"
} else {
    Write-Host "No hay carpeta data; el seed correrá en el próximo arranque."
}

Write-Host ""
Write-Host "Siguiente paso:"
Write-Host "  cd $backendDir"
Write-Host "  mvn spring-boot:run"
Write-Host ""
Write-Host "Cuenta organizador demo:"
Write-Host "  email:    org@escrims.local"
Write-Host "  password: secret123"

@echo off
setlocal
cd /d "%~dp0.."
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0setup-cursor-mcps.ps1"
set "EXITCODE=%ERRORLEVEL%"
echo.
if not "%EXITCODE%"=="0" (
  echo CONFIGURACAO NAO CONCLUIDA. Copie a mensagem de erro e envie no chat.
) else (
  echo CONFIGURACAO CONCLUIDA. Feche e abra o Cursor novamente.
)
pause
exit /b %EXITCODE%

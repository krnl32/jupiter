@echo off
pushd %~dp0\..\..\
echo Running launcher from installDist...
call launcher\build\install\launcher\bin\launcher.bat
popd
pause

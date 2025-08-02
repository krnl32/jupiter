@echo off
pushd %~dp0\..\..\
echo Running editor from installDist with args: %*
call editor\build\install\editor\bin\editor.bat "%*"
popd
pause

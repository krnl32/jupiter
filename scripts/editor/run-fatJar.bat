@echo off
pushd %~dp0\..\..\
echo Running editor from Fat Jar with args: %*
call java -jar editor\build\libs\editor-all.jar "%*"
popd
pause

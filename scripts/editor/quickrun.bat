@echo off
pushd %~dp0\..\..\
echo Running editor with args: %*...
call gradlew.bat :editor:run --args="%*"
popd
pause

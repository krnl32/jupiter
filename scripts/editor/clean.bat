@echo off
pushd %~dp0\..\..\
echo Cleaning editor...
call gradlew.bat :editor:clean
popd
pause

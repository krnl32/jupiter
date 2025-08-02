@echo off
pushd %~dp0\..\..\
echo Building Distribution Zip for editor...
call gradlew.bat :editor:distZip
popd
pause

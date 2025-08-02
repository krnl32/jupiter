@echo off
pushd %~dp0\..\..\
echo Building Distribution Zip for launcher...
call gradlew.bat :launcher:distZip
popd
pause

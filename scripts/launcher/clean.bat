@echo off
pushd %~dp0\..\..\
echo Cleaning launcher...
call gradlew.bat :launcher:clean
popd
pause

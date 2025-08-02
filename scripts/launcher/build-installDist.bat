@echo off
pushd %~dp0\..\..\
echo Building installDist Runnable Folder for launcher...
call gradlew.bat :launcher:installDist
popd
pause

@echo off
pushd %~dp0\..\..\
echo Building installDist Runnable Folder for editor...
call gradlew.bat :editor:installDist
popd
pause

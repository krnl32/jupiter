@echo off
pushd %~dp0\..\
@REM Clean Editor
echo Cleaning editor...
call gradlew.bat :editor:clean

@REM Clean Launcher
echo Cleaning launcher...
call gradlew.bat :launcher:clean
popd
pause

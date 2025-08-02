@echo off
pushd %~dp0\..\
@REM Build Editor
echo Building Fat Jar for editor...
call gradlew.bat :editor:shadowJar

@REM Build Launcher
echo Building Fat Jar for launcher...
call gradlew.bat :launcher:shadowJar
popd
pause

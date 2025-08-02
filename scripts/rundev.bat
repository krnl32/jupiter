@echo off
pushd %~dp0\..\
echo Running Editor...
call gradlew.bat :editor:run --args="--launch %CD%\editor\Sandbox"
popd
pause

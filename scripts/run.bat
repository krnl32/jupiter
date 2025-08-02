@echo off
pushd %~dp0\..\
echo Running launcher...
call gradlew.bat :launcher:run
popd
pause

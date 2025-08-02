@echo off
pushd %~dp0\..\..\
echo Building Fat Jar With ShadowJar for launcher...
call gradlew.bat :launcher:shadowJar
popd
pause

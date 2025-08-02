@echo off
pushd %~dp0\..\..\
echo Building Fat Jar With ShadowJar for editor...
call gradlew.bat :editor:shadowJar
popd
pause

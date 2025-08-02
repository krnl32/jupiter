@echo off
pushd %~dp0\..\..\
echo Building Plain Jar for editor...
call gradlew.bat :editor:jar
popd
pause

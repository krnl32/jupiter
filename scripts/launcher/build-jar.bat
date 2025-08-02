@echo off
pushd %~dp0\..\..\
echo Building Plain Jar for launcher...
call gradlew.bat :launcher:jar
popd
pause

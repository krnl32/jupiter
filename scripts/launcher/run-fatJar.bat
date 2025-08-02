@echo off
pushd %~dp0\..\..\
echo Running launcher from Fat Jar...
call java -jar launcher\build\libs\launcher-all.jar
popd
pause

@echo off
pushd %~dp0\..\..\
echo Running launcher from Fat Jar...
call java -jar launcher\build\libs\jupiter-launcher.jar
popd
pause

@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "JAR_PATH=%~dp0out\artifacts\Verkehrssimulation_jar\Verkehrssimulation.jar"
set "TEST_ROOT=%~dp0src\main\resources\input"

if not exist "%JAR_PATH%" (
    echo Fehler: Jar nicht gefunden: %JAR_PATH%
    pause
    exit /b 1
)

if not exist "%TEST_ROOT%" (
    echo Fehler: Test_Root nicht gefunden: %TEST_ROOT%
    pause
    exit /b 1
)

echo Starte alle Testfaelle unter: %TEST_ROOT%
echo.

for /d %%D in ("%TEST_ROOT%\*") do (
    for %%F in ("%%~fD\*.txt") do (
        java -jar "%JAR_PATH%" "%%~fF"
        if errorlevel 1 (
            echo Fehler beim Lauf: %%~fF
        )
    )
)

echo.
echo Fertig.
pause
endlocal

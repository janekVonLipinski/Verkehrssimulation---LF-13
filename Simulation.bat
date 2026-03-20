@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "JAR_PATH=%~dp0out\artifacts\Verkehrssimulation_jar\Verkehrssimulation.jar"
set "TEST_ROOT=%~dp0src\main\resources\input"
set "OUTPUT_ROOT=%~dp0src\main\resources\output"

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

if not exist "%OUTPUT_ROOT%" (
    echo Fehler: Output_Root nicht gefunden: %OUTPUT_ROOT%
    pause
    exit /b 1
)

echo Starte alle Testfaelle unter: %TEST_ROOT%
echo.

for /d %%D in ("%TEST_ROOT%\*") do (
    set "TEST_NAME=%%~nD"
    set "TEST_OUTPUT_DIR=%OUTPUT_ROOT%\!TEST_NAME!"

    if not exist "!TEST_OUTPUT_DIR!" (
        mkdir "!TEST_OUTPUT_DIR!"
    )

    java -jar "%JAR_PATH%" "%%~fD\Eingabe.txt" "!TEST_OUTPUT_DIR!"
    if errorlevel 1 (
        echo Fehler beim Lauf: %%~fD\Eingabe.txt
    )
)

echo.
echo Fertig.
pause
endlocal

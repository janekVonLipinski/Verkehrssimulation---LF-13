@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "JAR_PATH=%~dp0TestRunner.jar"
set "TEST_ROOT=%~dp0tests"

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
    echo == Testfall: %%~fD
    for %%F in ("%%~fD\*.txt") do (
        echo ^> %%~fF
        java -jar "%JAR_PATH%" "%%~fF"
        if errorlevel 1 (
            echo Fehler beim Lauf: %%~fF
            REM Wenn du bei Fehlern abbrechen willst, naechste Zeile aktivieren:
            REM exit /b 1
        )
    )
)

echo.
echo Fertig.
pause
endlocal

@ECHO off
TITLE Aion Lightning - Game Server Console
:START
CLS
SET JAVAVER=1.7
SET NUMAENABLE=false
CLS
IF "%MODE%" == "" (
CALL PanelGS.bat
)

IF "%JAVAVER%" == "1.7" (
SET JAVA_OPTS=-XX:+TieredCompilation %JAVA_OPTS%
)
IF "%NUMAENABLE%" == "true" (
SET JAVA_OPTS=-XX:+UseNUMA %JAVA_OPTS%
)
ECHO Starting Aion Lightning Game Server in %MODE% mode.
JAVA %JAVA_OPTS% -ea -javaagent:./libs/al-commons-1.3.jar -cp ./libs/*;AL-Game.jar com.aionemu.gameserver.GameServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Game Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Game Server is terminated!
ECHO.
PAUSE
EXIT
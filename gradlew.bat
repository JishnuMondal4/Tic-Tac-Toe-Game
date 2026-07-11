@rem Gradle startup script for Windows
@rem Standard wrapper script. If the wrapper jar is missing, open the project
@rem in Android Studio and choose "Sync Project with Gradle Files" to have it
@rem regenerated automatically.

@echo off
set DIRNAME=%~dp0
set APP_HOME=%DIRNAME%
set CLASSPATH=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

java -Xmx64m -Xms64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

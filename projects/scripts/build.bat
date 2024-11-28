::@echo off
:: > Setup required Environment
:: -------------------------------------
set COMPILER_DIR=/usr/in/gcc
set PATH=%PATH%;%COMPILER_DIR%
cd %~dp0
:: .
:: > Compile simple .rc file
:: ----------------------------
cmd /c windres ..\..\src\jumping-runner.rc -o ..\..\src\jumping-runner.rc.data
:: .
:: > Generating project
:: --------------------------
cmd /c mingw32-make -f ..\..\src\Makefile ^
PROJECT_NAME=jumping-runner ^
PROJECT_VERSION=1.0 ^
PROJECT_DESCRIPTION="Jumping Runner" ^
PROJECT_INTERNAL_NAME=jumping-runner ^
PROJECT_PLATFORM=PLATFORM_DESKTOP ^
PROJECT_SOURCE_FILES="jumping-runner.c" ^
BUILD_MODE="RELEASE" ^
BUILD_WEB_ASYNCIFY=FALSE ^
BUILD_WEB_MIN_SHELL=TRUE ^
BUILD_WEB_HEAP_SIZE=268435456 ^
RAYLIB_MODULE_AUDIO=TRUE ^
RAYLIB_MODULE_MODELS=TRUE

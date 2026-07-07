@echo off
REM ================================================
REM MO GitHub Setup - Windows Version
REM ================================================

echo.
echo ============================================
echo    MO GitHub Setup Script
echo ============================================
echo.

REM تأكد إننا داخل مجلد المشروع
if not exist "build.gradle.kts" (
    echo ERROR: شغّل السكريبت من داخل مجلد mo-android
    pause
    exit /b 1
)

set /p GITHUB_USER="GitHub username: "
set /p REPO_NAME="Repo name (default: mo-android): "
if "%REPO_NAME%"=="" set REPO_NAME=mo-android

echo.
echo سترفع على: https://github.com/%GITHUB_USER%/%REPO_NAME%
pause

REM Init git
if not exist ".git" (
    git init
    git branch -M main
)

REM تأكد من .gitignore
findstr /c:"local.properties" .gitignore >nul || echo local.properties >> .gitignore

REM Remote
git remote remove origin 2>nul
git remote add origin https://github.com/%GITHUB_USER%/%REPO_NAME%.git

REM Add + commit
git add .
git commit -m "Initial MO app commit"

echo.
echo ============================================
echo الخطوات التالية:
echo ============================================
echo 1. اعمل repo جديد على https://github.com/new
echo 2. اسم الـ repo: %REPO_NAME%
echo 3. لا تضف README أو .gitignore
echo 4. ارجع هنا واضغط أي مفتاح للـ push
echo ============================================
pause

git push -u origin main

echo.
echo تم! افتح الرابط في المتصفح:
echo https://github.com/%GITHUB_USER%/%REPO_NAME%
pause
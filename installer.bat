@echo off
setlocal enabledelayedexpansion

:: 检查是否以管理员身份运行
NET SESSION >nul 2>&1
if %errorLevel% neq 0 (
    echo 请以管理员身份运行此脚本！
    pause
    exit /b 1
)

:: 设置安装目录
set "INSTALL_DIR=C:\Program Files"
set "JDK_DIR=!INSTALL_DIR!\Java\jdk-17"
set "MYSQL_DIR=!INSTALL_DIR!\MySQL\MySQL Server 8.0"
set "DOWNLOAD_DIR=%TEMP%"

:: 下载链接 (请根据实际情况更新为最新链接)
set "JDK_URL=https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe"
set "MYSQL_URL=https://dev.mysql.com/get/Downloads/MySQL-8.0/mysql-8.0.33-winx64.msi"

:: 文件名
set "JDK_EXE=jdk-17_windows-x64_bin.exe"
set "MYSQL_MSI=mysql-8.0.33-winx64.msi"

echo ==============================================
echo 开始安装JDK 17和MySQL
echo 安装目录: !INSTALL_DIR!
echo ==============================================

:: 创建下载目录
if not exist "!DOWNLOAD_DIR!" mkdir "!DOWNLOAD_DIR!"

:: 下载JDK 17
echo 正在下载JDK 17...
if not exist "!DOWNLOAD_DIR!\!JDK_EXE!" (
    powershell -Command "(New-Object Net.WebClient).DownloadFile('!JDK_URL!', '!DOWNLOAD_DIR!\!JDK_EXE!')"
    if %errorLevel% neq 0 (
        echo JDK下载失败，请检查网络连接或下载链接是否有效
        pause
        exit /b 1
    )
)

:: 安装JDK 17
echo 正在安装JDK 17...
"!DOWNLOAD_DIR!\!JDK_EXE!" /s INSTALLDIR="!JDK_DIR!" NOSTARTMENU=1 ADDLOCAL="ToolsFeature,SourceFeature,PublicjreFeature"

:: 配置JDK环境变量
echo 配置JDK环境变量...
setx /M JAVA_HOME "!JDK_DIR!"
setx /M PATH "%%JAVA_HOME%%\bin;%%PATH%%"

:: 下载MySQL
echo 正在下载MySQL...
if not exist "!DOWNLOAD_DIR!\!MYSQL_MSI!" (
    powershell -Command "(New-Object Net.WebClient).DownloadFile('!MYSQL_URL!', '!DOWNLOAD_DIR!\!MYSQL_MSI!')"
    if %errorLevel% neq 0 (
        echo MySQL下载失败，请检查网络连接或下载链接是否有效
        pause
        exit /b 1
    )
)

:: 安装MySQL
echo 正在安装MySQL...
msiexec /i "!DOWNLOAD_DIR!\!MYSQL_MSI!" /quiet INSTALLDIR="!MYSQL_DIR!" ROOTPASSWORD="root" ADDLOCAL=All

:: 配置MySQL环境变量
echo 配置MySQL环境变量...
setx /M MYSQL_HOME "!MYSQL_DIR!"
setx /M PATH "%%MYSQL_HOME%%\bin;%%PATH%%"

:: 启动MySQL服务
echo 启动MySQL服务...
net start MySQL80

:: 验证安装
echo ==============================================
echo 验证安装结果:

echo 检查JDK版本...
java -version
if %errorLevel% equ 0 (
    echo JDK 17 安装成功
) else (
    echo JDK 17 安装失败
)

echo 检查MySQL版本...
mysql --version
if %errorLevel% equ 0 (
    echo MySQL 安装成功
) else (
    echo MySQL 安装失败
)

echo ==============================================
echo 安装完成！
echo JDK安装路径: !JDK_DIR!
echo MySQL安装路径: !MYSQL_DIR!
echo MySQL默认密码: root
echo 请重启命令行窗口使环境变量生效
pause

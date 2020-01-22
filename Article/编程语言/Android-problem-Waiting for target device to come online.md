# Android-Problem-"Waiting for target device to come online".

<<<<<<< HEAD
**环境**：win10专业版（创意者），Android studio 2.3.1  

**问题描述**：安装玩Android studio之后创建一个项目，建立AVD之后，运行程序时一直不能启动AVD，具体描述为：``"Waiting for target device to come online"。即“等待目标设备在线”``。  

**问题分析：**  
1.此处参考了Android 中 adb 相关问题http://blog.csdn.net/Jack_windows/article/details/64503132一文，出现此问题的原因大多是因为adb的端口被占用（eclipse，模拟器等）。  

2.也可能有这种问题，博主就是这种问题，参考了“waiting for target device to come online” in Android Studio 2.3 http://stackoverflow.com/questions/42757928/waiting-for-target-device-to-come-online-in-android-studio-2-3 一文  

**问题解决：**  
> 分析1：命令行（win+r--cmd）定位到android_sdk/platform-tools/目录下,执行"adb kill-server"  

> 分析2：I was also having the same issue 2 days ago when i update my android studio, today I solve my issue when I was playing around with settings then I saw that in my SDK tools setting Android Emulator is uncheck so I simply checked that box and now emulator is working fine.
> Try this steps may work for you also:
> Go to SDK tools > SDK Tools
> Check Android Emulator and click Apply
=======
环境：win10专业版（创意者），Android studio 2.3.1  
问题描述：安装玩Android studio之后创建一个项目，建立AVD之后，运行程序时一直不能启动AVD，具体描述为："Waiting for target device to come online"。即“等待目标设备在线”。  
问题分析：1.此处参考了Android 中 adb 相关问题http://blog.csdn.net/Jack_windows/article/details/64503132一文，出现此问题的原因大多是因为adb的端口被占用（eclipse，模拟器等）。  
          2.也可能有这种问题，博主就是这种问题，参考了“waiting for target device to come online” in Android Studio 2.3 http://stackoverflow.com/questions/42757928/waiting-for-target-device-to-come-online-in-android-studio-2-3 一文  
问题解决：分析1：命令行（win+r--cmd）定位到android_sdk/platform-tools/目录下,执行"adb kill-server"  
          分析2：I was also having the same issue 2 days ago when i update my android studio, today I solve my issue when I was playing around with settings then I saw that in my SDK tools setting Android Emulator is uncheck so I simply checked that box and now emulator is working fine.  

Try this steps may work for you also:  

Go to SDK tools > SDK Tools  
Check Android Emulator and click Apply  

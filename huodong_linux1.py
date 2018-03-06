# -*- coding: utf-8 -*-
import os,sys,threading
from time import sleep, ctime
import subprocess
import time

def runtestcase(devicename):
    timeout=1200
    adbCommand("adb -s",devicename,"install -r "+apkpath)
    #安装微信
    adbCommand("adb -s",devicename,"shell am start -n com.example.unlock/.Unlock")


def adbCommand(command,device,args):
    """
    执行adb命令
    """
    cmd = "%s %s %s" % (command, device, args)
    print(cmd)
    r = os.popen(cmd)
    info = r.read()    
    print(info,' '+device)
    return info
    
def deviceslist():
    cmd="adb devices"
    r = os.popen(cmd)
    info = r.read()
    info = info[25:]
    info = info.strip()
##    print(info)
    deviceslist = info.split("device")
##    print(deviceslist)
    devices=[]
    for i in deviceslist:
        if(i!=''):
            i=i.replace('\r','').replace('\n','').replace('\t','')
            devices.append(i)
    print(devices)
    return devices

class MyThread(threading.Thread):
    def __init__(self,device):
        threading.Thread.__init__(self)
        self.device = device
    def run(self):
        runtestcase(self.device)
                
if __name__ == '__main__':
    print("===开始测试===")
    apkpath="/tmp/weixin.apk"
    unlockapkpath="/tmp/HuoDong/unlock_apk-debug.apk"
    threads = []
    #获取设备列表
    deviceslist=deviceslist()    
    print("程序开始运行%s" % ctime())
    for devicename in deviceslist:
##        for i in range(len(deviceslist)):
        print(devicename)
        th=MyThread(devicename)
        th.start()
        threads.append(th)
        # 等待线程运行完毕
    for th in threads:
        th.join()
         
    print("程序结束运行%s" % ctime())

   

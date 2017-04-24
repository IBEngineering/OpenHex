#!/usr/bin/python
import os
import platform

def call():
    """
    Currently has Linux and Windows support.
    """
    print(platform.system())
    if platform.system() == 'Linux':
        callLinux
    elif platform.system() == 'Windows':
        callWindows()

def callLinux():
    os.system("gradle build ; ./gradlew run")

def callWindows():
    os.system("gradle build")
    os.system("gradlew.bat")

if __name__ == '__main__':
    call()

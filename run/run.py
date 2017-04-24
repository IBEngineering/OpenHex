#!/usr/bin/python
from os import *

def doGradleThings():
    """
    This doesn't check if it's in the right directory.
    It simply assumes it can call ./gradlew.
    It also won work on windows.
    """
    system("gradle build ; ./gradlew run")

if __name__ == '__main__':
    doGradleThings();

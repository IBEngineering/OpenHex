#!/usr/bin/python

import sys
from os import *

DEBUG = False

def printInfo(info):
    if DEBUG:
        print('[Run.py] %s' % info)

def execJava(path):
    system('java -jar %s' % path)

if __name__ == '__main__':
    printInfo("Running with args: %s" % sys.argv)
    printInfo("Argument count: %d" % len(sys.argv))
    if len(sys.argv) == 1:
        if sys.argv[0] == 'help':
            printInfo("Use commands like this:")
            printInfo("run.py <ignored> <file>")
    elif len(sys.argv) == 2:
        printInfo("Running java file %s ..." % sys.argv[1])
        execJava(sys.argv[1])
        printInfo("Done!")

#!/bin/sh
javac -encoding gbk /root/design/uwaparking_version4.java
java /root/design/uwaparking_version4
echo $! > /root/design/uwaparking.pid

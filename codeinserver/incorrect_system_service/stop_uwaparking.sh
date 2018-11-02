#!/bin/sh
PID=$(cat /root/design/uwaparking.pid)
kill -9 $PID
rm -fr /root/design/uwaparking.pid

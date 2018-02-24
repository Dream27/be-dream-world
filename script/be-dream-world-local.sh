#!/usr/bin/env bash
# Desc: Script to start, stop or restart service.
# Author: Zhou Shengsheng
# Date: 24/02/18
# Version: 0.0.1

#====================================================
# Configurations
#====================================================

# jdk
JAVA_HOME=/home/dream27/3rd/jdk1.8.0_162

# service config
SERVICE_DIR=/home/dream27/workspace/dream-world
SERVICE_FULL_NAME=be-dream-world
SERVICE_JAR_FILE=${SERVICE_DIR}/${SERVICE_FULL_NAME}.jar
SERVICE_PID_FILE=${SERVICE_DIR}/${SERVICE_FULL_NAME}.pid

# jvm config
JVM_ARGS="-Xms512m -Xmx512m"
PROFILE="local"

# shell config
set -o pipefail
set -o nounset

#====================================================
# Functions
#====================================================

function printUsage() {
    echo "Usage: $0 [start|stop|restart]"
}

function startService() {
    echo "===> Starting ${SERVICE_FULL_NAME}"
    nohup ${JAVA_HOME}/bin/java ${JVM_ARGS} -jar -Dspring.profiles.active=${PROFILE} ${SERVICE_JAR_FILE} >/dev/null 2>&1 &
    echo $! > ${SERVICE_PID_FILE}
}

function stopService() {
    echo "===> Stopping ${SERVICE_FULL_NAME}"
    kill `cat ${SERVICE_PID_FILE} 2> /dev/null` 2> /dev/null
    rm -rf ${SERVICE_PID_FILE}

    maxTimeout=30 # in seconds
    servicePid=-1
    startTime=`date +%s`

    while [[ -n ${servicePid} ]];
    do
        servicePid=`ps -ef | grep -w "${SERVICE_JAR_FILE}" | grep -v "grep" | awk '{print $2}'`
        if [[ -n ${servicePid} ]]; then
            kill ${servicePid} 2> /dev/null
            sleep 1
            curTime=`date +%s`
            timeout=$(( ${curTime} - ${startTime} ))
            if [[ ${timeout} -ge ${maxTimeout} ]]; then
                kill -9 ${servicePid} 2> /dev/null
                servicePid=""
            fi
        fi
    done
}

function restartService() {
    stopService
    startService
}

#====================================================
# Checks
#====================================================

# check jre
if [[ ! -x ${JAVA_HOME}/bin/java ]]; then
    >&2 echo "Jre checking failure"
    exit 1
fi

# check service jar file
if [[ ! -r ${SERVICE_JAR_FILE} ]]; then
    >&2 echo "Jar file ${SERVICE_JAR_FILE} checking failure"
    exit 1
fi

#====================================================
# Service start, stop and restart
#====================================================

cd ${SERVICE_DIR}

if [[ $# -eq 0 ]]; then
    restartService
else
    case "$1" in
        start)
            startService
            ;;
        stop)
            stopService
            ;;
        restart)
            restartService
            ;;
        -h)
            printUsage
            ;;
        *)
            >&2 echo "invalid argument"
            printUsage
            exit 1
            ;;
esac
fi

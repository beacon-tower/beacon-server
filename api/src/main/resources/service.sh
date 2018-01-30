#!/bin/bash

SELF=$(cd $(dirname $0); pwd -P)/$(basename $0)
null_buffer=/dev/null
should_exit=./flag.exit
pid_file=./flag.pid
log=app.log
wait_time=5
application='.:../lib/* com.beacon.Application'

#检查进程是否存在,传入进程号
check_process() {
    if [ "$1" = "" ]; then
        return 1
    fi

    kill -0 $1 &> $null_buffer
    if [ $? -eq 0 ]; then
        return 0
    else
        return 1
    fi
}

# 等待进程退出
wait_process() {
    while true;
    do
        kill -0 $1 &> $null_buffer
        if [ $? != 0 ]; then
            return
        fi

        sleep 0.5s
    done
}

#等待进程退出,$1为进程PID，$2为最长等待时间单位s
wait_for_sometime() {
    local wait=0
    kill -0 $1 &> $null_buffer
    while ( [ $? = 0 ] && [ $wait -lt $2 ] ); do
        sleep 1s
        wait=$((wait+1))
        kill -0 $1 &> $null_buffer
    done

    kill -0 $1 &> $null_buffer
    if [ $? -ne 0 ]; then
        return 0
    else
        return 1
    fi
}
#启动服务程序
service_start() {
    if [[ "r" = "$2" ]]; then
        echo "Start server by remote"
        JAVA_MEM_OPTS = " -server -Xmx1g -Xms1g -Xmn200m -XX:PermSize=50m -Xss512k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=28m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
        nohup java ${JAVA_MEM_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5013 -Dspring.profiles.active="$1" -cp ${application} >$null_buffer 2>&1 &
    else
        echo "Start server"
        nohup java -Dspring.profiles.active="$1" -cp ${application} >$null_buffer 2>&1 &
    fi
}

#关闭服务程序
service_stop() {
    echo "Stop server"
    local checkResult=1
    if [ -f $pid_file ]; then
        check_process `cat $pid_file`
        checkResult=$?
    else
        echo "Have no process file"
        return 1
    fi

    if [ $checkResult -eq 1 ]; then
        echo "$1 has been stopped, skip!!!"
        return 0
    fi

    local PID=$(cat "$pid_file")
    touch "$should_exit"

    #等待进程退出
    wait_for_sometime $PID $wait_time
    if [ $? -ne 0 ]; then
        echo "service $1 does't exit in 5s, try to kill its process"
        kill -9 $PID &> $null_buffer
    fi

    wait_process $PID

    #删除进程文件
    rm -f $pid_file &> $null_buffer
    rm -f $should_exit &> $null_buffer

    echo "Server $PID has exit!!!"
}

#查看服务程序状态
service_status() {
    local checkResult=1
    if [ -f $pid_file ]; then
        check_process `cat $pid_file`
        checkResult=$?
    else
        echo "Have no process file"
        return 1
    fi

    if [ $checkResult -eq 1 ]; then
        echo "Program not running!!!"
        return 1
    else
        echo "Program is running!!!"
        return 0
    fi
}


case "${1:-''}" in
    "start")
        service_start $2 $3
        ;;
    "stop")
        service_stop
        ;;
    "restart")
        echo "restart server"
        service_stop
        service_start $2 $3
        ;;
    "status")
        service_status
        ;;
    *)
        echo "Usage: $SELF start test r|stop|restart|status"
        exit 1
        ;;
esac

exit 0
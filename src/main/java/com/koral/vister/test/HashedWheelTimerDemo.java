package com.koral.vister.test;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class HashedWheelTimerDemo {
    public static void main(String[] args) {
        System.out.println("主线程：" + Thread.currentThread().getName() + "::" + Thread.currentThread().getId());
        Timer timer = new HashedWheelTimer();
        Timeout timeout = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("执行timeout的线程" + Thread.currentThread().getName() + "::" + Thread.currentThread().getId());
                System.out.println("5秒后执行该任务");
            }
        }, 5, TimeUnit.SECONDS);
        System.out.println("hahha");

        Timeout timeout2 = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("10秒后执行了该任务");
            }
        }, 10, TimeUnit.SECONDS);

        System.out.println("hahha2");
        // 取消这个任务
        if (!timeout.isExpired()) {
            System.out.println("取消了这个任务");
            timeout.cancel();
        }
        System.out.println("hahha3");

        // 3秒后执行
        timer.newTimeout(timeout.task(),3,TimeUnit.SECONDS );
    }
}

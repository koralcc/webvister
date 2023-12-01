package com.koral.vister.test;

import java.util.concurrent.locks.LockSupport;

public class DeamonThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程一执行");
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            System.out.println("----zizi线程执行-----");
                        }
                    }
                });
                thread2.setDaemon(true);
                thread2.start();
                for (int i = 0; i < 1000000; i++) {
                    System.out.println("zi线程执行");
                }
                Thread.currentThread().interrupt();
            }
        });
        thread1.start();
        Thread.sleep(1000);
        thread1.stop();
        System.out.println("子线程结束");
        Thread.sleep(1000000);
        System.out.println("主线程结束1");
    }
}

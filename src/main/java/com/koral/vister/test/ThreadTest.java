package com.koral.vister.test;

public class ThreadTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程运行中");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("子线程执行结束");
            }
        }).start();
        System.out.println("主线程执行结束");
    }
}

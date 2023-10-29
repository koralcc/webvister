package com.koral.vister.test;

public class Testmain {
    public static void main(String[] args) {
        System.out.println("主线程" + TenantContext.getTenantCodeHolder());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程：" + TenantContext.getTenantCodeHolder());
            }
        });
        thread.start();
    }
}

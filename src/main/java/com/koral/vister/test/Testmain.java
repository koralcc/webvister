package com.koral.vister.test;

public class Testmain {
    public static void main(String[] args) {
        // 打印主线程的租户代码
        System.out.println("主线程" + TenantContext.getTenantCodeHolder());

// 创建新线程并启动
        Thread thread = new Thread(Testmain::run);
        thread.start();
    }

    private static void run() {
        System.out.println("子线程：" + TenantContext.getTenantCodeHolder());
    }
}

package com.koral.vister.test;

import java.util.concurrent.locks.LockSupport;

public class GCTest {
    byte[] bt = new byte[1024 * 1024 * 10];
    public static void main(String[] args) {
        System.out.println("GCTest");
        LockSupport.park();
    }
}

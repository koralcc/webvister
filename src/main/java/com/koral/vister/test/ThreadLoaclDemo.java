package com.koral.vister.test;

public class ThreadLoaclDemo {


    public static void main(String[] args) {
        /**
         * Java code to demonstrate the usage of ThreadLocal class.
         * The code sets a value in the ThreadLocal variable "tl" as "hello",
         * creates a new thread and sets a different value in "tl" as "world",
         * and finally removes the value from "tl".
         */
        try {
            Mytl.tl.set("hello");
            Thread tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    Mytl.tl.set("world");
                }
            });
            tr.start();
        } catch (Exception e) {
        } finally {
            Mytl.tl.get();
            Mytl.tl.remove();
        }

    }
}

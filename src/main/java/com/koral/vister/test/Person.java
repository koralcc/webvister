package com.koral.vister.test;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;

public class Person {
    short age;
    String name;
//    byte[] atr = new byte[1024 * 1024 * 200];

    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
//        ArrayList<Person> people = new ArrayList<>();
//        while (true){
//            people.add(new Person());
//        }

    }
}

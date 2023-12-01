package com.koral.vister.test;

import freemarker.template.TemplateHashModelEx2;
import io.netty.util.HashedWheelTimer;

import java.util.HashMap;

public class MapDemo {
    public static void main(String[] args) {
        String zs = "张三";
        HashMap<String, Integer> nameToAgeMap = new HashMap<>();
        nameToAgeMap.put(zs,18);
        System.out.println(nameToAgeMap.get(zs));
        // 存在就不覆盖
        Integer integer = nameToAgeMap.putIfAbsent(zs, 28);
        System.out.println(nameToAgeMap.get(zs));
        System.out.println(integer);
        Integer integer1 = nameToAgeMap.putIfAbsent("李四", 100);
        System.out.println(integer1);

    }
}

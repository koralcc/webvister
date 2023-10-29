package com.koral.vister.test;

import java.util.Arrays;
import java.util.function.Function;

public class LambdaDemo {
    public static void main(String[] args) {
        System.out.println(LambdaDemo.getSetName().apply("hello"));
        System.out.println(LambdaDemo.getSetName3().apply("hello","hello2","hello3"));
    }

    public static Function<String,String> getSetName(){
// 相当于apply方法里面穿就是调用的set方法
        //////////////////1/////////////////
//        return new Function<String,String>() {
//            @Override
//            public String apply(String s) {
//                return LambdaDemo.setName(s);
//            }
//        };
        //////////////////2/////////////
          // return s -> LambdaDemo.setName(s);
        //////////////////3////////////////////////
        return LambdaDemo::setName; // 入参与返回参数与 Function一致的都能这样写
    }

    public static String setName(String name){
        return name;
    }

    //可以这样写的前提是，必须 LambdaDemo::setName3与ThreeFunction的入参和出参类型一摸一样
    public static ThreeFunction<String,String,String,String> getSetName3(){
        return  LambdaDemo::setName3;
    }

    // 参数对应的是名字
    public static String setName3(String a,String b,String c){
        return a + b + c;
    }

}

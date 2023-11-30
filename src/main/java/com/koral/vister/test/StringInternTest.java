package com.koral.vister.test;

public class StringInternTest {
    public static void main(String[] args) {

    }
    public  int add(int a,int b){
        int c=0;
        c=a+b;
        return c;
    }

    public void strTest(){
        String a = "a";
        String b = "aa" + "bb";
        String c = new String("abc" + "ccc");

        String d = new String("abc") +"ddd";
    }
}

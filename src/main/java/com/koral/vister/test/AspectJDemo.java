package com.koral.vister.test;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

public class AspectJDemo {
    @Component
    static class A {
        public void f(){
            System.out.println("aaaaaaaaa");
        }
    }

    //切面类
    @Aspect
    @Component
    // 切面类
    static class Aop{
        @Pointcut("execution(* com.koral.vister.test..*.*(..))")
        private void pointcut(){}

        @After("pointcut()")
        public void advice(){
            System.out.println("之后增强......");
        }
    }
}

package com.koral.vister.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class CircleReferenceDemo {
    @Component
    static class DependA implements InitializingBean {
        @Value("${depend.name:A}")
        private String name;

        private int age;

        @Autowired
        private DependD dependD;

        @Resource
        private DependE dependE;
//        @Autowired
//        private DependB dependB;


        public void printA(){
            System.out.println("AAAAAAAAA");
        }

        @PostConstruct
        public void initAfter(){
            System.out.println("做了一些事情");
            System.out.println("调用了postConstruct方法");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("调用了初始化方法");
        }
    }
    @Component
    static class DependB{
        @Autowired
        private DependA dependA;
    }

    @Component
    static class DependC{
        @Autowired
        private DependA dependA;

        public void printC(){
            System.out.println("AAAAAAAAA");
        }
    }

    @Component
    static class  DependD{

    }

    @Component("dependE")
    static class DependE{}


}

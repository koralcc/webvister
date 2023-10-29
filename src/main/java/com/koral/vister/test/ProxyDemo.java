package com.koral.vister.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        System.out.println("**********************JDK Proxy********************************");
        jdkProxy();
        System.out.println("**********************CGLIB Proxy******************************");
        cglibProxy();
    }

    private static void cglibProxy() {
        // 通过CGLIB动态代理获取代理对象的过程
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类
        Enhancer enhancer = new Enhancer();
        // 设置目标类的字节码文件
        enhancer.setSuperclass(Student.class);
        // 设置回调函数
        enhancer.setCallback(new MyInterceptor());

        Student stu = (Student) enhancer.create();
        stu.studing();
    }

    private static void jdkProxy() {
        Tenent tenent = new Tenent();
        tenent.setName("koral");
        Person te = (Person)Proxy.newProxyInstance(Tenent.class.getClassLoader(), new Class[]{Person.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // proxy 代表代理对象
                // method 代表正在执行的方法
                // args 代表参数
                System.out.println("代理方法调用前");
                Object invoke = method.invoke(tenent, args);
                System.out.println("代理方法调用后");
                return invoke;
            }
        });
        te.sleep();
        te.eat();
    }

    static class Tenent implements Person{

        private String name;

        @Override
        public void eat() {
            System.out.println(this.getName()+"tenent 吃饭");
        }

        @Override
        public void sleep() {
            System.out.println("tenent 睡觉"+this.getName());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class Student{

        public void studing(){
            System.out.println("学生学习...");
        }

        private String name;
        private String age;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    static class MyInterceptor implements MethodInterceptor{

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            before(method.getName());
//            obj：表示要进行增强的对象；
//            method：表示要被拦截的方法；
//            objects：表示要被拦截方法的参数；
//            methodProxy：表示要触发父类的方法对象。
            // 注意这里是调用invokeSuper而不是invoke，否则死循环
            // methodProxy.invokeSuper执行的是原始类的方法；
            // method.invoke执行的是子类的方法
            Object result = methodProxy.invokeSuper(o, objects);
            after(method.getName());
            return result;
        }

        private void after(String name) {
            System.out.println("调用方法" + name +"之【后】的日志处理");
        }

        private void before(String name) {
            System.out.println("调用方法" + name +"之【前】的日志处理");
        }
    }
    interface Person{
        void eat();
        void sleep();
    }
}

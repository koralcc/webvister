package com.koral.vister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@SpringBootTest
public class TestAll {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> "hello world";
        FutureTask<String> stringFutureTask = new FutureTask<>(callable);
        Thread thread = new Thread(new FutureTask<>(callable));
        thread.start();
        System.out.println(stringFutureTask.get());
    }

    @Test
    public void testCallable2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Object> submit = executorService.submit(Object::new);

        submit.get();


    }

    @Test
    public void testGeneric() {
        ArrayList<? extends A> as = new ArrayList();
        for (A a : as) {
            System.out.println(a);
        }

        ArrayList<? super A> as2 = new ArrayList();
        as2.add(new A());
        as2.add(new B());


        ArrayList<A> as3 = new ArrayList();
        as2.add(new A());
        as2.add(new B());
        as2.add(new C());
        ArrayList<B> as3B = new ArrayList();
        as3B.add(new B());

        print(as3);

        print2(as3B);

        print3(as3);
    }

    public static void print(ArrayList<A> a) {
        for (A a1 : a) {
            System.out.println(a1);
        }
    }

    public static void print2(ArrayList<? extends A> a) {
        for (A a1 : a) {
            System.out.println(a1);
        }
    }

    public static void print3(ArrayList<? super A> a) {
        a.add(new A());
    }

    @Test
    public void testThreadLocal() {
        ThreadLocal<String> tl = new ThreadLocal<>();
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            tl.set(Thread.currentThread().getName() + ":" + i);
            System.out.println("线程：" + Thread.currentThread().getName() + ",local:" + tl.get());
        }).start());
        tl.remove();

    }

    @Test
    public void testStatic() {

        StaticA.printa();
        StaticB.printa();
    }


    @Test
    public void testDCCC() {
        BoundListOperations ops = redisTemplate.boundListOps("red_packet_list_5");
        ListOperations listOperations = redisTemplate.opsForList();
        Long size = listOperations.size("red_packet_list_5");
        System.out.println(size);
    }

    public static class A {
    }

    public static class B extends A {
    }

    public static class C extends A {
    }

    public static class StaticA {
        public static  void printa() {
            System.out.println("aaaaa");
        }
    }

    public static class StaticB extends StaticA {
        public static void printa() {
            System.out.println("bbbbb");
        }
    }
}

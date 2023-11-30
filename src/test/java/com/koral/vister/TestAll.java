package com.koral.vister;

import com.koral.vister.test.ReferenceCountingGC;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class TestAll {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> "hello world";
        FutureTask<String> stringFutureTask = new FutureTask<>(callable);
        Thread thread = new Thread(stringFutureTask);
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
        public static void printa() {
            System.out.println("aaaaa");
        }
    }

    public static class StaticB extends StaticA {
        public static void printa() {
            System.out.println("bbbbb");
        }
    }

    @Test
    public void testThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        thread.start();
    }

    @Test
    public void testThreadPoolException() throws ExecutionException, InterruptedException {
//        ThreadPoolExecutor tp = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
//        Future<?> future = tp.submit(() -> {
//            System.out.println("hello");
//            int i = 1 / 0;
//            System.out.println("end");
//        });
//        future.get();
//       tp.submit(()->{
//           System.out.println("当线程池抛出异常后继续新的任务");
//       });

        //1.实现一个自己的线程池工厂
        ThreadFactory factory = (Runnable r) -> {
            //创建一个线程
            Thread t = new Thread(r);
            //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
            t.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
                System.out.println("线程工厂设置的exceptionHandler" + e.getMessage());
            });
            return t;
        };

//        //2.创建一个自己定义的线程池，使用自己定义的线程工厂
//        ExecutorService service = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue(10),factory);
//
//        //3. submit  提交任务
//        service.submit(()->{
//            int i=1/0;
//        });

        //定义线程池
        ExecutorService es = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(10)) {

            //重写afterExecute方法
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t != null) { //这个是excute提交的时候
                    System.out.println("afterExecute里面获取到异常信息" + t.getMessage());
                }

                //如果r的实际类型是FutureTask 那么是submit提交的，所以可以在里面get到异常
                if (r instanceof FutureTask) {
                    try {
                        Future<?> future = (Future<?>) r;
                        future.get();
                    } catch (Exception e) {
                        log.error("future里面取执行异常", e);
                    }
                }
            }
        };

        //2.提交任务
        es.execute(() -> {
            int i = 1 / 0;
        });
    }

    @Test
    public void testReentrantLock() {
        ReentrantLock rl = new ReentrantLock();
        rl.lock();
        rl.unlock();
    }

    @Test
    public void testThreadInterrupt() throws InterruptedException {
        ParkAndCheckInterrupt SPCK = new ParkAndCheckInterrupt();
        Thread th = new Thread(() -> {
            System.out.println("Before Park. ");
            if (SPCK.parkAndCheckInterrupt()) {
                System.out.println("中断返回");
                System.out.println(Thread.interrupted());
            } else {

                System.out.println("unpark返回");
            }
        });
        th.start();
        TimeUnit.SECONDS.sleep(1);
        th.interrupt();
    }


    class ParkAndCheckInterrupt {
        private final boolean parkAndCheckInterrupt() {
//          LockSupport.park(this);
            return Thread.interrupted();
        }
    }

    @Test
    public void testCountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNIO() {
        String name = "data.txt";
        final int BSIZE = 1024;
        try (FileChannel fc = new FileInputStream(name).getChannel()) {
            fc.write(ByteBuffer.wrap("some text".getBytes()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 在文件尾添加


    }


    @Test
    public void testGC引用计数() {
        ReferenceCountingGC.testGC();
    }



}

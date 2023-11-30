package com.koral.vister.testnoaop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 *在网上看到大部分的帖子都有介绍性能调优的案例，其中有一项就是告诉你 **`Xms`** 和 **`Xmx`** 参数一定要设置成相同的，这样就可以达到优化的目的，就像这样
 *-Xms1024m -Xmx1024m
 *但是却没说为什么要这么设置，那么这就是来告诉你这样设置的目的
 */
public class XmxAndXms {
    public static void main(String[] args) throws InterruptedException {

        XmxAndXms test = new XmxAndXms();
        // 添加内存前先打印默认的堆内存情况
        test.showHeapSpace();

        // 开始添加内存
        test.addMemory();
    }

    /**
     * 展示堆空间大小
     */
    public void showHeapSpace(){
        // 每次增加对象时，totalMemory都会增大100倍
        System.out.print("当前堆内存：" + Runtime.getRuntime().totalMemory()/1024/1024 + "M") ;
        System.out.print("，最大内存："+Runtime.getRuntime().maxMemory()/1024/1024+ "M");
        System.out.print("，空闲内存：" + Runtime.getRuntime().freeMemory()/1024/1024+ "M");
        System.out.println();
    }

    /**
     * 每2秒钟往堆内存添加一个占用100m内存的对象
     * @throws InterruptedException
     */
    public void addMemory() throws InterruptedException {
        List<byte[]> list = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            System.out.print("第"+(j+ 1)+"次添加内存");
            // 每2秒增加100M内存
            list.add(new byte[1024 * 1024 * 100]);
            // 添加完后展示内存大小
            this.showHeapSpace();
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
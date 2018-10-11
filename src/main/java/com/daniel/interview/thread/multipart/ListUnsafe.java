package com.daniel.interview.thread.multipart;

import java.util.ArrayList;
import java.util.List;

/**
 * Daniel on 2018/10/11.
 * <p>
 * ArrayList是线程不安全的 要线程安全就使用 Vector
 */
public class ListUnsafe {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    //ArrayList 在并发情况下会出现的几种现象
    //java.lang.ArrayIndexOutOfBoundsException: 2776
    //程序正常运行，输出了少于实际容量的大小
    //程序正常运行，输出了预期容量的大小
    private static void test1() throws InterruptedException {
        List<Integer> list = new ArrayList<>();

        Thread a = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        }, "A");

        Thread b = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        }, "B");

        Thread c = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        }, "C");
        a.start();
        b.start();
        c.start();

        a.join();
        b.join();
        c.join();

        System.out.println(list.size());
    }
}

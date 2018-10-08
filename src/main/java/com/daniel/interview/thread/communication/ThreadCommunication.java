package com.daniel.interview.thread.communication;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Daniel on 2018/10/8.
 * <p>
 * 线程间通信
 *
 * thread.join(),
 * object.wait(),
 * object.notify(),
 * CountdownLatch,
 * CyclicBarrier,
 * FutureTask,
 * Callable
 *
 * 两个线程A、B分别按照不同顺序打印 1-3 三个数字
 */
public class ThreadCommunication {
    public static void main(String[] args) {
        test6();
    }

    //A 和 B 同时无序打印
    private static void test1() {
        Thread a = new Thread(ThreadCommunication::printNumber, "A");
        Thread b = new Thread(ThreadCommunication::printNumber, "B");
        a.start();
        b.start();
        //A==>> 1
        //B==>> 1
        //B==>> 2
        //A==>> 2
        //B==>> 3
        //A==>> 3
    }


    //B 等 A 全部打印完后再开始打印
    // thread.join() 实现
    private static void test2() {
        Thread a = new Thread(ThreadCommunication::printNumber, "A");

        Thread b = new Thread(() -> {
            System.out.println("thread-B start to wait ... ");
            try {
                //a.join() 方法会让B一直等待 直到A运行完毕
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printNumber();
        }, "B");
        a.start();
        b.start();
        //A==>> 1
        //A==>> 2
        //A==>> 3
        //B==>> 1
        //B==>> 2
        //B==>> 3
    }

    //A打印完1后，让B打印 1, 2, 3，最后再回到 A 继续打印 2, 3
    //synchronized + object.wait() + object.notify() 实现
    private static void test3() {
        Object lock = new Object();

        Thread a = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "==>> " + 1);
                try {
                    //A打印1后 进入等待
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "==>> " + 2);
                System.out.println(Thread.currentThread().getName() + "==>> " + 3);
            }
        }, "A");

        Thread b = new Thread(() -> {
            synchronized (lock) {
                printNumber();
                //B打印完成后 唤醒A继续执行
                lock.notify();
            }
        }, "B");
        a.start();
        b.start();
        //A==>> 1
        //B==>> 1
        //B==>> 2
        //B==>> 3
        //A==>> 2
        //A==>> 3
    }

    //A打印10内的奇数 B打印10内的偶数 交替执行
    private static void test4() {
        Num num = new Num(10);
        Thread a = new Thread(() -> printOddNum(num), "A");
        Thread b = new Thread(() -> printEvenNum(num), "B");
        a.start();
        b.start();

        //B:even number==>0
        //A:odd number==>1
        //B:even number==>2
        //A:odd number==>3
        //B:even number==>4
        //A:odd number==>5
        //B:even number==>6
        //A:odd number==>7
        //B:even number==>8
        //A:odd number==>9
    }

    //四个线程 A B C D，其中 D 要等到 A B C 全执行完毕后才执行，而且 A B C 是同步运行的
    private static void test5() {
        int worker = 3;
        //CountDownLatch 可以用于线程间倒计数
        CountDownLatch countDownLatch = new CountDownLatch(worker);

        //等待线程
        new Thread(() -> {
            System.out.println("D is waiting for other three threads");
            try {
                //等待其他线程执行完成 即等待计数器=0
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("All done, D starts working");
        }, "D").start();

        //其他线程
        for (char threadName = 'A'; threadName <= 'C'; threadName++) {
            String name = String.valueOf(threadName);
            new Thread(() -> {
                System.out.println(name + " is working");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " finished");
                //倒计数
                countDownLatch.countDown();
                System.out.println("countDown=" + countDownLatch.getCount());
            }, name).start();
        }
    }

    //三个运动员各自准备，等到三个人都准备好后，再一起跑
    //针对 线程 A B C 各自开始准备，直到三者都准备完毕，然后再同时运行 。也就是要实现一种 线程之间互相等待 的效果
    //CountDownLatch 可以用来倒计数，但当计数完毕，只有一个线程的 await() 会得到响应，无法让多个线程同时触发
    //CyclicBarrier 可以实现线程间互相等待这种需求
    private static void test6() {
        int runner = 3;
        //CyclicBarrier 可以实现线程间互相等待
        CyclicBarrier cyclicBarrier = new CyclicBarrier(runner);
        Random random = new Random();

        for (char name = 'A'; name <= 'C'; name++) {
            String tName = String.valueOf(name);
            new Thread(() -> {
                int prepareTime = random.nextInt(10000) + 1000;
                //开始准备
                System.out.println(tName + ": is preparing for time: " + prepareTime);
                try {
                    Thread.sleep(prepareTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(tName + " is prepared, waiting for others");
                try {
                    // 当前线程准备完毕，等待其他准备好
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("ALL done, " + tName + " starts running"); // 所有运动员都准备好了，一起开始跑
            }, tName).start();

        }
    }

    //创建子线程完成一些耗时任务，然后把任务执行结果回传给主线程使用
    //举例 子线程计算从 1 加到 100，并把算出的结果返回到主线程
    //Callable + FutureTask
    //通过 FutureTask 和 Callable 可以直接在主线程获得子线程的运算结果，但是需要阻塞主线程。如果不希望阻塞主线程，可以利用 ExecutorService，把 FutureTask 放到线程池去管理执行
    private static void test7() {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Task starts");
                Thread.sleep(1000);
                int result = 0;
                for (int i=0; i<=100; i++) {
                    result += i;
                }
                System.out.println("Task finished and return result");
                return result;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            System.out.println("Before futureTask.get()");
            System.out.println("Result: " + futureTask.get());
            System.out.println("After futureTask.get()");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void printNumber() {
        int i = 0;
        while (i++ < 3) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "==>> " + i);
        }
    }

    /**
     * 打印奇数
     *
     * @param num
     */
    private static void printOddNum(Num num) {
        while (num.getIndex() < num.getMax()) {
            synchronized (num) {
                if (num.isEven()) {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ":odd number==>" + num.getIndex());
                    num.setIndex(num.getIndex() + 1);
                    num.setEven(true);
                    num.notify();
                }
            }
        }
    }

    /**
     * 打印偶数
     *
     * @param num
     */
    private static void printEvenNum(Num num) {
        while (num.getIndex() < num.getMax()) {
            synchronized (num) {
                if (num.isEven()) {
                    System.out.println(Thread.currentThread().getName() + ":even number==>" + num.getIndex());
                    num.setIndex(num.getIndex() + 1);
                    num.setEven(false);
                    num.notify();
                } else {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Num {
        private long max;
        private long index = 0;
        private boolean even = true;

        public Num(int max) {
            this.max = max;
        }

        public long getIndex() {
            return index;
        }

        public void setIndex(long index) {
            this.index = index;
        }

        public boolean isEven() {
            return even;
        }

        public void setEven(boolean even) {
            this.even = even;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }
    }
}

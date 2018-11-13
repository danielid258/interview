package com.daniel.interview.thread.forkjoin;

import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * Daniel on 2018/10/9.
 *
 * Fork/Join是Java7提供的一个用于并行执行任务的框架 把大任务分割成若干个小任务 最终汇总每个小任务的结果后得到大任务的结果
 *
 * Fork就是把一个大任务切分为若干子任务并行的执行
 * Join就是合并这些子任务的执行结果 最后得到这个大任务的结果
 *
 * 比如计算1+2+...＋10000 可以分割成10个子任务,每个子任务分别对1000个数进行求和 最终汇总这10个子任务的结果
 *
 * 设计一个Fork/Join框架
 *  1 分割任务
 *      首先需要一个fork类把大任务分割成子任务 可能子任务还是很大 还需要继续分割 直到分割出的子任务足够小
 *  2 执行任务并合并结果
 *      分割的子任务分别放入双端队列中 然后启动线程分别从双端队列里获取任务执行 子任务执行完的结果都放在一个队列里
 *      启动一个线程从队列里取子任务的结果然后合并这些数据
 *
 * Fork/Join使用两个类来完成以上两件事情:
 *  ForkJoinTask: 提供在任务中执行fork()和join()操作的机制 通常不需要直接继承ForkJoinTask 只需要继承它的子类
 *      RecursiveAction: 用于没有返回结果的任务
 *      RecursiveTask: 用于有返回结果的任务
 * ForkJoinPool: 执行ForkJoinTask, 分割出的子任务会添加到当前工作线程所维护的双端队列的头部
 * 当工作线程的队列里暂时没有任务时 它会随机从其他工作线程的队列的尾部获取一个任务
 *
 */
public class ForkJoin {
    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
    }

    private static void test1() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCalculatorTask task = new ForkJoinCalculatorTask(0L, 10000000000L);

        long milli = Instant.now().toEpochMilli();
        Long sum = forkJoinPool.invoke(task);
        long end = Instant.now().toEpochMilli();

        System.out.println(sum);
        System.out.println(end - milli);

    }

    private static void test2() {
        long sum = 0;
        long milli = Instant.now().toEpochMilli();
        for (long i = 0L; i <= 10000000000L; i++) {
            sum += i;
        }
        long end = Instant.now().toEpochMilli();

        System.out.println(sum);
        System.out.println(end - milli);
    }

    /**
     * Stream 并行流
     */
    private static void test3() {
        long milli = Instant.now().toEpochMilli();
        long sum = LongStream.rangeClosed(0L, 10000000000L).parallel().sum();
        long end = Instant.now().toEpochMilli();
        System.out.println(sum);
        System.out.println(end - milli);
    }
}

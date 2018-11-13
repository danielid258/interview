package com.daniel.interview.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Daniel on 2018/10/24.
 */
public class ThreadPool {
    /**
     *
     public interface ExecutorService extends Executor
                                                |- void execute(Runnable command);
                      |- <T> Future<T> submit(Callable<T> task);
                      |- <T> Future<T> submit(Runnable task, T result);
                      |- void execute(Runnable command);


     //构造函数
     public ThreadPoolExecutor(int corePoolSize,
                             int maximumPoolSize,
                             long keepAliveTime,
                             TimeUnit unit,
                             BlockingQueue<Runnable> workQueue,
                             ThreadFactory threadFactory,
                             RejectedExecutionHandler handler)

     corePoolSize 核心线程数的最大值
     maximumPoolSize 线程池中能容纳线程数的最大值
     workQueue 用于缓存任务的 阻塞队列
        通过向线程池添加新任务来说明 corePoolSize maximumPoolSize workQueue三者间的关系:
            1 没有空闲的线程执行新任务 且当前运行的线程数 < corePoolSize ==> 则创建新线程执行新任务
            2 没有空闲的线程执行新任务 且当前运行的线程数 = corePoolSize 且workQueue未满 ==> 则将新任务加入workQueue 而不添加新的线程
            3 没有空闲的线程执行新任务 且workQueue未满 且当前运行的线程数 < maximumPoolSize  ==> 则创建新线程执行新任务
            4 没有空闲的线程执行新任务 且workQueue未满 且当前运行的线程数 = maximumPoolSize  ==> 则根据构造函数中的handler指定的策略来拒绝新的任务
     handler 表示当workQueue已满 且池中的线程数=maximumPoolSize时 线程池拒绝添加新任务时采取的策略


     通俗解释
        如果把线程池比作一个单位的话,corePoolSize就表示正式工,线程就可以表示一个员工。
        向单位委派一项工作时,如果单位发现正式工还没招满,单位就会招个正式工来完成这项工作。
        随着向这个单位委派的工作增多,使正式工全部满了,工作还是干不完,那么单位只能按照新委派的工作按先后顺序将它们找个地方搁置起来,这个地方就是workQueue
        等正式工完成了手上的工作,就到workQueue来取新的任务

        如果不巧,年末了,各个部门都向这个单位委派任务,导致workQueue已经没有空位置放新的任务,于是单位决定招点临时工吧（临时工：又是我！）。
        临时工也不是想招多少就找多少,上级部门通过这个单位的maximumPoolSize确定了你这个单位的人数的最大值,换句话说最多招maximumPoolSize–corePoolSize个临时工。
        当然,在线程池中,谁是正式工,谁是临时工是没有区别,完全同工同酬。
     */
    /**/
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10,
                Thread::new
                //==
                //(runnable) -> new Thread(runnable)
                //==
                //    new ThreadFactory() {
                //        @Override
                //        public Thread newThread(Runnable r) {
                //            return null;
                //        }
                //}
        );

        executorService = Executors.newCachedThreadPool();
        executorService = Executors.newScheduledThreadPool(10);
        executorService = Executors.newSingleThreadExecutor();
    }

}
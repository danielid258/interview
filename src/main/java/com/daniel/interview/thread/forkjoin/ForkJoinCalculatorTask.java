package com.daniel.interview.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Daniel on 2018/10/9.
 */
public class ForkJoinCalculatorTask extends RecursiveTask<Long> {
    private static final long serialVersionUID = 13475679780L;

    //阈值
    private static final long THRESHOLD = 10000L;

    private long start;
    private long end;

    public ForkJoinCalculatorTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        long length = end - start;

        //如果任务足够小就计算任务
        if (length <= THRESHOLD) {
            for (long i = start; i <=end ; i++) {
                sum += i;
            }
        }else {//如果任务大于阀值 拆分成两个子任务

            //拆分任务
            long middle = (start + end) / 2;
            ForkJoinCalculatorTask left = new ForkJoinCalculatorTask(start, middle);
            ForkJoinCalculatorTask right = new ForkJoinCalculatorTask(middle + 1, end);

            //执行子任务
            left.fork();
            right.fork();

            //等待子任务执行完 并得到其结果
            //主线程需要等待子线程执行完成之后再结束,这时候就要用到join()
            Long leftResult = left.join();
            Long rightResult = right.join();

            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }
}

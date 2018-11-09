package com.daniel.interview.thread.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * Daniel on 2018/11/9.
 */
public class ListForkJoin {
    public static void main(String[] args) throws Exception {
        List<Long> list = new LinkedList<>();
        LongStream.rangeClosed(0L, 1000000000).forEach(list::add);

        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(processors);

        Instant start = Instant.now();
        ListForkJoinTask task = new ListForkJoinTask(100000, list);
        //ForkJoinTask<Long> feature = forkJoinPool.submit(task);
        //Long result = feature.get();
        //equals to
        Long result = forkJoinPool.invoke(task);

        long millis = Duration.between(start, Instant.now()).toMillis();
        forkJoinPool.shutdown();
        System.out.println(result);
        System.out.println(millis);

    }
}

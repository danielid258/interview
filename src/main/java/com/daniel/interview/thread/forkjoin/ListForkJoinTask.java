package com.daniel.interview.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Daniel on 2018/11/9.
 */
public class ListForkJoinTask extends RecursiveTask<Long>{
    int taskSize = 100;
    private List<Long> list = new ArrayList<>();

    public ListForkJoinTask(int taskSize, List<Long> list) {
        this.taskSize = taskSize;
        this.list = list;
    }

    @Override
    protected Long compute() {
        //execute calculate task
        if (this.list.size() < taskSize) {
            return this.list.stream().reduce(0L, Long::sum);
        }

        //split list

        int middle = list.size() / 2 ;
        ListForkJoinTask leftSubTask = new ListForkJoinTask(this.taskSize, this.list.subList(0, middle));
        ListForkJoinTask rightSubTask = new ListForkJoinTask(this.taskSize, this.list.subList(middle, this.list.size()));

        //leftSubTask.fork();
        //rightSubTask.fork();
        //equals to
        invokeAll(leftSubTask, rightSubTask);

        Long left = leftSubTask.join();
        Long right = rightSubTask.join();

        return left + right;
    }
}

package com.daniel.interview.sort;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Daniel on 2018/10/25.
 *
 * 支付服务类
 */
public class PaymentRemoteSerivce {
    ExecutorService service = Executors.newFixedThreadPool(5);

    /**
     * 返回当前可用的支付方式
     *
     * @return 可用支付方式类型列表
     * @throws Exception
     */
    public List<PaymentType> enabledPayTypeList(List<PaymentType> paymentTypeList) throws Exception {
        List<Task> list = new ArrayList<>();
        for (PaymentType type : paymentTypeList) {
            list.add(new Task(type));
        }

        //调用线程池执行检查任务
        List<Future<PaymentType>> futures = service.invokeAll(list);
        if (CollectionUtils.isEmpty(futures))
            return null;

        //return futures.stream()
        //        .filter(future -> future.get() != null) //过滤为空 即不可用的支付方式
        //        .map(future->future.get())
        //        .collect(Collectors.toList());  //返回
        return null;
    }
}


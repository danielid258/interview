package com.daniel.interview.sort;

import java.util.concurrent.Callable;

/**
 * Daniel on 2018/10/25.
 *
 * 执行检查支付方式是否可用的Task
 */
public class Task implements Callable<PaymentType> {
    PaymentType paymentType;

    public Task(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public PaymentType call() throws Exception {
        boolean enable = payTypeEnable(this.getPaymentType());
        return enable ? this.getPaymentType() : null;
    }

    /**
     * 判断支付方式是否可用
     *
     * @param paymentType 具体支付方式
     * @return
     */
    private boolean payTypeEnable(PaymentType paymentType) {
        //判断
        //do ...
        return true;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}

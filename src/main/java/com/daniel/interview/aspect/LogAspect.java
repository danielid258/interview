package com.daniel.interview.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Daniel on 2018/11/5.
 */
@Slf4j
@Aspect
@Component
@Order(value = -1)
public class LogAspect {
    @Pointcut("execution(public * com.daniel.interview.controller.*.insert*(..))")
    public void logPointcut(){}

    /**
     * ProceedingJoinPoint和JoinPoint的区别
     * ProceedingJoinPoint继承了JoinPoint 并在JoinPoint的基础上暴露出proceed方法 proceed用于显式调用目标方法
     * 暴露出proceed就能支持 aop:around 这种切面(环绕通知=前置通知+目标方法执行+后置通知)
     * 如果不调用proceed则目标方法不会被执行 前置通知和后置通知都默认调用目标方法
     */
    @Around("logPointcut()")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format("============== before target: %s", joinPoint.getSignature().getName()));
        Object object = joinPoint.proceed();
        log.info(String.format("============== after target: %s", joinPoint.getSignature().getName()));
        return object;
    }
}

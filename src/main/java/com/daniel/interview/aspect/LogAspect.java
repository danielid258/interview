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

    @Around("logPointcut()")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format("============== before target: %s", joinPoint.getSignature().getName()));
        Object object = joinPoint.proceed();
        log.info(String.format("============== after target: %s", joinPoint.getSignature().getName()));
        return object;
    }
}

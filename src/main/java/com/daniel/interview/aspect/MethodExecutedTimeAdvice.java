package com.daniel.interview.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Daniel on 2018/11/5.
 *
 * 拦截service层的方法
 *
 *
 * 另外需要配置如下信息 指定 拦截器setInterceptorNames和需要被拦截的类setBeanNames
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
    BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
    proxyCreator.setInterceptorNames("methodExecutedTimeAdvice");
    proxyCreator.setBeanNames("indexServiceImpl");
    return proxyCreator;
}
 */
@Slf4j
@Component
public class MethodExecutedTimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String name = methodInvocation.getMethod().getName();
        log.info(String.format("======== target method [%s] beginning", name));
        StopWatch watch = new StopWatch();
        watch.start();
        Object result = methodInvocation.proceed();
        watch.stop();
        log.info(String.format("======== target method [%s] finished,takes [%s] milliSeconds; ", name, watch.getTotalTimeMillis()));
        return result;
    }
}

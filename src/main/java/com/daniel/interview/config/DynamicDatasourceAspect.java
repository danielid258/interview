package com.daniel.interview.config;

import com.daniel.interview.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Daniel on 2018/10/12.
 * <p>
 * 利用AOP将设置数据源的操作从代码中抽离 粒度控制在方法级别 用@DS注解的形式标注方法涉及的数据源
 */
@Slf4j
@Aspect
@Component
public class DynamicDatasourceAspect {

    /**
     * 访问目标方法之前切换数据源 没有@DS注解显式标明数据源的方法使用默认数据源
     *
     * @param joinPoint
     */
    @Before("@annotation(com.daniel.interview.annotation.DS)")
    public void beforeSwitchDataSource(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class[] argClasses = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        String datasource = DataSourceContextHolder.DEFAULT_DS;

        try {
            //得到访问的目标方法
            Method method = className.getMethod(methodName, argClasses);
            //判断目标方法是否有@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                datasource = annotation.value();
            }
        } catch (NoSuchMethodException e) {
            log.error("switch dataSource error: ", e);
        }
        //切换数据源
        DataSourceContextHolder.setDB(datasource);
    }

    /**
     * 访问目标方法完成后清空数据源
     *
     * @param joinPoint
     */
    @After("@annotation(com.daniel.interview.annotation.DS)")
    public void afterSwitchDataSource(JoinPoint joinPoint) {
        DataSourceContextHolder.clearDB();
    }
}

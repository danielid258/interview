package com.daniel.interview.designpattern.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Daniel on 2018/10/12.
 *
 * Cglib代理
 *
 * 有时目标对象没有实现任何的接口,此时可以用 目标对象子类的方式类实现代理 即Cglib代理 也叫作子类代理
 * Cglib代理在内存中构建一个子类对象从而实现对目标对象功能的扩展
 *
 */
public class CglibProxyFactory implements MethodInterceptor{
    private Object target;

    public CglibProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        //1 增强 工具类
        Enhancer enhancer = new Enhancer();
        //2 设置父类
        enhancer.setSuperclass(target.getClass());
        //3 设置回调函数
        enhancer.setCallback(this);
        //4 创建子类(代理对象)
        return enhancer.create();
    }
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("begin transaction ...");
        Object result = method.invoke(target, args);
        System.out.println("finish transaction ...");
        return result;
    }
}

package com.daniel.interview.designpattern.proxy;

import java.lang.reflect.Proxy;

/**
 * Daniel on 2018/10/11.
 *
 * 动态代理 也叫做:JDK代理,接口代理
 *
 *  代理对象不需要实现目标对象接口 但是目标对象一定要实现接口,否则不能用动态代理
 *  代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 *
 *  代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理
 *
 *  代理类所在包:java.lang.reflect.Proxy
 *
 */
public class DynamicProxyFactory {
    private Object target;

    public DynamicProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("begin transaction ...");

                    //执行目标对象方法
                    Object result = method.invoke(target, args);

                    System.out.println("finish transaction ...");
                    return result;
                }

                //new InvocationHandler() {
                //    @Override
                //    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //        System.out.println("begin transaction ...");
                //
                //        //执行目标对象方法
                //        Object result = method.invoke(target, args);
                //
                //        System.out.println("finish transaction ...");
                //        return result;
                //    }
                //}
        );
    }
}

package com.daniel.interview.designpattern.proxy;

/**
 * Daniel on 2018/10/11.
 */
public class APPTest {
    public static void main(String[] args) {

        //staticProxyTest();

        dynamicProxyTest();
        //
        //cglibProxyTest();

    }

    private static void cglibProxyTest() {
        UserDao target = new UserDao();

        System.out.println(target.getClass());

        UserDao proxy = (UserDao) new CglibProxyFactory(target).getProxyInstance();

        System.out.println(proxy.getClass());

        proxy.save();
    }

    /**
     *
     */
    private static void staticProxyTest() {
        IUserService target = new UserService();
        StaticProxy proxy = new StaticProxy(target);
        proxy.save();
    }

    /**
     *
     */
    private static void dynamicProxyTest() {
        IUserService target = new UserService();
        System.out.println(target.getClass());
        System.out.println(target.getClass().getClassLoader());
        System.out.println(target.getClass().getInterfaces());

        //为目标对象 创建代理对象
        IUserService proxy = (IUserService) new DynamicProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());

        proxy.save();
    }
}

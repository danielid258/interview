package com.daniel.interview.designpattern.proxy;

/**
 * Daniel on 2018/10/11.
 *
 * 静态代理
 *  可以做到在不修改目标对象功能的前提下,对目标功能扩展.
 *
 *  缺点  因为代理对象需要与目标对象实现一样的接口,会导致代理类太多. 同时,一旦接口增加方法,目标对象与代理对象都要维护
 */
public class StaticProxy implements IUserService{
    private IUserService target;

    public StaticProxy(IUserService target) {
        this.target = target;
    }

    @Override
    public void save() {
        //其他操作
        System.out.println("begin transaction ...");

        //执行目标对象的方法
        target.save();

        //其他操作
        System.out.println("finish transaction ...");
    }
}

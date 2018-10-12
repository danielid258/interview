package com.daniel.interview.designpattern.proxy;

/**
 * Daniel on 2018/10/12.
 *
 * Cglib代理的目标类,没有实现任何接口
 */
public class UserDao {
    public void save() {
        System.out.println("save user successfully");
    }
}

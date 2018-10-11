package com.daniel.interview.designpattern.proxy;

/**
 * Daniel on 2018/10/11.
 */
public class UserService implements IUserService {
    @Override
    public void save() {
        System.out.println("save user successfully");
    }
}

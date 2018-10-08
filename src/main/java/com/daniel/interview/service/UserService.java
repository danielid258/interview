package com.daniel.interview.service;

import com.daniel.interview.domain.User;

import java.util.List;

/**
 * Daniel on 2018/9/29.
 */
public interface UserService {
    List<User> getUserListFromDB();

    List<User> getUserListFromRedis();
}

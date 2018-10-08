package com.daniel.interview.service.impl;

import com.daniel.interview.domain.User;
import com.daniel.interview.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Daniel on 2018/9/29.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUserListFromDB() {
        return null;
    }

    @Override
    public List<User> getUserListFromRedis() {
        return null;
    }
}

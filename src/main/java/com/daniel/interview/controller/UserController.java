package com.daniel.interview.controller;

import com.daniel.interview.domain.UserInfo;
import com.daniel.interview.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Daniel on 2018/9/29.
 */
@RestController("/user")
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    IUserInfoService userInfoService;

    @GetMapping("/add")
    public void addToDB2() {
        UserInfo dan = new UserInfo(1L, "dan", 18);
        userInfoService.insert(dan);
    }

    @PostMapping("/add")
    public void addToDB1() {
        List<UserInfo> list = Arrays.asList(new UserInfo(1L, "dan", 18), new UserInfo(2L, "jack", 20));
        userInfoService.insertList(list);
    }

    @GetMapping("/list")
    public List<UserInfo> userList() {
        return null;
    }
}

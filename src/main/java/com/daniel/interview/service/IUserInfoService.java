package com.daniel.interview.service;

import com.daniel.interview.domain.UserInfo;

import java.util.List;

/**
 * Daniel on 2018/10/12.
 */
public interface IUserInfoService {
    Long insert(UserInfo pojo);

    int insertList(List<UserInfo> pojos);

    List<UserInfo> findByUserName(String userName);

    void update(UserInfo pojo);
}

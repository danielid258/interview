package com.daniel.interview.service;

import com.daniel.interview.domain.UserInfo;

import java.util.List;

/**
 * Daniel on 2018/10/12.
 */
public interface IUserInfoService {
    int insert(UserInfo pojo);

    int insertList(List<UserInfo> pojos);

    List<UserInfo> select(UserInfo pojo);

    int update(UserInfo pojo);
}

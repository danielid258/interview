package com.daniel.interview.service;

import com.daniel.interview.annotation.DS;
import com.daniel.interview.config.DataSourceConfiguration;
import com.daniel.interview.dao.UserInfoDao;
import com.daniel.interview.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @DS(DataSourceConfiguration.DATASOURCE2)
    @Override
    public int insert(UserInfo pojo){
        return userInfoDao.insert(pojo);
    }

    @Override
    public int insertList(List<UserInfo> pojos){
        return userInfoDao.insertList(pojos);
    }

    @Override
    public List<UserInfo> select(UserInfo pojo){
        return userInfoDao.select(pojo);
    }

    @Override
    public int update(UserInfo pojo){
        return userInfoDao.update(pojo);
    }

}

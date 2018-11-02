package com.daniel.interview.service;

import com.daniel.interview.annotation.DS;
import com.daniel.interview.config.DataSourceConfiguration;
import com.daniel.interview.domain.UserInfo;
import com.daniel.interview.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @DS(DataSourceConfiguration.DATASOURCE2)
    @Override
    public Long insert(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
        return userInfo.getId();
    }

    @Override
    public int insertList(List<UserInfo> userInfos) {
        userInfoRepository.saveAll(userInfos);
        return userInfos.size();
    }

    @Override
    public List<UserInfo> findByUserName(String userName) {
        return userInfoRepository.findByUserName(userName);
    }

    @Override
    @DS(DataSourceConfiguration.DATASOURCE2)
    public void update(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

}

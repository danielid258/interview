package com.daniel.interview.repository;

import com.daniel.interview.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Daniel on 2018/10/26.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findByUserName(String userName);
}

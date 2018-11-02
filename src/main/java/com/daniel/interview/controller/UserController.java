package com.daniel.interview.controller;

import com.daniel.interview.domain.UserInfo;
import com.daniel.interview.service.IUserInfoService;
import com.daniel.interview.vo.ResultVO;
import com.daniel.interview.vo.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Daniel on 2018/9/29.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserInfoService userInfoService;

    @PostMapping("/addToDB2")
    public ResultVO addToDB2() {
        UserInfo dan = new UserInfo(1L, "dan", 18);
        Long userId = userInfoService.insert(dan);
        return ResultVOUtil.succeed("userId", userId);
    }

    @PostMapping("/addListToDB1")
    public ResultVO addListToDB1() {
        List<UserInfo> list = Arrays.asList(new UserInfo(1L, "tom", 18), new UserInfo(2L, "jack", 20));
        userInfoService.insertList(list);
        return ResultVOUtil.succeed();
    }

    @GetMapping("/listFromDB1")
    public ResultVO getListByUserNameFromDB1(String userName) {
        List<UserInfo> list = userInfoService.findByUserName(userName);
        return ResultVOUtil.succeed(list);
    }

    @PutMapping("/updateToDB2")
    public ResultVO updateToDB2(UserInfo userInfo) {
        userInfoService.update(userInfo);
        return ResultVOUtil.succeed();
    }
}

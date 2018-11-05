package com.daniel.interview.controller;

import com.daniel.interview.annotation.RequestLimit;
import com.daniel.interview.service.IndexService;
import com.daniel.interview.vo.ResultVO;
import com.daniel.interview.vo.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Daniel on 2018/10/26.
 */
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @RequestMapping("/index")
    @RequestLimit(count = 3, duration = 5000)
    public ResultVO index() {
        indexService.test();
        return ResultVOUtil.succeed("index");
    }
}

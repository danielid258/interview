package com.daniel.interview.controller;

import com.daniel.interview.annotation.RequestLimit;
import com.daniel.interview.vo.ResultVO;
import com.daniel.interview.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Daniel on 2018/10/26.
 */
@RestController
public class IndexController {
    @RequestMapping("/index")
    @RequestLimit(count = 3, duration = 5000)
    public ResultVO index() {
        return ResultVOUtil.succeed("index");
    }
}

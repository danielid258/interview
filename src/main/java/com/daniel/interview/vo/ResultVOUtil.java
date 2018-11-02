package com.daniel.interview.vo;


import com.daniel.interview.enums.ResultEnum;

import java.util.HashMap;

/**
 * Daniel on 2018/9/25.
 */
public class ResultVOUtil {
    public static ResultVO succeed() {
        return new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static ResultVO succeed(Object data) {
        return new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static ResultVO succeed(String dataKey, Object data) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(dataKey, data);
        return new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), map);
    }

    public static ResultVO fail() {
        return fail(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getDesc(), null);
    }

    public static ResultVO fail(Object data) {
        return fail(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getDesc(), data);
    }

    public static ResultVO failedWithDetailMsg(String errorMessage) {
        HashMap<String, String> map = new HashMap<>();
        map.put("errorMessage", errorMessage);
        return fail(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getDesc(), map);
    }

    public static ResultVO failedWithDetailMsg(String code, String errorMessage) {
        HashMap<String, String> map = new HashMap<>();
        map.put("errorMessage", errorMessage);
        return fail(code, ResultEnum.FAILURE.getDesc(), map);
    }

    public static ResultVO fail(String code, String message, Object data) {
        return new ResultVO(code, message, data);
    }
}

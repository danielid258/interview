package com.daniel.interview.handler;

import com.daniel.interview.exception.RequestLimitException;
import com.daniel.interview.vo.ResultVO;
import com.daniel.interview.vo.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Daniel on 2018/9/25.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleException(Exception e) {
        log.error("====>> Exception message={}", e.getMessage(), e);
        return ResultVOUtil.failedWithDetailMsg(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestLimitException.class)
    public ResultVO handleRequestParamException(RequestLimitException e) {
        log.error("====>> RequestParamException code={}, message={}", e.getCode(), e.getMessage());
        return ResultVOUtil.failedWithDetailMsg(e.getCode(), e.getMessage());
    }
}
package com.daniel.interview.exception;

import com.daniel.interview.enums.ResultEnum;
import lombok.Data;

/**
 * Daniel on 2018/9/25.
 */
@Data
public class RequestLimitException extends RuntimeException {
    protected String code;

    public RequestLimitException(ResultEnum resultEnum) {
        this(resultEnum.getCode(), resultEnum.getDesc());
    }

    public RequestLimitException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

    public RequestLimitException(String code, String message) {
        super(message);
        this.code = code;
    }
}
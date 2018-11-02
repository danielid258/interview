package com.daniel.interview.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Daniel on 2018/9/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {
    protected String code;
    private String message;
    private Object data;
}

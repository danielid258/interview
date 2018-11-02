package com.daniel.interview.enums;

/**
 * Daniel on 2018/9/25.
 */
public enum ResultEnum {
    SUCCESS("0000", "success"),
    FAILURE("0001", "failure"),
    PARAMS_ERROR("0008", "request params error");

    protected String code;
    private String desc;

    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.daniel.interview.annotation;

/**
 * Daniel on 2018/11/5.
 */
public @interface TableSplit {
    //是否分表
    boolean split() default true;

    //表名
    String value();

    //获取分表策略,如YYYY or yyyyMM
    String strategy();
}
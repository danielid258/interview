package com.daniel.interview.config;

import lombok.extern.slf4j.Slf4j;

/**
 * Daniel on 2018/10/12.
 */
@Slf4j
public class DataSourceContextHolder {
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "dataSourceDB1";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
        log.info("switch to [{}] dataSource");
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        log.info("clear dataSource successfully ");
        contextHolder.remove();
    }
}

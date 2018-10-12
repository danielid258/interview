package com.daniel.interview.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Daniel on 2018/10/12.
 *
 * AbstractRoutingDataSource 实现动态路由数据源
 *
 * 根据用户定义的规则选择数据库 设置查询读取从库 执行完成后再恢复到主库
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceContextHolder.getDB();

        log.info("current datasource [{}]", dataSource);
        return dataSource;
    }
}

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
    /**
     *     AbstractRoutingDataSource抽象类知识，实现AOP动态切换的关键
         1.AbstractRoutingDataSource中determineTargetDataSource()方法中获取数据源 
     Object lookupKey = determineCurrentLookupKey();
     DataSource dataSource = this.resolvedDataSources.get(lookupKey);
     根据determineCurrentLookupKey()得到Datasource,并且此方法是抽象方法,应用可以实现
         2.resolvedDataSources的值根据targetDataSources所得
     afterPropertiesSet()方法中(在@Bean所在方法执行完成后，会调用此方法)：
     Map.Entry<Object, Object> entry : this.targetDataSources.entrySet()
         3.然后在xml中使用<bean>或者代码中@Bean 设置DynamicDataSource的defaultTargetDataSource(默认数据源)和targetDataSources(多数据源)
         4.利用自定义注解，AOP拦截动态的设置ThreadLocal的值
         5.在DAO层与数据库建立连接时会根据ThreadLocal的key得到数据源
     */

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceContextHolder.getDB();

        log.info("current datasource [{}]", dataSource);
        return dataSource;
    }
}

package com.daniel.interview.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Daniel on 2018/10/12.
 * <p>
 * 多数据源配置类
 */
@Configuration
public class DataSourceConfiguration {
    public static final String DATASOURCE1="dataSource1";
    public static final String DATASOURCE2="dataSource2";

    /**
     * DataSource1
     *
     * @return
     */
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    /**
     * DataSource2
     *
     * @return
     */
    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Primary        //@Primary 多个相同类型的bean中 优先使用@Primary注解的bean
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        // 配置多数据源
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DATASOURCE1, dataSource1());
        dataSourceMap.put(DATASOURCE2, dataSource2());

        dynamicDataSource.setTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }

    /**
     * 配置多数据源的 @Transactional注解事物支持
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}

package com.yicj.study.rw.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @Since 2023/12/3 15:40
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public ReadWriteDataSource readWriteDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DsType.MASTER.getCode(), masterDataSource);
        targetDataSources.put(DsType.SLAVE.getCode(), slaveDataSource);
        //
        ReadWriteDataSource readWriteDataSource = new ReadWriteDataSource();
        readWriteDataSource.setDefaultTargetDataSource(masterDataSource);
        readWriteDataSource.setTargetDataSources(targetDataSources);
        return readWriteDataSource;
    }

}

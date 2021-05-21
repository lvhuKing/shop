//package com.order.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
///**
//* @Description 作用: 代理数据源
//* @Author ccl
//* @CreateDate 2020/10/9 16:12
//**/
//@Configuration
//public class DataSourceProxyAutoConfiguration {
//    /**
//     * 数据源属性配置
//     * {@link DataSourceProperties}
//     */
//    private DataSourceProperties dataSourceProperties;
//
//    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {
//        this.dataSourceProperties = dataSourceProperties;
//    }
//
//    /**
//     * 配置数据源代理，用于事务回滚
//     *
//     * @return The default datasource
//     * @see DataSourceProxy
//     */
//    @Primary
//    @Bean("dataSource")
//    @RefreshScope
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
//        dataSource.setUsername(dataSourceProperties.getUsername());
//        dataSource.setPassword(dataSourceProperties.getPassword());
//        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return new DataSourceProxy(dataSource);
//    }
//}
//

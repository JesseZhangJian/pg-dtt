package com.bizdata.config;

import javax.sql.DataSource;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * Created by wang669 on 2017/4/18.
 */
@Configuration
//注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(ShardDataSourceConfig.class)
public class MybatisConfigurationScanner {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.bizdata.*.mapper");
        return mapperScannerConfigurer;
    }
    
    @Bean(name = "transactionManager",value = "transactionManager")
    public JpaTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager manager= new JpaTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }
}

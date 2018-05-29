package com.bizdata.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.bizdata.algorithm.MultipleTableShardingAlgorithm;
import com.bizdata.commons.utils.FileUtil;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.github.pagehelper.PageHelper;

@Configuration
@EnableConfigurationProperties(ShardDataSourceProperties.class)
@Component
public class ShardDataSourceConfig {
	
	private static final List<String> list = new ArrayList<String>();
	
	static {
		list.add("hair_production_item_201805");
		list.add("hair_production_item_201806");
		list.add("pcc_production_item_201805");
		list.add("pcc_production_item_201806");
		list.add("other_production_item_201805");
		list.add("other_production_item_201806");
	}
	
	@Autowired
	private ShardDataSourceProperties shardDataSourceProperties;
	
	private BasicDataSource masterDs() throws SQLException {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(shardDataSourceProperties.getDriverClassName());
		ds.setUsername(shardDataSourceProperties.getUsername());
		ds.setUrl(shardDataSourceProperties.getUrl());
		ds.setPassword(shardDataSourceProperties.getPassword());
		ds.setMaxActive(shardDataSourceProperties.getMaxActive());
		ds.setInitialSize(shardDataSourceProperties.getInitialSize());
		ds.setMaxWait(shardDataSourceProperties.getMaxWait());
		ds.setMinIdle(shardDataSourceProperties.getMinIdle());
		ds.setTimeBetweenEvictionRunsMillis(shardDataSourceProperties.getTimeBetweenEvictionRunsMillis());
		ds.setMinEvictableIdleTimeMillis(shardDataSourceProperties.getMinEvictableIdleTimeMillis());
		ds.setValidationQuery(shardDataSourceProperties.getValidationQuery());
		ds.setTestWhileIdle(shardDataSourceProperties.isTestWhileIdle());
		ds.setTestOnBorrow(shardDataSourceProperties.isTestOnBorrow());
		ds.setTestOnReturn(shardDataSourceProperties.isTestOnReturn());
		ds.setPoolPreparedStatements(shardDataSourceProperties.isPoolPreparedStatements());
		ds.setRemoveAbandoned(shardDataSourceProperties.isRemoveAbandoned());
		ds.setRemoveAbandonedTimeout(shardDataSourceProperties.getRemoveAbandonedTimeout());
		ds.setLogAbandoned(shardDataSourceProperties.isLogAbandoned());
		ds.setConnectionInitSqls(shardDataSourceProperties.getConnectionInitSqls());
		return ds;
	}

	private DataSourceRule dataSourceRule() throws SQLException {
		//设置分库映射
		Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
		dataSourceMap.put("pg-dtt", masterDs());
		//设置默认db为scp，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，
		//但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
//		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "scp");
		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
		return dataSourceRule;
	}
	
    @Bean
    public DataSource shardingDataSource() throws SQLException {
        DataSourceRule dataSourceRule = dataSourceRule();

        TableRule tableRule = TableRule.builder("production_item").actualTables(list)
        				.dataSourceRule(dataSourceRule).build();

		Collection<String> columns = new LinkedList<>();
        columns.add("category");
        columns.add("production_datetime");
        
        ShardingRule shardingRule = ShardingRule.builder()
        		.dataSourceRule(dataSourceRule)
        		.tableRules(Arrays.asList(tableRule))
//        		.databaseShardingStrategy(new DatabaseShardingStrategy("order_id", new DatabaseShardingAlgorithm()))
        		.tableShardingStrategy(new TableShardingStrategy(columns, new MultipleTableShardingAlgorithm()))
        		.build();
        DataSource shardingDataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return shardingDataSource;
    }

	@Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        // 配置mybatis的扫描，找到所有的*mybatis.xml映射文件
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/*/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
	
    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(shardingDataSource());
    }
    
    @Bean
    public DefaultKeyGenerator defaultKeyGenerator(){
    	return new DefaultKeyGenerator();
    }
    
    //mybabtis分頁組件
    @Bean
    public PageHelper pageHelper() {
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        props.setProperty("dialect", "mysql");
        pageHelper.setProperties(props);
        return pageHelper;
    }
}

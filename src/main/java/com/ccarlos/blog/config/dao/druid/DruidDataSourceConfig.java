package com.ccarlos.blog.config.dao.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @description: DruidDataSource连接池类
 * @author: ccarlos
 * @date: 2019/6/17 18:10
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig {

	private static Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

	@Autowired
	private DruidConfig druidConfig;

	/**
	 * @description: 创建PropertySourcesPlaceholderConfigurer Bean
	 * @author: ccarlos
	 * @date: 2019/6/17 18:18
	 * @return: org.springframework.context.support.PropertySourcesPlaceholderConfigurer
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * @description: 创建druidDataSource连接池(配置通过配置文件 生成配置一个 Bean 连接池对象)
	 * @author: ccarlos
	 * @date: 2019/6/17 18:14
	 * @return: javax.sql.DataSource
	 */
	@Bean
	public DataSource druidDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(druidConfig.getDriverClassName());
		druidDataSource.setUrl(druidConfig.getUrl());
		druidDataSource.setUsername(druidConfig.getUsername());
		druidDataSource.setPassword(druidConfig.getPassword());
		druidDataSource.setInitialSize(druidConfig.getInitialSize());
		druidDataSource.setMinIdle(druidConfig.getMinIdle());
		druidDataSource.setMaxActive(druidConfig.getMaxActive());
		druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
		druidDataSource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
		druidDataSource.setValidationQuery(druidConfig.getValidationQuery());
		druidDataSource.setTestWhileIdle(druidConfig.isTestWhileIdle());
		druidDataSource.setTestOnBorrow(druidConfig.isTestOnBorrow());
		druidDataSource.setTestOnReturn(druidConfig.isTestOnReturn());
		druidDataSource.setPoolPreparedStatements(druidConfig.isPoolPreparedStatements());
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfig.getMaxPoolPreparedStatementPerConnectionSize());
		druidDataSource.setFilters(druidConfig.getFilters());
		druidDataSource.setConnectionProperties(druidConfig.getConnectionProperties());
		log.info("Druid连接池配置信息{}:", druidDataSource);
		return druidDataSource;
	}

	/**
	 * @description: 连接池事务管理器
	 * @author: ccarlos
	 * @date: 2019/6/17 18:16
	 * @return: org.springframework.transaction.PlatformTransactionManager
	 * @throws: SQLException
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(druidDataSource());
		return txManager;
	}

}

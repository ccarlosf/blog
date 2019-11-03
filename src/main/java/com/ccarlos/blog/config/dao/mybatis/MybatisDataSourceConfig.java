package com.ccarlos.blog.config.dao.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @description: mybatis数据源配置
 * @author: ccarlos
 * @date: 2019/6/18 9:39
 */
@Slf4j
@Configuration
public class MybatisDataSourceConfig {

	@Autowired
	private DataSource dataSource;

	/**
	 * @description: 初始化sqlSessionFactory Bean
	 * @author: ccarlos
	 * @date: 2019/6/18 10:24
	 * @return: org.apache.ibatis.session.SqlSessionFactory
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
			SqlSessionFactory sqlSessionFactory = bean.getObject();
			sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
			return sqlSessionFactory;
		} catch (Exception e) {
			log.error("创建sqlSessionFactory Bean发生异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @description: 初始化sqlSessionTemplate Bean
	 * @author: ccarlos
	 * @date: 2019/6/18 12:36
	 * @param: sqlSessionFactory sql会话工厂Executor
	 * @return: org.mybatis.spring.SqlSessionTemplate
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}

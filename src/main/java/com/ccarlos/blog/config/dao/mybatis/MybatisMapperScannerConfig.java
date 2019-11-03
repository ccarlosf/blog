package com.ccarlos.blog.config.dao.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: mapper配置文件扫描配置类
 * @author: ccarlos
 * @date: 2019/6/18 12:38
 */
@Configuration
@AutoConfigureAfter(MybatisDataSourceConfig.class)
public class MybatisMapperScannerConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.ccarlos.blog.dao");
		return mapperScannerConfigurer;
	}
}

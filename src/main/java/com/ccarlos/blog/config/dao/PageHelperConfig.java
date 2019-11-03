package com.ccarlos.blog.config.dao;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @description: Mybatis PageHelper配置类
 * @author: ccarlos
 * @date: 2019/4/25 20:32
 */
@Component
public class PageHelperConfig {

	/**
	 * @description: 配置 PageHelper Bean
	 * @author: ccarlos
	 * @date: 2019/4/25 20:33
	 * @return: com.github.pagehelper.PageHelper
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
//		properties.setProperty("offsetAsPageNum", "true");
//		properties.setProperty("rowBoundsWithCount", "true");
//		properties.setProperty("reasonable", "true");
		//配置mysql数据库的方言
		properties.setProperty("dialect", "mysql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}

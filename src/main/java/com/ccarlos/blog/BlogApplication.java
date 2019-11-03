package com.ccarlos.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @description: 博客程序启动类
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:05
 */
@SpringBootApplication
@ServletComponentScan
// 扫描JPA包路径
@EnableJpaRepositories(basePackages = {"com.ccarlos.blog.repository"})
// 扫描mybatis mapper包路径
@MapperScan(basePackages = "com.ccarlos.blog.dao")
public class BlogApplication {

	/**
	 * @description: 博客程序启动main方法
	 * @author: ccarlos
	 * @date: 2019/2/25 22:05
	 * @param: args 参数args
	 * @return: void 无返回值类型
	 */
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}

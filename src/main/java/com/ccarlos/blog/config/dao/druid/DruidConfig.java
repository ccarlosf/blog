package com.ccarlos.blog.config.dao.druid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: Druid数据源配置
 * @author: ccarlos
 * @date: 2019/6/17 17:34
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix="spring.datasource")
@PropertySource("classpath:druid.properties")
public class DruidConfig {

	//驱动名
	private String driverClassName;

	//数据库连接url
	private String url;

	//用户名
	private String username;

	//密码
	private String password;

	//初始化连接数量
	@Value("${druid.initialSize}")
	private int initialSize;

	//最小空闲连接数
	@Value("${druid.minIdle}")
	private int minIdle;

	//最大并发连接数
	@Value("${druid.maxActive}")
	private int maxActive;

	//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	@Value("${druid.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	//配置一个连接在池中最小生存的时间，单位是毫秒
	@Value("${druid.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;

	//用来检测连接是否有效的sql，要求是一个查询语句
	@Value("${druid.validationQuery}")
	private String validationQuery;

	//申请连接的时候检测
	@Value("${druid.testWhileIdle}")
	private boolean testWhileIdle;

	//申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能
	@Value("${druid.testOnBorrow}")
	private boolean testOnBorrow;

	//归还连接时执行validationQuery检测连接是否有效，配置为true会降低性能
	@Value("${druid.testOnReturn}")
	private boolean testOnReturn;

	//打开PSCache，并且指定每个连接上PSCache的大小
	@Value("${druid.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	////打开PSCache，并且指定每个连接上PSCache的大小
	@Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	//属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
	//监控统计用的filter:stat
	//日志用的filter:log4j
	//防御SQL注入的filter:wall
	@Value("${druid.filters}")
	private String filters;

	//通过connectProperties属性来打开mergeSql功能；慢SQL记录
	@Value("${druid.connectionProperties}")
	private String connectionProperties;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}
}

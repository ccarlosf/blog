package com.ccarlos.blog.common.constant;

/**
 * @description: RabbitMQ常量类
 * @author: Created by ccarlos
 * @date: 2019/3/31 16:54
 */
public class RabbitMQConst {

	public static final String USERNAME = "username";
	public static final String EMAIL = "email";
	public static final String COOKIE_NAME_TOKEN = "token";
	public static String PROPERTIES_NAME = "application.properties";
	public static String MD5_ENCRYPTION_NAME = "MD5";

	/**
	 * @description: 角色常量接口类
	 * @author: ccarlos
	 * @date: 2019/6/24 19:05
	 */
	public interface Role {

		int ROLE_CUSTOMER = 0; //普通用户

		int ROLE_ADMIN = 1; //管理员
	}

	/**
	 * @description: 消息发送状态接口类
	 * @author: ccarlos
	 * @date: 2019/6/24 19:06
	 */
	public interface MessageStatus {

		String SENDING = "0";//发送中

		String SEND_SUCCESS = "1";//发送成功

		String SEND_FAILURE = "2";//发送失败

		int SEND_TIMEOUT = 1; //消息重试超时时间 分钟超时单位：min
	}

	/**
	 * @description: 消息时间接口类
	 * @author: ccarlos
	 * @date: 2019/6/24 19:19
	 */
	public interface MessageTime {

		int SEND_TIMEOUT = 60; //消息重试超时时间  分钟超时单位：min
	}

	/**
	 * @description: 消息次数接口类
	 * @author: ccarlos
	 * @date: 2019/6/27 23:48
	 */
	public interface MessageCount {
		int RETRY_COUNT = 3; //消息重试次数
		int ADD_COUNT = 1; //消息递增次数
		int DEFAULT_SEND_COUNT = 0; //初始化默认消息发送次数
		int DEFAULT_CONSUME_COUNT = 0; //初始化默认消息消费次数
		int DEFAULT_TRY_COUNT = 0; //初始化默认消息重试次数
	}

	/**
	 * @description: 消息发送类型接口类
	 * @author: ccarlos
	 * @date: 2019/6/24 19:07
	 */
	public interface MessageType {

		//消息类型(0.创建 1.更新 2.查询)

		int CREATE = 0;//创建

		int UPDATE = 1;//更新

		int QUERY = 2;//查询
	}

	/**
	 * @description: 博客消息常量类
	 * @author: ccarlos
	 * @date: 2019/7/1 15:10
	 */
	public interface BlogMessage {

		String CREATE_EXCHANGE = "blog-exchange"; //创建博客消息交换机

		String CREATE_ROUTING_KEY = "blog.create"; //创建博客消息routingKey
	}

}

package com.ccarlos.blog.util;

import com.ccarlos.blog.common.constant.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @description: 获取配置文件参数工具类
 * @author: ccarlos
 * @date: 2019/5/18 9:39
 */
@Slf4j
public class PropertiesUtil {

	private static Properties properties;

	//初始化静态代码块
	static {
		String propertiesName = RabbitMQConst.PROPERTIES_NAME;
		properties = new Properties();
		try {
			InputStreamReader inputStreamReader = new InputStreamReader
					((PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName)), "UTF-8");
			properties.load(inputStreamReader);
		} catch (IOException e) {
			log.error("配置文件读取发生异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
		}
	}

	/**
	 * @description: 根据配置文件的键获取对应的值
	 * @author: ccarlos
	 * @date: 2019/5/18 10:00
	 * @param: key 键
	 * @return: java.lang.String
	 */
	public static String getProperty(String key) {
		String value = properties.getProperty(key.trim());
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return value.trim();
	}

	/**
	 * @description: 根据配置文件的键获取对应的值(为空时 ， 设置为默认值)
	 * @author: ccarlos
	 * @date: 2019/5/18 10:01
	 * @param: key 键
	 * @param: defaultValue 默认值
	 * @return: java.lang.String
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = properties.getProperty(key.trim());
		if (StringUtils.isBlank(value)) {
			value = defaultValue;
		}
		return value.trim();
	}
}

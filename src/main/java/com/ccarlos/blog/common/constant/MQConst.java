package com.ccarlos.blog.common.constant;

/**
 * @description: MQ常量类
 * @author: ccarlos
 * @date: 2019/7/1 15:08
 */
public class MQConst {

	public interface MQType {

		//消息队列类型(RabbitMQ、RocketMQ、Kafka)

		String RABBIT_MQ = "RabbitMQ"; //RabbitMQ

		String ROCKET_MQ = "RocketMQ"; //RocketMQ

		String KAFKA = "Kafka"; //Kafka
	}
}

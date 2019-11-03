package com.ccarlos.blog.mq.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.dao.MessageLogMapper;
import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.MessageLog;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
 * @description: 博客消息者(RabbitMQ)
 * @author: ccarlos
 * @date: 2019/6/19 15:10
 */
@Slf4j
@Component
public class RabbitMQBlogConsumer {

	@Autowired
	private MessageLogMapper messageLogMapper;

	/**
	 * @description: 博客消息消息者
	 * @author: ccarlos
	 * @date: 2019/6/19 15:13
	 * @param: blog 博客
	 * @param: channel 管道
	 * @param: headers headers头信息
	 * @return: void
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.blog.queue.name}",
					durable = "${spring.rabbitmq.listener.blog.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.blog.exchange.name}",
					durable = "${spring.rabbitmq.listener.blog.exchange.durable}",
					type = "${spring.rabbitmq.listener.blog.exchange.type}",
					ignoreDeclarationExceptions = "${spring.rabbitmq.listener.blog.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.blog.create.key}"
	)
	)
	@RabbitHandler
	public void consumeBlogMessage(@Payload BlogWithBLOBs blogWithBLOBs, Channel channel, @Headers Map<String, String> headers) throws IOException {
		log.info("消息id为:{}，接收的博客消息为:{}", blogWithBLOBs.getCreateMessageId(), JSON.toJSONString(blogWithBLOBs));

		//TODO 类型转换
		String deliveryTagStr = String.valueOf(headers.get(AmqpHeaders.DELIVERY_TAG));
		log.info("deliveryTag:{}", headers.get(AmqpHeaders.DELIVERY_TAG));
		Long deliveryTag = Long.valueOf(deliveryTagStr);

		/**
		 *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
		 *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
		 */
		channel.basicAck(deliveryTag, false);

		// 消息消息次数加1
		MessageLog updateMessageLog = MessageLog.builder()
				.messageId(blogWithBLOBs.getCreateMessageId()).consumeCount(RabbitMQConst.MessageCount.ADD_COUNT).updateTime(new Date())
				.build();
		messageLogMapper.updateMessageCount(updateMessageLog);
	}
}

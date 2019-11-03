package com.ccarlos.blog.mq.rabbitmq.producer;

import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.dao.MessageLogMapper;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.MessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;

import java.util.Date;

/**
 * @description: 博客生产者(RabbitMQ)
 * @author: ccarlos
 * @date: 2019/6/19 12:38
 */
@Slf4j
@Component
public class RabbitMQBlogProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private MessageLogMapper messageLogMapper;

	/**
	 * @description: 发送消息方法调用：构建自定义对象消息
	 * @author: ccarlos
	 * @date: 2019/6/19 12:43
	 * @param: blog 博客
	 * @return: void
	 */
	public void sendBlog(BlogWithBLOBs blogWithBLOBs) {
		//监听回调函数
		rabbitTemplate.setConfirmCallback(confirmCallback);

		CorrelationData correlationData = new CorrelationData(blogWithBLOBs.getCreateMessageId());

		//发送消息
		rabbitTemplate.convertAndSend(RabbitMQConst.BlogMessage.CREATE_EXCHANGE, //交换机
				RabbitMQConst.BlogMessage.CREATE_ROUTING_KEY, //路由键
				blogWithBLOBs, //消息体内容
				correlationData); //消息唯一ID

		// 发送次数加1
		MessageLog updateMessageLog=MessageLog.builder()
				.messageId(blogWithBLOBs.getCreateMessageId()).sendCount(RabbitMQConst.MessageCount.ADD_COUNT).updateTime(new Date())
				.build();
		messageLogMapper.updateMessageCount(updateMessageLog);
		log.info("消息ID为:{}的消息发送成功，correlationData:{}", blogWithBLOBs.getCreateMessageId(), correlationData);
	}

	final ConfirmCallback confirmCallback = new ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String s) {
			String messageId = correlationData.getId();
			if (ack) {
				//发送成功，更新状态
				messageLogMapper.updateStatusByMessageId(messageId, RabbitMQConst.MessageStatus.SEND_SUCCESS, new Date());
				log.info("消息id:{}的消息，已经ACK", messageId);
			} else {
				//失败则进行具体的后续操作：重试或者补偿等手段(定时器处理)
				log.error("消息ID为:{}的消息发送失败，进行定时器处理", messageId);
			}
		}
	};


}

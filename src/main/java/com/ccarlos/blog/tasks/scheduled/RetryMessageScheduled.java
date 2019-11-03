package com.ccarlos.blog.tasks.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.dao.MessageLogMapper;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.MessageLog;
import com.ccarlos.blog.mq.rabbitmq.producer.RabbitMQBlogProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @description: 消息重试定时器
 * @author: ccarlos
 * @date: 2019/6/27 23:18
 */
@Slf4j
@Component
public class RetryMessageScheduled {

	@Autowired
	private RabbitMQBlogProducer rabbitMQBlogProducer;

	@Autowired
	private MessageLogMapper messageLogMapper;

	/**
	 * @description: mq消息记录重发定时器
	 * @author: ccarlos
	 * @date: 2019/6/27 23:28
	 * @param:
	 * @return: void
	 * @throws:
	 */
	@Scheduled(initialDelay = 5000, fixedDelay = 600000)
	public void retryMessageLog() {
		log.info("RabbitMQ Scheduled消息记录重发定时任务开启");
		List<MessageLog> messageLogList = messageLogMapper.selectTimeOutMessageListByStatus(RabbitMQConst.MessageStatus.SENDING);
		log.info("消息重试数量:{}",messageLogList.size());
		messageLogList.forEach(messageLog -> {
			String messageId = messageLog.getMessageId();
			//超过3次，状态更新为发送失败状态
			if (messageLog.getTryCount() >= RabbitMQConst.MessageCount.RETRY_COUNT) {
				log.info("消息id:{}重发次数，已经超过3次", messageId);
				messageLogMapper.updateStatusByMessageId(messageId, RabbitMQConst.MessageStatus.SEND_FAILURE, new Date());
			} else {

				//更新重试次数、重试时间等
				MessageLog updateMessageLog=MessageLog.builder()
						.messageId(messageId).tryCount(RabbitMQConst.MessageCount.ADD_COUNT)
						.nextRetryTime(DateUtils.addMinutes(new Date(), RabbitMQConst.MessageTime.SEND_TIMEOUT))
						.build();
				messageLogMapper.updateMessageCount(updateMessageLog);
				BlogWithBLOBs ReSendBlogWithBLOBs = JSONObject.parseObject(messageLog.getMessage(), BlogWithBLOBs.class);
				try {
					rabbitMQBlogProducer.sendBlog(ReSendBlogWithBLOBs);
					log.info("消息id:{}消息重发次数加1", messageId);
				} catch (Exception e) {
					//TODO 需要进行异常处理
					log.error("消息id:{}的消息重发发生异常,异常原因:{}，异常描述:{}", messageId, e.getMessage(), e.toString());
				}
			}
		});
	}
}

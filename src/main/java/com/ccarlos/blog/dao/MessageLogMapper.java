package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.MessageLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MessageLogMapper {
	int deleteByPrimaryKey(Long id);

	int insert(MessageLog record);

	int insertSelective(MessageLog record);

	MessageLog selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MessageLog record);

	int updateByPrimaryKeyWithBLOBs(MessageLog record);

	int updateByPrimaryKey(MessageLog record);

	/**
	 * @description: 根据消息Id更新消息记录表状态
	 * @author: ccarlos
	 * @date: 2019/6/19 14:52
	 * @param: messageId 消息id
	 * @param: sendSuccess 消息体状态(0.发送中 1.发送成功 2.发送失败)
	 * @param: date 更新时间
	 * @return: int
	 */
	int updateStatusByMessageId(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);

	/**
	 * @description: 根据消息状态和超时时间，查询超时的消息记录表
	 * @author: ccarlos
	 * @date: 2019/6/27 23:35
	 * @return: java.util.List<com.ccarlos.blog.model.MessageLog>
	 */
	List<MessageLog> selectTimeOutMessageListByStatus(String status);

	/**
	 * @description: 根据消息id重置消息次数+1
	 * @author: ccarlos
	 * @date: 2019/6/28 1:16
	 * @param: messageId 消息id
	 * @return: int
	 */
	int updateMessageCount(MessageLog messageLog);

	/**
	 * @description: 根据消息id重置消息发送次数+1
	 * @author: ccarlos
	 * @date: 2019/6/28 9:11
	  * @param: messageId 消息id
	 * @param: sendCount 发送次数
	 * @return: int
	 */
//	int updateSendCount(@Param("messageId") String messageId, @Param("sendCount") Integer sendCount);

	/**
	 * @description: 更新消息记录表结果状态 成功 or 失败
	 * @author: ccarlos
	 * @date: 2019/6/27 23:52
	 * @param: messageId 消息id
	 * @param: status 状态
	 * @return: int
	 */
//	int updateMessageLogStatus(String messageId, String status);
}
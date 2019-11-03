package com.ccarlos.blog.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 博客实体类
 * @author: ccarlos
 * @date: 2019/5/28 9:54
 */
/*@Entity*/
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {

	private static final long serialVersionUID = 8136571334226855579L;
	//博文id
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	//标题
	/*@NotEmpty(message = "标题不能为空")
	@Size(min=2, max=50)*/
//	@NotNull(message = "标题不能为空")
	private String title;

	//摘要
/*	@NotEmpty(message = "摘要不能为空")
	@Size(min=2, max=300)*/
//	@NotNull(message = "摘要不能为空")
	private String blogDigest;

	//标签
	private String label;

	//阅读量
	private Integer readCount;

	//评论量
	private Integer commentCount;

	//点赞量
	private Integer voteCount;

	//分类id
	private Long categoryId;

	//用户id
	private Long userId;

	//创建消息id
	private String createMessageId;

	//更新消息id
	private String updateMessageId;

	//创建的消息队列类型(RabbitMQ、RocketMQ、Kafaka)
	private String createMessageType;

	//更新的消息队列类型(RabbitMQ、RocketMQ、Kafaka)
	private String updateMessageType;

	//创建时间
	private Date createTime;

	//更新时间
	private Date updateTime;
}

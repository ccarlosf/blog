package com.ccarlos.blog.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageLog {

    //主键id
    private Long id;

    //消息id
    private String messageId;

    //消息体状态(0.发送中 1.发送成功 2.发送失败)
    private String status;

    //消息类型(0.创建 1.更新 2.查询)
    private Integer messageType;

    //消息队列类型(RabbitMQ、RocketMQ、Kafaka)
    private String mqType;

    //发送次数
    private Integer sendCount;

    //消费次数
    private Integer consumeCount;

    //消息重试次数
    private Integer tryCount;

    //下次重试时间
    private Date nextRetryTime;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //消息体
    private String message;

}
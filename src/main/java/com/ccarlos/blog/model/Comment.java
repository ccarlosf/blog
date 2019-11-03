package com.ccarlos.blog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Comment {

    //评论id
    private Long id;

    //评论内容
    private String content;

    //用户id
    private Long userId;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
}
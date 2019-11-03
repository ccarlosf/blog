package com.ccarlos.blog.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment {

    //博客id
    private Long blogId;

    //评论id
    private Long commentId;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
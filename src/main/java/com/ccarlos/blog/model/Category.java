package com.ccarlos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    //分类id
    private Long id;

    //分类名
    private String categoryName;

    //用户id
    private Long userId;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
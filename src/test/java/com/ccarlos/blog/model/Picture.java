package com.ccarlos.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: 图片实体测试类
 * @author: ccarlos
 * @date: 2019/5/10 16:49
 */
@Getter
@Setter
@NoArgsConstructor
public class Picture implements Serializable {

	private static final long serialVersionUID = -6157451015984047471L;

	private String picUrl;

	private String picId;
}

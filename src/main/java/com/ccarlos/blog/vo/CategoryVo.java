package com.ccarlos.blog.vo;

import com.ccarlos.blog.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: 分类Vo对象
 * @author: ccarlos
 * @date: 2019/5/30 19:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo implements Serializable {

	private static final long serialVersionUID = 3401221721279841554L;

	//用户名
	private String username;

	//分类对象
	private Category category;
}

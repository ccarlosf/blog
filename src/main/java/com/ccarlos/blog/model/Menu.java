package com.ccarlos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 菜单实体
 * @author: ccarlos
 * @date: 2019/4/24 14:43
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Menu implements Serializable {
	private static final long serialVersionUID = 744166073680246511L;

	//菜单id
	private Integer id;

	//菜单名
	private String menuName;

	//菜单url
	private String menuUrl;

	//启用状态 1：正常，0：冻结状态，2：删除
	private Integer status;

	//创建者
	private String creator;

	//更新者
	private String updater;

	//创建时间
	private Date createTime;

	//更新时间
	private Date updateTime;
}

package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.Menu;

import java.util.List;

/**
 * @description: 菜单Dao层接口定义
 * @author: ccarlos
 * @date: 2019/4/24 16:50
 */
public interface MenuMapper {

	/**
	 * @description: 查询所有的菜单信息
	 * @author: ccarlos
	 * @date: 2019/4/24 16:52
	 * @return: java.util.List<com.ccarlos.blog.model.Menu>
	 */
	List<Menu> selectAllMenu();

	/**
	 * @description: 插入一条菜单数据
	 * @author: ccarlos
	 * @date: 2019/4/29 14:17
	 * @return: int
	 */
	int insertMenu(Menu menu);
}

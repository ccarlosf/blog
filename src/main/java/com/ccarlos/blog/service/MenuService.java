package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Menu;

import java.util.List;

/**
 * @description: 菜单服务接口
 * @author: ccarlos
 * @date: 2019/4/24 16:31
 */
public interface MenuService {

	/**
	 * @description: 获取菜单列表
	 * @author: ccarlos
	 * @date: 2019/4/24 16:48
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<List<Menu>> getMenuList();
}

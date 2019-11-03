package com.ccarlos.blog.service.impl;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dao.MenuMapper;
import com.ccarlos.blog.model.Menu;
import com.ccarlos.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 菜单服务接口实现
 * @author: ccarlos
 * @date: 2019/4/24 16:49
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	/**
	 * @description: 获取所有的菜单信息
	 * @author: ccarlos
	 * @date: 2019/4/24 17:01
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	public JsonResponse<List<Menu>> getMenuList() {
		List<Menu> menuList=menuMapper.selectAllMenu();
		return JsonResponse.createBySuccess(menuList);
	}
}

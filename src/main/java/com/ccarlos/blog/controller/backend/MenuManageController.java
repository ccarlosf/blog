package com.ccarlos.blog.controller.backend;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Menu;
import com.ccarlos.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 菜单管理控制器
 * @author: ccarlos
 * @date: 2019/4/24 16:37
 */
@RestController
@RequestMapping("/menu")
public class MenuManageController {

	@Autowired
	private MenuService menuService;

	/**
	 * @description: 获取菜单列表
	 * @author: ccarlos
	 * @date: 2019/4/24 16:43
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@GetMapping("/list")
	public JsonResponse<List<Menu>> getMenuList(){
 		return menuService.getMenuList();
	}

}

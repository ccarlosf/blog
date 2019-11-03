package com.ccarlos.blog.controller.backend;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Menu;
import com.ccarlos.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @description: 后台端视图路由控制器
 * @author: 180493
 * @date: 2019/4/23 10:23
 */
@Controller
@RequestMapping("/manage")
public class BackendController {

	@Autowired
	private MenuService menuService;

	/**
	 * @description: 博客后台管理视图路由
	 * @author: ccarlos
	 * @date: 2019/4/24 16:34
	 * @return: java.lang.String
	 */
	@GetMapping("/manage_view")
	public ModelAndView manageView(Model model){
		JsonResponse<List<Menu>> response = menuService.getMenuList();
		model.addAttribute("list", response.getData());
		return new ModelAndView("/admins/manage", "model", model);
	}

	/**
	 * @description: 添加用户视图路由
	 * @author: ccarlos
	 * @date: 2019/4/23 11:03
	 * @return: java.lang.String
	 */
	@GetMapping("/add_user_view")
	public String addUserView() {
		return "/users/add";
	}
}

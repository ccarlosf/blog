package com.ccarlos.blog.controller.backend;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 用户管理控制器
 * @author: ccarlos
 * @date: 2019/4/23 10:05
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {

	@Autowired
	private UserService userService;

	/**
	 * @description: 获取用户列表(带分页) (根据用户名进行模糊搜索)
	 * @author: ccarlos
	 * @date: 2019/5/16 14:43
	 * @param: async 是否异步
	 * @param: pageNum 页码
	 * @param: pageSize 页大小
	 * @param: keyword 查询关键字
	 * @param: model 模型
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@GetMapping("/list")
	public ModelAndView getUserListByKeywordUsername(@RequestParam(value = "async", required = false) boolean async,
	                                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
	                                                 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {

		JsonResponse<PageInfo> pageInfo = userService.getUserListByKeywordUsername(keyword, pageNum, pageSize);

		model.addAttribute("page", pageInfo.getData());
		model.addAttribute("userList", pageInfo.getData().getList());
		return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel",
				model);
	}


	/**
	 * @description: 根据用户id删除用户
	 * @author: ccarlos
	 * @date: 2019/5/16 14:50
	 * @param: id 用户id
	 * @param: model 模型
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@DeleteMapping(value = "/{id}")
	public JsonResponse<String> removeUser(@PathVariable("id") Integer id, Model model) {
		return userService.removeUser(id);
	}


	/**
	 * @description: 获取修改用户的界面及数据
	 * @author: ccarlos
	 * @date: 2019/5/16 16:12
	 * @param: id 用户id
	 * @param: model 模型
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@GetMapping(value = "/edit/{id}")
	public ModelAndView modifyUserForm(@PathVariable("id") Integer id, Model model) {
		JsonResponse<User> response = userService.getUserById(id);
		model.addAttribute("user", response.getData());
		return new ModelAndView("users/edit", "userModel", model);
	}

	/**
	 * @description: 修改用户信息
	 * @author: ccarlos
	 * @date: 2019/5/16 16:14
	 * @param: user 用户实体
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@PostMapping
	public JsonResponse<String> updateUser(User user) {
		return userService.updateUser(user);
	}


}

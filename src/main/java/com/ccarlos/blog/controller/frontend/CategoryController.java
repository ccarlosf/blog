package com.ccarlos.blog.controller.frontend;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Category;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.service.CategoryService;
import com.ccarlos.blog.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 分类控制器
 * @author: ccarlos
 * @date: 2019/5/30 19:13
 */
@Slf4j
@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * @description: 添加、修改分类
	 * @author: ccarlos
	 * @date: 2019/5/30 19:19
	 * @param: categoryVo 分类Vo对象
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@PostMapping
	@ResponseBody
	public JsonResponse<String> addCategory(@RequestBody CategoryVo categoryVo, User user) {
		log.info("添加分类的Json格式数据为：{}", JSON.toJSONString(categoryVo));
		String username = categoryVo.getUsername();
		Category category = categoryVo.getCategory();
//		User user = (User) userDetailsService.loadUserByUsername(username);
		category.setUserId(Long.valueOf(user.getId()));
		//分类id为空时，添加分类
		if(category.getId()==null){
			return categoryService.addCategory(category);
		}
		//分类id不为空时，修改分类
		return categoryService.modifyCategory(category);
	}


	/**
	 * @description: 获取分类添加界面
	 * @author: ccarlos
	 * @date: 2019/5/31 10:29
	 * @param: model 模型
	 * @return: java.lang.String
	 */
	@GetMapping("/edit")
	public String getCategoryEdit(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "/userspace/category_edit";
	}


	/**
	 * @description: 获取分类列表
	 * @author: ccarlos
	 * @date: 2019/5/31 10:31
	 * @param: username 用户名
	 * @param: model 模型
	 * @return: java.lang.String
	 */
	@GetMapping
	public String getCategoryList(@RequestParam(value = "username", required = true) String username, Model model, User user) {
//		User user = (User) userDetailsService.loadUserByUsername(username);
		JsonResponse<List<Category>> response = categoryService.getCategoryList(Long.valueOf(user.getId()));

		// 判断操作用户是否是分类的所有者
		model.addAttribute("categoryList", response.getData());
		return "/userspace/u :: #catalogRepleace";
	}

	/**
	 * @description: 根据id删除分类
	 * @author: ccarlos
	 * @date: 2019/5/31 15:54
	 * @param: id 分类id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@ResponseBody
	@DeleteMapping("/{id}")
	public JsonResponse<String> removeCategory(@PathVariable("id") Long id) {
		return categoryService.removeCategory(id);
	}

	/**
	 * @description: 获取分类修改界面
	 * @author: ccarlos
	 * @date: 2019/6/2 11:04
	 * @param: id 分类id
	 * @param: model 模型
	 * @return: java.lang.String
	 */
	@GetMapping("/edit/{id}")
	public String getCategoryById(@PathVariable("id") Long id, Model model) {
		JsonResponse<Category> response = categoryService.getCategoryById(id);
		model.addAttribute("category", response.getData());
		return "/userspace/category_edit";
	}

}

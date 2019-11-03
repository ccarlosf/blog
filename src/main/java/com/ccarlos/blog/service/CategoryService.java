package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Category;

import java.util.List;

/**
 * @description: 分类服务接口
 * @author: ccarlos
 * @date: 2019/5/30 19:13
 */
public interface CategoryService {

	/**
	 * @description: 添加分类
	 * @author: ccarlos
	 * @date: 2019/5/30 19:37
	 * @param: category 分类对象
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> addCategory(Category category);

	/**
	 * @description: 根据用户id获取分类列表
	 * @author: ccarlos
	 * @date: 2019/5/31 10:34
	 * @param: userId 用户id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.util.List   <   com.ccarlos.blog.model.Category>>
	 */
	JsonResponse<List<Category>> getCategoryList(Long userId);

	/**
	 * @description: 根据id删除分类
	 * @author: ccarlos
	 * @date: 2019/5/31 15:55
	 * @param: id 分类id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> removeCategory(Long id);

	/**
	 * @description: 根据分类id获取分类信息
	 * @author: ccarlos
	 * @date: 2019/6/2 11:05
	 * @param: id 分类id
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.Category>
	 */
	JsonResponse<Category> getCategoryById(Long id);

	/**
	 * @description: 修改分类
	 * @author: ccarlos
	 * @date: 2019/6/2 11:42
	 * @param: category 分类
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> modifyCategory(Category category);

}

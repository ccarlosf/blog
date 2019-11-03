package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description: 用户服务层接口定义
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:38
 */
public interface UserService {

	/**
	 * @description: 通过token获取用户
	 * @author: ccarlos
	 * @date: 2019/4/5 18:57
	 * @param: response 响应对象
	 * @param: token token值
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.User>
	 */
	JsonResponse<User> getUserByToken(HttpServletResponse response, String token);

	/**
	 * @description: 获取用户列表(带分页) (根据用户名进行模糊搜索)
	 * @author: ccarlos
	 * @date: 2019/4/25 19:58
	 * @param: keyword 搜索关键字
	 * @param: pageNum 页码
	 * @param: pageSize 页大小
	 * @return: com.ccarlos.blog.common.JsonResponse<com.github.pagehelper.PageInfo>
	 */
	JsonResponse<PageInfo> getUserListByKeywordUsername(String keyword, int pageNum, int pageSize);

	/**
	 * @description: 删除
	 * @author: ccarlos
	 * @date: 2019/5/16 14:52
	 * @param: id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 * @throws:
	 */
	JsonResponse<String> removeUser(Integer id);

	/**
	 * @description: 根据用户id获取用户数据
	 * @author: ccarlos
	 * @date: 2019/5/16 15:41
	 * @param: id
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.User>
	 */
	JsonResponse<User> getUserById(Integer id);

	/**
	 * @description: 修改用户信息
	 * @author: ccarlos
	 * @date: 2019/5/16 16:16
	 * @param: user 用户实体
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> updateUser(User user);

	/**
	 * @description: 修改用户头像地址
	 * @author: ccarlos
	 * @date: 2019/5/27 10:52
	 * @param: user 用户对象
	 * @return: com.ccarlos.blog.common.JsonResponse
	 */
	JsonResponse<String> modifyAvatar(User user);

	/**
	 * @description: 修改个人设置
	 * @author: ccarlos
	 * @date: 2019/5/27 15:09
	 * @param: user 用户对象
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> modifyProfile(User user);
}


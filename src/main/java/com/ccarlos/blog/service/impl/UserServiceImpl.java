package com.ccarlos.blog.service.impl;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dao.UserMapper;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.redis.RedisService;
import com.ccarlos.blog.redis.UserKey;
import com.ccarlos.blog.service.LocalAuthService;
import com.ccarlos.blog.service.UserService;
import com.ccarlos.blog.util.MD5Util;
import com.ccarlos.blog.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户服务层接口实现
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:39
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private LocalAuthService localAuthService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserMapper userMapper;

	/**
	 * @description: 通过token获取用户
	 * @author: ccarlos
	 * @date: 2019/4/5 18:59
	 * @param: response 响应对象
	 * @param: token token值
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.User>
	 */
	@Override
	public JsonResponse<User> getUserByToken(HttpServletResponse response, String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		User user = redisService.get(UserKey.userTokenKey, token, User.class);
		//验证通过,延长有效期
		if (user != null) {
			localAuthService.addCookie(response, user, token);
		}
		return JsonResponse.createBySuccess(user);
	}


	/**
	 * @description: 获取用户列表(带分页) (根据用户名进行模糊搜索)
	 * @author: ccarlos
	 * @date: 2019/4/25 19:58
	 * @param: keyword 搜索关键字
	 * @param: pageNum 页码
	 * @param: pageSize 页大小
	 * @return: com.ccarlos.blog.common.JsonResponse<com.github.pagehelper.PageInfo>
	 */
	@Override
	public JsonResponse<PageInfo> getUserListByKeywordUsername(String keyword, int pageNum, int pageSize) {
		if (StringUtils.isBlank(keyword)) {

		}
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("update_time DESC");
		List<User> userList = userMapper.selectUserByUsername(keyword);
		PageInfo pageInfo = new PageInfo(userList);
		pageInfo.setList(userList);
		return JsonResponse.createBySuccess(pageInfo);
	}

	/**
	 * @description: 根据用户id删除用户
	 * @author: ccarlos
	 * @date: 2019/5/16 14:54
	 * @param: id 用户id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	@Transactional
	public JsonResponse<String> removeUser(Integer id) {

		int resultCount = userMapper.deleteUserById(id);
		if (resultCount > 0) {
			return JsonResponse.createBySuccessMessage("删除用户成功");
		}
		return JsonResponse.createByErrorMessage("删除用户失败");
	}

	/**
	 * @description: 根据用户id获取用户数据
	 * @author: ccarlos
	 * @date: 2019/5/16 15:42
	 * @param: id 用户id
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.User>
	 */
	@Override
	public JsonResponse<User> getUserById(Integer id) {
		User user = userMapper.selectUserById(id);
		if (user == null) {
			return JsonResponse.createByErrorMessage("该用户不存在");
		}
		user.setPassword(StringUtils.EMPTY);
		return JsonResponse.createBySuccess(user);
	}

	/**
	 * @description: 修改用户信息
	 * @author: ccarlos
	 * @date: 2019/5/16 16:16
	 * @param: user 用户实体
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	@Transactional
	public JsonResponse<String> updateUser(User user) {
		//username是不能被更新的
		//email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
		int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
		if (resultCount > 0) {
			return JsonResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
		}
		int updateCount = userMapper.updateUser(user);
		if (updateCount > 0) {
			return JsonResponse.createBySuccessMessage("更新个人信息成功");
		}
		return JsonResponse.createByErrorMessage("更新个人信息失败");
	}

	/**
	 * @description: 修改用户头像地址
	 * @author: ccarlos
	 * @date: 2019/5/27 10:52
	 * @param: user 用户对象
	 * @return: com.ccarlos.blog.common.JsonResponse
	 */
	@Override
	@Transactional
	public JsonResponse<String> modifyAvatar(User user) {
		user.setUpdateTime(new Date());
		int updateCount = userMapper.updateUser(user);
		if (updateCount > 0) {
			return JsonResponse.createBySuccess("修改头像成功", user.getAvatar());
		}
		return JsonResponse.createByErrorMessage("修改头像失败");
	}

	/**
	 * @description: 修改个人设置
	 * @author: ccarlos
	 * @date: 2019/5/27 15:09
	 * @param: user 用户对象
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 * @throws:
	 */
	@Override
	@Transactional
	public JsonResponse<String> modifyProfile(User user) {
		user.setUpdateTime(new Date());
		User existUser = userMapper.selectUserById(user.getId());
		if (!existUser.getPassword().trim().equals(user.getPassword().trim())) {
			String salt = UUIDUtil.getUUID();
			user.setSalt(salt);
			user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword() + salt));
			user.setPassword(StringUtils.EMPTY);
			user.setPassword(null);
			user.setSalt(null);
			log.info("用户Id:{},在修改个人设置时，修改了密码", user.getId());
		}
		int updateCount = userMapper.updateUser(user);
		if (updateCount > 0) {
			return JsonResponse.createBySuccess("修改个人设置成功");
		}
		return JsonResponse.createByErrorMessage("修改个人设置成功");
	}
}

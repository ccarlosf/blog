package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: 用户服务接口测试
 * @author: ccarlos
 * @date: 2019/5/16 10:16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	/**
	 * @description: 测试获取用户列表(带分页) (根据用户名进行模糊搜索)
	 * @author: ccarlos
	 * @date: 2019/5/16 10:17
	 * @return: void
	 */
	public void testGetUserListByKeywordUsername(){
		/*PageHelper.startPage(1,10);
		PageHelper.orderBy("update_time DESC");
		List<User> userList=userMapper.selectUserByUsername(keyword);
		PageInfo pageInfo=new PageInfo(userList);
		pageInfo.setList(userList);
		return JsonResponse.createBySuccess(pageInfo);*/
//		userService
	}
}

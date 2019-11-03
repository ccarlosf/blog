package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: 用户dao层接口测试类
 * @author: ccarlos
 * @date: 2019/4/29 14:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	/**
	 * @description: 批量插入用户数据
	 * @author: ccarlos
	 * @date: 2019/5/16 10:09
	 * @return: void
	 */
	@Test
	public void testBatchInsertUser() {
//		log.info(userMapper.selectUserByUsername());
	}

	/**
	 * @description: 测试查询用户列表
	 * @author: ccarlos
	 * @date: 2019/5/16 10:11
	 * @return: void
	 */
	@Test
	public void testSelectUserList() {
		List<User> userList = userMapper.selectUserByUsername("");
		log.info("userList的长度:{}", userList.size());
	}
}

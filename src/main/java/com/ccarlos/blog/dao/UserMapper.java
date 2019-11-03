package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 用户Dao层接口定义
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:04
 */
public interface UserMapper {

	/**
	 * @description: 新增用户
	 * @author: ccarlos
	 * @date: 2019/2/25 22:05
	 * @param: users 用户实体类
	 * @return: int
	 */
	int insertUser(User users);

	/**
	 * @description: 查询该用户名个数
	 * @author: ccarlos
	 * @date: 2019/3/31 19:27
	 * @param: username 用户名
	 * @return: int
	 */
	int checkUsername(String username);

	/**
	 * @description: 查询该邮箱个数
	 * @author: ccarlos
	 * @date: 2019/3/31 19:28
	 * @param: email 邮箱
	 * @return: int
	 */
	int checkEmail(String email);

	/**
	 * @description: 根据用户名、密码查询用户
	 * @author: ccarlos
	 * @date: 2019/4/3 18:47
	 * @param: username 用户名
	 * @param: password 密码
	 * @return: com.ccarlos.blog.model.User
	 */
	User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	/**
	 * @description: 根据用户名或者邮箱查询用户
	 * @author: ccarlos
	 * @date: 2019/4/3 19:17
	 * @param: username 用户名
	 * @param: email 邮箱
	 * @return: com.ccarlos.blog.model.User
	 */
	User selectByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

	/**
	 * @description: 根据用户名模糊查询用户列表
	 * @author: ccarlos
	 * @date: 2019/4/25 20:07
	 * @param: username 用户名
	 * @return: java.util.List<com.ccarlos.blog.model.User>
	 */
	List<User> selectUserByUsername(@Param("username") String username);

	/**
	 * @description: 根据用户id删除用户
	 * @author: ccarlos
	 * @date: 2019/5/16 15:00
	 * @param: id 用户id
	 * @return: int
	 */
	int deleteUserById(Integer id);

	/**
	 * @description: 根据用户id查询用户
	 * @author: ccarlos
	 * @date: 2019/5/16 15:46
	  * @param: id 用户id
	 * @return: com.ccarlos.blog.model.User
	 */
	User selectUserById(Integer id);

	/**
	 * @description: 查询不是当前用户，该邮箱是否存在
	 * @author: ccarlos
	 * @date: 2019/5/16 16:20
	  * @param: email 邮箱
	 * @param: id 用户id
 	 * @return: int
	 */
	int checkEmailByUserId(@Param("email") String email, @Param("id") Integer id);

	/**
	 * @description: 更新用户信息
	 * @author: ccarlos
	 * @date: 2019/5/16 16:27
	  * @param: user 用户实体
	 * @return: int
	 */
	int updateUser(User user);
}

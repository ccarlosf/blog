package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.Category;
import com.ccarlos.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @description: 博客服务接口
 * @author: ccarlos
 * @date: 2019/5/28 13:52
 */
public interface BlogService {

	/**
	 * @description: 根据用户名和标题查询个人最热的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 14:15
	 * @param: user 用户对象
	 * @param: title 标题
	 * @param: pageable 分页对象
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	Page<Blog> getHottestBlogListByUserIdAndTitle(Pageable pageable, Long userId, String title);

	/**
	 * @description: 根据用户名和标题查询个人最新的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 16:00
	 * @param: pageable 分页对象
	 * @param: user 用户对象
	 * @param: title 标题
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	Page<Blog> getNewestBlogListByUserIdAndTitle(Pageable pageable, Long userId, String title);

	/**
	 * @description: 添加博客
	 * @author: ccarlos
	 * @date: 2019/6/18 15:32
	 * @param: blogWithBLOBs 博客
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> addBlog(BlogWithBLOBs blogWithBLOBs);

	/**
	 * @description: 根据博客id获取博客信息
	 * @author: ccarlos
	 * @date: 2019/7/2 15:24
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.BlogWithBLOBs>
	 * @throws:
	 */
	JsonResponse<BlogWithBLOBs> getBlogById(Long id);

	/**
	 * @description: 增加阅读量
	 * @author: ccarlos
	 * @date: 2019/7/2 15:41
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> increaseReadCount(Long id);

	/**
	 * @description: 根据id删除博客
	 * @author: ccarlos
	 * @date: 2019/7/3 10:47
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> removeBlog(Long id);

	/**
	 * @description: 修改博客
	 * @author: ccarlos
	 * @date: 2019/7/4 10:58
	 * @param: blogWithBLOBs 博客
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> modifyBlog(BlogWithBLOBs blogWithBLOBs);
}

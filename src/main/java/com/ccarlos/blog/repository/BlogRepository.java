package com.ccarlos.blog.repository;

import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: Blog仓库接口
 * @author: ccarlos
 * @date: 2019/5/28 14:19
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

	/**
	 * @description: 根据用户名和标题查询个人最热的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 15:53
	 * @param: user 用户对象
	 * @param: title 标题
	 * @param: pageable 分页对象
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	Page<Blog> findByUserIdAndTitleLike(Long userId, String title, Pageable pageable);

	/**
	 * @description: 根据用户名和标题查询个人最新的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 16:05
	 * @param: pageable 分页对象
	 * @param: user 用户对象
	 * @param: title 标题
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	Page<Blog> findByUserIdAndTitleLikeOrderByCreateTimeDesc(Pageable pageable, Long userId, String title);

	/**
	 * @description: 根据用户名和标题、标签查询个人最新的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 16:09
	 * @param: pageable 分页对象
	 * @param: user 用户对象
	 * @param: title 标题
	 * @param: label 标签
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	Page<Blog> findByUserIdAndTitleLikeOrLabelLikeOrderByCreateTimeDesc(Pageable pageable, Long userId, String title, String label);
}

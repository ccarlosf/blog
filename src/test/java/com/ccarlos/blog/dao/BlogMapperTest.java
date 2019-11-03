package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.Menu;
import com.ccarlos.blog.repository.BlogRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @description: 博文dao层接口测试类
 * @author: ccarlos
 * @date: 2019/5/30 12:27
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogMapperTest {

	@Autowired
	private BlogMapper blogMapper;

	/**
	 * @description: 批量插入博文
	 * @author: ccarlos
	 * @date: 2019/5/30 12:29
	 * @return: void
	 */
	@Test
	public void testBatchInsertBlog(){
		int successCount = 0;
		int failCount = 0;
		int a = 1000;
		int b = 100;
		int count = a * b;
		long startTime = System.currentTimeMillis();
		List<BlogWithBLOBs> blogWithBLOBsList= Lists.newArrayList();
		for (int i = 10000; i < count; i++) {
			Blog blog = Blog.builder()
					.id(Long.valueOf(i)).title("标题" + i).blogDigest("摘要" + i).label("标签"+i)
					.readCount(i).commentCount(i).voteCount(i).categoryId(Long.valueOf(i)).userId(Long.valueOf(100002))
					.createTime(new Date()).updateTime(new Date()).build();
			BlogWithBLOBs blogWithBLOBs=new BlogWithBLOBs();
			blogWithBLOBs.setId(blog.getId());
			blogWithBLOBs.setTitle(blog.getTitle());
			blogWithBLOBs.setBlogDigest(blog.getBlogDigest());
			blogWithBLOBs.setLabel(blog.getLabel());
			blogWithBLOBs.setReadCount(blog.getReadCount());
			blogWithBLOBs.setCommentCount(blog.getCommentCount());
			blogWithBLOBs.setVoteCount(blog.getVoteCount());
			blogWithBLOBs.setCategoryId(blog.getCategoryId());
			blogWithBLOBs.setUserId(blog.getUserId());
			blogWithBLOBs.setCreateTime(blog.getCreateTime());
			blogWithBLOBs.setUpdateTime(blog.getUpdateTime());
			blogWithBLOBs.setContent("markdown内容"+i);
			blogWithBLOBs.setHtmlContent("html内容"+i);
			blogWithBLOBsList.add(blogWithBLOBs);
			int resultCount = 0;
			try {
				resultCount = blogMapper.insert(blogWithBLOBs);
			} catch (Exception e) {
				log.info("插入第{}条数据发生异常，异常原因:{}，异常描述:{}", i, e.getMessage(), e.toString(), e);
			}
			if (resultCount == 1) {
				successCount++;
			} else {
				failCount++;
			}
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("list大小:"+blogWithBLOBsList.size());
		System.out.println("耗时cost:" + time);
		System.out.println("成功次数:" + successCount);
		System.out.println("失败次数:" + failCount);
	}
}

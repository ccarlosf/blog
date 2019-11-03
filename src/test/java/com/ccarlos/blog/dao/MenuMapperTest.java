package com.ccarlos.blog.dao;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.model.Menu;
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
 * @description: 菜单dao层接口测试类
 * @author: ccarlos
 * @date: 2019/4/29 14:07
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuMapperTest {

	@Autowired
	private MenuMapper menuMapper;


	/**
	 * @description: 测试插入1000000000条菜单信息
	 * @author: ccarlos
	 * @date: 2019/4/29 14:38
	 * @return: void
	 */
	@Test
	public void batchInsertMenu() {
		int successCount = 0;
		int failCount = 0;
		int a = 100000;
		int b = 10000;
		int count = a * b;
		long startTime = System.currentTimeMillis();
		List<Menu> menuList= Lists.newArrayList();
		for (int i = 0; i < count; i++) {
			Menu menu = Menu.builder()
					.id(i).menuName("HelloWorld" + i).menuUrl("/.avatar.jpg" + i).status(1)
					.creator("ccarlos").updater("ccarlos").createTime(new Date())
					.updateTime(new Date()).build();
			menuList.add(menu);
			int resultCount = 0;
			try {
//				resultCount = menuMapper.insertMenu(menu);
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
		System.out.println("list大小:"+menuList.size());
		System.out.println("耗时cost:" + time);
		System.out.println("成功次数:" + successCount);
		System.out.println("失败次数:" + failCount);
	}
}

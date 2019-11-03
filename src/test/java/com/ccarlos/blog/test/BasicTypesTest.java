package com.ccarlos.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 基础类型测试类
 * @author: Created by ccarlos
 * @date: 2019/3/11 21:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BasicTypesTest {


	/**
	 * @description: String类型测试
	 * @author: ccarlos
	 * @date: 2019/4/29 19:50
	 * @return: void
	 */
	//TODO 测试TODO警告
	@Test
	public void testString() {
		String str = "     1        ";
		log.info("str为空吗:{}", StringUtils.isBlank(str));
		log.info("str的长度为:{}", str.length());
	}

	/**
	 * @description: Long类型测试
	 * @author: ccarlos
	 * @date: 2019/5/17 16:34
	 * @param:
	 * @return: void
	 * @throws:
	 */
	@Test
	public void testLong() {
//        Long orderItemId=null;
//        orderItemId=1L;
//        log.info("orderItemId:{}",orderItemId);
//        "4".equals(null);
		String a = "1";
		Long al = Long.valueOf(a);
	}

	/**
	 * @description: 测试String类型相等于int类型
	 * @author: ccarlos
	 * @date: 2019/7/15 16:30
	 * @return: void
	 */
	@Test
	public void testStringEqualsInt() {
		String a = "1";
		int b = 1;
		if (a.equals(String.valueOf(b))) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}


}

package com.ccarlos.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 条件判断测试类
 * @author: Created by ccarlos
 * @date: 2019/3/30 10:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConditionTest {

	/**
	 * @description: 测试一个字符串是否在几个字符中中
	 * @author: ccarlos
	 * @date: 2019/3/30 10:31
	 * @return: void
	 */
	@Test
	public void testIndexOf() {
		String ip = "219.134.187.251,58.61.29.233,210.21.231.61,218.17.131.251,123.58.41.251";
		if (ip.indexOf("58.61.29.233") > -1) {
			System.out.println("Yes");
		} else {
			System.out.println("NO");
		}
	}

	/**
	 * @description: 测试if elseif
	 * @author: ccarlos
	 * @date: 2019/4/26 14:56
	 * @return: void
	 */
	@Test
	public void testOneIfElseIf() {
		String oneFlag = "是";
		String twoFlag = "否";
		if (oneFlag.equals("是")) {
			log.info("是");
		} else if (twoFlag.equals("否")) {
			log.info("否");
		} else {
			log.info("其他1");
		}
	}

	/**
	 * @description: 测试if elseif
	 * @author: ccarlos
	 * @date: 2019/4/26 14:56
	 * @return: void
	 */
	@Test
	public void testTwoIfElseIf() {
		String oneFlag = "否";
		if (oneFlag.equals("是")) {
			log.info("是");
		} else if (oneFlag.equals("否")) {
			log.info("否");
		} else {
			log.info("其他2");
		}
	}

	/**
	 * @description: 测试true条件
	 * @author: ccarlos
	 * @date: 2019/4/26 14:55
	 * @return: void
	 */
	@Test
	public void testTrue() {
		Boolean a = false;
		if (a) {
			log.info("YES");
		}
	}

	/**
	 * @description: 测试indexOf条件
	 * @author: ccarlos
	 * @date: 2019/4/30 16:49
	 * @return: void
	 */
	@Test
	public void testIndexOfAgain() {
		//计算包裹数
		Integer parcelQuantity = 80;
		String sellerSkuCode = "SJ00100290";
		String sellerSkuCodeList = "SJ00100080,SJ00100160,SJ00100170,SJ00100200,SJ00100210,SJ00100250,SJ00100280,SJ00100270,SJ00100290";
		if (sellerSkuCodeList.indexOf(sellerSkuCode) > -1) {
			int orderCount = (int) Math.ceil((double) parcelQuantity / (double) 10);
			parcelQuantity = Integer.valueOf(orderCount);
			log.info("手机包裹数量为:{}", parcelQuantity);
		} else {
			log.info("包裹数量:{}", parcelQuantity);
		}
	}

}

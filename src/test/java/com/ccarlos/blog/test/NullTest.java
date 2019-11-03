package com.ccarlos.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @description: 判断测试类
 * @author: ccarlos
 * @date: 2019/5/18 15:02
 */
@Slf4j
public class NullTest {

	/**
	 * @description: Long类型判空
	 * @author: ccarlos
	 * @date: 2019/5/18 15:02
	 * @return: void
	 */
	@Test
	public void testLongNull(){
		Long orderId;
		orderId=201905150413133701L;
		log.info("orderId为:{}",orderId);
		if(orderId!=null){
			log.info("YES");
		}
	}
}

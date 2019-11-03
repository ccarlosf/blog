package com.ccarlos.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @description: BigDecimal测试类
 * @author: 180493
 * @date: 2019/5/23 9:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BigDecimalTest {

	/**
	 * @description: 测试new一个BigDecimal
	 * @author: ccarlos
	 * @date: 2019/5/30 17:00
	 * @return: void
	 */
	@Test
	public void testBigDecimal() {
		String a = "";
		String b = " ";
		String c = "120.0";
//		BigDecimal a1 = new BigDecimal(a);
//		BigDecimal b1 = new BigDecimal(b);
		BigDecimal c1 = new BigDecimal(c);
//		log.info("a:{}", a1);
//		log.info("b:{}", b1);
		log.info("c:{}", c1);
	}

	/**
	 * @description: 测试BigDecimal的精度
	 * @author: ccarlos
	 * @date: 2019/5/30 17:02
	 * @return: void
	 */
	@Test
	public void testAccuracy() {
		String a = "67.0";
		String b = "0.00";
		BigDecimal aBigDecimal = new BigDecimal(a);
		BigDecimal bBigDecimal = new BigDecimal(b);
		BigDecimal cbigDecimal = aBigDecimal.subtract(bBigDecimal);
		log.info("cbigDecimal:{}", cbigDecimal);
	}

}

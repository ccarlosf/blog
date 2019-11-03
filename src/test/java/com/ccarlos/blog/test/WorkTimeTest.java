package com.ccarlos.blog.test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 工作时间测试类
 * @author: ccarlos
 * @date: 2019/4/29 20:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkTimeTest {

	/**
	 * @description: 计算这个月加了多少的班
	 * @author: ccarlos
	 * @date: 2019/4/29 20:16
	 * @return: void
	 */
	@Test
	public void testCalculateWorkTime() {
		BigDecimal totalWorkTime = new BigDecimal("0");
		String workTimeStr = "3.00,3.00,3.00,3.00,3.00,3.00,1.50,3.00,3.00,3.00," +
				"1.50,2.50,2.00,3.00,3.00,1.50,3.00,3.00,9.50,1.00,1.50,2.00," +
				"3.00,3.00,3.00,1.50,2.00,8.50,1.50,3.00,2.00,3.50";
		List<String> workTimeList = Splitter.on(",").splitToList(workTimeStr);
		List<BigDecimal> actualWorkTimeList = Lists.newArrayList();
		for (String workTime : workTimeList) {
			actualWorkTimeList.add(new BigDecimal(workTime));
		}
		for (BigDecimal actualWorkTime : actualWorkTimeList) {
			totalWorkTime=totalWorkTime.add(actualWorkTime);
		}
		System.out.println("这个月，加班小时为:"+ totalWorkTime);
		log.info("这个月，加班小时为:{}", totalWorkTime);
	}
}

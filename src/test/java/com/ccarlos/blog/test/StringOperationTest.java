package com.ccarlos.blog.test;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.model.Picture;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: 字符串操作测试类
 * @author: Created by ccarlos
 * @date: 2019/4/1 17:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StringOperationTest {

	/**
	 * @description: 测试Split操作
	 * @author: ccarlos
	 * @date: 2019/5/15 20:30
	 * @return: void
	 */
	@Test
	public void testStringSplit() {
		String blogNo = "123456";
		String blogAgainNo = "123456,7894561,456321";
		String oneBlogNo = blogNo.split(",")[0].trim();
//        blogNo = blogNo.substring(0, blogNo.indexOf(",")); //数组越界异常
		String oneAgainBlogNo = blogAgainNo.split(",")[0].trim();
		log.info("oneBlogNo:{}", oneBlogNo);
		log.info("oneAgainBlogNo:{}", oneAgainBlogNo);
	}


	/**
	 * @description: 测试SubString操作
	 * @author: ccarlos
	 * @date: 2019/5/15 20:31
	 * @return: void
	 */
	@Test
	public void testSubString() {
		String orderItemId="201611081414851";
		String exchangeOrderItemId="HHH201611081414851";
		String shipNode = "X0101443";
		int length=shipNode.length();
		String suffix = shipNode.substring(length - 7);
		String actualOrderItemId=orderItemId+suffix;
		log.info("suffix:{}", suffix);
		log.info("actualOrderItemId:{}", actualOrderItemId);

		int orderItemIdLength=actualOrderItemId.length();
		String checkOrderItemId=actualOrderItemId.substring(0,orderItemIdLength-7);
		String checkExchangeOrderItemId=actualOrderItemId.substring(0,exchangeOrderItemId.length()-7);
		log.info("checkLenght:{}", orderItemIdLength);
		log.info("checkOrderItemId:{}", checkOrderItemId);
		log.info("checkExchangeOrderItemId:{}", checkExchangeOrderItemId);
		Assert.assertEquals(checkOrderItemId, orderItemId);
		Assert.assertEquals(checkExchangeOrderItemId, orderItemId);
	}

	/**
	 * @description: 测试末尾字符截取操作
	 * @author: ccarlos
	 * @date: 2019/5/21 16:20
	 * @return: void
	 */
	@Test
	public void testSubLast(){
		String picStr="/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;";
//		String pirStr="/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;";
//		picStr = picStr.substring(0, picStr.length() - 1);
		List pictureList = Splitter.on(";").splitToList(picStr);
		List actualPictureList = Lists.newArrayList();
		for (Object picture : pictureList) {
			Picture actualPicture = new Picture();
			actualPicture.setPicUrl(String.valueOf(picture));
			actualPictureList.add(actualPicture);
		}
		log.info("图片JSON格式数据:{}",JSON.toJSONString(actualPictureList));

		String a="213123123  ";
		Long b=Long.parseLong(a);
		log.info("b:{}",b);

	}

	@Test
	public void  testPicUrlsSplit(){
		String picUrls="/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;";
//		String picUrls=";/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg;/album/2019/5/21/cfc1c4d2-28c2-415c-a1d2-d30243dd664c.jpg";
		String[] pic = picUrls.split(";");
		for(String s:pic){
			System.out.println(s);
		}
		if(picUrls.endsWith(";")){
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}

	@Test
	public void testGetStringLength(){
		String str="<?xml version='1.0' encoding='UTF-8'?><Response service=\"RouteService\"><Head>OK</Head><Body/></Response>";
		log.info("str字符串的长度为:{}",str.length());
	}
}

package com.ccarlos.blog.test;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.model.Picture;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @description: Json转换测试类
 * @author: ccarlos
 * @date: 2019/5/10 16:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JsonTest {

	/**
	 * @description: 测试把list转换为Json数据
	 * @author: ccarlos
	 * @date: 2019/5/10 16:30
	 * @return: void
	 */
	@Test
	public void testParseListsToJson() {
		//末尾带;号
//		String pictureStr = "/album/2019/5/5/9db82047-d30f-4aa7-af8d-19cacd35978d.png;/album/2019/5/5/590fc601-e424-4533-b7c6-318fec9d7f79.jpeg;/album/2019/5/5/11229b32-97b4-40a7-b5b7-03e923b263b4.jpeg;";
		//末尾不带;号
		String pictureStr = "/album/2019/5/5/9db82047-d30f-4aa7-af8d-19cacd35978d.png;/album/2019/5/5/590fc601-e424-4533-b7c6-318fec9d7f79.jpeg;/album/2019/5/5/11229b32-97b4-40a7-b5b7-03e923b263b4.jpeg";
		List pictureList = Splitter.on(";").splitToList(pictureStr);
		List actualPictureList = Lists.newArrayList();
		for (Object picture : pictureList) {
			Picture actualPicture = new Picture();
			actualPicture.setPicUrl(String.valueOf(picture));
			actualPictureList.add(actualPicture);
		}
		log.info("转换前，图片Json格式数据:{}", JSON.toJSONString(pictureList));
		log.info("转换后，图片Json格式数据:{}", JSON.toJSONString(actualPictureList));
	}
}

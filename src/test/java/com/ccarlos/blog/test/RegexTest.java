package com.ccarlos.blog.test;

import com.ccarlos.blog.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 正则表达式测试类
 * @author: Created by ccarlos
 * @date: 2019/4/1 19:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RegexTest {

    @Test
    public void testEmail(){
        String email1="adsd12231312@qq.com";
        String email2="dasdadasdad";
        String email3="asdasdada@sadasda";
        log.info("email1验证结果:{}", ValidatorUtil.isEmail(email1));
        log.info("email2验证结果:{}",ValidatorUtil.isEmail(email2));
        log.info("email3验证结果:{}",ValidatorUtil.isEmail(email3));
    }
}

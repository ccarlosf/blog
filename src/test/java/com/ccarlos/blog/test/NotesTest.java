package com.ccarlos.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 测试注释类
 * @author: Created by ccarlos
 * @date: 2019/3/10 23:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NotesTest {

    /**
     * @description: 测试方法注释
     * @author: ccarlos
     * @date: 2019/3/10 23:14
     * @param: a 参数a
     * @param: b 参数b
     * @param: c 参数c
     * @return: T 返回值类型
     * @throws: RuntimeException 运行时异常
     */
    public <T> T testMethodNotes(String a, Integer b, Boolean c) throws RuntimeException {
        T t = null;
        return t;
    }
}

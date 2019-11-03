package com.ccarlos.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: Created by ccarlos
 * @date: 2019/3/7 23:05
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    /**
     * @description: 日志测试方法
     * @author: ccarlos
     * @date: 2019/3/7 23:06
     * @return: void 无返回值类型
     */
    @GetMapping("/logs")
    public void testLogs() {
        log.trace("TRACE Hello World!");
        log.debug("DEBUG Hello World!");
        log.info("INFO Hello World!");
        log.error("ERROR Hello World!");
    }
}

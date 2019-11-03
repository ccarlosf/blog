package com.ccarlos.blog.config.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description: ftp服务器配置
 * @author: ccarlos
 * @date: 2019/5/17 20:01
 */
@Slf4j
@Getter
@Setter
@Component
@Configuration
public class FTPServerConfigBean {

  /*  //ftp服务器IP地址
    @Value("${ftp.server.ip}")
    private String serverIp;

    @Value("${ftp.port}")
    private int port;

    //ftp服务器登录用户名
    @Value("${ftp.user}")
    private String user;

    //ftp服务器登录密码
    @Value("${ftp.pass}")
    private String pass;

    //前缀
    @Value("{ftp.server.ftp.prefix}")
    private String prefix;*/

}

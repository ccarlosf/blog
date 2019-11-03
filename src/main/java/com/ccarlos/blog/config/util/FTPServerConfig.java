package com.ccarlos.blog.config.util;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: ftp服务器配置
 * @author: Created by ccarlos
 * @date: 2019/5/23 17:14
 */
@Getter
@Setter
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "ftp")
public class FTPServerConfig {

    //ftp服务器IP地址
    private String serverIp;

    //ftp服务器端口号
    private int port;

    //ftp服务器登录用户名
    private String user;

    //ftp服务器登录密码
    private String pass;

    //前缀
    private String httpPrefix;
}

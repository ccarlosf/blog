package com.ccarlos.blog.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: Redis基本信息类
 * @author: Created by ccarlos
 * @date: 2019/3/8 22:46
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisConfig {

    //redis服务器地址
    private String host;

    //redis开放端口号
    private Integer port;

    //TODO int类型还是Integer类型好
    //连接超时时间
    private Integer timeout;

    //资源池中最大连接数
    private Integer poolMaxTotal;

    //资源池允许最大空闲的连接数
    private Integer poolMaxIdle;

    //当资源池连接用尽后，调用者的最大等待时间(单位为毫秒)
    private Integer poolMaxWait;

    //向资源池借用连接时是否做连接有效性检测(ping)，无效连接会被移除
    private Boolean poolTestOnBorrow;

    //redis 连接密码
//    private String password;

}

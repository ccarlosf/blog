package com.ccarlos.blog.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户实体类
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -7789299786275895243L;
    //用户id
    private Integer id;

    // 用户名 登录名称
    private String username;

    // 用户密码
    private String password;

    //盐值
    private String salt;

    //昵称 显示名称
    private String nickname;

    //邮箱
    private String email;

    //手机号码
    private String mobile;

    //用户头像地址
    private String avatar;

    //用户角色(角色0-普通用户,1-管理员)
    private Integer role;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}

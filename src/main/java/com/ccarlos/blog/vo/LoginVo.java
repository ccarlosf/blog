package com.ccarlos.blog.vo;

import com.ccarlos.blog.validator.IsEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @description: 登录Vo对象
 * @author: Created by ccarlos
 * @date: 2019/3/31 20:18
 */
//TODO 两个NotNull
@Getter
@Setter
@ToString
public class LoginVo {

//    @NotNull
    private String username;

//    @NotNull
    @IsEmail
    private String email;

    @NotNull
    @Length(min = 6,message = "密码长度必须大于6位")
    private String password;
}

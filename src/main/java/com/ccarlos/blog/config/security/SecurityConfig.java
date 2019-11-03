package com.ccarlos.blog.config.security;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/

/**
 * @description: Spring Security 配置类
 * @author: ccarlos
 * @date: 2019/4/24 10:46
 */
/*
@EnableWebSecurity
//启用方法级别安全设置
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	*/
/**
	 * @description: 初始化Bean authenticationProvider
	 * @author: ccarlos
	 * @date: 2019/4/24 11:10
	 * @return: org.springframework.security.authentication.AuthenticationProvider
	 *//*

	//TODO 与上面初始化Bean方法调换顺序
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		//TODO 设置
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		//TODO 设置加密方式
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	*/
/**
	 * @description: 初始化Bean passwordEncoder
	 * @author: ccarlos
	 * @date: 2019/4/24 11:07
	 * @return: org.springframework.security.crypto.password.PasswordEncoder
	 *//*

	@Bean
	public PasswordEncoder passwordEncoder() {
		//使用BCrypt加密算法
		return new BCryptPasswordEncoder();
	}

	*/
/**
	 * @description: 安全认证基础信息设置
	 * @author: ccarlos
	 * @date: 2019/4/24 11:17
	 * @param: authenticationManagerBuilder 认证信息管理构造对象
	 * @return: void
	 *//*

	//TODO 使用Autowired
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
		authenticationManagerBuilder.authenticationProvider(authenticationProvider());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll() //设置为都可以访问
				.antMatchers("/manage/**").hasRole("ADMIN") //需要管理员权限才可以访问
				.antMatchers("/h2-console/**").permitAll() //设置为都可以访问
				.and()
				.formLogin()
				.loginPage("/login").failureUrl("/login-fail") //自定义登录页面与登录失败页面
				.and()
				.rememberMe().key("") //启用rememberMe功能
				.and()
				.exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到 403 页面

		http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
	}
}
*/

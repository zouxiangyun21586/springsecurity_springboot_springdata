package com.yr.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * springsecurity 配置类
 * @author zxy-un
 * 
 * 2018年7月31日 下午8:56:44
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Autowired
	UserDetailsService customUserService;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService);
		auth.authenticationProvider(authenticationProvider()); // 加密
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/addAccount","/addUser").permitAll();
		
		 http.authorizeRequests()
	         .anyRequest().authenticated() // 任何请求,登录后可以访问
	         .and().csrf().disable()
	         .formLogin()
	         .loginPage("/loginPage") // 登录页面 (mappring路径,或者login.jsp所在全路径)
	         .failureUrl("/loginPage?error") // 登录错误页面
	         .successForwardUrl("/user/a") // 登录成功后跳转的页面
	         .loginProcessingUrl("/spring_security_check_AA") // 自定义验证使用的URL (必须这样写,否则请求不到)
	         .usernameParameter("account")
	         .passwordParameter("password")
	         .permitAll() // 登录页面任意用户访问 (配置permitAll 代表不需验证,可直接访问)
	         .and().exceptionHandling().accessDeniedPage("/Access_Denied") //跳转到没权限页面
	         // 记住我功能: 点击remember me 单选框后,退出登录再次访问不需要权限的页面(admin) 会显示记住的用户进入的. 86400: 24h (换算公式: 86400(已知时间) / 3600 = h)
	 	  	 .and().logout().logoutUrl("/logout").logoutSuccessUrl("/loginPage").invalidateHttpSession(true) // 登出 登出成功Url
	         //	如果开启了CSRF 退出则需要使用POST访问，可以使用以下方式解决，但并不推荐
	         //	登陆成功后跳转的地址，以及删除的cookie名称
	         .permitAll(); // 注销行为任意访问
//		  调用过滤器(不调用也会自动加载) [这里代表我们自定义的拦截器要先于FilterSecurityInterceptor.class执行,不想写可以在myFilterSecurityInterceptor类中加注解]
//		 http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}
	
	@Override
	public void configure(WebSecurity web)throws Exception {
		 web.ignoring().antMatchers("/static/scripts/**","/static/images/**"); // 放行静态资源(解决 403)
		 // 设置不拦截规则
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(customUserService); // 去数据库中查询 用户角色权限
	    authenticationProvider.setPasswordEncoder(passwordEncoder());
	    return authenticationProvider;
	}
}

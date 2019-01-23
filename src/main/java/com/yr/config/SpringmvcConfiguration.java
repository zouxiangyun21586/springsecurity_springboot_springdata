package com.yr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringmvcConfiguration implements WebMvcConfigurer  {
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) { // 可以方便的实现一个请求直接映射成视图，而无需书写controller
        registry.addViewController("/loginPage").setViewName("login"); // 登录页面
     }
	
	@Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {  
	    registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/scripts/");
	    registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
	}

}
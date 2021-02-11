package com.myblog.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.myblog.myblog.interceptor.LoginInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin")
				.excludePathPatterns("/admin/login");
		
		
	}
	
	

}

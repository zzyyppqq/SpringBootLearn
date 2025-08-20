package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.interceptor.LogInterceptor;
import com.zyp.springboot.learn.interceptor.OldLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());

        registry.addInterceptor(new OldLoginInterceptor()).addPathPatterns("/admin/oldLogin");

    }
}

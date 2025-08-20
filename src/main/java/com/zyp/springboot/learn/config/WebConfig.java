package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.interceptor.LogInterceptor;
import com.zyp.springboot.learn.interceptor.OldLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 如果你想保留那些Spring Boot MVC定制，并进行更多的 MVC定制（Interceptor、Formatter、视图控制器和其他功能），
 * 你可以添加你自己的 @Configuration 类，类型为 WebMvcConfigurer ，但 不 含 @EnableWebMvc。
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());

        registry.addInterceptor(new OldLoginInterceptor()).addPathPatterns("/admin/oldLogin");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }
}

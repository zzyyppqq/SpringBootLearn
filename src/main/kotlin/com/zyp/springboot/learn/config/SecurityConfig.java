package com.zyp.springboot.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * 打开本地的浏览器，访问http://localhost:8080。此时会重定向到登录页面，
 * 这是因为在项目创建时，我们添加了Spring Security依赖包，它默认启用了认证和授权机制。
 * 我们暂时先加个配置，以绕过Spring Security栈，允许匿名访问所有接口。
 */
@Configuration // 1. 表示这是一个配置类
public class SecurityConfig {
    @Bean // 2. 自定义的配置Bean
    public static WebSecurityCustomizer webSecurityCustomizer() {
        // 这里ignore的就完全不会进入Security栈了
        return (web -> web.ignoring().requestMatchers("/*"));
    }
}

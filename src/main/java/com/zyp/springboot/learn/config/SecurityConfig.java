package com.zyp.springboot.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 打开本地的浏览器，访问http://localhost:8080。此时会重定向到登录页面，
 * 这是因为在项目创建时，我们添加了Spring Security依赖包，它默认启用了认证和授权机制。
 * 我们暂时先加个配置，以绕过Spring Security栈，允许匿名访问所有接口。
 */
@Configuration // 1. 表示这是一个配置类
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerExceptionResolver resolver) throws Exception {
        // 1. 打开cors
        http.cors(Customizer.withDefaults());

        // 访问http://localhost:8088/druid/index.html登陆账号密码有HTTP 403错误，可能是Spring Security拦截了请求。需在安全配置中添加白名单
        http.csrf().disable().authorizeHttpRequests((requests) -> requests
                .antMatchers("/druid/**").permitAll());

        // 2. 暂时先允许所有接口的匿名访问
        http.authorizeHttpRequests((requests) -> requests
                .antMatchers("/**").permitAll()); //
        return http.build();
    }

    @Bean
    public static WebSecurityCustomizer webSecurityCustomizer() {
        // 这里ignore的就完全不会进入Security栈了
//        return (web -> web.ignoring().antMatchers("/*"));
        // 3. 所有url要走security栈
        // Spring MVC也有CORS控制，但是我们希望CORS也都统一在Security栈。
        return (web -> {});
    }

    // 4. Spring Security的cors配置
    @Bean(value = "corsConfigurationSource") // 按名称注入的， 因此名称要固定不变
    public static CorsConfigurationSource corsConfigurationSource() {
        var configSource = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        // 允许跨域访问
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        configSource.registerCorsConfiguration("/**", config);
        return configSource;
    }
}

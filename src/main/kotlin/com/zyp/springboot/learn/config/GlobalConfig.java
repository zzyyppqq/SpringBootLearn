package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.infra.errorcode.ErrorCodeRegister;
import com.zyp.springboot.learn.infra.global.AccessFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Configuration
public class GlobalConfig {
    @Bean
    public static ErrorCodeRegister Init() {
        log.info("GlobalConfig Init");
        ErrorCodeRegister.init();
        return new ErrorCodeRegister();
    }

    @Bean
    public static FilterRegistrationBean<AccessFilter> accessFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        log.info("GlobalConfig accessFilter");
        var accessFilter = new AccessFilter(resolver);
        /** 其它属性配置 **/
        var registrationBean = new FilterRegistrationBean<AccessFilter>();
        registrationBean.setFilter(accessFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MIN_VALUE); // 确保它是第一个Filter
        return registrationBean;
    }

}
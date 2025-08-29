package com.zyp.springboot.learn.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zyp.springboot.learn.constant.HttpHeader;
import com.zyp.springboot.learn.infra.errorcode.ErrorCodeRegister;
import com.zyp.springboot.learn.infra.global.AccessFilter;
import com.zyp.springboot.learn.infra.global.AccessServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Set;

@Slf4j
@Configuration
public class GlobalConfig {

    @Bean
    public static void Init() {
        log.info("GlobalConfig Init");
        ErrorCodeRegister.init();
    }

    /**
     * 看阿里实现
     * @see com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration
     */
    @Bean
    public static FilterRegistrationBean<AccessFilter> accessFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        var accessFilter = new AccessFilter(resolver);
        accessFilter.setIncludeHeaders(true);
        accessFilter.setIncludePayload(true);
        accessFilter.setIncludeResponse(true);
        accessFilter.setIncludeQueryString(true);
        accessFilter.setMaxResponseLength(1024 * 1024);
        accessFilter.setMaskHeaders(Set.of(HttpHeader.X_Access_Token));
        var registrationBean = new FilterRegistrationBean<AccessFilter>();
        registrationBean.setFilter(accessFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MIN_VALUE); // 确保它是第一个Filter
        return registrationBean;
    }

    /**
     * 看阿里实现
     * @see com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration
     */
    @Bean
    public static ServletRegistrationBean<AccessServlet> accessBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new AccessServlet("web/resource"));
        return registrationBean;
    }

}
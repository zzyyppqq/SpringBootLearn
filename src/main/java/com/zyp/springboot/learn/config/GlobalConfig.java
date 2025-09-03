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
     * 加了以下Bean，会导致http请求返回空白
     * Spring Boot 项目默认使用 DispatcherServlet来处理所有的 HTTP 请求，并将其映射到根路径（"/"）。
     * 当你通过 ServletRegistrationBean注册另一个 Servlet 时，如果没有显式地为其指定 URL 映射模式，它可能会干扰甚至覆盖默认的 DispatcherServlet，
     * 导致本应由 Spring MVC 控制器（如你的 @PostMapping("/login")方法）处理的请求被错误地路由到你的 AccessServlet。
     * 而 AccessServlet如果没有正确处理该请求并生成响应，就可能返回空白。
     */
    @Bean
    public static ServletRegistrationBean<AccessServlet> accessBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new AccessServlet("web/resource"));
        // 关键点：addUrlMappings方法用于指定一个或多个 URL 模式，只有匹配这些模式的请求才会被 AccessServlet处理。
        // 务必确保这些模式不会与你项目中 Spring MVC 控制器（如 @RestController或 @Controller中的 @RequestMapping）处理的路径重叠。
        // 明确指定此Servlet只处理以特定路径开头的请求，例如 "/access/*"
        registrationBean.addUrlMappings("/access/*");
        // 或者更精确的模式，如 "/static/*", "/resources/*" 等，根据你的"web/resource"路径含义决定
        return registrationBean;
    }

}
package com.zyp.springboot.learn.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 如果你想提供 RequestMappingHandlerMapping、RequestMappingHandlerAdapter 或 ExceptionHandlerExceptionResolver 的自定义实例，
 * 并仍然保持Spring Boot MVC的自定义，你可以声明一个 WebMvcRegistrations 类型的bean，用它来提供这些组件的自定义实例。
 */
@Configuration
public class MyWebMvcRegistrations implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return WebMvcRegistrations.super.getRequestMappingHandlerMapping();
    }

    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return WebMvcRegistrations.super.getRequestMappingHandlerAdapter();
    }

    @Override
    public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
        return WebMvcRegistrations.super.getExceptionHandlerExceptionResolver();
    }
}

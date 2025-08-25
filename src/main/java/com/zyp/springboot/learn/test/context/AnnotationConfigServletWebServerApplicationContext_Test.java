package com.zyp.springboot.learn.test.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AnnotationConfigServletWebServerApplicationContext
 * 特点：
 * Spring Boot Web应用的默认ApplicationContext实现
 * 继承自AnnotationConfigApplicationContext
 * 支持Web相关功能(如Servlet API)
 * 自动配置内嵌的Web服务器(Tomcat、Jetty等)
 *
 * 使用场景：
 * 基于Servlet的Spring Boot Web应用
 * RESTful API服务
 * 传统的MVC应用
 */
@SpringBootApplication
class MyWebApp {
    public static void main(String[] args) {
        SpringApplication.run(MyWebApp.class, args);
    }
}
package com.zyp.springboot.learn.test.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Spring Boot提供了多种ApplicationContext的实现类，适用于不同的应用场景：
 * <p>
 * AnnotationConfigApplicationContext
 * 特点：
 * 基于Java注解配置的容器
 * 支持@Configuration、@ComponentScan等注解
 * 适用于非Web环境的Spring Boot应用
 * <p>
 * 使用场景：
 * 独立的Spring Boot应用
 * 测试环境
 * 命令行工具应用
 */
public class AnnotationConfigApplicationContext_Test {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfigApplicationContext_Test.class);
        MyService service = context.getBean(MyService.class);
        service.doSomething();
        ((AnnotationConfigApplicationContext) context).close();
    }

    @Configuration
    public class AppConfig {

        @Bean
        @Primary
        public MyService myService() {
            return new MyService();
        }

        @Bean
        public MyService myService2() {
            return new MyService();
        }
    }

    public static class MyService {
        public void doSomething() {
            System.out.println("MyService doSomething");
        }
    }

}
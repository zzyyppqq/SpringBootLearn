package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.service.user.UserService;
import com.zyp.springboot.learn.service.user.UserServiceProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // 启用AOP代理
public class AppConfig {
    @Bean(initMethod="init", destroyMethod="close")
    MyDataSource createDataSource() {
        return new MyDataSource();
    }

    @Bean
    BeanPostProcessor createProxy() {
        // BeanPostProcessor是一种特殊Bean，它的作用是根据条件替换某些Bean
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                // 实现事务功能:
                if (bean instanceof UserService u) {
                    return new UserServiceProxy(u);
                }
                return bean;
            }
        };
    }


    public static class MyDataSource {

        public void  init() {

        }

        public void close() {

        }
    }
}
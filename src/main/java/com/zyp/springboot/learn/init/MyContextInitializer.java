package com.zyp.springboot.learn.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * ApplicationContext的定制化
 * Spring Boot提供了多种方式来定制ApplicationContext的行为：
 *
 * 1. ApplicationContextInitializer
 * 在ApplicationContext创建之前对其进行配置：
 */
public class MyContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 进行一些配置，例如设置属性
        applicationContext.getEnvironment().getPropertySources()
            .addLast(new MapPropertySource("myProps",
                Map.of("my.custom.property", "initializer-value")));
    }

}

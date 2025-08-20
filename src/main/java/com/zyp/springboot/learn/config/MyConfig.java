package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.configProperties.MyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public MyProperties getProperties() {
        return new MyProperties();
    }
}

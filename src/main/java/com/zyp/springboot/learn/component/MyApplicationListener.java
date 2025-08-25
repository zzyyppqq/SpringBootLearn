package com.zyp.springboot.learn.component;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * ApplicationListener
 * 监听ApplicationContext在不同阶段发布的事件：
 */
@Component
public class MyApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 在这里可以执行一些初始化后的操作

    }
}
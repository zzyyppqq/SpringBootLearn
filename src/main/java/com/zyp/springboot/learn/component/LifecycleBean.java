package com.zyp.springboot.learn.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class LifecycleBean implements BeanNameAware, InitializingBean, DisposableBean {
    
    public LifecycleBean() {
        log.info("1. 构造器调用");
    }
    
    @Override
    public void setBeanName(String name) {
        log.info("3. BeanNameAware.setBeanName()调用: " + name);
    }
    
    @PostConstruct
    public void postConstruct() {
        log.info("5. @PostConstruct方法调用");
    }
    
    @Override
    public void afterPropertiesSet() {
        log.info("6. InitializingBean.afterPropertiesSet()调用");
    }
    
    public void customInit() {
        log.info("7. 自定义init-method调用");
    }
    
    @PreDestroy
    public void preDestroy() {
        log.info("9. @PreDestroy方法调用");
    }
    
    @Override
    public void destroy() {
        log.info("10. DisposableBean.destroy()调用");
    }
    
    public void customDestroy() {
        log.info("11. 自定义destroy-method调用");
    }
}
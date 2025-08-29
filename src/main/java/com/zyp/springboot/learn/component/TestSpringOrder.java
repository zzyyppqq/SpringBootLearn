
package com.zyp.springboot.learn.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TestSpringOrder implements
        ApplicationContextAware,
        BeanFactoryAware,
        InitializingBean,
        SmartLifecycle,
        BeanNameAware,
        ApplicationListener<ContextRefreshedEvent>,
        CommandLineRunner,
        SmartInitializingSingleton {

   @PostConstruct
   public void postConstruct() {
      log.error("启动顺序:post-construct");
   }

   public void initMethod() {
      log.error("启动顺序:init-method");
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      log.error("启动顺序:afterPropertiesSet");
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      log.error("启动顺序:setApplicationContext");
   }

   @Override
   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      log.error("启动顺序:setBeanFactory");
   }

   @Override
   public void setBeanName(String name) {
      log.error("启动顺序:setBeanName: {}", name);
   }

   @Override
   public void afterSingletonsInstantiated() {
      log.error("启动顺序:afterSingletonsInstantiated");
      //Http、MQ、Rpc 入口流量必须在 SmartInitializingSingleton 之后开启流量。
   }

   @Override
   public void run(String... args) throws Exception {

   }

   @Override
   public void onApplicationEvent(ContextRefreshedEvent event) {
      log.error("启动顺序:ContextRefreshedEvent: {}", event);
   }

   @Override
   public void start() {
      log.error("启动顺序:Lifecycle start");
      // Http、MQ、Rpc 入口流量适合 在 SmartLifecyle 中开启
   }

   @Override
   public void stop() {
      log.error("启动顺序:Lifecycle stop");
   }

   @Override
   public boolean isRunning() {
      return false;
   }
}
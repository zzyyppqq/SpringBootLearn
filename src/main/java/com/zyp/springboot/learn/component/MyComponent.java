package com.zyp.springboot.learn.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * ApplicationContext的访问与操作
 *
 * ApplicationContextAware接口
 * 实现该接口的Bean会在初始化后自动注入ApplicationContext实例：
 *
 * 常用方法
 * getBean()：根据名称或类型获取Bean实例
 * getEnvironment()：获取应用的Environment
 * publishEvent()：发布应用事件
 * containsBean()：检查容器中是否包含指定名称的Bean
 * getType()：获取指定名称的Bean的类型
 * getBeanDefinitionNames()：获取容器中注册的所有Bean定义名称
 *
 */
@Component
public class MyComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    public void doSomething() {
        MyOtherBean otherBean = applicationContext.getBean(MyOtherBean.class);
        otherBean.test();
    }


    class MyOtherBean {
        public void test() {
            System.out.println("MyOtherBean test");
        }
    }

    /**
     * @Autowired注入
     * 更推荐的方式是直接使用@Autowired注入：
     */
    @Service
    public class MyService {
        @Autowired
        private ApplicationContext applicationContext;

        public void doSomething() {
            applicationContext.publishEvent(new MyEvent("Something happened"));
        }

        class MyEvent {

            public MyEvent(String event) {

            }
        }
    }
}

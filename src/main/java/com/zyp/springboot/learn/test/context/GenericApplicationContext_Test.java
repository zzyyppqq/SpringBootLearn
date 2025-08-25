package com.zyp.springboot.learn.test.context;

import org.springframework.context.support.GenericApplicationContext;

/**
 *  GenericApplicationContext
 * 特点：
 * 通用的ApplicationContext实现
 * 通常与BeanDefinitionRegistry结合使用
 * 支持编程式配置Bean定义
 * 不依赖于特定的配置方式(XML或注解)
 *
 * 使用场景：
 * 需要动态注册Bean定义的高级场景
 * 与Spring的编程式API集成
 * 框架扩展开发
 */
public class GenericApplicationContext_Test {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myBean", MyBean.class);
        context.refresh();
        MyBean bean = context.getBean(MyBean.class);
        bean.test();
    }


    public static class MyBean {
        public void test() {
            System.out.println("MyBean test");
        }
    }

}

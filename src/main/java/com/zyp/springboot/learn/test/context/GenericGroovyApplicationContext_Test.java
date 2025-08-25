package com.zyp.springboot.learn.test.context;

import org.springframework.context.support.GenericGroovyApplicationContext;

/**
 * GenericGroovyApplicationContext
 * 特点：
 * 支持从Groovy脚本加载Bean定义
 * 使用Groovy DSL进行配置
 * 在Spring Boot中较少使用
 *
 * 使用场景：
 * 使用Groovy DSL配置的Spring应用
 * 需要脚本化配置的特殊场景
 */
public class GenericGroovyApplicationContext_Test {
    public static void main(String[] args) {
//        GenericGroovyApplicationContext context = new GenericGroovyApplicationContext();
//        context.registerBean("myBean", MyBean.class);
//        context.refresh();
//        MyBean bean = context.getBean(MyBean.class);
//        bean.test();
    }


    public static class MyBean {
        public void test() {
            System.out.println("MyBean test");
        }
    }
}

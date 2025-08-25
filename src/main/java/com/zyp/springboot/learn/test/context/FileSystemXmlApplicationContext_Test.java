package com.zyp.springboot.learn.test.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * FileSystemXmlApplicationContext
 * 特点：
 * 从文件系统路径加载XML配置文件
 * 需要显式指定文件绝对路径
 * 在Spring Boot中很少使用
 *
 * 使用场景：
 * 需要从特定文件系统位置加载配置的场景
 * 外部化配置管理的特殊需求
 */
public class FileSystemXmlApplicationContext_Test {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("/Users/zyp/config/applicationContext.xml");
        MyService service = (MyService) context.getBean("myService");
        service.test();
    }

    public static class MyService {
        public void test() {
            System.out.println("MyService test");
        }
    }
}

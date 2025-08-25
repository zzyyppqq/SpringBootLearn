package com.zyp.springboot.learn.test.context;

import com.zyp.springboot.learn.test.bean.Car;
import com.zyp.springboot.learn.test.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassPathXmlApplicationContext
 * 特点：
 * 从类路径加载XML配置文件的传统容器
 * 在Spring Boot中不推荐使用，但仍支持
 * 兼容传统的Spring XML配置方式
 *
 * 使用场景：
 * 从传统Spring应用迁移到Spring Boot的过渡期
 * 需要与旧版XML配置兼容的应用
 */
public class ClassPathXmlApplicationContext_Test {
    public static void main(String[] args) throws Exception {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        MyService service = (MyService) context.getBean("myService");
//        service.test();

        testApplicationContext();

    }

    public static void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini

        // 为了确保销毁方法在虚拟机关闭之前执行，向虚拟机中注册一个钩子方法，
        // 查看AbstractApplicationContext#registerShutdownHook（非web应用需要手动调用该方法）。
        // 当然也可以手动调用ApplicationContext#close方法关闭容器。
        applicationContext.registerShutdownHook();
    }

    public static class MyService {
        public void test() {
            System.out.println("MyService test");
        }
    }
}

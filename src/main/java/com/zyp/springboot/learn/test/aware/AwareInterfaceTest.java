package com.zyp.springboot.learn.test.aware;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AwareInterfaceTest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
		System.out.println("ApplicationContext: " + helloService.getApplicationContext());
		System.out.println("BeanFactory: " + helloService.getBeanFactory());
	}
}
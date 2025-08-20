package com.zyp.springboot.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * 有一个小细节，就是去掉默认的MVC错误处理[ErrorMvcAutoConfiguration.class]，它在异常发生时会返回错误页面，并不适用于API服务。
 * @SpringBootApplication = @ComponentScan + @EnableAutoConfiguration + @Configuration
 */
@Slf4j
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class SpringBootLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearnApplication.class, args);
        log.info("启动成功: {}", Thread.currentThread().getName());
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        System.out.println("CommandLineRunner");
        if (true) return args -> {};
        return args -> {
            log.debug("============== All Beans ====================");
            for (var beanDefinitionName : ctx.getBeanDefinitionNames()) {
                log.debug("BeanName: {}", beanDefinitionName);
            }
            log.info("ActiveProfiles: {}", Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
        };
    }

}

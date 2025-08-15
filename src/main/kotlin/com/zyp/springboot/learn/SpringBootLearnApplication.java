package com.zyp.springboot.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class SpringBootLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearnApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("============== All Beans ====================");
            for (var beanDefinitionName : ctx.getBeanDefinitionNames()) {
                log.debug("BeanName: {}", beanDefinitionName);
            }
            log.info("ActiveProfiles: {}", Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
        };
    }

}

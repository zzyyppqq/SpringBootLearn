package com.zyp.springboot.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
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
        // 禁止重启
        // System.setProperty("spring.devtools.restart.enabled", "false");
//        new SpringApplicationBuilder()
//                .sources(SpringBootLearnApplication.class)
//                //.child(Application.class)
//                .bannerMode(Banner.Mode.OFF)
//                .listeners(new ApplicationListener<ApplicationEvent>() {
//                    @Override
//                    public void onApplicationEvent(ApplicationEvent event) {
//                        log.warn("onApplicationEvent event {}", event);
//                    }
//                })
//                .run(args);
        SpringApplication application = new SpringApplication(SpringBootLearnApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        // SpringApplication.run(SpringBootLearnApplication.class, args);
        log.info("启动成功: {}", Thread.currentThread().getName());
    }

    // 事件监听器不应该运行潜在耗时的任务，因为它们默认是在同一个线程中执行。 考虑使用 ApplicationRunner 和 CommandLineRunner 代替。
    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        log.debug("ApplicationRunner ctx {}", ctx);
        return args -> {};
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        log.debug("CommandLineRunner");
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

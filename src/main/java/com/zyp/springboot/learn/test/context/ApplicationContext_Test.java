package com.zyp.springboot.learn.test.context;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot中创建父子容器的示例
 */
public class ApplicationContext_Test {

    @Configuration
    public class ParentConfig {
        @Bean
        public ParentBean parentBean() {
            return new ParentBean();
        }
    }

    @Configuration
    @ComponentScan("com.example.web")
    public class WebConfig {
        @Bean
        public WebBean webBean(ParentBean parentBean) {
            return new WebBean(parentBean);
        }
    }

    @SpringBootApplication
    public class MyApp {
        public static void main(String[] args) {
            new SpringApplicationBuilder()
                    .sources(ParentConfig.class)
                    .child(WebConfig.class)
                    .web(WebApplicationType.SERVLET)
                    .run(args);
        }
    }

    class ParentBean { }

    class WebBean {
        private ParentBean parentBean;

        public WebBean(ParentBean parentBean) {
            this.parentBean = parentBean;
        }
    }

}

package com.zyp.springboot.learn.configProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * my:
 * service:
 * enabled: true
 * remote-address: 192.168.1.1
 * security:
 * username: "admin"
 * password: "123456"
 * roles:
 * - "USER"
 * - "ADMIN"
 */
@Data
@Component //使用@Component修饰了，无需使用@Configuration和@Bean实例化了
@ConfigurationProperties(prefix = "my.service")
public class MyProperties {
    private boolean enabled;

    private InetAddress remoteAddress;

    private final Security security = new Security();

    @Data
    public static class Security {
        private String username;

        private String password;

        private List<String> roles = new ArrayList<>(Collections.singleton("USER"));
    }

}
package com.zyp.springboot.learn.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 1. 加上这个注解，方法的返回值会自动序列化为JSON
public class HomeController {

    // welcome节点是我们自己加的配置，这里主要用于测试目的。我们把第一个HTTP API服务改造下，将HomeController#home方法返回的字符串改为配置文件中内容：
    @Value("${welcome.content}") // 1. Spring会自动将配置文件中的内容注入
    private String welcomeContent;

    @GetMapping("/env")
    public String env() {
        return welcomeContent; // 2. 改为返回配置文件的内容
    }

    @GetMapping("/") // 2. 定义这个API的url路径和可接收的Http Method（这里是GET）
    public String home() {
        return "Welcome Home";
    }

}
package com.zyp.springboot.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 1. 加上这个注解，方法的返回值会自动序列化为JSON
public class HomeController {
    @GetMapping("/") // 2. 定义这个API的url路径和可接收的Http Method（这里是GET）
    public String home() {
        return "Welcome Home";
    }
}
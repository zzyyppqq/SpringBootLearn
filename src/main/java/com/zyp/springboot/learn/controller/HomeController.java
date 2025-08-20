package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.configProperties.MyProperties;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.infra.errorcode.BusinessException;
import com.zyp.springboot.learn.infra.security.IgnorePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@Slf4j
@RestController // 1. 加上这个注解，方法的返回值会自动序列化为JSON
public class HomeController {

    // welcome节点是我们自己加的配置，这里主要用于测试目的。我们把第一个HTTP API服务改造下，将HomeController#home方法返回的字符串改为配置文件中内容：
    @Value("${welcome.content}") // 1. Spring会自动将配置文件中的内容注入
    private String welcomeContent;

    @Autowired
    MyProperties properties;

    @GetMapping("/test/my_properties")
    @IgnorePermission
    public MyProperties myProperties() {
        log.info("MyProperties properties hashcode: {}", System.identityHashCode(properties));
        return properties;
    }

    @GetMapping("/env")
    public String env() {
        return welcomeContent; // 2. 改为返回配置文件的内容
    }

    @GetMapping("/") // 2. 定义这个API的url路径和可接收的Http Method（这里是GET）
    @IgnorePermission
    public RespDTO<String> home() {
        return RespDTO.ok(welcomeContent);
    }

    @GetMapping("/system_error")
    @IgnorePermission
    public RespDTO<String> systemError() {
        // {
        //     "code": 10001,
        //     "msg": "系统异常，请稍后重试",
        //     "data": null
        // }
        return RespDTO.systemError("test system error");
    }

    @GetMapping("/system_error_test")
    public RespDTO<String> systemErrorTest() {
        // 返回值
        // {
        //     "code": 10001,
        //     "msg": "系统异常，请稍后重试",
        //     "data": null
        // }
        throw new BusinessException("test exception");
    }

}
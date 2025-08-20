package com.zyp.springboot.learn.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 如果你需要访问传递给 SpringApplication.run(..) 的命令行参数，
 * 你可以注入一个 org.springframework.boot.ApplicationArguments bean。
 * 通过 ApplicationArguments 接口，你可以访问原始的 String[] 参数以及经过解析的 option 和 non-option 参数。
 */
@Component
public class MyBean {

    public MyBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        System.out.println("MyBean files: " + files);
        // if run with "--debug logfile.txt" prints ["logfile.txt"]
    }

}



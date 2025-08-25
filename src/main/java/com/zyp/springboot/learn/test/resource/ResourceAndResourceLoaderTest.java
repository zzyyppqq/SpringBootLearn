package com.zyp.springboot.learn.test.resource;

import cn.hutool.core.io.IoUtil;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;

/**
 * FileSystemResource，文件系统资源的实现类
 * ClassPathResource，classpath下资源的实现类
 * UrlResource，对java.net.URL进行资源定位的实现类
 */
public class ResourceAndResourceLoaderTest {

    public static void main(String[] args) throws Exception {
        String classpath = System.getProperty("java.class.path");
        System.out.println("Current classpath: " + classpath);

        testResourceLoader();
    }

    public static void testResourceLoader() throws Exception {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        //加载classpath下的资源
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);


        //加载文件系统资源
        resource = resourceLoader.getResource("src/test/resources/hello.txt");
        if (resource instanceof FileSystemResource) {
            inputStream = resource.getInputStream();
            content = IoUtil.readUtf8(inputStream);
            System.out.println(content);
        }

        //加载url资源
        resource = resourceLoader.getResource("https://www.baidu.com");
        if (resource instanceof UrlResource) {
            inputStream = resource.getInputStream();
            content = IoUtil.readUtf8(inputStream);
            System.out.println(content);
        }
    }
}

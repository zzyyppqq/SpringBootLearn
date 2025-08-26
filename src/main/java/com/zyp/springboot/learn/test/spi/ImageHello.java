package com.zyp.springboot.learn.test.spi;

public class ImageHello implements HelloSPI {
    public void sayHello() {
        System.out.println("Image Hello");
    }
}
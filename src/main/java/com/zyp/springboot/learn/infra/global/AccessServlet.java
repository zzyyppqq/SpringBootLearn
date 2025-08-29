package com.zyp.springboot.learn.infra.global;

import com.alibaba.druid.support.http.ResourceServlet;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AccessServlet extends ResourceServlet {


    public AccessServlet(String resourcePath) {
        super(resourcePath);
    }

    @Override
    protected String process(String url) {
        return null;
    }
}

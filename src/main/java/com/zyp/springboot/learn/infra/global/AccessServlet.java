package com.zyp.springboot.learn.infra.global;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//public class AccessServlet extends HttpServletBean {
public class AccessServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(request, response);
    }

}

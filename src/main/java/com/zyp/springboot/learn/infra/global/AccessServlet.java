package com.zyp.springboot.learn.infra.global;

import com.alibaba.druid.support.http.ResourceServlet;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AccessServlet extends ResourceServlet {

    private String resourcePath;

    public AccessServlet(String resourcePath) {
        super(resourcePath);
        this.resourcePath = resourcePath;
    }

    @Override
    protected String process(String url) {
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8"); // 设置正确的Content-Type
        PrintWriter out = resp.getWriter();
        // 你的业务逻辑：例如读取资源、处理数据等
        out.print("Response Get from AccessServlet for path: " + resourcePath); // 务必向响应写入内容
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8"); // 设置正确的Content-Type
        PrintWriter out = resp.getWriter();
        // 你的业务逻辑：例如读取资源、处理数据等
        out.print("Response Post from AccessServlet for path: " + resourcePath); // 务必向响应写入内容
        out.flush();
    }
}

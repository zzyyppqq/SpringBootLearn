package com.zyp.springboot.learn.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class MyUserHandler {
    public ServerResponse getUser(ServerRequest serverRequest) {
        return null;
    }

    public ServerResponse getUserCustomers(ServerRequest serverRequest) {
        return null;
    }

    public ServerResponse deleteUser(ServerRequest serverRequest) {
        return null;
    }
}

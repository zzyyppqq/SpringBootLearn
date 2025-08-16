package com.zyp.springboot.learn.infra.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认情况下，AuthorizationFilter授权失败的话，处理逻辑是forward到/error。
 * 我们需要将这个默认行为改为返回标准的授权失败结果。
 * 方法是自定义一个错误处理类，实现AuthenticationEntryPoint接口。
 * 跟AccessFilter类似，异常的处理也委托给统一的全局异常处理GlobalExceptionHandler，
 * 因此这个类非常简单，只有委托逻辑：
 *
 * 最后，还需要将其注册到SecurityFilter才能生效，最后完整的SecurityFilterChain配置如下所示：
 *
 * 此时，就完成了整个用户登录和认证模块，/current_user也可以正常工作了。
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public SecurityAuthenticationEntryPoint(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        resolver.resolveException(request, response, null, authException);
    }
}
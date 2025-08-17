package com.zyp.springboot.learn.config;

import com.zyp.springboot.learn.infra.security.PowerAuthorizationManager;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import java.lang.annotation.Annotation;
import java.util.List;

// 打开方法级别的鉴权功能，并且关闭默认的切面配置，该类会自定义切面配置。
@EnableMethodSecurity(prePostEnabled = false)
public class MethodSecurityConfig {
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    Advisor preAuthorizeAuthorizationMethodInterceptor() {
        AuthorizationManagerBeforeMethodInterceptor interceptor = new 
            AuthorizationManagerBeforeMethodInterceptor(
                 // 注册自定义的切面配置
                AuthorizationMethodPointcuts.forAnnotations(), 
                // 注册自定义的AuthorizationManager
                new PowerAuthorizationManager());
        interceptor.setOrder(
            AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder());
        return interceptor;
    }

    // 自定义的切面配置类
    static final class AuthorizationMethodPointcuts {

        static Pointcut forAnnotations() {
            // 切面需要满足如下两个条件之一：
            // 第一： 类上要有@RestController并且方法加上了@PreAuthorize
            ComposablePointcut pointcut = new ComposablePointcut(
                    new AnnotationMatchingPointcut(
                        RestController.class, true));
            pointcut.intersection(
                new AnnotationMatchingPointcut(
                    null, PreAuthorize.class, true));
            // 第二：或者方法加了如下XXXMapping注解
            var mappingAnnotations = List.of(
                    RequestMapping.class,
                    GetMapping.class,
                    PostMapping.class,
                    PutMapping.class,
                    DeleteMapping.class,
                    PatchMapping.class);
            mappingAnnotations.forEach(item -> 
                pointcut.union(method(item)));
            return pointcut;
        }

        private static Pointcut method(
                Class<? extends Annotation> annotation) {
            return new AnnotationMatchingPointcut(null, annotation, true);
        }
        private AuthorizationMethodPointcuts() {}
    }

}
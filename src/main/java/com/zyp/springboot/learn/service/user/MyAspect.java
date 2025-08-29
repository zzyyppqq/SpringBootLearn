package com.zyp.springboot.learn.service.user;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Around("execution(* com.zyp.springboot.learn.service.user.UserService.*(..))") // 定义切点
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 方法执行前的逻辑（类似你的代理类中的 before）
        try {
            Object result = pjp.proceed(); // 执行原方法
            // 方法执行成功后的逻辑（类似 after-returning）
            return result;
        } catch (Exception e) {
            // 方法抛出异常后的逻辑（类似 after-throwing）
            throw e;
        } finally {
            // 方法执行后的逻辑（类似 after）
        }
    }
}
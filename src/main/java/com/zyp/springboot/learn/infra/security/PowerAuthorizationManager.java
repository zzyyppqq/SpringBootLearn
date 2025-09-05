package com.zyp.springboot.learn.infra.security;

import com.zyp.springboot.learn.constant.SpecialUsername;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.PreAuthorizeAuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

// 方法级别的鉴权，需要实现AuthorizationManager<MethodInvocation>接口
@Slf4j
public class PowerAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    // Spring Security默认的方法鉴权实现
    private final PreAuthorizeAuthorizationManager preAuthorizeAuthorizationManager;

    public PowerAuthorizationManager() {
        this.preAuthorizeAuthorizationManager = new PreAuthorizeAuthorizationManager();
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationGetter, MethodInvocation object) {
        if (true) {
            return null;
        }
        // 1. 如果方法上没有显式地设置权限注解，默认不允许匿名访问，
        // 不过的确有些方法是不需要权限控制的，因此我们增加了一个特殊的注解IgnorePermission，表示该方法无需权限控制
        var ignorePermission = object.getMethod().getDeclaredAnnotation(IgnorePermission.class);
        if (ignorePermission != null) {
            return null;
        }
        var authentication = authenticationGetter.get();
        // super用户特殊处理，拥有所有接口的访问权限
        if (SpecialUsername.SUPER.equals(authentication.getName())) {
            return null;
        }
        var annotation = object.getMethod().getDeclaredAnnotation(PreAuthorize.class);
        if (annotation == null) { // 如果方法上没有显式地设置权限注解
            // 默认给该方法加上"ControllerName.methodName"的权限名称
            var classNameArray = object.getMethod().getDeclaringClass().getName().split("\\.");
            var controllerName = classNameArray[classNameArray.length - 1];
            var methodName = object.getMethod().getName();
            var defaultAuthority = controllerName + "." + methodName;
            var authorities = authentication.getAuthorities();
            var isGranted = authorities.stream().anyMatch(x -> x.getAuthority().equals(defaultAuthority));
            if (!isGranted) {
                log.warn("no_match_default_authority:{}", defaultAuthority);
            }
            return new AuthorizationDecision(isGranted);
        }
        // 否则，保持默认的鉴权逻辑
        return preAuthorizeAuthorizationManager.check(authenticationGetter, object);
    }

}

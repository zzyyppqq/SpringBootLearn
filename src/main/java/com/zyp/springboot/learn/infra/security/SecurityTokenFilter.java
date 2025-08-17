package com.zyp.springboot.learn.infra.security;

import com.zyp.springboot.learn.constant.HttpHeader;
import com.zyp.springboot.learn.dto.auth.permission.PermissionDTO;
import com.zyp.springboot.learn.dto.user.UserFullInfoDTO;
import com.zyp.springboot.learn.service.token.TokenService;
import com.zyp.springboot.learn.service.user.UserService;
import com.zyp.springboot.learn.util.StrUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class SecurityTokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserService userService;

    public SecurityTokenFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    // 认证的结果，核心在于SecurityContextHolder的维护。
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xAccessToken = request.getHeader(HttpHeader.X_Access_Token);
        // header里没有token，直接接受这个filter
        // 后面的AuthorizationFilter会granted失败。
        if (StrUtils.hasNoLength(xAccessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.clearContext();

        // 校验token，并获取用户信息和权限列表
        Long uid = tokenService.verifyThenGetUid(xAccessToken);
        if (uid != null) {
            UserFullInfoDTO userData = userService.getUserData(uid).getData();
            if (userData != null) {
                // 如果校验通过，就将通过的Authentication对象放在上下文
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userData.getUsername(),
                        "********",
                        // 简单的模型转换，将我们的权限数据模型转换为Spring Security的规范模型
                        buildAuthorities(userData.getPermissions()));                      // 简单的模型转换，将我们的权限数据模型转换为Spring Security的规范模型

                authenticationToken.setDetails(userData);
                // 这是表示认证通过的核心代码
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // 没设置上下文，就是校验不通过，后面的filter会检查上下文
        // 默认情况下， Security会配置AnonymousAuthenticationFilter，也就是说会替换为AnonymousAuthenticationToken, 而不是null
        // 然后在AuthorizationFilter中授权granted失败，默认会forward到/error，返回403。
        // 你可以httpSecurity配置authenticationEntryPoint自定义错误处理。
        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(List<PermissionDTO> permissions) {
        return permissions.stream().map(perm -> new SimpleGrantedAuthority(perm.getName())).toList();
    }

}
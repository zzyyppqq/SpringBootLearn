package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.user.LoginUserBasicInfo;
import com.zyp.springboot.learn.dto.user.LoginUserDetails;
import com.zyp.springboot.learn.dto.user.UserLoginReq;
import com.zyp.springboot.learn.infra.security.IgnorePermission;
import com.zyp.springboot.learn.service.token.TokenService;
import com.zyp.springboot.learn.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    private final UserService userService;
    private final TokenService tokenService;

    public LoginController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @IgnorePermission
    public RespDTO<LoginUserBasicInfo> login(@Valid @RequestBody UserLoginReq userLoginReq) {
        RespDTO<LoginUserDetails> loginUserDetailsResp = userService.login(userLoginReq);
        if (loginUserDetailsResp.notOk()) {
            return loginUserDetailsResp.copyError();
        } else {
            var userBaseInfo = loginUserDetailsResp.getData().getLoginUserBasicInfo();
            var token = tokenService.createToken(userBaseInfo.getUid(), userBaseInfo.getUsername());
            userBaseInfo.setToken(token);
            return RespDTO.ok(userBaseInfo);
        }
    }

}

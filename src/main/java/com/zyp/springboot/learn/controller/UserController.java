package com.zyp.springboot.learn.controller;

import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.user.UserFullInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    @GetMapping("/current_user")
    public RespDTO<UserFullInfoDTO> currentUser() {
        return RespDTO.ok(UserFullInfoDTO.currentUser());
    }

}
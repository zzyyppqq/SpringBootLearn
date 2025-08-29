package com.zyp.springboot.learn.service.user;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.user.*;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.service.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


@Slf4j
public class UserServiceProxy extends UserService {

    private UserService service;

    public UserServiceProxy(UserService service) {
        this.service = service;
    }

    public RespDTO<LoginUserDetails> login(UserLoginReq req) {
        return service.login(req);
    }

    public RespDTO<UserFullInfoDTO> getUserData(Long uid) {
        return service.getUserData(uid);
    }

    public boolean add(UserAddReq req) {
        return service.add(req);
    }

    public IPage<UserEntity> query(UserQueryReq userQueryReq) {
        return service.query(userQueryReq);
    }

    public boolean update(UserUpdateReq req) {
        return service.update(req);
    }

    public List<RoleEntity> roles(long uid) {
        return service.roles(uid);
    }

    public Map<Long, List<RoleEntity>> roles(List<Long> uidList) {
        return service.roles(uidList);
    }
}

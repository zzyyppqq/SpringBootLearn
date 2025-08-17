package com.zyp.springboot.learn.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyp.springboot.learn.dto.auth.permission.PermissionDTO;
import com.zyp.springboot.learn.dto.auth.permission.PermissionListDTO;
import com.zyp.springboot.learn.dto.common.table.TableResultGenerator;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.service.auth.UserRoleService;
import com.zyp.springboot.learn.service.user.UserService;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.dto.common.AdjustReq;
import com.zyp.springboot.learn.dto.auth.role.RoleDTO;
import com.zyp.springboot.learn.dto.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/user/{uid}")
    @PreAuthorize("hasAuthority('users::read')")
    public RespDTO<UserFullInfoDTO> getById(@PathVariable long uid) {
        return userService.getUserData(uid);
    }

    @GetMapping("/current_user")
    @PreAuthorize("hasAuthority(T(com.zyp.springboot.learn.constant.GeneralPermissionName).Basic)")
    public RespDTO<UserFullInfoDTO> currentUser() {
        return RespDTO.ok(UserFullInfoDTO.currentUser());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('users::read')")
    public RespDTO<UserListDTO> list(@Valid UserQueryReq req) {
        var generator = new TableResultGenerator<UserDTO, UserEntity>();
        var listDTO = new UserListDTO();
        generator.list(() -> userService.query(req), listDTO, UserDTO.class);
        var uidList = listDTO.getData().stream().map(UserDTO::getUid).toList();
        var rolesByUid = this.userService.roles(uidList);
        listDTO.getData()
                .forEach(user -> user.setRoles(
                        rolesByUid.getOrDefault(user.getUid(), new ArrayList<>()).stream()
                                .map(RoleEntity::getName).toList()));
        return RespDTO.ok(listDTO);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('users::write')")
    public RespDTO<Boolean> add(@RequestBody @Valid UserAddReq userAddReq) {
        return RespDTO.ok(userService.add(userAddReq));
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority('users::write')")
    public RespDTO<Boolean> update(@RequestBody @Valid UserUpdateReq userUpdateReq) {
        return RespDTO.ok(userService.update(userUpdateReq));
    }

    @DeleteMapping("/users/{uid}")
    @PreAuthorize("hasAuthority('users::write')")
    public RespDTO<Boolean> delete(@PathVariable long uid) {
        return RespDTO.ok(userService.removeById(uid));
    }

    @GetMapping("/users/{id}/roles")
    @PreAuthorize("hasAuthority('users::read')")
    public RespDTO<List<RoleDTO>> permissions(@PathVariable long id) {
        var roles = userService.roles(id);
        return RespDTO.ok(roles.stream().map(RoleDTO::from).toList());
    }

    @PostMapping("/users/{id}/roles")
    @PreAuthorize("hasAuthority('users::write')")
    public RespDTO<Boolean> adjustPermissions(@PathVariable long id, @RequestBody AdjustReq req) {
        UserEntity role = this.userService.getById(id);
        if (role == null) {
            return RespDTO.errorMsg(SystemErrorCode.DATA_NOT_EXIST, "找不到用户");
        }
        return userRoleService.batchOperation(id, req);
    }

}
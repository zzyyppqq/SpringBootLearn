package com.zyp.springboot.learn.dto.auth.role_permission;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class RolePermissionUpdateReq extends RolePermissionAddReq {
    @NotNull(message = "id不能为空")
    private Long id;
}

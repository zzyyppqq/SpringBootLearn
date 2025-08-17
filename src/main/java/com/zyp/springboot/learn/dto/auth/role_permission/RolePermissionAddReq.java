package com.zyp.springboot.learn.dto.auth.role_permission;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class RolePermissionAddReq {
    @NotNull(message = "角色Id不能为空")
    private Long roleId;
    @NotNull(message = "权限Id不能为空")
    private Long permissionId;
}

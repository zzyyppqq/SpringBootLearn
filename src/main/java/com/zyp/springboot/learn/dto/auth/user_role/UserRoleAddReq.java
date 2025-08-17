package com.zyp.springboot.learn.dto.auth.user_role;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserRoleAddReq {
    @NotNull(message = "用户Id不能为空")
    private Long uid;
    @NotNull(message = "角色Id不能为空")
    private Long roleId;
}

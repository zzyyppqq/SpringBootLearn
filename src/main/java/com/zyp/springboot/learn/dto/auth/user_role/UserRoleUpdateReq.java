package com.zyp.springboot.learn.dto.auth.user_role;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserRoleUpdateReq extends UserRoleAddReq {
    @NotNull(message = "id不能为空")
    private Long id;
}

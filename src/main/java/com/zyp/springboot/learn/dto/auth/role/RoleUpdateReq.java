package com.zyp.springboot.learn.dto.auth.role;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class RoleUpdateReq extends RoleAddReq {
    @NotNull(message = "id不能为空")
    private Long id;
}

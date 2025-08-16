package com.zyp.springboot.learn.dto.auth.permission;

import com.zyp.springboot.learn.constant.PermissionType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class PermissionUpdateReq {
    @NotNull(message = "权限Id不能为空")
    private Long id;
    private PermissionType type;
    @Length(max = 128, message = "权限名称最多128字符")
    private String name;
    @Length(max = 256, message = "权限描述最多256字符")
    private String description;
}

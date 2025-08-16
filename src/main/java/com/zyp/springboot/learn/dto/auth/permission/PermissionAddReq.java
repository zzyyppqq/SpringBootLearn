package com.zyp.springboot.learn.dto.auth.permission;

import com.zyp.springboot.learn.constant.PermissionType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class PermissionAddReq {
    @NotNull(message = "类型不能为空")
    private PermissionType type;
    @Length(max = 256, message = "名称最多20字符")
    private String name;
    @Length(max = 256, message = "描述最多20字符")
    private String description;
}

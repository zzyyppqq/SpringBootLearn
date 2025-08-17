package com.zyp.springboot.learn.dto.auth.role;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Data
public class RoleAddReq {
    @NotNull(message = "名称不能为空")
    @Length(max = 256, message = "名称最多20字符")
    private String name;
    @Length(max = 256, message = "描述最多20字符")
    private String description;
}

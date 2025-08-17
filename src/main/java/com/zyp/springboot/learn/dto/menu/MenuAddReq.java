package com.zyp.springboot.learn.dto.menu;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class MenuAddReq {
    @NotNull(message = "菜单名称不能为空")
    @Length(max = 256, message = "菜单名称最多256字符")
    private String name;
    @NotNull(message = "菜单显示名称不能为空")
    @Length(max = 128, message = "菜单显示名称最多128字符")
    private String showName;
    @Length(max = 256, message = "菜单描述最多256字符")
    private String description;
    @NotNull(message = "菜单路径不能为空")
    private String path;
    private String icon;
    private Integer order;
    private Long parentId;
    @NotNull(message = "权限不能为空")
    private Long permissionId;
}

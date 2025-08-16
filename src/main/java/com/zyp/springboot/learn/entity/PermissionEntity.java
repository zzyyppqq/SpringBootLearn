package com.zyp.springboot.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zyp.springboot.learn.constant.PermissionType;
import lombok.Data;

@Data
@TableName("permission")
public class PermissionEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private PermissionType type;
    private String name;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;
}

package com.zyp.springboot.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("menu")
public class MenuEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String showName;
    private String description;
    private String path;
    @TableField("`order`")
    private Integer order;
    private Long parentId; // parent menu id
    private Long permissionId;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;
}

package com.zyp.springboot.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user_role")
public class UserRoleEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long uid;
    private Long roleId;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    public UserRoleEntity(Long uid, Long roleId) {
        this.uid = uid;
        this.roleId = roleId;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
    }
}

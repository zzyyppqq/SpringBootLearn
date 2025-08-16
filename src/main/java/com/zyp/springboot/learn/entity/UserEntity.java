package com.zyp.springboot.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Long uid;
    private String username;
    private String password;
    private String nickName;
    private Boolean disabled;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;
}

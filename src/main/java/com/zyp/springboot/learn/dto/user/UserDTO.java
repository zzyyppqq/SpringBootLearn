package com.zyp.springboot.learn.dto.user;

import com.zyp.springboot.learn.constant.ModuleName;
import com.zyp.springboot.learn.constant.TableFieldType;
import com.zyp.springboot.learn.dto.common.table.property.ActionProperty;
import com.zyp.springboot.learn.dto.common.table.property.FieldProperty;
import com.zyp.springboot.learn.dto.common.table.property.RuleProperty;
import com.zyp.springboot.learn.entity.UserEntity;
import com.zyp.springboot.learn.util.BeanUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@ActionProperty(module = ModuleName.USER)
public class UserDTO {
    @JsonProperty("id")
    private Long uid;
    @FieldProperty(title = "用户名", searchable = true)
    @RuleProperty(required = true)
    private String username;
    @FieldProperty(title = "昵称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String nickName;
    @FieldProperty(title = "禁用", canEdit = true, searchable = true,
            type = TableFieldType.Boolean, canAdd = false)
    private boolean disabled;
    @FieldProperty(title = "密码", canShow = false, type = TableFieldType.Password)
    @JsonIgnore
    private String password;
    @FieldProperty(title = "角色", canAdd = false)
    private List<String> roles;
    private long createTime;
    private long updateTime;

    public static UserDTO from(UserEntity entity) {
        return BeanUtils.copy(entity, UserDTO.class);
    }
}

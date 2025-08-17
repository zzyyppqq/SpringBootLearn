package com.zyp.springboot.learn.dto.auth.role;

import com.zyp.springboot.learn.constant.ModuleName;
import com.zyp.springboot.learn.dto.common.table.property.ActionProperty;
import com.zyp.springboot.learn.dto.common.table.property.FieldProperty;
import com.zyp.springboot.learn.dto.common.table.property.RuleProperty;
import com.zyp.springboot.learn.entity.RoleEntity;
import com.zyp.springboot.learn.util.BeanUtils;
import lombok.Data;

@Data
@ActionProperty(module = ModuleName.ROLE)
public class RoleDTO {
    private long id;
    @FieldProperty(title = "名称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String name;
    @FieldProperty(title = "展示名称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String description;
    private Long createTime;
    private Long updateTime;

    public static RoleDTO from(RoleEntity entity) {
        return BeanUtils.copy(entity, RoleDTO.class);
    }
}

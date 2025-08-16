package com.zyp.springboot.learn.dto.auth.permission;

import com.zyp.springboot.learn.constant.ModuleName;
import com.zyp.springboot.learn.constant.PermissionType;
import com.zyp.springboot.learn.dto.common.table.property.ActionProperty;
import com.zyp.springboot.learn.dto.common.table.property.FieldProperty;
import com.zyp.springboot.learn.dto.common.table.property.RuleProperty;
import com.zyp.springboot.learn.dto.common.table.property.SelectOptionProperty;
import com.zyp.springboot.learn.entity.PermissionEntity;
import com.zyp.springboot.learn.util.BeanUtils;
import lombok.Data;

import static com.zyp.springboot.learn.constant.PermissionType.SelectOption.*;
import static com.zyp.springboot.learn.constant.TableFieldType.EnumNumber;


@Data
@ActionProperty(module = ModuleName.PERMISSION)
public class PermissionDTO {
    private Long id;
    @FieldProperty(title = "名称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String name;
    @FieldProperty(title = "描述", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String description;
    @FieldProperty(title = "类型", canEdit = true, searchable = true, type = EnumNumber)
    @RuleProperty(required = true)
    @SelectOptionProperty(label = LabelWebFunction, intValue = WebFunction)
    @SelectOptionProperty(label = LabelApi, intValue = Api)
    @SelectOptionProperty(label = LabelMenu, intValue = Menu)
    private PermissionType type;
    private Long createTime;
    private Long updateTime;

    public static PermissionDTO from(PermissionEntity entity) {
        return BeanUtils.copy(entity, PermissionDTO.class);
    }
}

package com.zyp.springboot.learn.dto.menu;

import com.zyp.springboot.learn.constant.ModuleName;
import com.zyp.springboot.learn.constant.TableFieldType;
import com.zyp.springboot.learn.dto.common.table.property.ActionProperty;
import com.zyp.springboot.learn.dto.common.table.property.FieldAssociationProperty;
import com.zyp.springboot.learn.dto.common.table.property.FieldProperty;
import com.zyp.springboot.learn.dto.common.table.property.RuleProperty;
import com.zyp.springboot.learn.entity.MenuEntity;
import com.zyp.springboot.learn.util.BeanUtils;
import lombok.Data;

@Data
@ActionProperty(module = ModuleName.MENU)
public class MenuDTO {
    private Long id;
    @FieldProperty(title = "名称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String name;
    @FieldProperty(title = "展示名称", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String showName;
    @FieldProperty(title = "描述", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String description;
    @FieldProperty(title = "路径", canEdit = true, searchable = true)
    @RuleProperty(required = true)
    private String path;
    @FieldProperty(title = "排序", canEdit = true)
    private Integer order;
    @FieldProperty(title = "父菜单", canEdit = true, searchable = true, type = TableFieldType.Association)
    @FieldAssociationProperty(path = "/menus", queryFields = {"name"}, valueField = "id", labelFields = {"name", "showName", "description"})
    private Long parentId; // parent menu id
    private String parentIdLabel; // parent menu label for show
    @FieldProperty(title = "权限", canEdit = true, searchable = true, type = TableFieldType.Association)
    @FieldAssociationProperty(path = "/permissions", queryFields = {"name"}, valueField = "id", labelFields = {"name", "description"})
    private Long permissionId;
    private String permissionIdLabel; // for show
    private Long createTime;
    private Long updateTime;

    public static MenuDTO from(MenuEntity entity) {
        return BeanUtils.copy(entity, MenuDTO.class);
    }
}

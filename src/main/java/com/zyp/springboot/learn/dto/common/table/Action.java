package com.zyp.springboot.learn.dto.common.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zyp.springboot.learn.constant.ModuleName;
import com.zyp.springboot.learn.constant.SpecialUsername;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;


// 表格支持的操作，通常根据用户的权限设置
@Data
public class Action {
    /**
     * 是否允许编辑记录
     */
    private boolean canEdit;
    /**
     * 是否允许删除记录
     */
    private boolean canDelete;
    /**
     * 是否允许新增记录
     */
    private boolean canAdd;

    /**
     * 新增对应的权限名
     */
    @JsonIgnore
    private String addPermission;
    /**
     * 编辑对应的权限名
     */
    @JsonIgnore
    private String editPermission;
    /**
     * 删除对应的权限名
     */
    @JsonIgnore
    private String deletePermission;

    public Action() {
    }

    /**
     * 根据模块名设置权限名，默认是{moduleName}::write。
     * 也可以单独设置每个权限名
     *
     * @param moduleName 模块名，比如roles,permissions
     */
    public void setCommonPermissions(ModuleName moduleName) {
        // 默认只要有写权限就可以，写权限的名称是'{moduleName}::write'
        var commonPermissionName = moduleName.value() + "::write";
        deletePermission = editPermission = addPermission = commonPermissionName;
    }

    /**
     * 根据用户的权限列表设置操作权限
     */
    public void grant() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        // super用户有所有权限
        if (authentication.getPrincipal().equals(SpecialUsername.SUPER)) {
            canAdd = canEdit = canDelete = true;
            return;
        }
        var permissions = authentication.getAuthorities();
        if (CollectionUtils.isEmpty(permissions)) {
            return;
        }
        for (GrantedAuthority perm : permissions) {
            if (perm.getAuthority().equals(addPermission)) {
                canAdd = true;
            }
            if (perm.getAuthority().equals(editPermission)) {
                canEdit = true;
            }
            if (perm.getAuthority().equals(deletePermission)) {
                canDelete = true;
            }
        }
    }

}

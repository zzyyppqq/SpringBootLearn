package com.zyp.springboot.learn.dto.auth.role_permission;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class RolePermissionQueryReq extends AbstractPagination {
    private Long id;
    private Long roleId;
    private Long permissionId;
}

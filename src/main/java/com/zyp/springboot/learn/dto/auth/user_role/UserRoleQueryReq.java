package com.zyp.springboot.learn.dto.auth.user_role;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class UserRoleQueryReq extends AbstractPagination {
    private Long id;
    private Long uid;
    private Long roleId;
}

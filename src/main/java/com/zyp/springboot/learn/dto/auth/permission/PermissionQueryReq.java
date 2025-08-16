package com.zyp.springboot.learn.dto.auth.permission;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class PermissionQueryReq extends AbstractPagination {
    private Long id;
    private String name;
    private String type;
}

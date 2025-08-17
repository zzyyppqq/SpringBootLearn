package com.zyp.springboot.learn.dto.auth.role;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class RoleQueryReq extends AbstractPagination {
    private Long id;
    private String name;
    private String description;
}

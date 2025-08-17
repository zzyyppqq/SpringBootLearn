package com.zyp.springboot.learn.dto.menu;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class MenuQueryReq extends AbstractPagination {
    private Long id;
    private String name;
    private String showName;
    private String description;
    private String path;
    private Long permissionId;
    private Long parentId;
}

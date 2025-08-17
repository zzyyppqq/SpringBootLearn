package com.zyp.springboot.learn.dto.user;

import com.zyp.springboot.learn.dto.common.table.AbstractPagination;
import lombok.Data;

@Data
public class UserQueryReq extends AbstractPagination {
    private Long id;
    private Long uid; // same as id
    private String nickName;
    private String userName;
}

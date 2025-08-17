package com.zyp.springboot.learn.dto.common;

import lombok.Data;

@Data
public class AdjustReq {
    private Long[] added;
    private Long[] deleted;
}

package com.zyp.springboot.learn.dto.common.table;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Association {
    /**
     * 查询接口所使用的查询字段
     */
    private List<String> queryFields;
    /**
     * 值对应的字段名
     */
    private String valueField;
    /**
     * 前端用户展示的字段，可以合并多个字段用于展示
     */
    private List<String> labelFields;
    /**
     * 后端的查询接口路径，比如/permissions
     */
    private String path;
}

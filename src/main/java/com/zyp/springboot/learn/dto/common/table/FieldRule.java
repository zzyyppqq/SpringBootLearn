package com.zyp.springboot.learn.dto.common.table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldRule {
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 错误提示信息
     */
    private String message;
    /**
     * 字段格式要求（正则表达式）
     */
    private String pattern;
}

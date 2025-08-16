package com.zyp.springboot.learn.dto.common.table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SelectOption {
    /**
     * 前端显示标签
     */
    private String label;
    /**
     * 实际的值，一般是字符串或者数字
     */
    private Object value;

    public SelectOption(String label, Object value) {
        this.label = label;
        this.value = value;
    }
}

package com.zyp.springboot.learn.dto.common.table;

import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class AbstractPagination implements IPagination {
    protected int page;
    @Max(value = 50, message = "一次最多只能获取50条记录")
    protected int size;

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getSize() {
        return size;
    }
}

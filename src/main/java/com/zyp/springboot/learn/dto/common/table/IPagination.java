package com.zyp.springboot.learn.dto.common.table;

public interface IPagination {
    /**
     * 获取第几页的数据，从1开始
     */
    int getPage();

    /**
     * 每页的数据条数
     */
    int getSize();
}

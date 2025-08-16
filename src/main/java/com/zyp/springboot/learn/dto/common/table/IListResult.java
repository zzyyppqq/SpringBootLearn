package com.zyp.springboot.learn.dto.common.table;

import java.util.List;

/**
 * PowerTable表格对应的数据列表接口
 *
 * @param <T> 数据对应的DTO类, 比如PermissionDTO等
 */
public interface IListResult<T> {
    /**
     * 数据列表
     */
    List<T> getData();

    /**
     * 数据总数
     */
    long getTotal();

    void setTotal(long total);

    /*
     * 每个字段的配置
     */
    List<Field> getFields();

    void setFields(List<Field> fields);

    /**
     * 可以进行的操作（比如新增，删除，编辑等）
     */
    Action getAction();

    void setAction(Action action);

}

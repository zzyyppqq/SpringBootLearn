package com.zyp.springboot.learn.dto.common.table.property;


import com.zyp.springboot.learn.constant.TableFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldProperty {
    String title() default "";

    String dataIndex() default ""; // 默认是字段名

    TableFieldType type() default TableFieldType.String;

    boolean canShow() default true;

    boolean canEdit() default false;

    boolean canAdd() default true;

    boolean searchable() default false;
}

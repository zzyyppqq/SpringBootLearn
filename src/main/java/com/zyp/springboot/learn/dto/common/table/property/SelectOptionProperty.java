package com.zyp.springboot.learn.dto.common.table.property;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SelectOptionsProperty.class)
public @interface SelectOptionProperty {
    String label() default "";

    String strValue() default "";

    int intValue() default 0;
}

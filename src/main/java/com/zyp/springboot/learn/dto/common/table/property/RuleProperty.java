package com.zyp.springboot.learn.dto.common.table.property;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RuleProperties.class)
public @interface RuleProperty {
    boolean required() default false;

    String message() default ""; // 默认是字段的title+“不能为空”

    String pattern() default "";
}

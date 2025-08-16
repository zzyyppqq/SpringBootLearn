package com.zyp.springboot.learn.dto.common.table.property;


import com.zyp.springboot.learn.constant.ModuleName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionProperty {
    ModuleName module() default ModuleName.None;

    String addPermission() default "";

    String editPermission() default "";

    String deletePermission() default "";
}

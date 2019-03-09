package com.kg.report.utils;

import java.lang.annotation.*;

/**
 * 标示控制器 或 某个接口 是否需要管理员权限的注解
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoAuth {
}

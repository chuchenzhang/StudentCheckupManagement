package com.tphy.tsykxstj.common.utils;

import java.lang.annotation.*;

/**
 * @author 出陈
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelAlias {

    String value()  default "";

}

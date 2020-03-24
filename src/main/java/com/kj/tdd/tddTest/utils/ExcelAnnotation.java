package com.kj.tdd.tddTest.utils;


import java.lang.annotation.*;

/**
 * @interface 自定义ExcelAnnotation注解
 * 方法名对应注解参数名
 * 方法返回类型对应注解参数类型
 * https://www.cnblogs.com/peida/archive/2013/04/24/3036689.html，
 * https://www.cnblogs.com/peida/archive/2013/04/23/3036035.html
 */
@Target(ElementType.FIELD) //作用在属性范围
@Retention(RetentionPolicy.RUNTIME) //保留到运行时，可在反射中使用该注解
@Documented //文档元注解
public @interface ExcelAnnotation {

    /**
     * 列索引
     * @return
     */
    int columnIndex() default 0;

    /**
     * 列名
     * @return
     */
    String columnName() default "";

}

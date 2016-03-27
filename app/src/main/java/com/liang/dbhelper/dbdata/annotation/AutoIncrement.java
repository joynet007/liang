package com.liang.dbhelper.dbdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liang on 16/3/25.
 * 标识为 自增长字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIncrement{

    /**
     * 定义字段默认长度
     * @return
     */
    public int length() default 32 ;

}

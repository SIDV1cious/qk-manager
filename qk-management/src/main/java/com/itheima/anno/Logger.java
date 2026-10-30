package com.itheima.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE}) // 指定注解能够使用的位置
@Retention(RetentionPolicy.RUNTIME) // 指定注解的生效时期(源码阶段  字节码阶段  运行阶段)
public @interface Logger {

}
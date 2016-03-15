package com.ese.grid.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @File Name : Gid.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.annotation.config
 * @create Date : 2016. 3. 15.
 * @explain : Row데이터 중에서 하나의 Row를 식별가능한 Unique한 Key를  설정
 * @made by : "GOEDOKID"
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gid {
}

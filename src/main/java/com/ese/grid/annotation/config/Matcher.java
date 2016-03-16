package com.ese.grid.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @File Name : Matcher.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.annotation.config
 * @create Date : 2016. 2. 29.
 * @explain : Grider Format 설정이 function일 경우 function인 변수에 선언된 배열 정보에 매핑될 문자열
 * 			  EX) 국제화 메세지, 동일한 ROW 레벨의 다른 컬럼 값  
 * @made by : "GOEDOKID"
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Matcher {
	
	/**
	 * @Method Name : matche
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 동일  ROW 레벨의 다른 컬럼 정보(domain에서 설정한 변수값으로 지정) 
	 * 			  EX) Matcher={test1, test2}
	 * @return : String[]
	 */
	String[] matche() default {};

	/**
	 * @Method Name : message
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 변환할 국제화 메세지
	 * 			  EX) Message={com.ese.test, com.ese.default}  
	 * @return : String[]
	 */
	String[] message() default {};
	
}

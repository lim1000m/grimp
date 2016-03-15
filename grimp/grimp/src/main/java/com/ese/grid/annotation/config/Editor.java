package com.ese.grid.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @File Name : Editor.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.annotation
 * @create Date : 2016. 3. 2.
 * @explain : domain 객체 axisj 사용환경에 맞게 annotation을 설정하는 클래스 
 * @made by : "GOEDOKID"
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Editor {
	
	public enum Type {money, checkbox, calendar, THIS}
	
	/**
	 * @Method Name : Type
	 * @create Date : 2016. 3. 8.
	 * @made by : "GOEDOKID"
	 * @explain : 수정할때 수정되는 타입을 선택함
	 * @param : 
	 * @return : String[]
	 */
	Type type() default Type.THIS;
	
	/**
	 * @Method Name : updateWith
	 * @create Date : 2016. 3. 8.
	 * @made by : "GOEDOKID"
	 * @explain : 수정 후 업데이트 시 KEY 값으로 읽어올릴 값과 작업 종류 선택
	 * 			  0번 INDEX=money,calendar,checkbox, THIS
	 * 			  1번 INDEX=C,U,D,CU,CD,UD
	 * @param : 
	 * @return : String[]
	 */
	String[] updateWith() default {"","C"};
}
 
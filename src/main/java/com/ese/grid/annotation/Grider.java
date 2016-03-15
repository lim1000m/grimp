package com.ese.grid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @File Name : Grider.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.annotation
 * @create Date : 2016. 3. 2.
 * @explain : domain 객체 axisj 사용환경에 맞게 annotation을 설정하는 클래스 
 * @made by : "GOEDOKID"
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Grider {
	
	public enum Align {left, center, right};
	public enum Format {money, function, checkbox, radio, text, customCheckBox};
	public enum Sort {desc, asc, none};
	
	/**
	 * @Method Name : label
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 컬럼 헤더에 표출될 명
	 * 			  default ""
	 * @return : String
	 */
	String label() default "";

	/**
	 * @Method Name : width
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 각각의 그리드 컬럼의 폭을 설정 
	 * 			  default "*"
	 * @return : String
	 */
	String width() default "*";

	/**
	 * @Method Name : align
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 컬럼이 포함한 컨텐츠의 정렬(Align.center, Align.left, Align.right) 
	 * 			  default Align.center
	 * @return : Align
	 */
	Align align() default Align.center;

	/**
	 * @Method Name : display
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 컬럼의 표출 여부(true,false) 
	 * 			  default true 
	 * @return : boolean
	 */
	boolean display() default true;
	
	/**
	 * @Method Name : format
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 컬럼의 포멧 형태 지정(Format.function,Format.money) 
	 * 			  default Format.text 
	 * @param : 
	 * @return : Format
	 */
	Format format() default Format.text;
	
	/**
	 * @Method Name : sort
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 컬럼을 오름차순, 내림차순으로 정렬 (Sort.none, Sort.desc, Sort.asc) 
	 * 			  default Sort.none
	 * @param : 
	 * @return : Sort
	 */
	Sort sort() default Sort.none;

	/**
	 * @Method Name : editor
	 * @create Date : 2016. 3. 8.
	 * @made by : "GOEDOKID"
	 * @explain : 컬럼 내용을 그리드에서 수정 가능한 editable하게 만드는 것 
	 * @return : boolean
	 */
	boolean editor() default false;
}
 
package com.ese.config.advicer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import c.e.g.domain.Grivo;
import c.e.g.grimp.Grimp;

/**
 * @File Name : ResponseControllerAdvice.java
 * @Project Name : grimp
 * @package Name : com.ese.config.controller
 * @create Date : 2016. 3. 22.
 * @explain : Controller에서 HttpStringMessageConverter가 실행되기 전 
 * 			  ControllerAdvice에 Controller.class Annotation 클래스에 의해 
 * 			  @ResponseBody로 반환하는 형태의 메서드에 매핑되어 처리가 가능하다.
 * 			  Grivo 형태만 통과하게 되어 있음 
 * @made by : "GOEDOKID"
 */
@ControllerAdvice(annotations={Controller.class})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

	@Autowired
	private WebApplicationContext was;
	
	private static Grimp grimp = null; 
	
	/**
	 * Grivo 형의 리턴일 경우에만 beforeBodyWrite를 허용
	 */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    	return Grivo.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * Grivo 객체에 대한 리턴을 처리함
     * 1. Grimp 객체 생성 및 WebApplicationContext를 생성자로 적용
     * 2. Grivo 객체 형 변환
     * 3. Grivo 의 getGridCls의 여부에 따라 함수호출
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

    		if(grimp == null)
    			grimp = new Grimp(was);
    			
    		Grivo grivo = (Grivo) body;
    	
    		if(grivo.getGridCls() != null)
    			return grimp.buildGrimp(grivo, grivo.getGridCls());
    		else
    			return grimp.buildGrimp(grivo);
    }
}
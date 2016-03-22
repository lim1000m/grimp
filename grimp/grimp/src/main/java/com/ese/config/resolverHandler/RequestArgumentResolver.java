package com.ese.config.resolverHandler;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ese.config.resolverHandler.RequestMap.RequestMap;

/**
 * @File Name : RequestArgumentResolver.java
 * @Project Name : grimp
 * @package Name : com.ese.config.resolverHandler
 * @create Date : 2016. 3. 22.
 * @explain : Controller에서 requestMap 형태 클래스로 자료를 받을 경우 
 * 			  ArgumentResolver에서 자동으로 형태에 맞게 자료를 셋팅해줌.
 * 			    단, MessageConverter와 사용하면 우선순위에 밀려서 안됨.
 * @made by : "GOEDOKID"
 */
public class RequestArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return RequestMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		RequestMap requestMap = new RequestMap();
        
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Enumeration<?> enumeration = request.getParameterNames();
         
        String key = null;
        String[] values = null;
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            if(values != null){
            	requestMap.put(key, (values.length > 1) ? values:values[0] );
            }
        }
        return requestMap;
	}
}

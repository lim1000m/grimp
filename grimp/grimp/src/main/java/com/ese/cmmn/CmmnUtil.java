package com.ese.cmmn;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CmmnUtil {

	/**
	 * @Method Name : getParameterMap
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : HttpServletRequest Parameter를 HashMap으로 변환 
	 * @param : HttpServletRequest request
	 * @return : Map
	 */
	public static HashMap<String, Object> getParameterMap(HttpServletRequest request){

		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String paramName = (String)enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

			if(parameters.length > 1){
				parameterMap.put(paramName, parameters);
			}else{
				parameterMap.put(paramName, parameters[0]);
			}
		}
		return parameterMap;
	}
	
}

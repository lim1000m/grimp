package com.ese.grid.grider.extend;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;

import com.ese.comm.util.log.COMLog;
import com.ese.grid.annotation.config.Gid;
import com.ese.grid.annotation.config.Matcher;

/**
 * @File Name : GrimpUtil.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.xGrid.extend
 * @create Date : 2016. 3. 4.
 * @explain : AxisJ Grid 컴포넌트에 사용되는 함수 
 * @made by : "GOEDOKID"
 */
public class GrimpUtil {

	//MessageSource
	protected MessageSource message;
		
	//Locale 정보
	protected Locale locale = null;
		
	//WebContext 정보
	protected WebApplicationContext wac = null;
	
	/**
	 * @Method Name : getMetaDataValue
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : 클래스 값에 접근하여 값을 가져온다. 
	 * @param : Object obj(값을 조회할 클래스), String fieldName(값을 조회할 변수명)
	 * @return : Object
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected Object getMetaDataValue(Object obj, String fieldName) 
			throws SecurityException, IllegalArgumentException, IllegalAccessException { 

		Object value = "";
		Field field;		
		
		try {
			field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			value = field.get(obj);
			if(value == null) 
				value = "";
			
		} catch (NoSuchFieldException e) {
			COMLog.error("Not exist name of '"+e.getMessage()+"' variable in "+obj.getClass().getSimpleName()+" class.");
			COMLog.error("Check annotation out and @ notify string is well formed.");
			COMLog.error(e.getMessage());
		}
		return value;
	}
	
	/**
	 * @Method Name : getMessage
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : 메세지를 조회하는데 없을 경우 코드를 그대로 출력한다. 
	 * @param : String key, Locale locale
	 * @return : String
	 */
	protected String getMessage(String key) {
		return this.message.getMessage(key, new String[]{}, key, locale);
	}
	
	/**
	 * @Method Name : cnvtClmnToDmn
	 * @create Date : 2016. 3. 4.
	 * @made by : "GOEDOKID"
	 * @explain : goe_do_kid 형태의 DB 컬럼을 goeDoKid 형태로 변경
	 * @param : String value
	 * @return : String
	 */
	protected String cnvtClmnToDmn(String value) {
		
		char chr;
		StringBuffer sbf = new StringBuffer();
		String str = value.toLowerCase();
		int cnt = 0;
		
		for(int i=0; i<str.length(); i++) {
			chr = str.charAt(i);
			if(chr == '_') {
				cnt = 1;
			}else {
				if(cnt == 1) {
					sbf.append(Character.toUpperCase((char)chr));
					cnt = 0;
				}else {
					sbf.append(chr);
				}
			}
		}
		
		return sbf.toString();
	}
	
	/**
	 * @Method Name : cnvtDmnToClmn
	 * @create Date : 2016. 3. 4.
	 * @made by : "GOEDOKID"
	 * @explain : goeDoKid 형태의 DB 컬럼을 goe_do_kid  형태로 변경
	 * @param : String value
	 * @return : String
	 */
	protected String cnvtDmnToClmn(String name){
		StringBuffer result = new StringBuffer();
    	
    	if(name != null){
    		for(int i=0; i<name.length(); i++){
        		int code = name.charAt(i);
        		
        		if(code >= 65 && code <= 90){		// 대문자인 경우
        			result.append("_" + Character.toLowerCase((char) code));
        		}else{								// 소문자인 경우
        			result.append((char) code);
        		}
        	}
    	}
    	return result.toString();
	}
	
	/**
	 * @Method Name : getFunction
	 * @create Date : 2016. 3. 2.
	 * @made by : "GOEDOKID"
	 * @explain : 도메인의 변수 속성이 Format의 function일 경우 
	 * 			    동일 레벨의 function 패키지의 동일한 클래스명의 생성된 클래스에서 해당 변수와 같은 이름으로 
	 * 			    선언된 변수를 찾아 어노테이션 정보 및 변수 정보를 조회    
	 * @param : 
	 * @return : String
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws FieldNullPointException 
	 */
	@SuppressWarnings("unchecked")
	protected String getFunction(Object obj, String fieldName, Class<?>  mainCls) 
			throws InstantiationException, IllegalAccessException, 
				   ClassNotFoundException, NullPointerException, NoSuchFieldException { 
		
		Class<?> dataCls = null;
		String[] fncStr = null;
		String[] matArr = null;
		String[] msgArr = null;
		Field fncField = null;
		Object fncCls = null;
		String className = "";
		
		StringBuffer bufferFnc = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(mainCls != null) {
			map = (HashMap<String, Object>) obj;
			className = mainCls.getPackage().getName()+".function."+mainCls.getSimpleName();
		} else {
			dataCls = obj.getClass();
			className = dataCls.getPackage().getName()+".function."+dataCls.getSimpleName();
		}
		
		try {
			fncCls = Class.forName(className).newInstance();
			fncField = fncCls.getClass().getDeclaredField(fieldName);
			fncField.setAccessible(true);
			fncStr = (String[]) fncField.get(fncCls);
			
			matArr = fncField.getAnnotation(Matcher.class).matche();
			msgArr = fncField.getAnnotation(Matcher.class).message();
		
			if(fncStr.length > 0) {
				for (int i = 0; i < fncStr.length; i++) {
					String fncIndex = fncStr[i];
					if(matArr.length > 0) {
						for (int j = 0; j < matArr.length; j++) {
							Object value = "";
							if(mainCls != null) {
								if(map.containsKey(cnvtDmnToClmn(matArr[j]))) 
									value = map.get(cnvtDmnToClmn(matArr[j]));
							} else 	value = getMetaDataValue(obj, matArr[j]);
							if(value != "") 
								fncIndex = fncIndex.replaceAll("@"+matArr[j], value.toString());	
						}
					}
					if(msgArr.length > 0) {
						for (int j = 0; j < msgArr.length; j++) {
							fncIndex = fncIndex.replaceAll("@"+msgArr[j], getMessage(msgArr[j]));
						}	
					}
					bufferFnc.append(fncIndex);
				}	
			}
		} catch(NullPointerException e) {
			throw new NullPointerException();
		} 
		return bufferFnc.toString();
	}
	
	/**
	 * @Method Name : getCustomCheckBox
	 * @create Date : 2016. 3. 15.
	 * @made by : "GOEDOKID"
	 * @explain : Format.customCheckBox 설정이 되어 있을 경우 
	 * @param : Map<String, Object> map(Row 정보) 
	 * 			String fieldName(name으로 쓸 customCheckBox가 설정된 이름)
	 * 			String keyVar(Row에 @Gid로 설정되 있는 경우에 식별 가능한 값) 
	 * @return : String
	 */
	protected String getCustomCheckBox(Map<String, Object> map, String fieldName, String keyVar) {
		return "<input type='checkbox' name='"+fieldName+"' value='"+map.get(cnvtDmnToClmn(keyVar).toLowerCase())+"'>";
	}
	
	/**
	 * @Method Name : getCustomCheckBox
	 * @create Date : 2016. 3. 15.
	 * @made by : "GOEDOKID"
	 * @explain : Format.customCheckBox 설정이 되어 있을 경우 
	 * @param : String fieldName(name으로 쓸 customCheckBox가 설정된 이름) 
	 * 			String fieldValue(Row에 @Gid로 설정되 있는 경우에 식별 가능한 값) 
	 * @return : String
	 */
	protected String getCustomCheckBox(Object fieldValue, String fieldName) {
		return "<input type='checkbox' name='"+fieldName+"' value='"+fieldValue+"'>";
	}
	
	/**
	 * @Method Name : getGrimpGid
	 * @create Date : 2016. 3. 15.
	 * @made by : "GOEDOKID"
	 * @explain : CheckBox 구성시 Domain 객체에 설정된 유니크한 키로 정보로 사용할 KEY 변수를 조회한다.
	 * @param : 
	 * @return : String
	 */
	protected String getGrimpGid(Class<?> cls) {
		Field[] fieldList = cls.getDeclaredFields();
		String keyVar = "";
		for (int i = 0; i < fieldList.length; i++) {
			Gid gid = fieldList[i].getAnnotation(Gid.class);
			if(gid != null) 
				keyVar = fieldList[i].getName();
		}
		return keyVar;
	}
	
}

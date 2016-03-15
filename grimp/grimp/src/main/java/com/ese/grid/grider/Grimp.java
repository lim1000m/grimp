package com.ese.grid.grider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.ese.comm.util.log.COMLog;
import com.ese.grid.annotation.Grider;
import com.ese.grid.annotation.Grider.Format;
import com.ese.grid.annotation.Grider.Sort;
import com.ese.grid.annotation.config.Editor;
import com.ese.grid.annotation.config.Editor.Type;
import com.ese.grid.annotation.config.Gid;
import com.ese.grid.domain.Grivo;
import com.ese.grid.exception.GrimpExceptionHandler;
import com.ese.grid.exception.GrimpExceptionHandler.GrimpError;
import com.ese.grid.grider.extend.GrimpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @File Name : xGridUril.java
 * @Project Name : axgrid
 * @package Name : grid.xGrid
 * @create Date : 2016. 2. 19.
 * @explain : Domain 객체의 Annotation 정보는 토대로 Grid,Tree에 적합한 Json,Xml 형태로 생성
 * @made by : "GOEDOKID"
 * @version 0.9
 * @since 수정자/수정내용/날짜
 * @see 김영훈/grimp Grid Util 생성/2016.02.18
 */
@Component
public class Grimp extends GrimpUtil {
	
	//데이터를 화면에 출력할 형태
	public enum ExprType {TREE, GRID};
	//데이터 포맷 형태
	public enum DataType {JSON, XML};
	
	//각각의 형태에 대한 Default 값 설정
	private ExprType exprType = ExprType.GRID;
	private DataType dataType = DataType.JSON;

	/**
	 * @Component를 통해서 등록될때 사용하는 생서앚 
	 */
	public Grimp() {}
	
	/**
	 * Spring Config에  @Bean설정을 통해 등록될때 사용할 생성자
	 * @param message
	 */
	public Grimp(MessageSource message) {
		this.message = message;
	}

	/**
	 * @Bean생성을 통해서 WebApplicationContext를 생성자로 받을 경우
	 * @param wac
	 */
	public Grimp(WebApplicationContext wac) {
		this.wac = wac;
		this.message = (MessageSource) wac.getBean("messageSource");
	}
	
	/**
	 * @Method Name : headerBuilder
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : MessageSource를 직접 전달해서 사용 
	 * @param : Class<?> cls(해더 정보를 추출할 클래스), MessageSource message, Locale locale
	 * @return : String
	 */
	public String buildGrimpHeader(Class<?> cls, MessageSource message, Locale locale) {
		this.message = message;
		this.locale = locale;
		String buildStr = "";
		
		try {
			buildStr = buildHeader(cls);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : headerBuilder
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : @Bean설정에 의해서 MessageSource가 등록되었을 경우 사용
	 * @param : Class<?> cls, Locale locale
	 * @return : String
	 */
	public String buildGrimpHeader(Class<?> cls, Locale locale) {
		this.locale = locale;
		String buildStr = "";
		
		try {
			buildStr = buildHeader(cls);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grino, Class<?> cls          
	 * @return : String
	 */	
	public String buildGrimp(Grivo grivo, Class<?> cls) {
		
		String buildStr = ""; 
		try {
			buildStr = buildTypeCheck(grivo, cls);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grivo          
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	public String buildGrimp(Grivo grivo) {
		
		String buildStr = ""; 
		try {
			buildStr = buildTypeCheck(grivo , null);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildTypeCheck
	 * @create Date : 2016. 3. 4.
	 * @made by : "GOEDOKID"
	 * @explain : 원하는 타입에 따라 데이터 조합 형태 분기 
	 * @param : Grivo grivo, Class<?> cls
	 * 			Domain 형태일 경우 : buildTypeCheck(Grivo grivo)
	 * 	 		Map 형태일 경우 : buildTypeCheck(Grivo grivo, Domain.class)
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	public String buildTypeCheck(Grivo grivo, Class<?> cls) throws GrimpExceptionHandler {
		
		if(grivo.getPager().containsKey("exprType")) 
			this.exprType = (ExprType) grivo.getPager().get("exprType");
		if(grivo.getPager().containsKey("dataType"))
			this.dataType = (DataType) grivo.getPager().get("dataType");
		
		String grimpRst = "";
		
		try {
			if(this.exprType.equals(ExprType.GRID)) {
				if(this.dataType.equals(DataType.JSON))
					if(cls != null)
						grimpRst = getJsonToGridMap(grivo, cls);
					else 
						grimpRst = getJsonToGridDomain(grivo);
				else
					grimpRst = getXmlToGrid(grivo);
			} else {
				if(this.dataType.equals(DataType.JSON))
					grimpRst = getJsonToTree(grivo);
				else
					grimpRst = getXmlToTree(grivo);
			}
		
		} catch (JsonProcessingException e) {
			throw new GrimpExceptionHandler(GrimpError.JPE, e);
		} catch (InstantiationException e) {
			throw new GrimpExceptionHandler(GrimpError.CIE, e);
		} catch (ClassNotFoundException e) {
			throw new GrimpExceptionHandler(GrimpError.CNFE, e);
		} catch (IllegalAccessException e) {
			throw new GrimpExceptionHandler(GrimpError.IAE, e);
		} catch (NoSuchFieldException e) {
			throw new GrimpExceptionHandler(GrimpError.NSFE, e);
		} catch (NullPointerException e) {
			throw new GrimpExceptionHandler(GrimpError.NPE, e);
		} 
			
		return grimpRst;
	}
	
	/**
	 * @Method Name : buildHeader
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 헤더 정보 생성 
	 * @param : Class<?> cls(Annotation을 설정한 클래스)
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	private String buildHeader(Class<?> cls) throws GrimpExceptionHandler {
		
		Field[] fields = cls.getDeclaredFields();
		
		ArrayList<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = "";
		
		try {
		
			for (int i = 0; i < fields.length; i++) {
				Grider grider = fields[i].getAnnotation(Grider.class);
				
				if(grider != null) {
					Map<String, Object> map  = new HashMap<String, Object>();
					map.put("key", fields[i].getName());
					map.put("label", getMessage(grider.label()));
					map.put("display", grider.display());
					map.put("align", grider.align());
					map.put("width", grider.width());
					
					if(grider.sort().equals(Sort.none)) map.put("sort", false); 
					else map.put("sort", grider.sort());
					
					if(!grider.format().equals(Format.function)) 
						if(!grider.format().equals(Format.text) && !grider.format().equals(Format.customCheckBox))
							map.put("formatter", grider.format());
						
					/**
					 * 에디터 설정 영역 차후 반영 검토
					 */
					if(grider.editor()) {
						String className = cls.getPackage().getName()+".function."+cls.getSimpleName();
						Object fncCls;
						
						fncCls = Class.forName(className).newInstance();
						
						Field fncField;
						fncField = fncCls.getClass().getDeclaredField(fields[i].getName());
						
						fncField.setAccessible(true);
						String[] updateWith = fncField.getAnnotation(Editor.class).updateWith();
						Type typeEnum = fncField.getAnnotation(Editor.class).type();
						
						if(updateWith[0].equals("")) 
							updateWith[0] = fields[i].getName();
						
						Map<String, Object> editorFnc = new HashMap<String, Object>();
						editorFnc.put("type", typeEnum);
						editorFnc.put("updateWith", updateWith);
						map.put("editor", editorFnc);
					}
					jsonList.add(map);	
				} 
			}
			
			jsonResult = mapper.writeValueAsString(jsonList);
		} catch (JsonProcessingException e) {
			throw new GrimpExceptionHandler(GrimpError.JPE, e);
		} catch (InstantiationException e) {
			throw new GrimpExceptionHandler(GrimpError.CIE, e);
		} catch (IllegalAccessException e) {
			throw new GrimpExceptionHandler(GrimpError.IAE, e);
		} catch (ClassNotFoundException e) {
			throw new GrimpExceptionHandler(GrimpError.CNFE, e);
		} catch (NoSuchFieldException e) {
			throw new GrimpExceptionHandler(GrimpError.NSFE, e);
		}
		return jsonResult;
	}
	
	/**
	 * @Method Name : getJsonToGrid
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 목록을 JSON 형태로 반환
	 * @param : Grivo grivo 
	 * @return : void
	 * @throws JsonProcessingException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws FieldNullPointException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	private String getJsonToGridDomain(Grivo grivo) 
			throws JsonProcessingException, IllegalArgumentException, 
				   IllegalAccessException, InstantiationException, ClassNotFoundException, 
				   NoSuchFieldException, NullPointerException { 

		Map<String, Object> grimpMap = new HashMap<String, Object>(); //최종 JSON 형태를 생성하는 grimpMap
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); //데이터 정보를 생성하는 MAP
		Map<String, Integer> pageMap = new HashMap<String, Integer>(); //페이지 정보를 생성하는 MAP
		Grider grider = null; //Grider Annotation vo 정의
		String mapperJson = ""; //최종 JSON 리턴
		
		ArrayList<?> grivoList = grivo.getGridList(); //데이터(VO), 페이징 정보
		Map<String, Object> grivoPager = grivo.getPager(); //grivo에서 페이징 정보 획득
		
		
		if(grivoList.size() > 0) { //조회된 데이터가 없을 경우
			
			Class<?> cls = grivoList.get(0).getClass();
			Field[] fields = cls.getDeclaredFields(); //데이터 리스트의 첫번째 데이터의 클래스에 정의된 필드를 조회한다.
			String keyVar = getGrimpGid(cls);
			
			for (Object obj :  grivoList) { 
				
				Map<String, Object> jsonMap  = new HashMap<String, Object>();
				
				for (int i = 0; i < fields.length; i++) {
					grider = fields[i].getAnnotation(Grider.class);
					if(grider != null) {
						if(!grider.format().equals(Format.function)) {
							if(grider.format().equals(Format.customCheckBox)) {
								jsonMap.put(fields[i].getName(), getCustomCheckBox(getMetaDataValue(obj, keyVar), fields[i].getName()));		
							} else {
								jsonMap.put(fields[i].getName(), getMetaDataValue(obj, fields[i].getName()));
							}
						} else {
							jsonMap.put(fields[i].getName(), getFunction(obj, fields[i].getName(), null));
						}
					}
				}
				dataList.add(jsonMap);
			}
		}
		
		pageMap.put("pageNo", Integer.parseInt(grivoPager.get("pageNo").toString())); //현재 페이지
		pageMap.put("pageCount", Integer.parseInt(grivoPager.get("pageCnt").toString())); //페이지 개수
		pageMap.put("listCount", Integer.parseInt(grivoPager.get("totalCnt").toString())); //페이지 사이즈
		
		grimpMap.put("list", dataList);
		grimpMap.put("page", pageMap);
		
		ObjectMapper mapper = new ObjectMapper();
	
		mapperJson = mapper.writeValueAsString(grimpMap);
			
		
		return mapperJson;
	}
	
	/**
	 * @Method Name : getJsonToGrid
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 목록을 JSON 형태로 반환
	 * @param : Grivo grivo 
	 * @return : void
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws FieldNullPointException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws JsonProcessingException 
	 */
	@SuppressWarnings("unchecked")
	private String getJsonToGridMap(Grivo grivo, Class<?> cls) 
			throws NoSuchFieldException, InstantiationException, 
				   IllegalAccessException, ClassNotFoundException, GrimpExceptionHandler, 
				   JsonProcessingException { 

		Map<String, Object> grimpMap = new HashMap<String, Object>(); //최종 JSON 형태를 생성하는 grimpMap
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); //데이터 정보를 생성하는 MAP
		Map<String, Integer> pageMap = new HashMap<String, Integer>(); //페이지 정보를 생성하는 MAP
		Grider grider = null; //Grider Annotation vo 정의
		String mapperJson = ""; //최종 JSON 리턴
		
		ArrayList<?> grivoList = grivo.getGridList(); //데이터(VO), 페이징 정보
		Map<String, Object> grivoPager = grivo.getPager(); //grivo에서 페이징 정보 획득
		
		if(grivoList.size() > 0) { //조회된 데이터가 없을 경우
			String keyVar = getGrimpGid(cls);
			for (Object  map :  grivoList) { 
				Map<String, Object> jsonMap  = new HashMap<String, Object>();
				Map<String, Object> thisMap = (Map<String, Object>) map;
				Field[] fields = cls.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					grider = fields[i].getAnnotation(Grider.class);
					if(grider != null) {
						if(!grider.format().equals(Format.function)) {
							if(grider.format().equals(Format.customCheckBox)) 
								jsonMap.put(fields[i].getName(), getCustomCheckBox(thisMap, fields[i].getName(), keyVar));
							else
								jsonMap.put(fields[i].getName(), thisMap.get(cnvtDmnToClmn(fields[i].getName())));	
						} else {
							jsonMap.put(fields[i].getName(), getFunction(map, fields[i].getName(), cls));
						}
					}
				}
				dataList.add(jsonMap);
			}
		}
		
		pageMap.put("pageNo", Integer.parseInt(grivoPager.get("pageNo").toString())); //현재 페이지
		pageMap.put("pageCount", Integer.parseInt(grivoPager.get("pageCnt").toString())); //페이지 개수
		pageMap.put("listCount", Integer.parseInt(grivoPager.get("totalCnt").toString())); //페이지 사이즈
		
		grimpMap.put("list", dataList);
		grimpMap.put("page", pageMap);
		
		ObjectMapper mapper = new ObjectMapper();
		mapperJson = mapper.writeValueAsString(grimpMap);
			
		return mapperJson;
	}
	
	/**
	 * @Method Name : getXmlToGrid
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : GRID에 XML 형태로 데이터를 리턴
	 * @param : ArrayList<Object> clsList 
	 * @return : String
	 */
	private String getXmlToGrid(Grivo grivo) {
		return "";
	}

	/**
	 * @Method Name : getXmlToTree
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : XML 형태로 TREE에 표출
	 * @param : ArrayList<Object> clsList
	 * @return : String
	 */
	@Deprecated
	private String getXmlToTree(Grivo grivo) {
		return "";
	}

	/**
	 * @Method Name : getJsonToTree
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : JSON 형태로 TREE에 표출 
	 * @param : ArrayList<Object> clsList
	 * @return : void
	 */
	@Deprecated
	private String getJsonToTree(Grivo grivo) {
		return "";
	}
	
	/**
	 * @Method Name : buildSelectString
	 * @create Date : 2016. 3. 9.
	 * @made by : "GOEDOKID"
	 * @explain : Select Box 조회시 AxisJ에 알맞는 SelectBox json을 리턴한다.
	 * @param : ArrayList<Map<String, Object>> selStr (key, value)
	 * @return : String
	 */
	public String buildSelectString(ArrayList<Map<String, Object>> selStr) {
		
		Map<String, Object> mainStr = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> listStr = new ArrayList<Map<String, Object>>();
		String returnStr = "";
		if(selStr.size() > 0) {
			mainStr.put("result", "ok");
			for(Map<String, Object> str : selStr) {
				Map<String, Object> cntnMap = new HashMap<String, Object>();
				cntnMap.put("optionValue", str.get("key"));
				cntnMap.put("optionText", str.get("value"));
				listStr.add(cntnMap);
			}
			mainStr.put("options", listStr);
			mainStr.put("etcs", "");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			returnStr = mapper.writeValueAsString(mainStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return returnStr;
	}
}

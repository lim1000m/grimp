package com.ese.main;

import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import c.e.g.domain.Grivo;
import c.e.g.grimp.Grimp;

import com.ese.cmmn.CmmnUtil;
import com.ese.config.resolverHandler.RequestMap.RequestMap;
import com.ese.domain.Dvsn;
import com.ese.main.service.MainService;

/**
 * @File Name : MainController.java
 * @Project Name : grimp
 * @pakage Name : com.ese.main
 * @create Date : 2016. 2. 23.
 * @explain : AXISJ GRID 테스트 클래스
 * 			    클래스 단에 RequestMapping을 넣어준 것은 
 * 			  HttpStringConverter 기본 설정이 text/plain charset=ISO-8859이여서 
 * 			    한글 정보가 깨진다. response header의 content-type을 변경한다. 
 *            response content-type이 application/json charset=UTF-8이라면 
 *            제거해도 된다.
 * @made by : "GOEDOKID"
 */
@Controller
@RequestMapping(produces="application/json; charset=utf8") 
public class MainController {
	
	@Autowired
	Grimp grimp;

	@Autowired
	MainService mainService;
	
	@Autowired
	MessageSource message;
	
	
	/**
	 * @Method Name : main
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 화면 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/main")
	public String main(
			Model model,
			Locale locale
			) {
		model.addAttribute("grimpHeader", grimp.buildGrimpHeader(Dvsn.class, message, locale));
		return "main/main";
	}
	
	/**
	 * @Method Name : grider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 목록 조회 ControllerAdvice, ArgumentResolver Spring 설정
	 * @param : RequestMap requestMap
	 * @return : Grivo
	 */
	@RequestMapping(value="/grider")
	public @ResponseBody Grivo grider(
			RequestMap requestMap
			) {
		return mainService.getDvsnDomain(requestMap.getMap());
	}
	
	/**
	 * @Method Name : mrider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 맵 그리드 목록 조회 ControllerAdvice, ArgumentResolver Spring 설정
	 * @param : RequestMap requestMap
	 * @return : Grivo
	 */
	@RequestMapping(value="/mrider")
	public @ResponseBody Grivo mrider(
			RequestMap requestMap
			) {
		return mainService.getDvsnMap(requestMap.getMap());
	}
	
	/**
	 * @Method Name : grider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 목록 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/griderStr")
	public @ResponseBody String griderStr(
			@RequestParam("pageNo") String pageNo,
			@RequestParam("pageSize") String pageSize
			) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNo", pageNo);
		paramMap.put("pageSize", pageSize);
		
		return grimp.buildGrimp(mainService.getDvsnDomain(paramMap));
	}
	
	/**
	 * @Method Name : mrider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 맵 그리드 목록 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/mriderStr")
	public @ResponseBody String mriderStr(
			@RequestParam("pageNo") String pageNo,
			@RequestParam("pageSize") String pageSize
			) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNo", pageNo);
		paramMap.put("pageSize", pageSize);
		
		return grimp.buildGrimp(mainService.getDvsnMap(paramMap), Dvsn.class);
	}
	
	/**
	 * @Method Name : template
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 템플릿 그리드
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/template")
	public String template() {
		return "angur/axGrid_temp";
	}
	
	/**
	 * @Method Name : single
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 테스트용 싱글 그리드 
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/single")
	public String single(
			HttpServletRequest request,
			Locale locale,
			Model model
			) {
		String grimpHeader = grimp.buildGrimpHeader(Dvsn.class, message, locale);
		model.addAttribute("grimpHeader", grimpHeader);
		return "angur/axGrid_single";
	}
	
	/**
	 * @Method Name : getDvsnCd
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : Grid 내부의 SelectBox 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/getDvsnCd")
	public @ResponseBody String getDvsnCd() {
		return grimp.buildSelectString(mainService.getDvsnCd());
	}
	
	/**
	 * @Method Name : saveGrid
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 저장 및 수정
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/saveGrid")
	public @ResponseBody String saveGrid(
			HttpServletRequest request
			) {

		Map<String, Object> paramMap = CmmnUtil.getParameterMap(request); 

		String type = paramMap.get("requestType").toString();
		int result = 0;
		JSONObject json = new JSONObject();
		
		if(type.equals("new")) {
			paramMap.put("ordr", 1);
			result = mainService.addDvsnCd(paramMap);	
		} else {
			result = mainService.updateDvsnCd(paramMap);
		}
		json.put("isNew", type);
		
		if(result > 0)
			json.put("isDone", true);
		else
			json.put("isDone", false);
		
		return json.toString();
	}
	
	/**
	 * @Method Name : deleteDvsn
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드 삭제 
	 * @param : String dvsnCd
	 * @return : String
	 */
	@RequestMapping(value="/deleteDvsn")
	public @ResponseBody String deleteDvsn(
			@RequestParam(name="dvsnCd") String dvsnCd
			) {
	
		int result = mainService.deleteDvsn(dvsnCd);
		
		if(result > 0)
			return "true";
		else
			return "false";
	}
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016. 3. 14.
	 * @made by : "GOEDOKID"
	 * @explain : Hibernate로 조회 
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="getDvsnList")
	public @ResponseBody String getDvsnList(
			@RequestParam("pageNo") String pageNo,
			@RequestParam("pageSize") String pageSize
			) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNo", pageNo);
		paramMap.put("pageSize", pageSize);
		
		Grivo grivo =  mainService.getDvsnList(paramMap);
		
		return grimp.buildGrimp(grivo);
	}
}

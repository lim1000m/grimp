package com.ese.main.service.mapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ese.domain.Dvsn;

/**
 * @File Name : MainMapper.java
 * @Project Name : DMS
 * @pakage Name : com.ese.main.service.mapper
 * @create Date : 2015. 9. 2.
 * @explain : Mapper 
 * @made by : GOEDOKID
 */
public interface MainMapper {

	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Dvsn>
	 */

	@Select("SELECT * FROM("
			+ "		SELECT ROW_NUMBER() OVER() AS no, DVSN_CD dvsnCd, DVSN_NM dvsnNm, STD_DVSN_CD stdDvsnCd, ORDR ordr, USE_YN useYn "
			+ "		FROM EEAM_DVSN_TBL) A "
		    + "WHERE no BETWEEN ${startNum} AND ${endNum} "
		    + "ORDER BY no") 
	ArrayList<Dvsn> getDvsnDomain(Map<String, Object> paramMap);
	
	/**
	 * @Method Name : getDvsnCnt
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 카운트 
	 * @param : 
	 * @return : int
	 */
	@Select("SELECT count(dvsn_cd) FROM EEAM_DVSN_TBL")
	int getDvsnDomainCnt();
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Map<String, String>>
	 */
	@Select("SELECT dvsn_cd, dvsn_nm, ordr, std_dvsn_cd, use_yn FROM EEAM_DVSN_TBL")
	ArrayList<Map<String, String>> getDvsnMap();
	
	/**
	 * @Method Name : getDvsnCnt
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 카운트 
	 * @param : 
	 * @return : int
	 */
	@Select("SELECT count(dvsn_cd) FROM EEAM_DVSN_TBL")
	int getDvsnMapCnt();
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Map<String, Object>
	 */

	@Select("SELECT * FROM("
			+ "		SELECT ROW_NUMBER() OVER() AS no, DVSN_CD, DVSN_NM, STD_DVSN_CD, ORDR, USE_YN, ORDR as nowordr"
			+ "		FROM EEAM_DVSN_TBL "
			+ "		ORDER BY ordr) A "
		    + "WHERE no BETWEEN ${startNum} AND ${endNum} "
		    + "ORDER BY no") 
	ArrayList<Map<String, Object>> getDvsnDomainMap(Map<String, Object> paramMap);
	
	/**
	 * @Method Name : getDvsnCd
	 * @create Date : 2016. 3. 9.
	 * @made by : "GOEDOKID"
	 * @explain : dvsnCd 리스트 조회 
	 * @return : Map<String,Object>
	 */
	@Select("SELECT DVSN_CD as key, DVSN_CD as value FROM EEAM_DVSN_TBL")
	ArrayList<Map<String, Object>> getDvsnCd();
	
	/**
	 * 
	 * @Method Name : updateDvsnCd
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드 수정
	 * @param : Map<String, Object> paramMap
	 * @return : int
	 */
	@Update("UPDATE EEAM_DVSN_TBL SET dvsn_cd = #{dvsnCd}, dvsn_nm = #{dvsnNm}, use_yn = #{useYn} WHERE std_dvsn_cd = #{stdDvsnCd}")
	int updateDvsnCd(Map<String, Object> paramMap);
	
	/**
	 * @Method Name : deleteDvsn
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드 삭제 
	 * @param : String dvsnCd
	 * @return : int
	 */
	@Delete("DELETE FROM EEAM_DVSN_TBL WHERE DVSN_CD = #{dvsnCd}")
	int deleteDvsn(String dvsnCd);
	
	/**
	 * @Method Name : addDvsn
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드를 등록 
	 * @param : Map<String, Object> paramMap
	 * @return : int
	 */
	@Insert("INSERT INTO EEAM_DVSN_TBL (dvsn_cd, dvsn_nm, use_yn, std_dvsn_cd, ordr) VALUES (#{dvsnCd},#{dvsnNm},#{useYn},#{stdDvsnCd},#{ordr})")
	int addDvsnCd(Map<String, Object> paramMap);
 }
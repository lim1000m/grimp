package com.ese.grid.grider;

import java.util.Map;

import com.ese.comm.util.log.COMLog;

/**
 * @File Name : Pager.java
 * @Project Name : grimp
 * @package Name : com.ese.grid.xGrid
 * @create Date : 2016. 2. 24.
 * @explain : 페이징 처리 
 * @made by : "GOEDOKID"
 */
public class Pager {

	/**
	 * @Method Name : paging
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : 페이징 처리 메서드 
	 * @param : int pageNo(페이지 번호), int pageSize(페이지 크기), int totalCnt(조회된 전체크기)
	 * @return : Integer[]
	 */
	public static Integer[] paging(int pageNo, int pageSize, int totalCnt) {
		Integer[] paging = new Integer[3];
		
		int pageCnt = totalCnt/pageSize;
		if(pageCnt == 0) pageCnt = 1;
		
		int startNum = 1;
		if(pageNo > 1) startNum = (pageNo-1)*pageSize;
		
		int endNum = startNum+pageSize-1;
		
		paging[1] = startNum;
		paging[2] = endNum;
		paging[3] = pageCnt;
		paging[4] = totalCnt;
		
		return paging;
	}
	
	/**
	 * @Method Name : paging
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : Map<String, Object> 형태의 페이징 처리 
	 * @param : Map<String, String> Map, int totalCnt
	 * @return : Map<String,Object>
	 */
	public static Map<String, Object> paging(Map<String, Object> map, int totalCnt) {
		
		COMLog.debug(map.toString());
		COMLog.debug(Integer.toString(totalCnt));
		
		int pageNo = Integer.parseInt(map.get("pageNo").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		
		int pageCnt = totalCnt/pageSize;
		if(pageCnt == 0) pageCnt = 1;
		if(pageSize*pageCnt < totalCnt) pageCnt++;
		int startNum = 1;
		startNum = ((pageNo-1)*pageSize);
		int endNum = startNum+pageSize;
		if(pageNo > 1) startNum++;
		
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("pageCnt", pageCnt);
		map.put("totalCnt", totalCnt);
		
		COMLog.debug(map.toString());
		
		return map;
	}
	
}
 
package com.ese.main.service;

import java.util.ArrayList;

import java.util.Map;

import javax.persistence.PostLoad;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import c.e.g.domain.Grivo;
import c.e.g.grimp.Pager;

import com.ese.domain.Dvsn;
import com.ese.main.service.mapper.MainMapper;

@Service("mainService")
public class MainService extends HibernateDaoSupport {

	/** Mybatis session	 */
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	/** Hibernate session */
	@Autowired
	public MainService(@Qualifier(value="sessionFactory") SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 분야 정보 조회
	 * @param : 
	 * @return : Grivo
	 */
	@SuppressWarnings("unchecked")
	public Grivo getDvsnList(Map<String, Object> paramMap) {
		
		Session session = null;
		ArrayList<Dvsn> dvsn = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			int rowCount = ((Long) session.createCriteria(Dvsn.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
			
			paramMap = Pager.paging(paramMap, rowCount);
			
			Criteria crtria = session.createCriteria(Dvsn.class);
					
			crtria = session.createCriteria(Dvsn.class)
			.setFirstResult(Integer.parseInt(paramMap.get("startNum").toString()))
			.setMaxResults(Integer.parseInt(paramMap.get("endNum").toString()));
			dvsn = (ArrayList<Dvsn>) crtria.list();	
		} finally {
			session.close();	
		}
		
		return new Grivo(paramMap, dvsn);
	}


	/**
	 * @Method Name : getDvsnDomain
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : 도메인 조회
	 * @param : Map<String, Object> paramMap
	 * @return : Grivo(Map<String, Object>, ArrayList<Class<?>>)
	 */
	public Grivo getDvsnDomain(Map<String, Object> paramMap) {
		MainMapper mapper = sqlSessionTemplate.getMapper(MainMapper.class);
		int count = mapper.getDvsnDomainCnt();
		paramMap = Pager.paging(paramMap,count);
		return new Grivo(paramMap, mapper.getDvsnDomain(paramMap));
	}
	
	/**
	 * @Method Name : getDvsnMap
	 * @create Date : 2016. 3. 3.
	 * @made by : "GOEDOKID"
	 * @explain : 맵으로 조회
	 * @param : Map<String, String> paramMap
	 * @return : Grivo(Class<?>, Map<String, Object>, ArrayList<Map<String, Object>>)
	 * 		Map형태로 변경
	 * 		기존 프로젝트 변경 방법
	 */
	public Grivo getDvsnMap(Map<String, Object> paramMap) {
		MainMapper mapper = sqlSessionTemplate.getMapper(MainMapper.class);
		int count = mapper.getDvsnDomainCnt();
		paramMap = Pager.paging(paramMap,count);
		return new Grivo(Dvsn.class, paramMap, mapper.getDvsnDomainMap(paramMap));
	}

	/**
	 * @Method Name : getDvsnCd
	 * @create Date : 2016. 3. 9.
	 * @made by : "GOEDOKID"
	 * @explain : dvsnCd 셀렉트 박스 조회 
	 * @param : 
	 * @return : ArrayList<Map<String, Object>>
	 */
	public ArrayList<Map<String, Object>> getDvsnCd() {
		MainMapper mapper = sqlSessionTemplate.getMapper(MainMapper.class);
		return mapper.getDvsnCd();
	}

	/**
	 * @Method Name : updateDvsnCd
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드 수정 
	 * @param : 
	 * @return : int
	 */
	public int updateDvsnCd(Map<String, Object> paramMap) {
		return sqlSessionTemplate.getMapper(MainMapper.class).updateDvsnCd(paramMap);
	}
	
	/**
	 * @Method Name : deleteDvsn
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류정보 삭제 
	 * @param : String dvsnCd
	 * @return : int
	 */
	public int deleteDvsn(String dvsnCd) {
		return sqlSessionTemplate.getMapper(MainMapper.class).deleteDvsn(dvsnCd);
	}
	
	/**
	 * @Method Name : addDvsnCd
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 분류코드 등록 
	 * @param : 
	 * @return : int
	 */
	public int addDvsnCd(Map<String, Object> paramMap) {
		return sqlSessionTemplate.getMapper(MainMapper.class).addDvsnCd(paramMap);
	}
}

package com.ese.domain.function; 

import c.e.g.annotation.config.Matcher;

/**
 * @File Name : Dvsn.java
 * @Project Name : grimp
 * @package Name : com.ese.fucntion
 * @create Date : 2016. 2. 29.
 * @explain : Grider Annotation의 format이 function일 경우에 
 * 			    지정한 변수명과 동일하게 배열 형태의 변수를 선언하고 해당 내용을 설정한다.
 * 			  @matcher(동일한 INDEX에 존재하는 다른 컬럼의 값을 참조하고자 할때 사용)
 * 			  @message(국제화 메세지를 참조하고자 할떄 사용)  
 * 			  EX) 함수 호출 시 참조할 변수명을 넘기고자 할 경우 - @dvsnCd
 * 				    함수 호출 시 인자를 통해 값을 넘겨받을 경우 - \"@dvsnCd\"
 * 				    문자열 그대로 화면에 표출해야 할 경우 - @dvsnCd
 * 				  Function과 Editor는 중복적용 할 수 없음
 * @made by : "GOEDOKID"
 */
public class Dvsn {
	
	@Matcher(matche={"dvsnCd","dvsnNm"}, message={"com.ese.gricher"})
	private String[] ordr = {"<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>",
		"<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>","<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>",
		"<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>","<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>",
		"<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>","<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>",
		"<button type='button' name='delete' onclick='showOrdr(\"@dvsnCd\");'>@dvsnNm</button>"};
}
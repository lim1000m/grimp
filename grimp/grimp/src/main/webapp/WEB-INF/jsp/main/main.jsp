<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<!-- css block -->
<link rel="stylesheet" type="text/css" href="<c:url value='/css/arongi/AXGrid.css'/> "/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui/arongi/page.css' /> "/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui/arongi/AXJ.min.css'/> "/>
<script type="text/javascript" src="<c:url value='/js/jquery-2.1.4.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/custom/custom.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/axisj/AXJ.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/axisj/AXGrid.js' />"></script>

<script type="text/javascript">
var myGrid;

$(document).ready(function(){
	myGrid = new AXGrid();
	myGrid.setConfig({
		targetID: "ui-grid-ajax",
		colGroup: ${grimpHeader},
		page:{
			paging:true,
			pageSize:5
		},
		editor: {
			rows: [
					[
						{
							colSeq:0, align:"center", valign:"middle", 
							 formatter: function(){
                                 return this.item.no;
                             }
						},
						{
							colSeq:1, 
							align:"center", 
							valign:"middle",
							form:{
								type:"text", 
								value: function() {
									return this.value.dec();
								}, 
								validate:function(){
									return true;
								}
							}
						},
						{
							colSeq:2, 
							align:"center", 
							valign:"middle",
							form:{
								type:"text", 
								value: function() {
									return this.value.dec();
								}, 
								validate:function(){
									return true;
								}
							},
							AXBind:{
								type:"selector", 
									config:{
									appendable:true,
									ajaxUrl:"<c:url value='/getDvsnCd.do' />",
									ajaxPars:"",
									onChange:function(){
										
										if(this.selectedOption){
											myGrid.setEditorForm({
												key:"dvsnCd",
												position:[0,2], // editor rows 적용할 대상의 배열 포지션 (다르면 적용되지 않습니다.)
												value:this.selectedOption.optionText
											});
										}
									}
								}
							}
						},
						{
							colSeq:3, 
							align:"center", 
							valign:"middle", 
							form:{
								type:"text", 
								value:"itemValue"
							}, 
						},
						{colSeq:4, align:"center", valign:"middle", form:{type:"text", value:"itemValue"},/* AXBind:{type:"money"}*/},
						{colSeq:5, align:"center", valign:"middle", 
							form:{
	                            type:"radio",
	                            value:"itemValue",
	                            options:[
	                                {value:'Y', text:'Y'},
	                                {value:'N', text:'N'}
	                            ]
							}	/* AXBind:{type:"number", config:{min:0, max:100}}*/},
						{
							colSeq:6, 
							align:"center", 
							valign:"middle",
							formatter: function(){
								console.log(this.item.nowordr);
                                return this.item.nowordr;
                            }
						}
					]
				],
				/**
				* 버튼 들어가 있는 컬럼에 다른 컬럼 내용을 가져오도록 해야함 
				* 등록 할 경우 아직 검증하지 않았음
				**/
				request:{ajaxUrl:"<c:url value='saveGrid.do' />", ajaxPars:""},
				response: function(){ // ajax 응답에 대해 예외 처리 필요시 response 구현
					
					if(this.error){
						trace(this);
						return;
					} 
				
					console.log(this.index);
					console.log(this.res);
					if(this.index == null){ // 추가
						var pushItem = this.res.item;
						myGrid.pushList(pushItem, this.insertIndex);
					}else{ // 수정
						AXUtil.overwriteObject(this.list[this.index], this.res.item, true); // this.list[this.index] object 에 this.res.item 값 덮어쓰기
						myGrid.updateList(this.index, this.list[this.index]);
					}
					
					if(this.res.isDone) alert("<spring:message code='com.ese.complate' />");
				},
				onkeyup: function(event, element){
					if(event.keyCode == 13 && element.name != "title") 
						myGrid.saveEditor();
					else if(event.keyCode == 13 && element.name == "title") 
						myGrid.focusEditorForm("regDate");
				}
			},
			contextMenu: {
				theme:"AXContextMenu", // 선택항목
				width:"150", // 선택항목
				menu:[{
					userType:1, label:"추가", className:"plus", 
					onclick:function(){
						myGrid.appendList(null);
					}
				},
				{
					userType:1, 
					label:"삭제", 
					className:"minus", 
					onclick:function(){
						if(this.sendObj){
							if(!confirm("<spring:message code='com.ese.doDelete'/>")) 
								return;
							var removeList = [];
							var item = this.sendObj.item;
							removeList.push({no:item.no});
							if(deleteList(item.dvsnCd))
								myGrid.removeList(removeList); 
						}
					}
				},
				{
					userType:1, label:"수정", className:"edit", 
					onclick:function(){
						if(this.sendObj){
							myGrid.setEditor(this.sendObj.item, this.sendObj.index);
						}
					}
				}],
				filter:function(id){
					return true;
				}
			}
		});
			
	myGrid.setList({
		method:"GET",
		ajaxUrl:"<c:url value='/mrider.do' />", ajaxPars:"", 
		onLoad:function() {
		},
		onsucc:function() {
		}
	});
});


/**
 * 삭제 프로세스
 */
function deleteList(dvsnCd) {
	var result = "";
	
	$.ajax({
		url : "<c:url value='/deleteDvsn.do' />",
   		type : 'post',
   		async : false,
   		data: {
   			"dvsnCd" : dvsnCd
   		},
   		success : function (json) {
   			if(json){
   				alert("<spring:message code='com.ese.isDelete'/>");
   			}else{
   				alert("<spring:message code='com.ese.isFailed'/>");
   			}
   			result = json;
   		},
        error : function(error) {
				console.log(error);
        }
   	});
	return result;
}

/**
 * function에서 설정된 버튼에 연동된 function
 */
function showOrdr(data) {
	alert(data);
}

/**
 * 체크된 항목을 가져오고자 할때
 */
function removeCheck() {
	//모든 셀
    var checkedList = myGrid.getCheckedListWithIndex(0);// colSeq
    if (checkedList.length == 0) {
        alert("선택된 목록이 없습니다. 삭제하시려는 목록을 체크하세요");
        return;
    }
    console.log(checkedList);
}

/*
 * 리드로우
 */
function redrawGrid() {
	myGrid.redrawGrid();
}

function reloadGrid() {
	myGrid.setList({
	    ajaxUrl:"<c:url value='/grider.do' />",
	    ajaxPars:""
	 });
	myGrid.reloadList();
}

function selectedItem () {
	console.log(myGrid.getSelectedItem());
}
</script>
</head>
<body>	
	<div id="ui-grid-ajax" style="height:300px;"></div>
	<input type="button" value="삭제하기" class="AXButton Red" onclick="removeCheck();">
	<input type="button" value="checkedColSeq(0, true)" class="AXButton Blue" onclick="myGrid.checkedColSeq(0, true);">
	<input type="button" value="checkedColSeq(0, false)" class="AXButton Blue" onclick="myGrid.checkedColSeq(0, false);">
	<input type="button" value="리드로우" class="AXButton Blue" onclick="redrawGrid();">
	<input type="button" value="리로드" class="AXButton Blue" onclick="reloadGrid();">
	<input type="button" value="선택된 아이템" class="AXButton Blue" onclick="selectedItem();">
	
</body>
</html>
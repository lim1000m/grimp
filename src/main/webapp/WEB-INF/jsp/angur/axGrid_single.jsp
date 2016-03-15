<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html ng-app="gridApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome page</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/arongi/AXGrid.css'/> "/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui/arongi/page.css' /> "/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui/arongi/AXJ.min.css'/> "/>

<script type="text/javascript" src="<c:url value='/js/jquery-2.1.4.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/angular/angular.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/axisj/AXJ.all.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/angular/angular.AXGrid.directive.js' />"></script>

<script type="text/javascript">
var list = [
            {no:1, title:"AXGrid 첫번째 줄 입니다.", writer:"김기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:1709401, amount:10}, // item
            {no:2, title:"AXGrid 두번째 줄 입니다.", writer:"정기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:12300.00, amount:7},
            {no:3, title:"AXGrid 세번째 줄 입니다.", writer:"한기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:12000, amount:5},
            {no:4, title:"AXGrid 세번째 줄 입니다.", writer:"박기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:13000, amount:4},
            {no:5, title:"AXGrid 세번째 줄 입니다.", writer:"곽기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:3000, amount:3},
            {no:6, title:"AXGrid 세번째 줄 입니다.", writer:"문기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:123000, amount:2},
            {no:7, title:"AXGrid 세번째 줄 입니다.", writer:"소기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:129500, amount:1},
            {no:8, title:"AXGrid 첫번째 줄 입니다.", writer:"재기영", regDate:"2013-01-18", desc:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", price:123000, amount:10}
            ];
</script>

<script type="text/javascript">
/**
* Normal Javascript Codes
*/

var app = angular.module('gridApp', ['ax-grid-module']);
var gridOptions = {
targetId : 'gridContents',
colGroup : [
			{key:"no",    label:"번호", width:"50", align:"right"},
			{key:"btns",  label:"삭제", width:"40", align:"center", formatter: function(){
			    return '<button type="button" name="delete" onclick="alert(' + this.index + ');"><i class="axi axi-trash-o"></i></button>';
			}},
			{key:"title", label:"제목", width:"200",
			    tooltip:function() {
			        return this.item.no + "." + this.item.title + "/" + this.key + "/" + this.value;
			    },
			    colHeadTool: false,
			    sort: false
			},
			{key:"writer",  label:"작성자", width:"100"},
			{key:"regDate", label:"작성일", width:"100"},
			{key:"price",   label:"가격",   width:"100", align:"right", formatter:"money"},
			{key:"amount",  label:"수량",   width:"80",  align:"right", formatter:"money"},
			{key:"cost",    label:"금액",   width:"100", align:"right",
			    formatter:function(){
			        return (this.item.price.number() * this.item.amount.number()).money();
			    }
			},
			{key:"desc", label:"비고", width:"200", formatter: function(){
			    return this.index;

			}}
        ],
colHeadAlign : 'center',
sort : true,
colHeadTool : true,
fitToWidth : true,
page:{
	paging:true,
	pageSize:5
}
}



app.controller('gridController', ['$scope', function($scope, $complie){
/*Type 1*/
//angular.extend($scope, {gridOptions:[gridOptions, rr4, yy77});
/*Type 1 End*/
}]);

$(document).ready(function(){

	/*Type 2*/
	bodyScope = $("body").scope();
	var grid = bodyScope.getAxGrid();
	grid.setConfig(gridOptions);
	/*Type 2 End*/

	$("#btnAction").on("click", function(){
		bodyScope = $("body").scope();
		var gird1 = bodyScope.getAxGrid(0);
		gird1.setConfig(newGridOptions);
		gird1.setList({
			method:"GET",
			ajaxUrl:"<c:url value='/grider.do' />", ajaxPars:"", 
			onLoad:function() {
			},
			onsucc:function() {
			}
		});	
	});
});

var newGridOptions = {
	colGroup : ${grimpHeader},
	page:{
		paging:true,
		pageSize:5
	},
	fitToWidth : true,
}

</script>

</head>
<body ng-controller="gridController" style="font-size:12pt">

<button id="btnAction" value="Action"> Action </button>
<br/>
<div ax-grid="" id="gridContents" style="width:100%; height:300px;"></div>
</body>
</html>

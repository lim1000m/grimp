<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html ng-app="gridApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome page</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/arongi/AXGrid.css'/> "/>

<script type="text/javascript" src="<c:url value='/js/jquery-2.1.4.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/angular/angular.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/axisj/AXJ.all.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/angular/angular.AXGrid.directive.js' />"></script>

<script type="text/javascript">
</script>

<script type="text/javascript">
/**
 * Normal Javascript Codes
 */

var app = angular.module('gridApp', ['ax-grid-module']);
app.controller('gridController', ['$scope', function($scope){
	angular.extend($scope, {gridOptions:[]});
}]);

var list = [
            {col1:1, col2:"AXGrid 첫번째 줄 입니다.", col3:"김기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:1709401, col7:10}, // item
            {col1:2, col2:"AXGrid 두번째 줄 입니다.", col3:"정기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:12300.00, col7:7},
            {col1:3, col2:"AXGrid 세번째 줄 입니다.", col3:"한기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:12000, col7:5},
            {col1:4, col2:"AXGrid 세번째 줄 입니다.", col3:"박기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:13000, col7:4},
            {col1:5, col2:"AXGrid 세번째 줄 입니다.", col3:"곽기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:3000, col7:3},
            {col1:6, col2:"AXGrid 세번째 줄 입니다.", col3:"문기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:123000, col7:2},
            {col1:7, col2:"AXGrid 세번째 줄 입니다.", col3:"소기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:129500, col7:1},
            {col1:8, col2:"AXGrid 첫번째 줄 입니다.", col3:"재기영", col4:"2013-01-18", col5:"myGrid.setList 의 첫번째 사용법 list json 직접 지정 법", col6:123000, col7:10}
            ];

$(document).ready(function(){
	bodyScope = $("body").scope();
	var grid = bodyScope.getAxGrid();
	grid.setList(list);
});

</script>

</head>
<body ng-controller="gridController">

<button id="btnAction" value="Action"> Action </button>
<br/>
<div ax-grid="0" id="gridContents" style="width:100%; height:170px;"></div>
<br/>
<div style="height:450px;">
<div ax-grid="1" id="gridContents2" style="width:100%; height:40%;"></div>
<div ax-grid="2" id="gridContents3" style="width:100%; height:40%;"></div>
</div>
</body>
</html>

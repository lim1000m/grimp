<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name='author' content='ese, 이에스이' />
<title>DIYDOCS</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script>
function loginForward() {
	window.location="<c:url value='/login.do'/>";
}
</script>
</head>
<body onload="loginForward()">
</body>
</html>

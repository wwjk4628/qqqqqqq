<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Success</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
</head>
<body>
  <div class="joinsuccess-box">
	<h1>Join Success</h1>
	<p>가입 승인 대기 중</p>
	<a href="<c:url value="/main"/>">메인으로 돌아가기</a>
  </div>
</body>
</html>

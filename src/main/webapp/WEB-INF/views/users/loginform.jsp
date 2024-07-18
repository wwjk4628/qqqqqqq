<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Home: Login</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
</head>
<body>
    <div class="container">
        <h2>로그인</h2>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                <c:choose>
                    <c:when test="${param.error == 'unauthorized'}">로그인이 필요합니다.</c:when>
                    <c:otherwise>아이디 또는 비밀번호가 잘못되었습니다.</c:otherwise>
                </c:choose>
            </div>
        </c:if>
        <form id="login-form" 
              name="loginform" 
              method="POST" 
              action="<c:url value='/user/login'/>">
            
            <label class="block-label" for="username">아이디</label> 
            <input id="username" name="username" type="text" value=""> 

            <label class="block-label" for="password">패스워드</label> 
            <input id="password" name="password" type="password" value="">

            <sec:csrfInput/>

            <input type="submit" value="로그인">
        </form>
    </div>
</body>
</html>

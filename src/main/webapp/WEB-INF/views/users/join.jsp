<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="<c:url value="/javascript/users.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
</head>
<body>
    <form id="joinForm" action="<c:url value="/user/join"/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <label for="name">사용자 아이디:</label>
        <input type="text" id="name" name="name" required><br>
        <input type="button" id="checkName"
               data-target="<c:url value="/user/checkName" />"
               value="이름 중복 체크" /><br>
        <input type="hidden" name="checkedName" value="n" /><br>
        <label for="email">이메일:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password" required><br><br>
        <label for="branchId">지점 코드:</label>
        <select id="branchId" name="branchId" required>
            <c:forEach var="branch" items="${branches}">
                <option value="${branch.branchId}">${branch.branchName}</option>
            </c:forEach>
        </select><br><br>
        <button type="submit">회원가입</button>
    </form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
    <script>
        window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            if (params.get('status') === 'success') {
                alert('임시 비밀번호가 이메일로 전송되었습니다.');
                window.location.href = '<c:url value="/user/login"/>';
            }
        };
    </script>
</head>
<body>
    <div class="container">
        <h2>비밀번호 재설정</h2>
        <c:if test="${not empty message}">
            <script>alert('${message}');</script>
        </c:if>
        <form action="<c:url value='/user/forgotPassword'/>" method="post">
            <sec:csrfInput/>
            <label for="username">사용자 아이디:</label>
            <input type="text" id="username" name="username" required><br><br>
            <button type="submit">비밀번호 재설정</button>
        </form>
    </div>
</body>
</html>

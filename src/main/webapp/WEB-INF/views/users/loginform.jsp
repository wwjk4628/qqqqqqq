<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Home: Login</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
    <script src="<c:url value="/javascript/users.js"/>"></script>
    <style>
        .container {
            position: relative;
            width: 300px; /* Adjust the width as needed */
            margin: 0 auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background: white;
        }
        .container .back-button {
            position: absolute;
            top: 10px;
            left: 10px;
            background-color: transparent;
            border: none;
            cursor: pointer;
            font-size: 16px;
            color: #4CAF50; /* Adjust color as needed */
        }
        .container .back-button svg {
            width: 20px;
            height: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <button class="back-button" onclick="history.back()">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
            </svg>
        </button>
        <h2>로그인</h2>
        <form id="login-form" 
              name="loginform" 
              method="POST" 
              action="<c:url value='/user/login'/>"
              onsubmit="return validateLoginForm(event)">
            
            <label class="block-label" for="username">아이디</label> 
            <input id="username" name="username" type="text" value=""> 

            <label class="block-label" for="password">패스워드</label> 
            <input id="password" name="password" type="password" value="">

            <sec:csrfInput/>

            <input type="submit" value="로그인">
        </form>
        <a href="<c:url value='/user/forgotPassword'/>">비밀번호 찾기</a> <!-- 비밀번호 찾기 링크 추가 -->
    </div>

</body>
</html>

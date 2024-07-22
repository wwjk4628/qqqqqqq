<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이 페이지</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
    <c:choose>
        <c:when test="${user.authCode == '2'}">
            <link rel="stylesheet" type="text/css" href="<c:url value='/css/admins.css'/>">
        </c:when>
        <c:when test="${user.authCode == '1'}">
            <link rel="stylesheet" type="text/css" href="<c:url value='/css/branches.css'/>">
        </c:when>
        <c:otherwise>
            <p>권한이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</head>

<body>
    <c:choose>
        <c:when test="${user.authCode == '2'}">
            <%@ include file="/WEB-INF/views/admin_includes/navigation.jsp"%>
        </c:when>
        <c:when test="${user.authCode == '1'}">
            <%@ include file="/WEB-INF/views/branch_includes/navigation.jsp"%>
        </c:when>
        <c:otherwise>
            <p>권한이 없습니다.</p>
        </c:otherwise>
    </c:choose>
    
    <div class="container">
        <div class="content">
            <h2>마이 페이지</h2>
            <p><strong>사용자 아이디:</strong> ${user.name}</p>
            <p><strong>이메일:</strong> ${user.email}</p>
            <p><strong>지점 코드:</strong> ${user.branchId}</p>
            <p><strong>지점 이름:</strong> ${user.branchName}</p>
            <p><strong>권한:</strong> 
                <c:choose>
                    <c:when test="${user.authCode == '2'}">관리자</c:when>
                    <c:when test="${user.authCode == '1'}">사용자</c:when>
                    <c:otherwise>게스트</c:otherwise>
                </c:choose>
            </p>
            <a href="<c:url value='/user/changePassword'/>" class="btn btn-custom btn-login">비밀번호 변경</a>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/branch_includes/footer.jsp"%>
</body>
</html>

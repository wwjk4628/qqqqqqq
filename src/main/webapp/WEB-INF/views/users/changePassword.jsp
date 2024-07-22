<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
    <c:choose>
        <c:when test="${authUser.authCode == '2'}">
            <link rel="stylesheet" type="text/css" href="<c:url value='/css/admins.css'/>">
        </c:when>
        <c:when test="${authUser.authCode == '1'}">
            <link rel="stylesheet" type="text/css" href="<c:url value='/css/branches.css'/>">
        </c:when>
        <c:otherwise>
            <p>권한이 없습니다.</p>
        </c:otherwise>
    </c:choose>
    
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/users.css'/>">
    <script>
        window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            if (params.get('status') === 'success') {
                alert('비밀번호 변경 성공');
                window.location.href = '<c:url value="/user/mypage"/>';
            } else if (params.get('status') === 'failure') {
                alert('비밀번호 변경 실패');
            }
        };
    </script>
</head>

<body>

    <c:choose>
        <c:when test="${authUser.authCode == '2'}">
            <%@ include file="/WEB-INF/views/admin_includes/navigation.jsp"%>
        </c:when>
        <c:when test="${authUser.authCode == '1'}">
            <%@ include file="/WEB-INF/views/branch_includes/navigation.jsp"%>
        </c:when>
        <c:otherwise>
            <p>권한이 없습니다.</p>
        </c:otherwise>
    </c:choose>

        <div class="content">
            <h2>비밀번호 변경</h2>
            <form action="<c:url value='/user/changePassword'/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <label for="currentPassword">현재 비밀번호:</label>
                <input type="password" id="currentPassword" name="currentPassword" required><br>
                <label for="newPassword">새 비밀번호:</label>
                <input type="password" id="newPassword" name="newPassword" required><br>
                <button type="submit">비밀번호 변경</button>
            </form>
        </div>
    <%@ include file="/WEB-INF/views/branch_includes/footer.jsp"%>
</body>
</html>

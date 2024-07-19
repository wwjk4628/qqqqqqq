<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand"
            href="<c:url value='/branch/home' />">지점 관리 시스템</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNav" aria-controls="navbarNav"
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link"
                    href="<c:url value='/branch/inventory' />">교재 재고</a></li>
                <li class="nav-item"><a class="nav-link"
                    href="<c:url value='/branch/order/form' />">발주</a></li>
                <li class="nav-item"><a class="nav-link"
                    href="<c:url value='/branch/stockin/list' />">입고</a></li>
                <li class="nav-item"><a class="nav-link"
                    href="<c:url value='/branch/stockout/list' />">출고</a></li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <c:if test="${sessionScope.authUser.authCode == '2'}">
                    <li class="nav-item">
                        <a class="nav-link btn btn-secondary" href="<c:url value='/admin/home' />">관리자 페이지</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <span class="navbar-text text-white">
                        <c:out value="${sessionScope.username}"/> 님
                    </span>
                </li>
                <li class="nav-item"><a class="nav-link"
                    href="<c:url value='/logout' />">로그아웃</a></li>
            </ul>
        </div>
    </nav>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .nav-link.active {
            font-weight: bold;
            color: white !important;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="<c:url value='/admin' />">본사 관리 시스템</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav" id="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/home' />">본사 홈</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/ordercheck' />">발주 승인</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/book/list' />">교재 관리</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/usermanage/list' />">회원 승인</a></li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <span class="navbar-text text-white">
                        <a href="<c:url value='/user/mypage'/>" class="text-white"><c:out value="${sessionScope.authUser.name}"/> 님</a>
                    </span>
                </li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/logout' />">로그아웃</a></li>
            </ul>
        </div>
    </nav>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            var currentPath = window.location.pathname;
            $('#navbar-nav .nav-link').each(function() {
                var href = $(this).attr('href');
                if (currentPath.indexOf(href) !== -1) {
                    $(this).addClass('active');
                }
            });
        });
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>지점 관리 시스템</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        nav {
            background-color: #333;
            padding: 10px;
        }

        nav ul {
            list-style-type: none;
            padding: 0;
        }

        nav ul li {
            display: inline;
            margin-right: 20px;
        }

        nav ul li a {
            color: white;
            text-decoration: none;
        }

        .content {
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th,
        td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .form-group {
            margin-bottom: 15px;
        }

        button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>

<body>
    <%@ include file="/WEB-INF/views/branch_includes/navigation.jsp" %>
    <div class="content">
        <h1>입고</h1>
        <h3><a href="/branches/branch_stock_in_detail.html">입고 기록</a></h3>
        <table border="1">
				<tr>
					<th>in_id</th>
					<th>order_id</th>
					<th>date</th>
					<th>check</th>
					<th>상세보기</th>
				</tr>
					
				<c:forEach items="${list }" var="vo">
					<tr>
						<td>${vo.id}</td>
						<td>${vo.orderId }</td>
						<td>${vo.flucDate}</td>
						<td>
						${vo.checkedIn }, 
						<c:choose>
                			<c:when test="${vo.checkedIn eq 0}">미확인</c:when>
                			<c:when test="${vo.checkedIn eq 1}">처리 완료</c:when>
                			<c:otherwise>알 수 없음</c:otherwise>
           				</c:choose>
           				</td>
						
						<td><a href = "<c:url value="/stock/in/${vo.id }/detail"/>">보러 가기</a></td>
					</tr>
				</c:forEach>
			</table>
        
    </div>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>본사 관리 시스템</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/admins.css'/>">
</head>
<body>
	<%@ include file="/WEB-INF/views/admin_includes/navigation.jsp"%>
	<div class="content">
		<h1>${id }번 order detail</h1>

		<h3 class="parent" style="text-align: left;">
			<a href="<c:url value='/admin/ordercheck/list'/>">발주 리스트 돌아가기</a>
		</h3>

		<table>
			<tr>
				<th>order_id</th>
				<th>branch_name</th>
				<th>order_date</th>
				<th>book_name</th>
				<th>quantity</th>
				<th>order_check</th>
			</tr>
			<c:forEach items="${list}" var="vo">
				<tr>
					<td>${vo.orderId}</td>
					<td><a
						href="<c:url value='/admin/ordercheck/list/${vo.branchId}'/>">${vo.branchName}</a></td>
					<td>${vo.orderDate}</td>
					<td>${vo.bookName}</td>
					<td>${vo.quantity}</td>
					<td><c:choose>
							<c:when test="${vo.checked eq 0}">미확인</c:when>
							<c:when test="${vo.checked eq 1}">반려</c:when>
							<c:when test="${vo.checked eq 2}">처리 완료</c:when>
							<c:otherwise>알 수 없음</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
		<c:choose>
			<c:when test="${checked eq 0}">
				<p><a href="<c:url value='/admin/ordercheck/refuse/${id}'/>" class="delete">반려</a></p>
				<p><a href="<c:url value='/admin/ordercheck/confirm/${id}'/>" class="update">승인</a>	</p>
			</c:when>
		</c:choose>
		
	</div>
	<%@ include file="/WEB-INF/views/admin_includes/footer.jsp"%>
</body>
</html>

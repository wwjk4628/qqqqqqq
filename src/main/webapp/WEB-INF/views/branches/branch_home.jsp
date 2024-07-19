<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>지점 관리 시스템</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/branches.css'/>">
<script src="<c:url value='/javascript/bookinven.js'/>"></script>

</head>
<body>
	<%@ include file="/WEB-INF/views/branch_includes/navigation.jsp"%>
	
	<div class="content">
		<h1>branch id: ${authUser.branchId }의 교재 재고 현황</h1>
		<h3>목록 (검색어: ${param.keyword })</h3>
		
		<form id="search-form">
			<label for="keyword">검색어: </label><input type="text" name="keyword" value="${param.keyword == null ? '' : param.keyword.trim()}">
			<input type="checkbox" 	name="check" id="check" value="check" ${param.check == 'check' ? 'checked' : ''} />
			<label for="check">재고 있는 책만 보기</label>
			<input type="hidden" id="orderBy" name="orderBy" value="${param.orderBy}">
			<input type="submit" value="검색">
			<button type="button" id="resetOrderBy">정렬 초기화</button>
		</form>
		<br />
		<table>
			<tr>
				<th>번호</th>
				<th onclick="updateOrderBy('kindcode')">분류</th>
				<th>book_name</th>
				<th onclick="updateOrderBy('price')">price</th>
				<th onclick="updateOrderBy('inventory')">inventory</th>
				<th>재고*가격</th>
				<th onclick="updateOrderBy('inDate')">최근 입고일</th>
        		<th onclick="updateOrderBy('outDate')">최근 출고일</th>
			</tr>
			<c:forEach items="${list }" var="vo" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td><c:choose>
						<c:when test="${vo.kindCode eq 1}">초등</c:when>
						<c:when test="${vo.kindCode eq 2}">중등</c:when>
						<c:when test="${vo.kindCode eq 3}">고등</c:when>
						<c:when test="${vo.kindCode eq 4}">수능</c:when>
						<c:otherwise>몰라</c:otherwise>
					</c:choose></td>
					<td>${vo.bookName}</td>
					<td><fmt:formatNumber value="${vo.price}" pattern="#,###"/></td>
            		<td>${vo.inventory}</td>
            		<td><fmt:formatNumber value="${vo.inventory * vo.price}" pattern="#,###"/></td>
            		<td>${vo.inDate }</td>
            		<td>${vo.outDate }</td>
				</tr>
				
			</c:forEach>
				
		</table>
		<p>
			<a href="<c:url value='/branch/order/list'/>">오더 리스트 보기</a>
		</p>
	</div>
</body>
</html>

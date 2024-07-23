<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>지점 관리 시스템</title>
<!-- CSRF 토큰을 메타 태그로 설정 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- jQuery 라이브러리 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- CSS 파일 추가 -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/branches.css'/>">

</head>
<body>
	<%@ include file="/WEB-INF/views/branch_includes/navigation.jsp"%>
	<div class="content">
	<div id="head"></div>
	<h3 class="parent">
		<a href="<c:url value="/branch/order/list" />">발주 리스트</a>
	</h3>

	<!-- 장바구니 섹션 -->
	<div id="cart" class="cart">
		<h2>Cart</h2>
		<input type="text" id="searchInput" placeholder="Search books...">
		<select id="ordering">
			<option value="">선택</option>
			<option value="asc">오름차순</option>
			<option value="desc">내림차순</option>
		</select> <select id="key">
			<option value="">선택</option>
			<option value="kindCode">과목코드</option>
			<option value="book_name">교재명</option>
			<option value="inventory">재고</option>
			<option value="price">가격</option>
		</select>
		<button id="searchBtn">검색</button>
		<button id="resetBtn">초기화</button>
		<!-- 장바구니 아이템이 여기에 동적으로 추가됨 -->
		<div id="cartItems" class="cart-content"></div>
		<button id="clearCartBtn" class="deleteAll">비우기</button>
		<!-- 발주 제출 폼 -->
		<form action="<c:url value="/branch/order/submit"/>" method="post">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button type="submit" id="orderBtn" class="update">발주</button>
		</form>
		<button id="saveBtn" class="add">장바구니 추가</button>
	</div>

	<!-- 검색 입력 및 버튼 -->

	<!-- 검색 결과가 여기에 렌더링됨 -->
	<div id="result"></div>

	<!-- Spring Security CSRF Token -->
	<form:form method="post" action="#">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
	</div>

	<script src="<c:url value='/javascript/bookorder.js'/>"></script>
</body>
</html>
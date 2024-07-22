<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>소비 페이지</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/branches.css'/>">
<script src="<c:url value= "/javascript/initialsetting.js"/>"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/branch_includes/navigation.jsp"%>

	<h1>재고 추가 페이지</h1>

	<form id="search-form">
		<label for="keyword">검색어: </label><input type="text" name="keyword" value="${param.keyword == null ? '' : param.keyword.trim()}">
		<input type="hidden" id="orderBy" name="orderBy" value="${param.orderBy}">
		<input type="submit" value="검색">
		<button type="button" onclick="resetKeyword()" class="add">초기화</button>
		<button type="button" onclick="showConfirmationModal()" class="update">확정</button>
		<div id="gije"></div>
	</form>
	
    <table id="table">    
    </table>

    <form id="orderForm" action="<c:url value='/branch/stockout/confirm'/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="button" onclick="showConfirmationModal()" class="update">확정</button>
    </form>

			
	<div id="confirmationModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>소비 처리 확인</h2>
            <div id="modal-body">
            </div>
            <button type="button" onclick="submitOrderForm()" class="add">확인</button>
            <button type="button" onclick="closeModal()" class="delete">취소</button>
        </div>
    </div>
</body>
</html>

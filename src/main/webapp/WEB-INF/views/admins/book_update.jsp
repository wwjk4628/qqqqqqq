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

<script>
	// 서버에서 전달받은 error 메시지를 alert 창에 출력하는 함수
	function displayAlert() {
		var error = "${error}"; // JSP에서 전달받은 error 메시지
		if (error && error.trim() !== "") {
			alert(error + "다시 처리해주세요"); // alert 창에 error 메시지 출력
		}
	}

	document.addEventListener("DOMContentLoaded", function() {
		// 여기에 코드를 넣으세요
		var addList = "${addList}";
		if (addList === "true") {
			alert("교재 리스트에 추가되었습니다.");
		}
	});

	// 페이지 로드 시 alert 창 표시
	window.onload = function() {
		displayAlert();
	};
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/admin_includes/navigation.jsp"%>


	<div class="content">
		<h1>교재 리스트 관리</h1>
		<form id="addToBookList" action="<c:url value='/admin/book/insert'/>"
			method="POST" onsubmit="return validatePriceInput();">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<table>
				<tr>
					<th>교재 ID</th>
					<th>교재명</th>
					<th>가격</th>
					<th>과목 코드</th>
					<th>추가</th>
				</tr>
				<tr>
					<td><input type="text" name="bookCode"></td>
					<td><input type="text" name="bookName"></td>
					<td><input type="number" name="price" id="priceInput" oninput="handleQuantityInput(this)"></td>
					<td><input type="number" name="kindCode" id="kindInput" oninput="handleQuantityInput(this)"></td>
					<td><button type="button" onclick="addToBookList()"
							class="add">추가</button></td>
				</tr>
			</table>
		</form>
		<form action="<c:url value='/admin/book/search'/>" method="GET">
			<table>
				<tr>
					<th>교재명</th>
					<td><input type="text" name="bookName"></td>
					<td><input type="submit" value="검색"></td>
				</tr>
			</table>
		</form>
		<table>
			<tr>
				<th>교재 ID</th>
				<th>교재명</th>
				<th>가격</th>
				<th>과목 코드</th>
				<th>작업</th>
			</tr>
			<c:forEach items="${list}" var="vo" varStatus="status">
				<tr>
					<td>[${vo.bookCode}]</td>
					<td>${vo.bookName}</td>
					<td>${vo.price}</td>
					<td>${vo.kindCode}</td>
					<td><a
						href="<c:url value='/admin/book/update/${vo.bookCode}'/>"
						class="update">수정</a> &nbsp; <a
						href="<c:url value='/admin/book/delete/${vo.bookCode}'/>"
						class="delete" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<%@ include file="/WEB-INF/views/admin_includes/footer.jsp"%>
	<script>
		function addToBookList() {
			var bookCode = document.getElementsByName("bookCode")[0].value;
			var bookName = document.getElementsByName("bookName")[0].value;
			var price = document.getElementById("priceInput").value;
			var kindCode = document.getElementById("kindInput").value;

			// 교재 코드 확인
			if (bookCode.trim() === "") {
				alert("교재 코드를 입력해 주세요.");
				return;
			}

			// 교재명 확인
			if (bookName.trim() === "") {
				alert("교재명을 입력해 주세요.");
				return;
			}

			// 가격 확인
			if (price.trim() === "" || isNaN(price) || parseFloat(price) <= 0) {
				alert("올바른 가격을 입력해주세요.");
				return;
			}

			// 과목 코드 확인
			if (kindCode.trim() === "" || isNaN(kindCode)
					|| parseFloat(kindCode) <= 0) {
				alert("과목 코드를 입력해 주세요.");
				return;
			}

			if (!isNumber(kindCode)) {
				alert("과목 코드는 숫자만 입력해주세요.");
				return;
			}

			// 가격이 숫자인지 검사
			if (!isNumber(price)) {
				alert("가격은 숫자만 입력해주세요.");
				return;
			}

			// 폼 제출
			var form = document.getElementById("addToBookList");
			form.submit();
		}

		// 숫자인지 확인하는 함수
		function isNumber(value) {
			return /^\d+$/.test(value);
		}
		
		
		function handleQuantityInput(input) {
			// 입력된 값을 정수로 변환합니다.
			let value = parseInt(input.value, 10);

			// 최소값(min)과 최대값(max) 사이의 값으로 제한합니다.
			if (isNaN(value)) {
				value = 0; // 숫자가 아니거나 값이 없으면 기본값으로 1을 설정합니다.
			} else {
				value = Math.min(Math.max(value, 0), 9999999);
			}

			// 제한된 값을 입력 필드에 반영합니다.
			input.value = value;
		}
	</script>

</body>
</html>

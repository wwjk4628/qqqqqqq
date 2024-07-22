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
		<form action="<c:url value='/admin/book/modify'/>" method="POST">

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="bookCode"
				value="${vo.bookCode }" />
			<table>
				<tr>
					<th>교재명</th>
					<th>가격</th>
					<th>과목 코드</th>
					<th>작업</th>
				</tr>
				<tr>
					<th><input type="text" name="bookName" value="${vo.bookName }"></th>
					<th><input type="number" name="price" value="${vo.price }"
						oninput="handleQuantityInput(this)"></th>
					<th><input type="number" name="kindCode"
						value="${vo.kindCode }" oninput="handleQuantityInput(this)"></th>
					<th><input type="submit" value="수정"></th>
				</tr>
			</table>
		</form>
	</div>

	<%@ include file="/WEB-INF/views/admin_includes/footer.jsp"%>

	<script type="text/javascript">
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

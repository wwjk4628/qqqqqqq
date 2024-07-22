<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류 페이지</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 800px;
	margin: 50px auto;
	background-color: #fff;
	padding: 20px;
	border: 1px solid #ccc;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
	color: #d9534f;
}

.error-message {
	color: #d9534f;
	font-size: 18px;
}

.stack-trace {
	background-color: #f7f7f7;
	padding: 10px;
	border: 1px solid #ccc;
}

pre {
	white-space: pre-wrap;
	word-wrap: break-word;
	font-size: 14px;
	color: #333;
}

.back-link {
	margin-top: 20px;
}

.back-link a {
	color: #337ab7;
	text-decoration: none;
}

.back-link a:hover {
	text-decoration: underline;
}
</style>
<script>
	// 이전 페이지로 이동하는 함수
	function goBack() {
		window.history.back();
	}
</script>
</head>
<body>
	<div class="container">
		<h1>오류 발생</h1>
		<p class="error-message">${errorMessage}</p>


		<%-- 예외 객체의 스택 트레이스 출력 --%>
		<c:if test="${not empty exception}">
			<div class='stack-trace'>
				<pre>
            <c:forEach var="stackTraceElement"
						items="${exception.stackTrace}">
                <c:out
							value="${stackTraceElement.className}.${stackTraceElement.methodName}(${stackTraceElement.fileName}:${stackTraceElement.lineNumber})" />
                <br />
            </c:forEach>
        </pre>
			</div>
		</c:if>


		<div class="back-link">
			<a href="javascript:void(0);" onclick="goBack();">이전 페이지로 돌아가기</a>
		</div>
	</div>
</body>
</html>

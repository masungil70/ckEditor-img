<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${message != ''}">
		<h2>${message}</h2>
	</c:if>	

	<div>
		<form method="POST" enctype="multipart/form-data" action="/">
			<table>
				<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
				<tr><td></td><td><input type="submit" value="Upload" /></td></tr>
			</table>
		</form>
		
		<a href="/ckeditorForm">ckEditor 예제</a>
	</div>

	<div>
		<ul>
			<c:forEach var="file" items="${files}">
			<li>
				<a href="/files/${file}">${file}</a>
			</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
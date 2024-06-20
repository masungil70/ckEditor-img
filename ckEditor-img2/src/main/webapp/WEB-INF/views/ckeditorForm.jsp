<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

<style>
.ck.ck-editor {
	width: 80%;
	max-width: 800px;
	margin: 0 auto;
}

.ck-editor__editable {
	height: 80vh;
}
</style>
</head>

<body>

	<div>
		<form id="mForm" method="post">
			제목 : <input name="title" id="title"/><br/>
			내용 : <textarea id="editor" name="editor"></textarea><br/>
			<input type="submit" value="Upload" />
		</form>
	</div>

<script>		
	let editor;
	ClassicEditor
	.create(document.querySelector('#editor'),{
		ckfinder: {
			uploadUrl : '/imageUpload?token=${token}'
		}
	})
	.then(editor => {
		console.log('Editor was initialized');
		window.editor = editor;
	})
	.catch(error => {
		console.error(error);
	});
	
	
	document.querySelector('#mForm').addEventListener("submit", e => {
		e.preventDefault();
		
		fetch("/ckeditorWrite", {
			method: "POST",
			headers: {
			    "Content-Type": "application/json",
			},
			body: JSON.stringify({
				token: '${token}',
				title: document.querySelector("#title").value,
				editor: document.querySelector("#editor").value,
			}),
		})
		.then(response => response.json())
		.then(result => console.log(result));
		
		return false;
	});
	
 </script>
 
</body>
</html>
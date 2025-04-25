<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Photo</title>
</head>
<body>
	<h1></h1>
	<form action="${pageContext.request.contextPath}/uploadPhotos"
	method="post" enctype="multipart/form-data">
	<input type="file" name="image" accept="image/*" required/> <br>
	<br> <input type="submit" value="Upload Photo"/>
	
	</form>
	<h2>Uploaded Photo:</h2>
	<img src="${path}" alt="Uploaded Image" />

</body>
</html>
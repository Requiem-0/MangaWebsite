<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload PDF</title>
</head>
<body>
<h2>Upload PDF</h2>
<form action="<%= request.getContextPath() %>/upload" method="post" enctype="multipart/form-data">
    Title: <input type="text" name="title" required><br><br>
    Select PDF: <input type="file" name="pdf" accept="application/pdf" required><br><br>
    <input type="submit" value="Upload">
        
</form>

<form method="post" action="delete-pdf" onsubmit="return confirm('Are you sure you want to delete this PDF?');">
    <input type="hidden" name="fileName" value="${pdf.fileName}">
    <button type="submit">Delete</button>
</form>


</body>
</html>

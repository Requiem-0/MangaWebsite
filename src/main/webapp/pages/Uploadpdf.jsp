<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload PDF</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #333;
        }
        form {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
        }
        input[type="file"] {
            margin: 10px 0;
        }
        input[type="submit"] {
            padding: 8px 16px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .message {
            margin-top: 20px;
            color: #006400;
        }
    </style>
</head>
<body>
    <h2>Upload PDF File</h2>
    <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
        <label>Select PDF file:</label><br>
        <input type="file" name="pdfFile" accept="application/pdf" required><br><br>
        <input type="submit" value="Upload">
    </form>
</body>
</html>

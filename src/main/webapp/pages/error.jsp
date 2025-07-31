<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2>Error</h2>
    <p style="color:red;">
        <%= request.getAttribute("error") %>
    </p>
    <a href="/MangaWebsite/index.jsp">← Back to Home</a>
</body>
</html>

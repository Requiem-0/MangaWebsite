<%@ page import="java.util.List" %>
<%@ page import="com.manga.controllers.dao.UploadPDFDAO" %>
<%@ page import="com.manga.models.UploadPDF" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Read PDFs</title></head>
<body>
    <h2>Available PDFs</h2>

    <%
        UploadPDFDAO dao = new UploadPDFDAO();
        List<UploadPDF> list = dao.getAllPDFs();
        for (UploadPDF pdf : list) {
    %>
        <p>
            <b><%= pdf.getTitle() %></b><br>
            <iframe src="view-pdf?id=<%= pdf.getId() %>" width="80%" height="400px"></iframe>
        </p>
    <%
        }
    %>
</body>
</html>

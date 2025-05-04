<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.manga.models.UploadPDF" %>
<%@ page import="com.manga.controllers.dao.UploadPDFDAO" %>

<!DOCTYPE html>
<html>
<head>
    <title>View PDFs</title>
</head>
<body>
<h2>Uploaded PDFs</h2>
<%
    UploadPDFDAO dao = new UploadPDFDAO();
    List<UploadPDF> pdfList = dao.getAllPDFs();

    if (pdfList != null && !pdfList.isEmpty()) {
%>
    <ul>
        <% for (UploadPDF pdf : pdfList) { %>
            <li>
                <strong><%= pdf.getTitle() %></strong> -
                <a href="<%= request.getContextPath() + "/" + pdf.getFilePath() %>" target="_blank">View PDF</a>
            </li>
        <% } %>
    </ul>
<% } else { %>
    <p>No PDFs found.</p>
<% } %>
</body>
</html>

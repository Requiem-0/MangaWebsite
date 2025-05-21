<%@ page import="java.sql.*, com.manga.database.DatabaseConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Read Chapter</title>
</head>
<body>
<%
    String mangaIdParam = request.getParameter("manga_id");
    String volumeParam = request.getParameter("volume");
    String chapterParam = request.getParameter("chapter");

    int mangaId = 1, volumeNum = 1, chapterNum = 1;

    try {
        mangaId = Integer.parseInt(mangaIdParam);
        volumeNum = Integer.parseInt(volumeParam);
        chapterNum = Integer.parseInt(chapterParam);
    } catch (Exception e) {
        out.println("<p style='color:red'>Invalid parameters.</p>");
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DatabaseConnection.getConnection();
        String query = "SELECT chapter_pdf FROM chapter WHERE volume_id = (SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?) AND chapterno = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, mangaId);
        pstmt.setInt(2, volumeNum);
        pstmt.setInt(3, chapterNum);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            String pdfPath = rs.getString("chapter_pdf");
%>
    <div style="text-align:center; padding: 20px;">
        <iframe 
            src="../<%= pdfPath %>#toolbar=1&navpanes=0&scrollbar=1" 
            width="100%" 
            height="1000px" 
            style="border: none;"
        >
            This browser does not support PDFs. <a href="../<%= pdfPath %>">Download PDF</a>
        </iframe>
    </div>
<%
        } else {
%>
    <p style="color:red; text-align:center;">PDF not found for selected chapter.</p>
<%
        }
    } catch (Exception e) {
%>
    <p style="color:red; text-align:center;">Error loading PDF: <%= e.getMessage() %></p>
<%
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) {}
        if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>
</body>
</html>

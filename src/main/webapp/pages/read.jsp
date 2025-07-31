<%@ page import="java.sql.*, com.manga.database.DatabaseConnection, java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Read Chapter</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        iframe {
            width: 100%;
            height: 1000px;
            border: none;
        }
        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

<%
    String mangaIdParam = request.getParameter("manga_id");
    String volumeParam = request.getParameter("volume");
    String chapterParam = request.getParameter("chapter");

    int mangaId = 0, volumeNum = 0, chapterNum = 0;
    boolean validParams = true;

    if (mangaIdParam == null || volumeParam == null || chapterParam == null) {
        validParams = false;
    } else {
        try {
            mangaId = Integer.parseInt(mangaIdParam);
            volumeNum = Integer.parseInt(volumeParam);
            chapterNum = Integer.parseInt(chapterParam);
        } catch (NumberFormatException e) {
            validParams = false;
        }
    }

    if (!validParams) {
%>
    <p class="error">Invalid or missing URL parameters.</p>
<%
    } else {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT chapter_pdf FROM chapter WHERE volume_id IN (SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?) AND chapterno = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, mangaId);
            pstmt.setInt(2, volumeNum);
            pstmt.setInt(3, chapterNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String pdfPath = rs.getString("chapter_pdf");

                if (pdfPath == null || pdfPath.trim().isEmpty()) {
%>
    <p class="error">No PDF file available for this chapter.</p>
<%
                } else {
                    String encodedPdfPath = URLEncoder.encode(pdfPath, "UTF-8");
%>
    <iframe
        src="<%= request.getContextPath() %>/read?file=<%= encodedPdfPath %>#toolbar=1&navpanes=0&scrollbar=1"
        title="Chapter PDF Viewer">
        This browser does not support PDFs.
        <a href="<%= request.getContextPath() %>/read?file=<%= encodedPdfPath %>">Download PDF</a>
    </iframe>
<%
                }
            } else {
%>
    <p class="error">PDF not found for the selected chapter.</p>
<%
            }
        } catch (Exception e) {
%>
    <p class="error">Error loading PDF: <%= e.getMessage() %></p>
<%
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ignored) {}
            if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
            if (conn != null) try { conn.close(); } catch (Exception ignored) {}
        }
    }
%>

</body>
</html>

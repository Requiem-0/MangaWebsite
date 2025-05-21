<%@ page import="java.util.List" %>
<%@ page import="com.manga.models.Bookmark" %>

<html>
<head>
    <title>Bookmarked Mangas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        table {
            width: 90%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #999;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        img {
            width: 100px;
            height: auto;
            border-radius: 8px;
        }
        .no-data {
            margin: 20px;
            font-size: 18px;
            color: #555;
        }
    </style>
</head>
<body>
    <h1>Bookmarked Mangas</h1>

    <%
        List<Bookmark> bookmarks = (List<Bookmark>) request.getAttribute("bookmarks");
        if (bookmarks != null && !bookmarks.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Manga ID</th>
                <th>Title</th>
                <th>Cover Image</th>
                <th>Username</th>
            </tr>
            <%
                for (Bookmark bookmark : bookmarks) {
            %>
            <tr>
                <td><%= bookmark.getMangaId() %></td>
                <td><%= bookmark.getMangaTitle() %></td>
                <td>
                    <img src="<%= request.getContextPath() %>/resources/images/<%= bookmark.getMangaImage() %>" 
                         alt="<%= bookmark.getMangaTitle() %>">
                </td>
                <td><%= bookmark.getUsername() %></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p class="no-data">No bookmarks found.</p>
    <%
        }
    %>

</body>
</html>

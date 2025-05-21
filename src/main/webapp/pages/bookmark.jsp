<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.manga.models.Bookmark" %>

<html>
<head>
    <title>Your Bookmarks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
</head>
<body>
<h2>Your Bookmarks</h2>

<div class="manga-container">
<%
    List<Bookmark> bookmarks = (List<Bookmark>) request.getAttribute("bookmarks");
    if (bookmarks == null || bookmarks.isEmpty()) {
%>
    <p>You have no bookmarks yet.</p>
<%
    } else {
        for (Bookmark b : bookmarks) {
            String volume = b.getVolumeTitle();

            // Convert volume title to an image filename
            // e.g., "One Piece" -> "onepiece.png"
            String imageName = volume.toLowerCase().replaceAll("\\s+", "") + ".png";
%>
    <div class="bookmark-item" style="margin-bottom:20px;">
        <img src="../resources/images/<%= imageName %>" alt="<%= volume %>" class="manga-cover" style="width:150px; height:auto; border:2px solid #444; border-radius:5px;" />
        <p style="color:#fff; font-weight:bold; margin-top:5px;"><%= volume %></p>

        <form action="BookmarkController" method="post">
            <input type="hidden" name="action" value="remove" />
            <input type="hidden" name="bookmarkId" value="<%= b.getId() %>" />
            <button type="submit">Remove Bookmark</button>
        </form>
    </div>
<%
        }
    }
%>
</div>

</body>
</html>

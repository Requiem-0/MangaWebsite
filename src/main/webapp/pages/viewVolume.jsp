<%@ page import="java.util.List, com.manga.models.Volume" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    // Get mangaId param (default to 1)
    int mangaId = 1;
    String mangaIdParam = request.getParameter("manga_id");
    if (mangaIdParam != null) {
        try {
            mangaId = Integer.parseInt(mangaIdParam);
        } catch (NumberFormatException e) {
            mangaId = 1;
        }
    }

    // Assume controller sets "volumeList" attribute with List<Volume> filtered by mangaId
    List<Volume> volumeList = (List<Volume>) request.getAttribute("volumeList");

    // You can also get manga details etc. here if you want, or from controller
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Manga Volumes</title>
  <link rel="stylesheet" href="../css/volume.css" />
</head>
<body>

<!-- Navbar -->
<header class="navbar">
  <div class="navbar-left">
    <img src="../resources/images/logo.png" alt="Logo" class="logo" />
  </div>
  <nav class="nav-center">
    <a href="<%=request.getContextPath()%>/HomeMangaServlet">Home</a>
    <a href="<%=request.getContextPath()%>/BookmarkServlet">Bookmark</a>
    <a href="<%=request.getContextPath()%>/pages/history.jsp">History</a>
    <a href="<%=request.getContextPath()%>/RandomMangaServlet">Random</a>
  </nav>
  <div class="navbar-right">
    <input type="text" placeholder="Search" class="search-bar" />
    <button class="login-btn">Login</button>
  </div>
</header>

<!-- Volume Section -->
<div id="Volume1" style="padding: 20px;">
  <h1 style="color:#9656ce;">Volumes</h1>
  <div class="pro-container">
    <% if (volumeList != null && !volumeList.isEmpty()) {
         for (Volume v : volumeList) {
           // Compose volume link and image URL
           String volImg = v.getVolume_img();
           String volLink = "volume.jsp?manga_id=" + mangaId + "&volume=" + v.getVolumeNumber() + "#SelectedChapters";
    %>
    <div class="pro">
      <a href="<%= volLink %>">
        <img src="<%= request.getContextPath() + volImg %>" alt="Volume <%= v.getVolumeNumber() %>" />
        <div class="des">Volume <%= v.getVolumeNumber() %></div>
      </a>
    </div>
    <%   }
       } else { %>
       <p style="color: #ccc; text-align:center;">No volumes available for this manga.</p>
    <% } %>
  </div>
</div>

<!-- You can keep the rest of your page content here, like chapters, footer, etc. -->

<!-- Footer -->
<footer class="footer">
  <img src="../resources/images/logo.png" alt="Footer Logo" class="footer-logo" />
  <div class="footer-links">
    <a href="#">Home</a>
    <a href="#">Privacy</a>
    <a href="#">Terms of Service</a>
  </div>
  <p class="footer-description">
    Read. Track. Repeat. Manga made seamless – follow your favorites, pick up where you left off, and dive into new worlds anytime.
  </p>
  <p class="footer-copy">Copyright © Book Choda Comic Padha</p>
</footer>

</body>
</html>

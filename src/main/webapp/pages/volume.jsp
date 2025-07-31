 <%@ page import="com.manga.models.User" %>
<%@ page import="java.util.*, com.manga.models.Manga, com.manga.models.Volume, com.manga.models.Chapter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//Retrieve user object from session
User loggedInUser = (User) session.getAttribute("user");

//Redirect if not logged in
if (loggedInUser == null) {
 response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
 return;
}%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Manga Reader</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/volume.css" />
</head>
<body>

<!-- Header -->
<header class="navbar">
  <div class="navbar-left">
    <img src="../resources/images/logo.png" alt="Logo" class="logo" />
  </div>
  <nav class="nav-center">

	    <a href="${pageContext.request.contextPath}/HomeMangaServlet">Home</a>
	    <a href="${pageContext.request.contextPath}/pages/history.jsp">History</a>
	    <a href="${pageContext.request.contextPath}/pages/profile.jsp">Profile</a>
	    <a href="${pageContext.request.contextPath}/RandomMangaServlet">Random</a>

  </nav>
  <div class="navbar-right">
    <input type="text" placeholder="Search" class="search-bar" />

  </div>
</header>

<%
    Manga manga = (Manga) request.getAttribute("manga");
    List<String> genres = (List<String>) request.getAttribute("genres");
    List<Volume> volumes = (List<Volume>) request.getAttribute("volumes");
    List<Chapter> chapters = (List<Chapter>) request.getAttribute("chapters");
    Integer selectedVolumeParam = (Integer) request.getAttribute("selectedVolume");
%>

<!-- Manga Info Section -->
<% if (manga != null) { %>
  <div id="container">
    <div class="media-card">
      <img src="<%= manga.getMangaImage() != null ? request.getContextPath() + manga.getMangaImage() : request.getContextPath() + "/resources/images/default.png" %>"
           alt="manga <%= manga.getMangaId() %>" class="cover-img" />
      <div class="card-details">
        <h2 class="main-title"><%= manga.getTitle() %></h2>
        <div class="meta-info">
          <p><strong>Type:</strong> Manga</p>
          <p><strong>Status:</strong> <%= manga.getStatus() %></p>
          <p><strong>Authors:</strong> <%= manga.getAuthor() %></p>
          <p><strong>Published:</strong> <%= manga.getPublishedDate() %></p>
        </div>

        <div class="genres">
          <p>
            <% if (genres != null) {
                 for (String genre : genres) { %>
              <button><%= genre %></button>
            <% }} %>
          </p>
        </div>

        <p class="desc"><%= manga.getDescription() %></p>
      </div>

    </div>
  </div>
<% } else { %>
  <div id="container">
    <p style="color: red; padding: 20px;">Manga details could not be loaded.</p>
  </div>
<% } %>

<% if (manga != null && volumes != null) { %>
<!-- Volume Section -->
<div id="Volume1" style="padding: 20px;">
  <h1 style="color:#9656ce;">Volumes</h1>
  <div class="pro-container">
    <% for (Volume vol : volumes) { %>
      <div class="pro">
        <a href="<%= request.getContextPath() %>/volume?manga_id=<%= manga.getMangaId() %>&volume=<%= vol.getVolumeNumber() %>#SelectedChapters">
          <img src="<%= vol.getVolume_img() != null ? request.getContextPath() + vol.getVolume_img() : request.getContextPath() + "/resources/images/default-volume.png" %>"
               alt="Volume <%= vol.getVolumeNumber() %>" />
          <div class="des">Volume <%= vol.getVolumeNumber() %></div>
        </a>
      </div>
    <% } %>
  </div>
</div>
<% } else { %>
  <p style="padding: 20px; color: red;">No volume data available.</p>
<% } %>

<% if (chapters != null && selectedVolumeParam != null) { %>
  <div id="SelectedChapters" class="section-p1" style="padding: 20px;">
    <h2 style="color: #9656ce">Chapters in Volume <%= selectedVolumeParam %></h2>
    <div class="chapter-list">
      <% if (chapters.isEmpty()) { %>
        <p>No chapters available for this volume.</p>
      <% } else {
          for (Chapter chap : chapters) { %>
            <div class="chapter-item">
              <a href="<%= request.getContextPath() %>/pages/read.jsp?manga_id=<%= manga.getMangaId() %>&volume=<%= selectedVolumeParam %>&chapter=<%= chap.getChapterno() %>">
                Chapter <%= chap.getChapterno() %>: <%= chap.getChaptertitle() %>
              </a>
            </div>
      <% } } %>
    </div>
  </div>
<% } %>

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

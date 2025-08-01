<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.manga.models.ReadingHistory.ReadingHistoryWithManga" %>

<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    List<ReadingHistoryWithManga> historyList = (List<ReadingHistoryWithManga>) request.getAttribute("historyList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Reading History</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/nav_footer.css">

  <style>
    .history-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .card {
      background-color: black;
      border-radius: 10px;
      padding: 15px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .card img {
      width: 100%;
      height: 200px;
      object-fit: cover;
      border-radius: 5px;
    }

    .details {
      margin-top: 10px;
      text-align: center;
      color: white;
    }

    .continue-btn-hist {
      display: inline-block;
      margin-top: 10px;
      padding: 8px 16px;
      background-color: #7289DA;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }

    .continue-btn-hist:hover {
      background-color: #0056b3;
    }

    .container {
      max-width: 1200px;
      margin: auto;
      padding: 20px;
    }

    h1 {
      text-align: center;
      margin-bottom: 20px;
      color: white;
    }
  </style>
</head>

<body>
<!-- Navbar Start -->
  <header class="navbar">
    <!-- Logo -->
<div class="navbar-left">
  <a href="${pageContext.request.contextPath}/pages/landing.jsp">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo" class="logo" />
  </a>
</div>

    <!-- Navigation Links (Centered) -->
    <nav class="nav-center">
      <a href="${pageContext.request.contextPath}/HomeMangaServlet">Home</a>
      <a href="${pageContext.request.contextPath}/pages/history.jsp">History</a>
      <a href="${pageContext.request.contextPath}/pages/profile.jsp">Profile</a>
      <a href="${pageContext.request.contextPath}/RandomMangaServlet">Random</a>
    </nav>

    <!-- Search and Login -->
    <div class="navbar-right">
      <form action="HomeMangaServlet" method="get">
        <input type="text" name="search" placeholder="Search" class="search-bar" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" />
      </form>
    </div>
  </header>
  <!-- Navbar End -->


  <section>
    <div class="container">
      <h1>Reading History</h1>

      <div class="history-grid">
        <%
          if (historyList == null || historyList.isEmpty()) {
        %>
          <p style="color: black; text-align: center;">No reading history found.</p>
        <%
          } else {
            for (ReadingHistoryWithManga history : historyList) {
        %>
          <div class="card">
           <img src="${pageContext.request.contextPath}/resources/images/<%= history.getMangaTitle().toLowerCase() %>.png" alt="<%= history.getMangaTitle() %>" class="manga-image" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/resources/images/default.png';" />
           
            <div class="details">
              <h2><%= history.getMangaTitle() %></h2>
              <p><strong>Author:</strong> <%= history.getAuthor() %></p>
              <p><strong>Description:</strong> <%= history.getDescription() %></p>
              <p><strong>Last Read:</strong> <%= history.getLastReadDate() %></p>
              
              <!-- Link to volume.jsp inside pages folder -->
              <a href="<%= request.getContextPath() %>/pages/volume.jsp?manga_id=<%= history.getMangaId() %>" class="continue-btn-hist">Continue</a>
            </div>
          </div>
        <%
            }
          }
        %>
      </div>
    </div>
  </section>

  <!-- Footer -->
  <footer class="footer">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Footer Logo" class="footer-logo" />

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

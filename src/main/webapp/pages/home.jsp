<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.manga.models.Manga" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Comic</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav_footer.css">
</head>
<body>

  <!-- Navbar Start -->
  <header class="navbar">
    <!-- Logo -->
    <div class="navbar-left">
      <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo" class="logo" />
    </div>

    <!-- Navigation Links (Centered) -->
    <nav class="nav-center">
      <a href="HomeMangaServlet">Home</a>
      <a href="#">Bookmark</a>
      <a href="history.jsp">History</a>
      <a href="#">Random</a>
    </nav>

    <!-- Search and Login -->
    <div class="navbar-right">
      <form action="HomeMangaServlet" method="get">
        <input type="text" name="search" placeholder="Search" class="search-bar" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" />
      </form>
      <button class="login-btn">Login</button>
    </div>
  </header>
  
  <!-- Navbar End -->

  <!-- body suru vayo -->
  <div class="highest-rated-frame">
  
    <div class="highest-rated-text">
      <h2>Highest Rated</h2>
    </div>
    
    <div class="image-and-content-section">
      <div class="image-section">
        <img src="${pageContext.request.contextPath}/resources/images/solo leveling.png" alt="solo leveling Poster" class="highest-rated-image" />
      </div>
      
      <div class="content-section">
        <h2>Solo Leveling</h2>
        <p class="description">
          E-class hunter Jinwoo Sung is the weakest of them all. Looked down on by everyone, <br>
          he has no money, no abilities to speak of, and no other job prospects. So when his party <br>
          finds a hidden dungeon, he's determined to use this chance to change his life...
        </p>
        <p><strong>Author:
        </strong><br> 추공 (Chugong)</p>
        
        <div class="genres">
          <p><strong>Genres:
          </strong><br> 
          <span>Action</span>
          <span>Adventure</span>
          <span>Fantasy</span>
          <span>Shounen</span></p>
        </div>
        
        <p><strong>Status:
        </strong> Completed</p>
        
        <p><strong>Rating:</strong> 10/10</p>
        
        <button class="read-button">Read Now</button>
      </div>
    </div>
  </div>

  <!-- Main Content Start -->
  <div class="comics">
    <div class="comics-header">
      <h2>Mangas</h2>

      <div class="filter-bar">
        <!-- Genre Filter -->
        <div class="filter-field">
          <label for="genre-select">Genre:</label>
          <select id="genre-select" class="filter-select" name="genre" onchange="applyFilter()">
            <option value="">All</option>
            <option value="Action">Action</option>
            <option value="Adventure">Adventure</option>
            <option value="Comedy">Comedy</option>
            <option value="Drama">Drama</option>
            <option value="Fantasy">Fantasy</option>
            <option value="Horror">Horror</option>
            <option value="Mystery">Mystery</option>
            <option value="Psychological">Psychological</option>
            <option value="Sci-Fi">Sci-Fi</option>
            <option value="Thriller">Thriller</option>
            <option value="Supernatural">Supernatural</option>
            <option value="Sports">Sports</option>
            <option value="Dark Fantasy">Dark Fantasy</option>
            <option value="Historical">Historical</option>
            <option value="Slice of Life">Slice of Life</option>
          </select>
        </div>

        <!-- Sort By Filter -->
        <div class="filter-field">
          <label for="sort-select">Sort By:</label>
          <select id="sort-select" class="filter-select" name="sort" onchange="applyFilter()">
            <option value="">Default</option>
            <option value="hottest">Hottest</option>
            <option value="latest">Latest</option>
            <option value="alpha">A-Z</option>
          </select>
        </div>
      </div>
    </div>
  </div>

  <!-- Manga Grid -->
  <div class="manga-grid">
    <%
      List<Manga> mangaList = (List<Manga>) request.getAttribute("mangaList");
      if (mangaList != null && !mangaList.isEmpty()) {
        for (Manga manga : mangaList) {
    %>
    <div class="manga-card">
      <img src="${pageContext.request.contextPath}/resources/images/<%= manga.getTitle().toLowerCase() %>.png" alt="<%= manga.getTitle() %>" class="manga-image" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/resources/images/default.png';" />
      <div class="manga-title"><%= manga.getTitle() %></div>
    </div>
    <%
        }
      } else {
    %>
    <p>No manga found.</p>
    <%
      }
    %>
  </div>

  <!-- Pagination -->
  <div class="pagination">
    <%
      Integer currentPage = (Integer) request.getAttribute("currentPage");
      Integer totalPages = (Integer) request.getAttribute("totalPages");
      String genre = (String) request.getAttribute("genre");
      String sort = (String) request.getAttribute("sort");
      if (currentPage == null) currentPage = 1;
      if (totalPages == null) totalPages = 1;
    %>
    <button class="prev-btn" <%= (currentPage == 1) ? "disabled" : "" %> onclick="location.href='HomeMangaServlet?page=<%= Math.max(currentPage - 1, 1) %>&genre=<%= genre %>&sort=<%= sort %>'">Previous</button>
    
    <% for (int i = 1; i <= totalPages; i++) { %>
      <button class="page-btn" <%= (currentPage == i) ? "disabled" : "" %> onclick="location.href='HomeMangaServlet?page=<%= i %>&genre=<%= genre %>&sort=<%= sort %>'"><%= i %></button>
    <% } %>
    
    <button class="next-btn" <%= (currentPage == totalPages) ? "disabled" : "" %> onclick="location.href='HomeMangaServlet?page=<%= Math.min(currentPage + 1, totalPages) %>&genre=<%= genre %>&sort=<%= sort %>'">Next</button>
  </div>

  <!-- Ongoing Section -->
  <div class="one-lanebar">
    <h2>Ongoing</h2>
  </div>

  <div class="manga-grid">
    <%
      List<Manga> ongoingMangaList = (List<Manga>) request.getAttribute("ongoingMangaList");
      if (ongoingMangaList != null && !ongoingMangaList.isEmpty()) {
        for (Manga manga : ongoingMangaList) {
    %>
    <div class="manga-card">
      <img src="${pageContext.request.contextPath}/resources/images/<%= manga.getTitle().toLowerCase() %>.png" alt="<%= manga.getTitle() %>" class="manga-image" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/resources/images/default.png';" />
      <div class="manga-title"><%= manga.getTitle() %></div>
    </div>
    <%
        }
      } else {
    %>
    <p>No ongoing manga found.</p>
    <%
      }
    %>
  </div>

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

  <script>
    function applyFilter() {
      const genre = document.getElementById('genre-select').value;
      const sort = document.getElementById('sort-select').value;
      window.location.href = 'HomeMangaServlet?page=1&genre=' + genre + '&sort=' + sort;
    }
  </script>
</body>
</html>

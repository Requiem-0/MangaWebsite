<%@ page import="java.sql.*, java.util.*, com.manga.database.DatabaseConnection, com.manga.models.Manga, com.manga.controllers.dao.ReadingHistoryDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer sessionUserId = (Integer) session.getAttribute("userId");
  

%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Manga Reader</title>
  <link rel="stylesheet" href="../css/volume.css" />
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
    int mangaId = 1;
    String mangaIdParam = request.getParameter("manga_id");
    String selectedVolumeParam = request.getParameter("volume");
    String selectedChapterParam = request.getParameter("chapter");

    if (mangaIdParam != null) {
        try {
            mangaId = Integer.parseInt(mangaIdParam);
        } catch (NumberFormatException e) {
            mangaId = 1;
        }
    }

    String mangatitle = "", author = "", mangadescription = "", status = "", publishedDate = "", mangaImage = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Integer userRating = null;
    double avgRating = 0.0;

    try {
        ReadingHistoryDAO historyDAO = new ReadingHistoryDAO();
        historyDAO.addOrUpdateReadingHistory(sessionUserId, mangaId);

        conn = DatabaseConnection.getConnection();

        // Get manga details
        String mangaQuery = "SELECT * FROM manga WHERE manga_id = ?";
        pstmt = conn.prepareStatement(mangaQuery);
        pstmt.setInt(1, mangaId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            mangatitle = rs.getString("mangatitle");
            author = rs.getString("author");
            mangadescription = rs.getString("mangadescription");
            status = rs.getString("status");
            publishedDate = rs.getString("published_date");
            mangaImage = rs.getString("mangaImage");
        }
        rs.close(); pstmt.close();

        // Get average rating
        String avgQuery = "SELECT AVG(rating) AS avg_rating FROM rating WHERE manga_id = ?";
        pstmt = conn.prepareStatement(avgQuery);
        pstmt.setInt(1, mangaId);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            avgRating = rs.getDouble("avg_rating");
        }
        rs.close(); pstmt.close();

        // Get current user's rating
        String userRatingQuery = "SELECT rating FROM rating WHERE manga_id = ? AND user_id = ?";
        pstmt = conn.prepareStatement(userRatingQuery);
        pstmt.setInt(1, mangaId);
        pstmt.setInt(2, sessionUserId);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            userRating = rs.getInt("rating");
        }
        rs.close(); pstmt.close();
%>

<div id="container">
  <div class="media-card">
    <img src="<%= request.getContextPath() + mangaImage %>" alt="manga <%= mangaId %>" class="cover-img" />
    <div class="card-details">
      <h2 class="main-title"><%= mangatitle %></h2>
      <div class="meta-info">
        <p><strong>Type:</strong> Manga</p>
        <p><strong>Status:</strong> <%= status %></p>
        <p><strong>Authors:</strong> <%= author %></p>
        <p><strong>Published:</strong> <%= publishedDate %></p>
      </div>

      <%
        // Genre retrieval
        String genreQuery = "SELECT g.genrename FROM genre g JOIN manga_genre mg ON g.genre_id = mg.genre_id WHERE mg.manga_id = ?";
        pstmt = conn.prepareStatement(genreQuery);
        pstmt.setInt(1, mangaId);
        rs = pstmt.executeQuery();
      %>
      <div class="genres">
        <p>
          <%
            while (rs.next()) {
              String genre = rs.getString("genrename");
          %>
          <button><%= genre %></button>
          <%
            }
            rs.close(); pstmt.close();
          %>
        </p>
      </div>

      <p class="desc"><%= mangadescription %></p>

      <!-- Rating -->
      <p><strong>Average Rating:</strong> <%= String.format("%.2f", avgRating) %> / 5</p>
      <form action="<%= request.getContextPath() %>/RatingController" method="post">

        <input type="hidden" name="mangaId" value="<%= mangaId %>" />
        <input type="hidden" name="userId" value="<%= sessionUserId %>" />
        <label for="rating">Rate this manga:</label>
        <select name="rating" id="rating" required>
          <option value="">-- Select --</option>
          <% for (int i = 1; i <= 5; i++) { %>
            <option value="<%= i %>" <%= (userRating != null && userRating == i) ? "selected" : "" %>>
              <%= i %>
            </option>
          <% } %>
        </select>
        <button type="submit">Submit</button>
      </form>

    </div>
  </div>
</div>

<!-- Volume Section -->
<div id="Volume1" style="padding: 20px;">
  <h1 style="color:#9656ce;">Volumes</h1>
  <div class="pro-container">
    <%
      String volumeQuery = "SELECT volumenumber, volume_img FROM volume WHERE manga_id = ? ORDER BY volumenumber";
      pstmt = conn.prepareStatement(volumeQuery);
      pstmt.setInt(1, mangaId);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        int volNumber = rs.getInt("volumenumber");
        String volImg = rs.getString("volume_img");
    %>
    <div class="pro">
      <a href="volume.jsp?manga_id=<%= mangaId %>&volume=<%= volNumber %>#SelectedChapters">
        <img src="<%= request.getContextPath() + volImg %>" alt="Volume <%= volNumber %>" />
        <div class="des">Volume <%= volNumber %></div>
      </a>
    </div>
    <%
      }
      rs.close(); pstmt.close();
    %>
  </div>
</div>

<%
    if (selectedVolumeParam != null) {
        int selectedVolume = Integer.parseInt(selectedVolumeParam);
        int volumeId = -1;

        String getVolumeIdQuery = "SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?";
        pstmt = conn.prepareStatement(getVolumeIdQuery);
        pstmt.setInt(1, mangaId);
        pstmt.setInt(2, selectedVolume);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            volumeId = rs.getInt("volume_id");
        }
        rs.close(); pstmt.close();

        if (volumeId != -1) {
            String chapterQuery = "SELECT chapterno, chaptertitle FROM chapter WHERE volume_id = ? ORDER BY chapterno";
            pstmt = conn.prepareStatement(chapterQuery);
            pstmt.setInt(1, volumeId);
            rs = pstmt.executeQuery();
%>
<div id="SelectedChapters" class="section-p1" style="padding: 20px;">
  <h2 style="color: #9656ce">Chapters in Volume <%= selectedVolume %></h2>
  <div class="chapter-list">
<%
      boolean hasChapters = false;
      while (rs.next()) {
        hasChapters = true;
        int chapterNum = rs.getInt("chapterno");
        String chapterTitle = rs.getString("chaptertitle");
%>
    <div class="chapter-item">
      <a href="read.jsp?manga_id=<%= mangaId %>&volume=<%= selectedVolume %>&chapter=<%= chapterNum %>">
        Chapter <%= chapterNum %>: <%= chapterTitle %>
      </a>
    </div>
<%
      }
      if (!hasChapters) {
%>
    <p>No chapters available for this volume.</p>
<%
      }
      rs.close(); pstmt.close();
%>
  </div>
</div>
<%
        }
    }
%>

<%
    } catch (Exception e) {
%>
  <p style="color:red; text-align:center;">Error: <%= e.getMessage() %></p>
<%
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) {}
        if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>

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

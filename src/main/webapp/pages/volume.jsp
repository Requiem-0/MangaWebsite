<%@ page import="java.sql.*, com.manga.database.DatabaseConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <a href="home.jsp">Home</a>
    <a href="#">Bookmark</a>
    <a href="history.jsp">History</a>
    <a href="#">Random</a>
  </nav>
  <div class="navbar-right">
    <input type="text" placeholder="Search" class="search-bar" />
    <button class="login-btn">Login</button>
  </div>
</header>

<%
    int mangaId = 1;
    String mangaIdParam = request.getParameter("manga_id");
    if (mangaIdParam != null) {
        try {
            mangaId = Integer.parseInt(mangaIdParam);
        } catch (NumberFormatException e) {
            mangaId = 1;
        }
    }

    String selectedVolumeParam = request.getParameter("volume");
    String selectedChapterParam = request.getParameter("chapter");

    if (selectedVolumeParam != null && selectedChapterParam != null) {
        // === Show only PDF view ===
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String getPDFQuery = "SELECT chapter_pdf FROM chapter WHERE volume_id = (SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?) AND chapterno = ?";
            pstmt = conn.prepareStatement(getPDFQuery);
            pstmt.setInt(1, mangaId);
            pstmt.setInt(2, Integer.parseInt(selectedVolumeParam));
            pstmt.setInt(3, Integer.parseInt(selectedChapterParam));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String pdfPath = rs.getString("chapter_pdf");
%>
    <div style="text-align:center; padding: 20px;">
 <iframe 
    src="uploads/<%= pdfPath %>#toolbar=1&navpanes=0&scrollbar=1" 
    width="100%" 
    height="1000px" 
    style="border: none;"
>
    This browser does not support PDFs. Please download the PDF to view it: 
    <a href="uploads/<%= pdfPath %>">Download PDF</a>
</iframe>


    </div>
<%
            } else {
%>
    <p style="color:red; text-align:center;">PDF not found for selected chapter.</p>
<%
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
%>
    <p style="color:red; text-align:center;">Error loading PDF: <%= e.getMessage() %></p>
<%
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) {}
            if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
            if (conn != null) try { conn.close(); } catch (Exception e) {}
        }
    } else {
        // === Show full manga + volumes + chapters ===
        String mangatitle = "", author = "", mangadescription = "", status = "", publishedDate = "", mangaImage = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
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
            rs.close();
            pstmt.close();
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
    // Genre list retrieval
    String genreQuery = "SELECT g.genrename FROM genre g " +
                        "JOIN manga_genre mg ON g.genre_id = mg.genre_id " +
                        "WHERE mg.manga_id = ?";
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
    rs.close();
    pstmt.close();
%>
  </p>
</div>

      <p class="desc">
        <%= mangadescription %>
      </p>
      <p class="desc">
  <%= mangadescription %>
</p>

<%
    // Handle rating submission
    String ratingParam = request.getParameter("rating");
    if (ratingParam != null) {
        try {
            int ratingValue = Integer.parseInt(ratingParam);
            if (ratingValue >= 1 && ratingValue <= 10) {
                String insertRatingSQL = "INSERT INTO rating (manga_id, rating_value) VALUES (?, ?)";
                PreparedStatement insertPstmt = conn.prepareStatement(insertRatingSQL);
                insertPstmt.setInt(1, mangaId);
                insertPstmt.setInt(2, ratingValue);
                insertPstmt.executeUpdate();
                insertPstmt.close();
            }
        } catch (Exception e) {
%>
    <p style="color: red;">Failed to submit rating: <%= e.getMessage() %></p>
<%
        }
    }

    // Calculate average rating
    double avgRating = 0.0;
    int ratingCount = 0;
    String avgQuery = "SELECT AVG(rating_value) AS avg_rating, COUNT(*) AS count FROM rating WHERE manga_id = ?";
    PreparedStatement avgPstmt = conn.prepareStatement(avgQuery);
    avgPstmt.setInt(1, mangaId);
    ResultSet avgRs = avgPstmt.executeQuery();
    if (avgRs.next()) {
        avgRating = avgRs.getDouble("avg_rating");
        ratingCount = avgRs.getInt("count");
    }
    avgRs.close();
    avgPstmt.close();
%>

<!-- Rating Display and Dropdown -->
<div class="rating-section" style="margin-top: 20px;">
  <p><strong>Rating:</strong> <%= String.format("%.1f", avgRating) %>/10 (<%= ratingCount %> ratings)</p>

  <form method="post" action="volume.jsp?manga_id=<%= mangaId %>">
    <label for="rating">Rate this manga:</label>
    <select name="rating" id="rating" style="margin-left: 10px;">
      <% for (int i = 10; i >= 1; i--) { %>
        <option value="<%= i %>"><%= i %></option>
      <% } %>
    </select>
    <button type="submit" style="margin-left: 10px;">Submit</button>
  </form>
</div>
      
</div>
      
        </div>
  </div>
</div>

<!-- Volume Section -->
<div id="Volume1" style="padding: 20px;">
  <h1 style="color:#9656ce;">Volumes</h1>
  <div class="pro-container">
    
    <%
    // Load volumes
    String volumeQuery = "SELECT volumenumber, volume_img FROM volume WHERE manga_id = ? ORDER BY volumenumber";
    pstmt = conn.prepareStatement(volumeQuery);
    pstmt.setInt(1, mangaId);
    rs = pstmt.executeQuery();

    while (rs.next()) {
      int volNumber = rs.getInt("volumenumber");
      String volImg = rs.getString("volume_img"); // example: /resources/images/volumes/vol1.jpg
  %>
  <div class="pro">
    <a href="volume.jsp?manga_id=<%= mangaId %>&volume=<%= volNumber %>#SelectedChapters">
      <img src="<%= request.getContextPath() + volImg %>" alt="Volume <%= volNumber %>" />
      <div class="des">Volume <%= volNumber %></div>
    </a>
  </div>
  <%
    }
    rs.close();
    pstmt.close();
  %>
</div>
</div>


<!-- Dynamic Chapter List -->
<%
    if (selectedVolumeParam != null) {
        int selectedVolume = Integer.parseInt(selectedVolumeParam);
        int volumeId = -1;

        // Get volume_id
        String getVolumeIdQuery = "SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?";
        pstmt = conn.prepareStatement(getVolumeIdQuery);
        pstmt.setInt(1, mangaId);
        pstmt.setInt(2, selectedVolume);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            volumeId = rs.getInt("volume_id");
        }
        rs.close();
        pstmt.close();

        if (volumeId != -1) {
            // Get chapters
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
      rs.close();
      pstmt.close();
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

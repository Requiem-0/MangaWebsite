<%@ page import="java.sql.*, com.manga.database.DatabaseConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Jujutsu Kaisen</title>
    <link rel="stylesheet" href="../css/volume.css" />
    <style>
      .nav-buttons {
        text-align: center;
        margin: 30px 0;
      }
      .nav-buttons a {
        padding: 10px 20px;
        border: 1px solid #fff;
        text-decoration: none;
        margin: 5px;
        display: inline-block;
        border-radius: 5px;
        color: #fff;
      }
      .nav-buttons a:hover,
      .nav-buttons a:focus {
        background: #9656ce;
        border-color: #9656ce;
        color: #fff;
      }
      .toggle-content {
        display: none;
      }
      .toggle-content:target {
        display: block;
      }
    </style>
  </head>
  <body>
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
      int mangaId = 10;
      if (request.getParameter("manga_id") != null) {
        mangaId = Integer.parseInt(request.getParameter("manga_id"));
      }

      String mangatitle = "", author = "", mangadescription = "", status = "", publishedDate = "", mangaImage = "";
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
        conn = DatabaseConnection.getConnection();

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
        <img src="../resources/images/<%= mangaImage %>" alt="manga <%= mangaId %>" class="cover-img" />
        <div class="card-details">
          <h2 class="main-title"><%= mangatitle %></h2>
          <div class="meta-info">
            <p><strong>Type:</strong> Manga</p>
            <p><strong>Status:</strong> <%= status %></p>
            <p><strong>Authors:</strong> <%= author %></p>
            <p><strong>Magazines:</strong> Shounen Jump (Weekly)</p>
            <p><strong>Published:</strong> <%= publishedDate %></p>
            <p><strong>Score:</strong> 8.52</p>
          </div>
          <div class="genres">
            <p>
              <button>Action</button>
              <button>Demons</button>
              <button>Fantasy</button>
              <button>Shounen</button>
              <button>Supernatural</button>
            </p>
          </div>
          <p class="desc">
            <%= mangadescription %>
          </p>
          <a href="#" class="read-full">+ Read full</a>
        </div>
      </div>
    </div>

  

    <!-- Volume Display -->
    <div id="Volume1" style="padding: 20px;">
      <h1 style="color:#9656ce;">Volumes</h3>
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
    		<img src="../resources/images/<%= volImg %>" alt="Volume <%= volNumber %>" />
    		<div class="des">
      			Volume <%= volNumber %>
    		</div>
  			</a>
		</div>
        <%
          }
        } catch (Exception e) {
          out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        } finally {
          if (rs != null) try { rs.close(); } catch (Exception e) {}
          if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
        }
        %>
      </div>
    </div>

    

    <!-- Dynamic Chapter List by Volume -->
    <%
      String selectedVolumeParam = request.getParameter("volume");
      if (selectedVolumeParam != null) {
        int selectedVolume = Integer.parseInt(selectedVolumeParam);
        int volumeId = -1;

        try {
          // Step 1: Get volume_id using manga_id and volumenumber
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
            // Step 2: Get chapters
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
          <a href="chapter.jsp?volume=<%= selectedVolume %>&chapter=<%= chapterNum %>">
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
      } catch (Exception e) {
        out.println("<p style='color:red;'>Error loading chapters: " + e.getMessage() + "</p>");
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
        Read. Track. Repeat. Manga made seamless – follow your favorites, pick
        up where you left off, and dive into new worlds anytime.
      </p>
      <p class="footer-copy">Copyright © Book Choda Comic Padha</p>
    </footer>
  </body>
</html>

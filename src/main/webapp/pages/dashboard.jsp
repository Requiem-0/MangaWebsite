<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Manga Admin Panel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="<%= request.getContextPath() %>/css/admin_dashboard.css" rel="stylesheet" />
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 dashbrd-sidebar d-md-block">
        <h2>Manga Admin type shi</h2>
        <a href="dashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ManageMangaController?action=list">Manage Manga</a>
        <a href="#">Manage Users</a>
        <a href="#">Logout</a>
      </nav>

      <!-- Main Content -->
      <main class="col-md-9 col-lg-10 dashbrd-main-content">
        <div class="dashbrd-header">
          <h1 class="h4">Welcome, Admin</h1>
        </div>

        <div class="dashbrd-content-box">
          <h2 class="h5">Dashboard Overview</h2>
          <div class="row g-4">
            <!-- Total Users -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Total Users</h5>
                <h3><%= request.getAttribute("userCount") %></h3>
              </div>
            </div>

            <!-- Total Mangas -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Total Mangas</h5>
                <h3><%= request.getAttribute("mangaCount") %></h3>
              </div>
            </div>

            <!-- Total Genres -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Total Genres</h5>
                <h3><%= request.getAttribute("genreCount") %></h3>
              </div>
            </div>

            <!-- Total Reviews -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Total Reviews</h5>
                <h3><%= request.getAttribute("reviewCount") %></h3>
              </div>
            </div>

            <!-- Most Bookmarked Manga -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Most Bookmarked Manga</h5>
                <h3><%= request.getAttribute("mostBookmarkedTitle") != null ? request.getAttribute("mostBookmarkedTitle") : "N/A" %></h3>
                <small>Bookmarked by <%= request.getAttribute("mostBookmarkedCount") != null ? request.getAttribute("mostBookmarkedCount") : "0" %> users</small>
              </div>
            </div>

            <!-- Highest Rated Manga -->
            <div class="col-md-4">
              <div class="dashbrd-stat-card">
                <h5>Highest Rated Manga</h5>
                <h3><%= request.getAttribute("highestRatedTitle") != null ? request.getAttribute("highestRatedTitle") : "N/A" %></h3>
                <small>Average Rating: <%= request.getAttribute("highestRating") != null ? request.getAttribute("highestRating") : "0.0" %></small>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

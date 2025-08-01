<%@ page import="com.manga.models.User" %>
<%
    // Retrieve user object from session
    User loggedInUser = (User) session.getAttribute("user");

    // Redirect if not logged in
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }

    // Check for role 
    if (!"admin".equals(loggedInUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/unauthorized.jsp");
        return;

    }
%>
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
      <h2>Admin Panel</h2>
     <a href="<%= request.getContextPath() %>/DashboardController">Dashboard</a>
      <a href="<%= request.getContextPath() %>/ManageMangaController">Manage Manga</a>
      <a href="<%= request.getContextPath() %>/ManageVolumeController">Manage Volume</a>
      <a href="<%= request.getContextPath() %>/ManageChapterController" class="active">Manage Chapter</a>
      <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
	  <a href="<%= request.getContextPath() %>/LogoutController" class="logout-link">Logout</a>
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
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

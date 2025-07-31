<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.manga.models.Volume, com.manga.models.Manga" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Manage Volumes</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="<%= request.getContextPath() %>/css/admin_manage_volume.css" rel="stylesheet" />
  <style>
    img { max-height: 100px; }
  </style>
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 mngvolume-sidebar d-md-block">
      <h2>Admin Panel</h2>
     <a href="<%= request.getContextPath() %>/DashboardController">Dashboard</a>
      <a href="<%= request.getContextPath() %>/ManageMangaController">Manage Manga</a>
      <a href="<%= request.getContextPath() %>/ManageVolumeController">Manage Volume</a>
      <a href="<%= request.getContextPath() %>/ManageChapterController" class="active">Manage Chapter</a>
      <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
	  <a href="<%= request.getContextPath() %>/LogoutController" class="logout-link">Logout</a>
      </nav>

      <!-- Main Content -->
      <main class="col-md-9 col-lg-10 mngvolume-main-content">
        <div class="mngvolume-header">
          <h1 class="h4">Manage Volumes</h1>
          <h5>Manga ID: <%= request.getAttribute("mangaId") != null ? request.getAttribute("mangaId") : "N/A" %></h5>
        </div>

        <div class="mngvolume-content-box">
          <% 
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            if (message != null) {
          %>
            <div class="alert alert-success"><%= message %></div>
          <% } 
             if (error != null) {
          %>
            <div class="alert alert-danger"><%= error %></div>
          <% } %>

          <table class="table table-striped table-bordered table-hover">
            <thead class="table-dark">
              <tr>
                <th>Volume Number</th>
                <th>ISBN</th>
                <th>Image</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
            <%
              List<Volume> volumeList = (List<Volume>) request.getAttribute("volumeList");
              if (volumeList == null || volumeList.isEmpty()) {
            %>
              <tr><td colspan="4" class="text-center">No volumes found.</td></tr>
            <%
              } else {
                for (Volume volume : volumeList) {
            %>
              <tr>
                <td><%= volume.getVolumeNumber() %></td>
                <td><%= volume.getIsbn() %></td>
                <td>
                  <% if (volume.getVolume_img() != null && !volume.getVolume_img().isEmpty()) { %>
                    <img src="<%= request.getContextPath() + volume.getVolume_img() %>" alt="Volume Image" />
                  <% } else { %>
                    No image
                  <% } %>
                </td>
                <td>
                  <form method="get" action="<%= request.getContextPath() %>/ManageVolumeController" style="display:inline;">
                    <input type="hidden" name="action" value="deleteVolume" />
                    <input type="hidden" name="volumeId" value="<%= volume.getVolumeId() %>" />
                    <input type="hidden" name="mangaId" value="<%= request.getAttribute("mangaId") %>" />
                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this volume?');">Delete</button>
                  </form>
                </td>
              </tr>
            <%
                }
              }
            %>
            </tbody>
          </table>

          <h3>Add New Volume</h3>
          <form method="post" action="<%= request.getContextPath() %>/ManageVolumeController" enctype="multipart/form-data" class="mb-5">
            <input type="hidden" name="action" value="addVolume" />
            <div class="row g-3 align-items-center">

              <div class="col-md-4">
                <label for="mangaId" class="form-label">Select Manga</label>
                <select id="mangaId" name="mangaId" class="form-select" required>
                  <option value="" disabled selected>Select a manga</option>
                  <%
                    List<Manga> mangaList = (List<Manga>) request.getAttribute("mangaList");
                    if (mangaList != null) {
                      String selectedMangaId = request.getParameter("mangaId");
                      for (Manga manga : mangaList) {
                  %>
                    <option value="<%= manga.getMangaId() %>" 
                      <%= (selectedMangaId != null && selectedMangaId.equals(String.valueOf(manga.getMangaId()))) ? "selected" : "" %>>
                      <%= manga.getTitle() %>
                    </option>
                  <%
                      }
                    }
                  %>
                </select>
              </div>

              <div class="col-md-2">
                <label for="volumeNumber" class="form-label">Volume Number</label>
                <input type="number" id="volumeNumber" name="volumeNumber" class="form-control" required 
                  value="<%= request.getParameter("volumeNumber") != null ? request.getParameter("volumeNumber") : "" %>" />
              </div>

              <div class="col-md-3">
                <label for="isbn" class="form-label">ISBN</label>
                <input type="text" id="isbn" name="isbn" class="form-control" required 
                  value="<%= request.getParameter("isbn") != null ? request.getParameter("isbn") : "" %>" />
              </div>

              <div class="col-md-3">
                <label for="volumeImageFile" class="form-label">Volume Image</label>
                <input type="file" id="volumeImageFile" name="volumeImageFile" accept="image/*" class="form-control" />
              </div>
            </div>

            <div class="mt-3">
              <button type="submit" class="btn btn-primary">Add Volume</button>
            </div>
          </form>

          <form method="get" action="<%= request.getContextPath() %>/ManageVolumeController" class="mb-4">
            <div class="input-group">
              <input type="number" name="mangaId" class="form-control" placeholder="Enter Manga ID to view volumes" required />
              <input type="hidden" name="action" value="list" />
              <button type="submit" class="btn btn-secondary">Show Volumes</button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

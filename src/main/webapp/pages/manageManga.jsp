<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.manga.models.Manga" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manga Admin Panel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="<%= request.getContextPath() %>/css/admin_manage_manga.css" rel="stylesheet">

</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 mngmanga-sidebar d-md-block">
        <h2>Manga Admin Panel</h2>
        <a href="<%= request.getContextPath() %>/DashboardController">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ManageMangaController?action=list">Manage Manga</a>
        <a href="<%= request.getContextPath() %>/ManageUsersController">Manage Users</a>
        <a href="#">Logout</a>
      </nav>

      <!-- Main Content -->
      <main class="col-md-9 col-lg-10 mngmanga-main-content">
        <div class="mngmanga-header">
          <h1 class="h4">Manage Manga</h1>
        </div>

        <div class="mngmanga-content-box">
          <h2 class="h5">Manga Management</h2>
          <button class="btn mngmanga-btn-discord mb-3" data-bs-toggle="modal" data-bs-target="#addMangaModal">Add New Manga</button>

          <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Genres</th>
                <th>Status</th>
                <th>Published</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
            <%
              List<Manga> mangaList = (List<Manga>) request.getAttribute("mangaList");
              if (mangaList == null || mangaList.isEmpty()) {
            %>
              <tr><td colspan="7" class="text-center">No manga found.</td></tr>
            <%
              } else {
                for (Manga manga : mangaList) {
            %>
              <tr>
                <td><%= manga.getMangaId() %></td>
                <td><%= manga.getTitle() %></td>
                <td><%= manga.getAuthor() %></td>
                <td>
                  <%
                    List<String> genres = manga.getGenres();
                    if (genres != null && !genres.isEmpty()) {
                      for (String genre : genres) {
                  %>
                    <span class="badge bg-secondary"><%= genre %></span>
                  <%
                      }
                    } else {
                  %>
                    <span class="badge bg-secondary">No genres available</span>
                  <%
                    }
                  %>
                </td>
                <td><%= manga.getStatus() %></td>
                <td><%= manga.getPublishedDate() %></td>
                <td>
                 <a href="<%= request.getContextPath() %>/ManageMangaController?action=editManga&mangaId=<%= manga.getMangaId() %>" class="btn btn-sm btn-primary custom-btn">Edit</a>
<a href="<%= request.getContextPath() %>/ManageMangaController?action=deleteManga&mangaId=<%= manga.getMangaId() %>" class="btn btn-sm btn-danger custom-btn" onclick="return confirm('Are you sure you want to delete this manga?')">Delete</a>


                </td>
              </tr>
            <%
                }
              }
            %>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  </div>

  <!-- Add Manga Modal -->
  <div class="modal fade" id="addMangaModal" tabindex="-1" aria-labelledby="addMangaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <form action="<%= request.getContextPath() %>/ManageMangaController" method="post">
          <input type="hidden" name="action" value="addManga">
          <div class="modal-header">
            <h5 class="modal-title" id="addMangaModalLabel">Add New Manga</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <div class="half-width">
                <label for="mangaTitle">Title</label>
                <input type="text" id="mangaTitle" name="title" class="form-control" required>
              </div>
              <div class="half-width">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" class="form-control" required>
              </div>
              <div class="half-width">
                <label for="genre">Genre (comma separated)</label>
                <input type="text" id="genre" name="genre" class="form-control">
              </div>
              <div class="half-width">
                <label for="status">Status</label>
                <select id="status" name="status" class="form-select">
                  <option value="Ongoing">Ongoing</option>
                  <option value="Completed">Completed</option>
                  <option value="Hiatus">Hiatus</option>
                </select>
              </div>
              <div class="half-width">
                <label for="publishedDate">Published Date</label>
                <input type="date" id="publishedDate" name="publishedDate" class="form-control">
              </div>
              <div class="full-width">
                <label for="description">Description</label>
                <textarea id="description" name="description" class="form-control" rows="3"></textarea>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary w-100 w-md-auto">Save</button>
            <button type="button" class="btn btn-secondary w-100 w-md-auto" data-bs-dismiss="modal">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

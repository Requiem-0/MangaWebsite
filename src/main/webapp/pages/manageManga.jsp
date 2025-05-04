<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manga Admin Panel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="../css/admin_manage_manga.css" rel="stylesheet">
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 mngmanga-sidebar d-md-block">
        <h2>Manga Admin type shi</h2>
        <a href="dashboard.jsp">Dashboard</a>
        <a href="manageManga.jsp">Manage Manga</a>
        <a href="manageUsers.jsp">Manage Users</a>
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
                <th>Genre</th>
                <th>Status</th>
                <th>Published</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>Naruto</td>
                <td>Masashi Kishimoto</td>
                <td>Action</td>
                <td>Completed</td>
                <td>1999-09-21</td>
                <td>
                  <button class="btn btn-sm mngmanga-btn-discord">Edit</button>
                  <button class="btn btn-sm btn-danger">Delete</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="addMangaModal" tabindex="-1" aria-labelledby="addMangaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <form action="addMangaServlet" method="post">
          <div class="modal-header">
            <h5 class="modal-title" id="addMangaModalLabel">Add New Manga</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <div class="half-width">
                <label for="mangaTitle">Title</label>
                <input type="text" id="mangaTitle" name="mangatitle" class="form-control" required>
              </div>
              <div class="half-width">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" class="form-control" required>
              </div>
              <div class="half-width">
                <label for="genre">Genre</label>
                <input type="text" id="manga genre" name="genre" class="form-control">
              </div>
              <div class="half-width">
                <label for="status">Status</label>
                <select id="manga status" name="status" class="form-select">
                  <option value="Ongoing">Ongoing</option>
                  <option value="Completed">Completed</option>
                  <option value="Hiatus">Hiatus</option>
                </select>
              </div>
              <div class="half-width">
                <label for="publishedDate">Published Date</label>
                <input type="manga pb date" id="publishedDate" name="published_date" class="form-control">
              </div>
              <div class="full-width">
                <label for="description">Description</label>
                <textarea id="manga description" name="mangadescription" class="form-control" rows="3"></textarea>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Save</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

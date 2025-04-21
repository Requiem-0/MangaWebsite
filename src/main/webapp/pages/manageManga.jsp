<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manga Admin Panel</title>
  <!-- Bootstrap 5 CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- External CSS -->
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
          <button class="btn mngmanga-btn-discord mb-3">Add New Manga</button>
          <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Genre</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <!-- Example row -->
              <tr>
                <td>1</td>
                <td>Naruto</td>
                <td>Masashi Kishimoto</td>
                <td>Action, Adventure</td>
                <td>
                  <button class="btn btn-sm mngmanga-btn-discord">Edit</button>
                  <button class="btn btn-sm btn-danger">Delete</button>
                  <button class="btn btn-sm btn-secondary">Volumes</button>
                  <button class="btn btn-sm btn-secondary">Chapters</button>
                  <button class="btn btn-sm btn-secondary">Reviews</button>
                </td>
              </tr>
              <!-- Additional manga rows will go here -->
            </tbody>
          </table>
        </div>
      </main>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

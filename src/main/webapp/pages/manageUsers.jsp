<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>User Admin Panel</title>
  <!-- Bootstrap 5 CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <!-- External Custom CSS -->
  <link href="../css/manage_users.css" rel="stylesheet" />
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- Sidebar -->
      <nav class="col-md-3 col-lg-2 sidebar d-md-block">
        <h2>Manga Admin Type Shi</h2>
        <a href="dashboard.jsp">Dashboard</a>
        <a href="manageManga.jsp">Manage Manga</a>
        <a href="manageUsers.jsp">Manage Users</a>
        <a href="#">Logout</a>
      </nav>

      <!-- Main Content -->
      <main class="col-md-9 col-lg-10 main-content">
        <div class="header">
          <h1 class="h4">Manage Users</h1>
        </div>

        <div class="content-box">
          <h2 class="h5">User Management</h2>
          <button class="btn btn-discord mb-3">Add New User</button>
          <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th>User ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Password</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>Naruto</td>
                <td>borutomidaf@gmail.com</td>
                <td>Sasukelover123</td>
                <td>
                  <button class="btn btn-sm btn-success">Add</button>
                  <button class="btn btn-sm btn-discord">Edit</button>
                  <button class="btn btn-sm btn-danger">Delete</button>
                </td>
              </tr>
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

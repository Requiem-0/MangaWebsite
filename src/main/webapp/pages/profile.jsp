<%
// Disable caching for this page
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

    // Retrieve the username and email from the session
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");

    // If the user is not logged in, redirect them to the login page
    if (username == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Settings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #1e2124;
      color: #fff;
      font-family: sans-serif;
      height: 100vh;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      position: relative;
    }

    .container {
      display: flex;
      flex-direction: column;
      gap: 20px;
      align-items: center;
    }

    .settings-box, .password-box {
      background-color: #2d2f33;
      border-radius: 10px;
      padding: 20px;
      max-width: 600px;
      width: 100%;
    }

    .btn-space {
      margin-right: 5px;
      padding: 0.3rem 0.8rem;
    }

    .profile-img-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 10px;
    }

    .profile-img-container input[type="file"] {
      font-size: 0.75rem;
      padding: 0.3rem;
    }

    .profile-img-container button {
      font-size: 0.75rem;
      padding: 0.4rem 1rem;
    }

    .logout-form button {
      background-color: #7289DA;
      border: none;
      padding: 0.5rem 1rem;
      border-radius: 6px;
      color: white;
      font-weight: bold;
      cursor: pointer;
    }

    .logout-form button:hover {
      background-color: #5a6cb2;
    }	
  </style>
</head>
<body>
  <!-- Main Container -->
  <div class="container">
    <!-- Account Info Section -->
    <div class="settings-box text-center">
      <div class="d-flex justify-content-center mb-3">
        <!-- Profile Image Upload Section -->
        <div class="profile-img-container me-3">
          <form action="#" method="post" enctype="multipart/form-data">
            <input type="file" name="profile-image" class="form-control mb-3" accept="image/*">
            <button class="btn btn-secondary" type="submit">Upload Photo</button>
          </form>
        </div>

        <div class="text-start">
          <form action="#" method="post">
            <input type="text" class="form-control mb-2" placeholder="<%= username != null ? username : "" %>">
            <input type="email" class="form-control mb-3" placeholder="<%= email != null ? email : "" %>" readonly>

            <div class="d-flex">
              <button class="btn btn-danger btn-space" type="button">Delete Photo</button>
              <button class="btn btn-primary" type="submit">Save Changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Change Password Section -->
    <div class="password-box text-center">
      <h5 class="mb-4">Change Password</h5>
      <form action="#" method="post">
        <input type="password" class="form-control mb-3" placeholder="Current password" required>
        <input type="password" class="form-control mb-3" placeholder="New Password" required>
        <input type="password" class="form-control mb-4" placeholder="Confirm Password" required>
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </form>
    </div>

    <!-- Logout Button -->
    <form action="<%= request.getContextPath() %>/LogoutController" method="post" class="logout-form text-center mt-4">
      <button type="submit">Log Out</button>
    </form>
  </div>
</body>
</html>

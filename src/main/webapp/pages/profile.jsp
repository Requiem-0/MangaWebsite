<%@ page import="com.manga.models.User" %>

<%
    User loggedInUser = (User) session.getAttribute("user");
    String profilePicPath = (loggedInUser != null && loggedInUser.getProfilePicture() != null)
        ? request.getContextPath() + loggedInUser.getProfilePicture()
        : request.getContextPath() + "/resources/images/hunter.png"; // fallback image
%>

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

//Retrieve any messages or errors from the request attributes
String message = (String) request.getAttribute("message");
String error = (String) request.getAttribute("error");

%>





<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Settings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav_footer.css">
  
  <style>
    .container {
      display: flex;
      flex-direction: column;
      gap: 20px;
      align-items: center;
      padding:20px 0;
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
  <!-- Navbar Start -->
  <header class="navbar">
    <!-- Logo -->
    <div class="navbar-left">
      <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo" class="logo" />
    </div>

    <!-- Navigation Links (Centered) -->
    <nav class="nav-center">
      <a href="home.jsp">Home</a>
      <a href="#">Bookmark</a>
      <a href="history.jsp">History</a>
      <a href="#">Random</a>
    </nav>

    <!-- Search and Login -->
    <div class="navbar-right">
      <input type="text" placeholder="Search" class="search-bar" />
      <button class="login-btn">Login</button>
    </div>
  </header>
  
  <!-- Navbar End -->
  
  
  <!-- Main Container -->
  <div class="container">
  <% if (message != null) { %>
  <div class="alert alert-success alert-dismissible fade show" role="alert">
    <%= message %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
<% } %>

<% if (error != null) { %>
  <div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%= error %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
<% } %>
  
    <!-- Account Info Section -->
    <div class="settings-box text-center">
      <div class="d-flex justify-content-center align-items-center mb-3">
        <!-- Profile Image Upload Section -->
        <div class="profile-img-container me-3">

         <!-- Card to show uploaded image -->
		  <div class="card mb-3" style="width: 120px; height: 150px; overflow: hidden;">
		    <img src="<%= profilePicPath %>" 
		         class="card-img-top" 
		         alt="Profile Image" 
		         style="width: 100%; object-fit: cover; height: 200px;">
		  </div>
		
		  <!-- Form to upload profile picture -->
		  <form action="${pageContext.request.contextPath}/ProfileController" method="post" enctype="multipart/form-data">
		    <input type="file" name="profilePicture" class="form-control mb-3" accept="image/*">
		    <input type="hidden" name="action" value="uploadProfilePicture">
		    <button class="btn btn-secondary" type="submit">Upload Photo</button>
		  </form>
        </div>

        <div class="text-start">
          <!-- Form to update username and email -->
          <form action="<%= request.getContextPath() %>/ProfileController" method="post">
            <input type="text" name="newUsername" class="form-control mb-2" value="<%= username != null ? username : "" %>">
            <input type="email" class="form-control mb-3" value="<%= email != null ? email : "" %>" readonly>
            <input type="hidden" name="action" value="updateProfile"> <!-- Action for updating the profile -->

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
      <!-- Form to change password -->
      <form action="<%= request.getContextPath() %>/ProfileController" method="post">
        <input type="password" name="currentPassword" class="form-control mb-3" placeholder="Current password" required>
        <input type="password" name="newPassword" class="form-control mb-3" placeholder="New Password" required>
        <input type="password" name="confirmPassword" class="form-control mb-4" placeholder="Confirm Password" required>
        <input type="hidden" name="action" value="changePassword"> <!-- Action for changing password -->
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </form>
    </div>

    <!-- Logout Button -->
    <form action="<%= request.getContextPath() %>/LogoutController" method="post" class="logout-form text-center mt-4">
      <button type="submit">Log Out</button>
    </form>
  </div>
  
    <!-- Footer -->
  <footer class="footer">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Footer Logo" class="footer-logo" />

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

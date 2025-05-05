<%
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
  
  /* Navbar Container */
.navbar {
  position: relative;
  display: flex;
  align-items: center;
  height: 90px;
  background-color: #3c3f45;
  padding: 0 2rem;
  border-radius: 5px;
}

/* Logo Section */
.navbar-left {
  flex: 1;
}

.logo {
  height: 120px;
  background-color: transparent;
}

/* Center Nav Links */
.nav-center {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 5rem;
}

.nav-center a {
  color: white;
  text-decoration: none;
  font-weight: 600;
  font-size: 1rem;
  transition: color 0.3s;
}

.nav-center a:hover {
  color: #7289DA;
}

/* Right Section */
.navbar-right {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
}

.search-bar {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
  font-weight: bold;
  color: #333;
}

.login-btn {
  background-color: #7289DA;
  border: none;
  padding: 0.5rem 1.2rem;
  border-radius: 6px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #4f56cc;
}
.logout-btn {
  background-color: #7289DA;
  border: none;
  padding: 0.5rem 1.2rem;
  border-radius: 6px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-btn:hover {
  background-color: #4f56cc;
}
  /* Footer */
.footer {
  background-color: #2a2c30;
  padding: 2rem 1rem;
  text-align: center;
  color: #ccc;
  border-top: 1px solid #444;
}

.footer-logo {
  height: 150px;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-bottom: 1rem;
}

.footer-links a {
  color: white;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s;
}

.footer-links a:hover {
  color: #7289DA;
}

.footer-description {
  max-width: 600px;
  margin: 0 auto 0.5rem;
  font-size: 0.9rem;
  color: #aaa;
}

.footer-copy {
  font-size: 0.85rem;
  color: #888;
}
  
  
  
  
  
  
    body {
      background-color: #1e2124;
      color: #fff;
      font-family: sans-serif;
    }

   .settings-box, .password-box {
      background-color: #2d2f33;
      border-radius: 10px;
      padding: 30px;
      margin: 20px auto 40px auto;
      max-width: 600px;
    }
    .profile-img {
      width: 150px;
      height: 150px;
      border-radius: 10px;
      object-fit: cover;
    }
    .btn-space {
      margin-right: 10px;
    }
    footer {
      text-align: center;
      color: #aaa;
      font-size: 0.85rem;
      margin-top: 30px;
    }
    footer img {
      height: 40px;
      margin-bottom: 10px;
    }

    footer {
      text-align: center;
      color: #ccc;
      font-size: 0.9rem;
      margin-top: 3rem;
    }

    footer a {
      color: #fff;
      margin: 0 10px;
      text-decoration: none;
    }

    footer a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<!-- Navbar Start -->
  <header class="navbar">
    <!-- Logo -->
    <div class="navbar-left">
      <img src="../resources/images/logo.png" alt="Logo" class="logo" />
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
      <button class="logout-btn">Log out</button>
    </div>
  </header>
  <!-- Account Info Section -->
  <div class="settings-box text-center">
    <div class="d-flex justify-content-center mb-3">
      <img src="../resources/images/eren.jpg" alt="Profile" class="profile-img me-3">
      <div class="text-start">
        <input type="text" class="form-control mb-2" placeholder="<%= username != null ? username : "" %>" value="">
         <input type="email" class="form-control mb-3" placeholder="<%= email != null ? email : "" %>" readonly>
        <div class="d-flex">
          <button class="btn btn-secondary btn-space">Edit Photo</button>
          <button class="btn btn-danger btn-space">Delete Photo</button>
          <button class="btn btn-primary">Save Changes</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Change Password Section -->
  <div class="password-box text-center">
    <h5 class="mb-4">Change Password</h5>
    <input type="password" class="form-control mb-3" placeholder="Current password">
    <input type="password" class="form-control mb-3" placeholder="New Password">
    <input type="password" class="form-control mb-4" placeholder="Confirm Password">
    <button class="btn btn-primary">Save Changes</button>
  </div>

  <!-- Footer -->
  <footer>
    <img src="../resources/images/logo.png" alt="Logo"><br>
    <a href="#" class="text-decoration-none text-light mx-2">Home</a>
    <a href="#" class="text-decoration-none text-light mx-2">Privacy</a>
    <a href="#" class="text-decoration-none text-light mx-2">Terms of Service</a>
    <p class="mt-2">Read. Track. Repeat. Manga made seamless – follow your favorites, pick up where you left off, and dive into new worlds anytime.</p>
    <p>Copyright © Book Choda Comic Padha</p>
  </footer>

</body>
</html>

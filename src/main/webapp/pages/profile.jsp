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

    .settings-box {
      background-color: #282b30;
      border: 1px solid #424549;
      border-radius: 10px;
      padding: 2rem;
      max-width: 400px;
      margin: 2rem auto;
    }

    .form-control {
      background-color: #36393e;
      border: none;
      color: #fff;
    }

    .form-control[readonly] {
      opacity: 0.7;
      cursor: not-allowed;
    }

    .form-control::placeholder {
      color: #ccc;
    }

    .btn-primary {
      background-color: #7289da;
      border: none;
      font-size: 0.9rem;
      padding: 6px 12px;
    }

    .btn-primary:hover {
      background-color: #5b6eae;
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
      <a href="#">Home</a>
      <a href="#">Bookmark</a>
      <a href="#">History</a>
      <a href="#">Random</a>
    </nav>

    <!-- Search and Login -->
    <div class="navbar-right">
      <input type="text" placeholder="Search" class="search-bar" />
      <button class="login-btn">Login</button>
    </div>
  </header>
  
  <!-- Navbar End -->

  <div class="settings-box">
    <h5 class="mb-3">Account Settings</h5>
    <input type="text" class="form-control mb-2" placeholder="<%= username != null ? username : "" %>" value="">
    <input type="email" class="form-control mb-3" placeholder="<%= email != null ? email : "" %>" readonly>
    <button class="btn btn-primary">Save changes</button>
  </div>

  <div class="settings-box">
    <h5 class="mb-3">Change Password</h5>
    <input type="password" class="form-control mb-2" placeholder="Current Password">
    <input type="password" class="form-control mb-2" placeholder="New Password">
    <input type="password" class="form-control mb-3" placeholder="Confirm Password">
    <button class="btn btn-primary">Update Password</button>
  </div>

 <!-- Footer -->
  <footer class="footer">
    <img src="../resources/images/logo.png" alt="Footer Logo" class="footer-logo" />

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

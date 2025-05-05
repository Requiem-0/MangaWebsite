<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookmark</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav_footer.css">
</head>
<style>
body {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #1f1f1f;
    color: #fff;
}

.navbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #111;
    padding: 10px 30px;
}

.logo {
    font-size: 20px;
    font-weight: bold;
    color: #f54c4c;
}
.logo span {
    color: orange;
}

.nav-links {
    list-style: none;
    display: flex;
    gap: 20px;
}
.nav-links li a {
    color: white;
    text-decoration: none;
}

.search-btn {
    padding: 5px 10px;
    border-radius: 4px;
    border: none;
}

.page-title {
    text-align: center;
    margin: 30px 0 10px;
}

.divider {
    height: 3px;
    background-color: #888;
    width: 80%;
    margin: 0 auto 30px;
}

.manga-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 30px;
    margin-bottom: 30px;
}
.manga-cover {
    width: 150px;
    height: auto;
    border: 2px solid #444;
    border-radius: 5px;
}

.pagination {
    text-align: center;
    margin-bottom: 50px;
}
.pagination button {
    margin: 0 10px;
    padding: 10px 20px;
    background-color: #444;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
.pagination button:hover {
    background-color: #666;
}

footer {
    text-align: center;
    background-color: #111;
    padding: 20px;
    color: #ccc;
}
.footer-logo {
    font-size: 18px;
    color: #f54c4c;
}
.footer-logo span {
    color: orange;
}
.footer-links {
    margin: 10px 0;
}
.footer-links a {
    margin: 0 10px;
    color: #bbb;
    text-decoration: none;
}
.footer-text {
    font-size: 12px;
    color: #999;
    margin: 10px auto;
    width: 60%;
}
.footer-copy {
    font-size: 12px;
    color: #777;
}


.logo img {
    height: 40px;
}</style>
<body>
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
    </div>
  </header>
  
  <!-- Navbar End -->

    <h2 class="page-title">Bookmark</h2>
    <div class="divider"></div>

    <div class="manga-container">
        <img src="../resources/images/berserk.png" class="manga-cover" alt="Berserk"/>
        <img src="../resources/images/onep.jpg" class="manga-cover" alt="One Piece"/>
        <img src="../resources/images/bleach.png" class="manga-cover" alt="Bleach"/>
        <img src="../resources/images/slam.png" class="manga-cover" alt="Slam Dunk"/>
    </div>

    <div class="pagination">
        <button>Previous</button>
        <button>Next</button>
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

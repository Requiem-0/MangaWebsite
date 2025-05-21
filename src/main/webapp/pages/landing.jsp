<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.manga.models.Manga" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Manga Reading - Free Manga Online</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav_footer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
</head>
<body>

  <!-- Navbar Start -->
  <header class="navbar">
    <!-- Logo -->
    <div class="navbar-left">
      <a href="${pageContext.request.contextPath}/pages/landing.jsp">
        <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo" class="logo" />
      </a>
    </div>

    <!-- Navigation Links (Centered) -->
    <nav class="nav-center">
      <a href="HomeMangaServlet">Home</a>
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

  <!-- Hero Section -->
  <section class="hero">
    <div class="hero-content">
      <h1>The best site to read manga online for Free</h1>
      <p>Enjoy a vast library of manga in high quality, with new releases updated daily.</p>
      <!-- Read Now Button -->
      <form action="${pageContext.request.contextPath}/HomeMangaServlet" method="get">
        <button type="submit" class="btn-primary">Read Now</button>
      </form>
    </div>
    <div class="hero-image">
      <img src="${pageContext.request.contextPath}/resources/images/landing.png" alt="Animation" class="animation-image" />
    </div>
  </section>

  <!-- Features Section -->
  <section class="features">
    <div class="feature-item">
      <h2>Safety</h2>
      <p>We ensure a safe reading experience with minimal ads and regular scans for harmful content.</p>
    </div>
    <div class="feature-item">
      <h2>Content Library</h2>
      <p>Discover a wide range of popular, classic, and current manga titles from various genres.</p>
    </div>
    <div class="feature-item">
      <h2>Quality</h2>
      <p>Read manga in high resolution with options to switch between different qualities.</p>
    </div>
    <div class="feature-item">
      <h2>Updates</h2>
      <p>New releases and requested content are added daily to keep you entertained.</p>
    </div>
    <div class="feature-item">
      <h2>User Interface</h2>
      <p>Our intuitive design makes it easy for everyone to navigate and find their favorite manga.</p>
    </div>
    <div class="feature-item">
      <h2>Device Compatibility</h2>
      <p>Enjoy reading manga on both mobile and desktop devices.</p>
    </div>
  </section>

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
<!DOCTYPE html> 
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Reading History</title>
  <link rel="stylesheet" href="../css/history.css" />
</head>

<body>
  <!-- Navbar Start -->
  <header class="navbar">
    <div class="navbar-left">
      <img src="../resources/images/logo.png" alt="Logo" class="logo" />
    </div>

    <nav class="nav-center">
      <a href="home.jsp">Home</a>
      <a href="#">Bookmark</a>
      <a href="history.jsp">History</a>
      <a href="#">Random</a>
    </nav>

    <div class="navbar-right">
      <input type="text" placeholder="Search" class="search-bar" />
      <button class="login-btn">Login</button>
    </div>
  </header>
  <!-- Navbar End -->

  <!-- Main Content -->
  <section id="mainContainer">
    <div class="container">
      <h1>Reading History</h1>

      <div class="card">
        <img src="../resources/images/jujutsu.jpg" alt="Manga Cover Image" />
        <div class="details">
          <h2>Isekai ni Kita Boku wa...</h2>
          <p><strong>Last Read</strong></p>
          <p>2 years ago</p>
          <a href="read.jsp?mangaId=1&chapter=1" class="continue-btn-hist">Continue</a>
        </div>
      </div>

      <div class="pagination">
        <button>&lt;</button>
        <span class="page-number">1</span>
        <button>&gt;</button>
      </div>
    </div>
  </section>

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

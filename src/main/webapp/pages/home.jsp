<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Comic</title>
  <link rel="stylesheet" href="../css/home.css" />
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
    </div>
  </header>
  
  <!-- Navbar End -->

  <!-- body suru vayo -->
  <div class="highest-rated-frame">
  
    <div class="highest-rated-text">
      <h2>Highest Rated</h2>
    </div>
    
    <div class="image-and-content-section">
      <div class="image-section">
        <img src="../resources/images/solo.jpeg" alt="Solo Leveling Poster" class="highest-rated-image" />
      </div>
      
      <div class="content-section">
        <h2>Solo Leveling</h2>
        <p class="description">
          E-class hunter Jinwoo Sung is the weakest of them all. Looked down on by everyone, <br>
          he has no money, no abilities to speak of, and no other job prospects. So when his party <br>
          finds a hidden dungeon, he's determined to use this chance to change his life...
        </p>
        <p><strong>Author:
        </strong><br> 추공 (Chugong)</p>
        
        <div class="genres">
          <p><strong>Genres:
          </strong><br> 
          <span>Action</span>
          <span>Adventure</span>
          <span>Fantasy</span>
          <span>Shounen</span></p>
        </div>
        
        <p><strong>Rating:</strong> 10/10</p>
        
        <button class="read-button">Read Now</button>
      </div>
    </div>
  </div>

  <div class="comics">
    <div class="comics-header">
      <h2>Comics</h2>

      <div class="filter-bar">
        <!-- Genre Filter -->
        <div class="filter-field">
          <label for="genre-select">Genre:</label>
          <select id="genre-select" class="filter-select">
            <option value="">All</option>
            <option value="Action">Action</option>
            <option value="Adventure">Adventure</option>
            <option value="Fantasy">Fantasy</option>
            <option value="Comedy">Comedy</option>
            <option value="Shounen">Shounen</option>
            <option value="Horror">Horror</option> 
            <option value="Supernatural">Supernatural</option>
            <option value="Sport">Sport</option>
            <option value="Isekai">Isekai</option>
            <option value="Romance">Romance</option>
            <option value="Thriller">Thriller</option>
          </select>
        </div>

        <!-- Sort By Filter -->
        <div class="filter-field">
          <label for="sort-select">Sort By:</label>
          <select id="sort-select" class="filter-select">
            <option value="">Default</option>
            <option value="hottest">Hottest</option>
            <option value="latest">Latest</option>
            <option value="alpha">A-Z</option>
          </select>
        </div>

        <!-- Apply Button -->
        <button id="applyFilter" class="btn-filter">Filter</button>
      </div>
    </div>
  </div>

  <div class="manga-grid">
    <div class="manga-card">
      <img src="../resources/images/bad.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">bad </div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/baki.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">baki</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/berserk.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">berserk</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/bleach.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">bleach</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/dragon.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">dragon</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/eren.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">eren</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/fullmetal.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">fullmetal</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/gintama.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">gintama</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/haikyuu.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">haikyuu</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/hunter.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">hunter</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/jojo.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">jojo</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/jujutsu.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">jujutsu</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/kingdom.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">kingdom</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/myhero.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">myhero</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/naruto.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">naruto</div>
    </div>

    <!-- Pagination buttons -->
    <div class="pagination">
      <button class="prev-btn">Previous</button>
      <button class="next-btn">Next</button>
    </div>
  </div>

  <div class="one-lanebar">
    <h2>Recommendation</h2>
  </div>

  <div class="manga-grid">
    <div class="manga-card">
      <img src="../resources/images/onep.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">onep</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/seven.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">seven</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/slam.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">slam</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/tail.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">tail</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/tanjiro.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">tanjiro</div>
    </div>
  </div>

  <div class="share-bar">
    <div class="share-info">
      <img src="../resources/images/logo.png" alt="Avatar">
      <div>
        <div class="share-title">Share Book Choda Comic Padha</div>
        <div class="share-subtitle">to your friends</div>
      </div>
    </div>

    <div class="share-buttons">
      <a href="#" class="btn telegram">Share</a>
      <a href="#" class="btn twitter">Tweet</a>
      <a href="#" class="btn facebook">Share</a>
      <a href="#" class="btn reddit">Share</a>
    </div>
  </div>

  <div class="one-lanebar">
    <h2>Completed</h2>
  </div>

  <div class="manga-grid">
    <div class="manga-card">
      <img src="../resources/images/tokyo.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">tokyo </div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/vega.png" alt="Manga Title" class="manga-image" />
      <div class="manga-title">vega</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/ghoul.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">ghoul</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/chain.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">chain</div>
    </div>
    <div class="manga-card">
      <img src="../resources/images/pokemon.jpg" alt="Manga Title" class="manga-image" />
      <div class="manga-title">pokemon</div>
    </div>
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
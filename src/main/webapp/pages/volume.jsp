<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Jujutsu Kaisen</title>
    <link rel="stylesheet" href="../css/volume.css" />
    <style>
      .nav-buttons {
        text-align: center;
        margin: 30px 0;
      }
      .nav-buttons a {
        padding: 10px 20px;
        border: 1px solid #fff;
        text-decoration: none;
        margin: 5px;
        display: inline-block;
        border-radius: 5px;
        color: #fff;
      }
      .nav-buttons a:hover,
      .nav-buttons a:focus {
        background: #9656ce;
        border-color: #9656ce;
        color: #fff;
      }
      .toggle-content {
        display: none;
      }
      .toggle-content:target {
        display: block;
      }
    </style>

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
  </head>
  <body>
    <div id="container">
      <div class="media-card">
        <img src="../resources/images/jujutsu.jpg" alt="Volume 1 Cover" class="cover-img" />
        <div class="card-details">
          <h2 class="main-title">Jujutsu Kaisen</h2>
          <div class="meta-info">
            <p><strong>Type:</strong> Manga</p>
            <p><strong>Status:</strong> Finished</p>
            <p><strong>Authors:</strong> Akutami, Gege (Story & Art)</p>
            <p><strong>Magazines:</strong> Shounen Jump (Weekly)</p>
            <p><strong>Published:</strong> Mar 5, 2018 to ?</p>
            <p><strong>Score:</strong> 8.52</p>
          </div>
          <div class="genres">
            <a href="#"><button>Action</button></a>
            <a href="#"><button>Demons</button></a>
            <a href="#"><button>Fantasy</button></a>
            <a href="#"><button>Shounen</button></a>
            <a href="#"><button>Supernatural</button></a>
          </div>
          <p class="desc">
            Hidden in plain sight, an age-old conflict rages on. Supernatural
            monsters known as "Curses"...
          </p>
          <a href="#" class="read-full">+ Read full</a>
        </div>
      </div>
    </div>

    <div class="nav-buttons">
      <a href="#Volume1">List Volume</a>
      <a href="#Chapters">List Chapter</a>
    </div>

    <section id="Volume1" class="section-p1 toggle-content">
      <h2 style="color: #9656ce">List Volume</h2>
      <div class="pro-container">
        <a href="chapter-list.html"
          ><div class="pro">
            <img src="../resources/images/vega.png" alt="Vol 1" />
            <div class="des"><h5>Vol 1</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/seven.png" alt="Vol 2" />
            <div class="des"><h5>Vol 2</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/myhero.png" alt="Vol 3" />
            <div class="des"><h5>Vol 3</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/eren.jpg" alt="Vol 4" />
            <div class="des"><h5>Vol 4</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/gintama.png" alt="Vol 5" />
            <div class="des"><h5>Vol 5</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/ghoul.jpg" alt="Vol 6" />
            <div class="des"><h5>Vol 6</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/fullmetal.jpg" alt="Vol 7" />
            <div class="des"><h5>Vol 7</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/eren.jpg" alt="Vol 8" />
            <div class="des"><h5>Vol 8</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/dragon.png" alt="Vol 9" />
            <div class="des"><h5>Vol 9</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/chain.jpg" alt="Vol 10" />
            <div class="des"><h5>Vol 10</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/bad.png" alt="Vol 11" />
            <div class="des"><h5>Vol 11</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/baki.png" alt="Vol 12" />
            <div class="des"><h5>Vol 12</h5></div>
          </div></a
        >
        <a href="#"
          ><div class="pro">
            <img src="../resources/images/berserk.png" alt="Vol 13" />
            <div class="des"><h5>Vol 13</h5></div>
          </div></a
        >
      </div>
    </section>

    <section id="Chapters" class="section-p1 toggle-content">
      <h2 style="color: #9656ce">List Chapter</h2>
      <div class="chapter-list">
        <div class="chapter-list">
          <div class="chapter-item">
            <a href="chapter.html?ch=284">Chapter 20: Domain Expansion</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=283">Chapter 19: The Cursed Womb</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=282">Chapter 18: Hidden Inventory</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=281">Chapter 17: Premature Death</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=280">Chapter 16: The Shibuya Incident</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=279">Chapter 15: Black Flash</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=278">Chapter 14: Divergent Fist</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=277"
              >Chapter 13: The Origin of Obedience</a
            >
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=276">Chapter 12: Idle Transfiguration</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=275">Chapter 11: The Night Parade</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=274"
              >Chapter 10: Kyoto Sister School Exchange</a
            >
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=273">Chapter 9: The Strongest</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=272"
              >Chapter 8: Cursed Spirit Manipulation</a
            >
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=7"
              >Chapter 7: The Origin of Blind Obedience</a
            >
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=6">Chapter 6: Fearsome Womb</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=5">Chapter 5: Hidden Potential</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=4">Chapter 4: A Reason to Fight</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=3">Chapter 3: Reunion</a>
          </div>
          <div class="chapter-item">
            <a href="chapter.html?ch=2">Chapter 2: Revenge</a>
          </div>
          <div class="chapter-item">
            <a href="https://mangareader.to/read/jujutsu-kaisen-168/en/volume-1"
              >Chapter 1: That Day</a
            >
          </div>
        </div>
        <!-- More chapters can be added here -->
      </div>
    </section>
    <!-- Footer -->
    <footer class="footer">
      <img src="../resources/images/logo.png" alt="Footer Logo" class="footer-logo" />

      <div class="footer-links">
        <a href="#">Home</a>
        <a href="#">Privacy</a>
        <a href="#">Terms of Service</a>
      </div>

      <p class="footer-description">
        Read. Track. Repeat. Manga made seamless – follow your favorites, pick
        up where you left off, and dive into new worlds anytime.
      </p>

      <p class="footer-copy">Copyright © Book Choda Comic Padha</p>
    </footer>
  </body>
</html>

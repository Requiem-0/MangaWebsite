CREATE DATABASE book_choda_comic_padha;

USE book_choda_comic_padha;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    role VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    profile_picture VARCHAR(255) DEFAULT NULL
);

CREATE TABLE manga (
    manga_id INT PRIMARY KEY AUTO_INCREMENT,
    mangatitle VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    mangadescription TEXT,
    status VARCHAR(50),
    published_date DATE,
    mangaImage VARCHAR(255)    
);

CREATE TABLE genre (
    genre_id INT PRIMARY KEY AUTO_INCREMENT,
    genrename VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE manga_genre (
    manga_id INT,
    genre_id INT,
    PRIMARY KEY (manga_id, genre_id),
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
);

CREATE TABLE volume (
    volume_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE,
    volumenumber INT NOT NULL,
    volume_img VARCHAR(255),
    manga_id INT,
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id) ON DELETE CASCADE
);

CREATE TABLE chapter (
    chapter_id INT PRIMARY KEY AUTO_INCREMENT,
    chapterno INT NOT NULL,
    chaptertitle VARCHAR(200),
    volume_id INT,
    chapter_pdf VARCHAR(255), 
    FOREIGN KEY (volume_id) REFERENCES volume(volume_id) ON DELETE CASCADE
);


DROP TABLE IF EXISTS reading_history;

CREATE TABLE reading_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    manga_id INT NOT NULL,
    last_read_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id) ON DELETE CASCADE
);
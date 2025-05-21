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
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id),
    FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
);

CREATE TABLE volume (
    volume_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE,
    volumenumber INT NOT NULL,
    volume_img VARCHAR(255),
    manga_id INT,
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id)
);


CREATE TABLE chapter (
    chapter_id INT PRIMARY KEY AUTO_INCREMENT,
    chapterno INT NOT NULL,
    chaptertitle VARCHAR(200),
    volume_id INT,
    chapter_pdf VARCHAR(255), 
    FOREIGN KEY (volume_id) REFERENCES volume(volume_id)
);


CREATE TABLE rating (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    rating_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    manga_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id)
);



CREATE TABLE user_manga (
    user_id INT,
    manga_id INT,
    PRIMARY KEY (user_id, manga_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id)
);

CREATE TABLE uploaded_pdf (
    id INT(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reading_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    manga_id INT NOT NULL UNIQUE,
    last_read_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id)
);

CREATE TABLE comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    manga_id INT NOT NULL,
    comment_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (manga_id) REFERENCES manga(manga_id)
);






package com.manga.models;

import java.util.List;

public class Manga {
    private int mangaId;
    private String title;
    private String author;
    private List<String> genres;
    private String status;
    private String publishedDate;
    private String description;
    
    public Manga() {
		// TODO Auto-generated constructor stub
	}
    
    public Manga(String title, String author, List<String> genres, String status, String publishedDate, String description) {
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    // Constructor for updating manga (with mangaId)
    public Manga(int mangaId, String title, String author, List<String> genres, String status, String publishedDate, String description) {
        this.mangaId = mangaId;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
    }

	// Getters and Setters for all fields (you already have these, don't forget to add them if not)
    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
}

public class Manga {
    private int mangaId;
    private String title;
    private String author;
    private String description;
    private String imageUrl;

    // Getters and Setters
    public int getMangaId() { return mangaId; }
    public void setMangaId(int mangaId) { this.mangaId = mangaId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}


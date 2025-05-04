package com.manga.models;

import java.util.List;

public class Manga {
    private int mangaId;
    private String title;
    private String author;
    private String status;
    private String publishedDate;
    private String description;
    private List<String> genres; // List of genre names

    // Default constructor
    public Manga() {}

    // Constructor without ID (for insert)
    public Manga(String title, String author, String status, String publishedDate, String description) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    // Getters and setters
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}

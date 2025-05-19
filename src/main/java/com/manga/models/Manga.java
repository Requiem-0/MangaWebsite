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
    private String mangaImage;  // 🆕 New field for image path or filename

    public Manga() {
        // Default constructor
    }

    // Constructor without mangaId (for insertion)
    public Manga(String title, String author, List<String> genres, String status, String publishedDate, String description, String mangaImage) {
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
        this.mangaImage = mangaImage;
    }

    // Constructor with mangaId (for update)
    public Manga(int mangaId, String title, String author, List<String> genres, String status, String publishedDate, String description, String mangaImage) {
        this.mangaId = mangaId;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
        this.mangaImage = mangaImage;
    }

    // Getters and Setters
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

    public String getMangaImage() {
        return mangaImage;
    }

    public void setMangaImage(String mangaImage) {
        this.mangaImage = mangaImage;
    }
}

package com.manga.models;

public class Bookmark {
    private int mangaId;
    private String mangaTitle;
    private String mangaImage;
    private String username;

    public Bookmark(int mangaId, String mangaTitle, String mangaImage, String username) {
        this.mangaId = mangaId;
        this.mangaTitle = mangaTitle;
        this.mangaImage = mangaImage;
        this.username = username;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public String getMangaTitle() {
        return mangaTitle;
    }

    public void setMangaTitle(String mangaTitle) {
        this.mangaTitle = mangaTitle;
    }

    public String getMangaImage() {
        return mangaImage;
    }

    public void setMangaImage(String mangaImage) {
        this.mangaImage = mangaImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

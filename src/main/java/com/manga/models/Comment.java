package com.manga.models;

import java.sql.Timestamp;

public class Comment {
    private int userId;
    private int mangaId;
    private String text;
    private String username;
    private Timestamp createdAt;

    // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getMangaId() { return mangaId; }
    public void setMangaId(int mangaId) { this.mangaId = mangaId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

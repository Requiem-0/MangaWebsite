package com.manga.models;

public class Bookmark {
    private int id;
    private int userId;
    private String volumeTitle;

    public Bookmark() {}

    public Bookmark(int id, int userId, String volumeTitle) {
        this.id = id;
        this.userId = userId;
        this.volumeTitle = volumeTitle;
    }

    public Bookmark(int userId, String volumeTitle) {
        this.userId = userId;
        this.volumeTitle = volumeTitle;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getVolumeTitle() { return volumeTitle; }
    public void setVolumeTitle(String volumeTitle) { this.volumeTitle = volumeTitle; }
}

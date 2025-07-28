package com.manga.models;

import java.sql.Timestamp;

public class Rating {
    private int ratingId;
    private int rating;
    private Timestamp ratingDate;
    private int userId;
    private int mangaId;

    // Getters and Setters
    public int getRatingId() { return ratingId; }
    public void setRatingId(int ratingId) { this.ratingId = ratingId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Timestamp getRatingDate() { return ratingDate; }
    public void setRatingDate(Timestamp ratingDate) { this.ratingDate = ratingDate; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getMangaId() { return mangaId; }
    public void setMangaId(int mangaId) { this.mangaId = mangaId; }
}

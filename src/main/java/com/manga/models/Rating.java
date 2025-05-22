package com.manga.models;

public class Rating {
    private int ratingId;
    private int rating;
    private int userId;
    private int mangaId;

    // Constructors
    public Rating() {}

    public Rating(int rating, int userId, int mangaId) {
        this.rating = rating;
        this.userId = userId;
        this.mangaId = mangaId;
    }

    // Full constructor
    public Rating(int ratingId, int rating, int userId, int mangaId) {
        this.ratingId = ratingId;
        this.rating = rating;
        this.userId = userId;
        this.mangaId = mangaId;
    }

    // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }
}

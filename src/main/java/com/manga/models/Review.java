package com.manga.models;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private int userId;
    private int mangaId;

    // Constructors
    public Review() {}

    public Review(int rating, String comment, int userId, int mangaId) {
        this.rating = rating;
        this.comment = comment;
        this.userId = userId;
        this.mangaId = mangaId;
    }

    // Full constructor
    public Review(int reviewId, int rating, String comment, Timestamp reviewDate, int userId, int mangaId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.userId = userId;
        this.mangaId = mangaId;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
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

package com.manga.controllers;

import com.manga.controllers.dao.ReviewDAO;

public class ReviewDAOTest {
    public static void main(String[] args) {
        ReviewDAO reviewDAO = new ReviewDAO();

        // Test: Count total reviews on the website
        int totalReviews = reviewDAO.getReviewCount();
        System.out.println("Total Reviews in the Website: " + totalReviews);
    }
}

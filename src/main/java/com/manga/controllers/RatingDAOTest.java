package com.manga.controllers;

import com.manga.controllers.dao.RatingDAO;
import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

public class RatingDAOTest {

    public static void main(String[] args) {
        MangaDAO mangaDAO = new MangaDAO();
        RatingDAO ratingDAO = new RatingDAO(mangaDAO);

        int testUserId = 1;
        int testMangaId = 1;

        // 1. Test submitOrUpdateRating - Insert
        boolean inserted = ratingDAO.submitOrUpdateRating(testUserId, testMangaId, 4);
        System.out.println("Insert Rating: " + (inserted ? "Success" : "Failed"));

        // 2. Test getUserRating after insert
        Integer rating = ratingDAO.getUserRating(testUserId, testMangaId);
        System.out.println("User Rating Retrieved: " + (rating != null ? rating : "No rating found"));

        // 3. Test submitOrUpdateRating - Update
        boolean updated = ratingDAO.submitOrUpdateRating(testUserId, testMangaId, 5);
        System.out.println("Update Rating: " + (updated ? "Success" : "Failed"));

        // Verify update
        Integer updatedRating = ratingDAO.getUserRating(testUserId, testMangaId);
        System.out.println("Updated User Rating: " + (updatedRating != null ? updatedRating : "No rating found"));

        // 4. Test getAverageRating with multiple ratings
        ratingDAO.submitOrUpdateRating(2, testMangaId, 3);
        ratingDAO.submitOrUpdateRating(3, testMangaId, 4);
        double avgRating = ratingDAO.getAverageRating(testMangaId);
        System.out.println("Average Rating for Manga ID " + testMangaId + ": " + avgRating);

        // 5. Test getUserRating for user without rating
        Integer noRating = ratingDAO.getUserRating(9999, testMangaId);
        System.out.println("Rating for non-existing user: " + (noRating != null ? noRating : "No rating found"));

        // 6. Test getHighestRatedManga
        Manga highestRated = ratingDAO.getHighestRatedManga();
        if (highestRated != null) {
            System.out.println("Highest Rated Manga ID: " + highestRated.getMangaId());
            System.out.println("Title: " + highestRated.getTitle());
        } else {
            System.out.println("No highest rated manga found.");
        }
    }
}

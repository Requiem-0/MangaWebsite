package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Rating;

import java.sql.*;

public class RatingDAO {

    public void addOrUpdateRating(Rating rating) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Check if rating already exists
            String checkSql = "SELECT rating_id FROM rating WHERE user_id = ? AND manga_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, rating.getUserId());
            pstmt.setInt(2, rating.getMangaId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                rs.close(); 
                pstmt.close();

                // Update rating
                String updateSql = "UPDATE rating SET rating = ?, rating_date = CURRENT_TIMESTAMP WHERE rating_id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, rating.getRating());
                pstmt.setInt(2, ratingId);
                pstmt.executeUpdate();

                System.out.println("Rating updated.");
            } else {
                rs.close();
                pstmt.close();

                // Insert new rating
                String insertSql = "INSERT INTO rating (rating, user_id, manga_id) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, rating.getRating());
                pstmt.setInt(2, rating.getUserId());
                pstmt.setInt(3, rating.getMangaId());
                pstmt.executeUpdate();

                System.out.println("New rating inserted.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("User ID: " + rating.getUserId());
        System.out.println("Manga ID: " + rating.getMangaId());
        System.out.println("Rating Value: " + rating.getRating());
    }
}

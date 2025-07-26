package com.manga.controllers.dao;

import com.manga.models.Rating;

import java.sql.*;

public class RatingDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mangadb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public void addOrUpdateRating(Rating rating) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Check if rating exists for this user and manga
            String checkSql = "SELECT rating_id FROM rating WHERE user_id = ? AND manga_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, rating.getUserId());
            pstmt.setInt(2, rating.getMangaId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Update existing rating
                int ratingId = rs.getInt("rating_id");
                String updateSql = "UPDATE rating SET rating = ? WHERE rating_id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, rating.getRating());
                pstmt.setInt(2, ratingId);
                pstmt.executeUpdate();
            } else {
                // Insert new rating
                String insertSql = "INSERT INTO rating (rating, user_id, manga_id) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, rating.getRating());
                pstmt.setInt(2, rating.getUserId());
                pstmt.setInt(3, rating.getMangaId());
                pstmt.executeUpdate();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        } finally {
            // Close connection and statement safely
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

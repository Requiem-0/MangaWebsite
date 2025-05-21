package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;

public class RatingDAO {

    private MangaDAO mangaDao;

    public RatingDAO(MangaDAO mangaDao) {
        this.mangaDao = mangaDao;
    }

    // 1. Insert or update rating for user & manga
    public boolean submitOrUpdateRating(int userId, int mangaId, int rating) {
        String sql = "INSERT INTO rating (rating, user_id, manga_id) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE rating = VALUES(rating)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rating);
            stmt.setInt(2, userId);
            stmt.setInt(3, mangaId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Get average rating for a manga
    public double getAverageRating(int mangaId) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM rating WHERE manga_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mangaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0; // no ratings yet
    }

    // 3. Get user's rating for a manga
    public Integer getUserRating(int userId, int mangaId) {
        String sql = "SELECT rating FROM rating WHERE user_id = ? AND manga_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("rating");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null; // user has not rated yet
    }

    // 4. Get manga with highest average rating
    public Manga getHighestRatedManga() {
        String sql = "SELECT manga_id, AVG(rating) AS avg_rating " +
                     "FROM rating GROUP BY manga_id ORDER BY avg_rating DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int mangaId = rs.getInt("manga_id");
                return mangaDao.getMangaById(mangaId);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}

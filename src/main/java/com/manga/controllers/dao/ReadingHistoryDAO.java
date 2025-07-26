package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.ReadingHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryDAO {

    public List<ReadingHistory.ReadingHistoryWithManga> getByUserIdWithMangaDetails(int userId) {
        List<ReadingHistory.ReadingHistoryWithManga> list = new ArrayList<>();

        String sql = "SELECT rh.id AS history_id, rh.last_read_date, " +
                     "m.manga_id, m.mangatitle, m.author, m.mangadescription, m.status, " +
                     "m.published_date, m.mangaImage " +
                     "FROM reading_history rh " +
                     "JOIN manga m ON rh.manga_id = m.manga_id " +
                     "WHERE rh.user_id = ? ORDER BY rh.last_read_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ReadingHistory.ReadingHistoryWithManga entry = new ReadingHistory.ReadingHistoryWithManga(
                        rs.getInt("history_id"),
                        rs.getTimestamp("last_read_date"),
                        rs.getInt("manga_id"),
                        rs.getString("mangatitle"),
                        rs.getString("author"),
                        rs.getString("mangadescription"),
                        rs.getString("status"),
                        rs.getDate("published_date"),
                        rs.getString("mangaimage")  
                    );
                    list.add(entry);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addOrUpdateReadingHistory(int userId, int mangaId) {
        String checkSql = "SELECT id FROM reading_history WHERE user_id = ? AND manga_id = ?";
        String updateSql = "UPDATE reading_history SET last_read_date = CURRENT_TIMESTAMP WHERE id = ?";
        String insertSql = "INSERT INTO reading_history (user_id, manga_id, last_read_date) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, mangaId);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int historyId = rs.getInt("id");
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, historyId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, mangaId);
                        insertStmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

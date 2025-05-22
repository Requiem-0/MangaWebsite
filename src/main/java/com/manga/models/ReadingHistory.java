package com.manga.models;

import com.manga.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadingHistory {

    // Inner class to represent reading history with manga details
    public static class ReadingHistoryWithManga {
        private int historyId;
        private Timestamp lastReadDate;
        private int mangaId;
        private String mangaTitle;
        private String author;
        private String description;
        private String status;
        private Date publishedDate;

        public ReadingHistoryWithManga(int historyId, Timestamp lastReadDate, int mangaId,
                                       String mangaTitle, String author, String description,
                                       String status, Date publishedDate) {
            this.historyId = historyId;
            this.lastReadDate = lastReadDate;
            this.mangaId = mangaId;
            this.mangaTitle = mangaTitle;
            this.author = author;
            this.description = description;
            this.status = status;
            this.publishedDate = publishedDate;
        }

        // Getters
        public int getHistoryId() { return historyId; }
        public Timestamp getLastReadDate() { return lastReadDate; }
        public int getMangaId() { return mangaId; }
        public String getMangaTitle() { return mangaTitle; }
        public String getAuthor() { return author; }
        public String getDescription() { return description; }
        public String getStatus() { return status; }
        public Date getPublishedDate() { return publishedDate; }
    }

    // Method to fetch reading history for a specific user with manga details
    public static List<ReadingHistoryWithManga> getByUserIdWithMangaDetails(int userId) {
        List<ReadingHistoryWithManga> list = new ArrayList<>();

        String sql = "SELECT rh.id AS history_id, rh.last_read_date, " +
                     "m.manga_id, m.mangatitle, m.author, m.mangadescription, m.status, m.published_date " +
                     "FROM reading_history rh " +
                     "JOIN manga m ON rh.manga_id = m.manga_id " +
                     "WHERE rh.user_id = ? " +
                     "ORDER BY rh.last_read_date DESC"; // Sort by most recent read date

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ReadingHistoryWithManga entry = new ReadingHistoryWithManga(
                        rs.getInt("history_id"),
                        rs.getTimestamp("last_read_date"),
                        rs.getInt("manga_id"),
                        rs.getString("mangatitle"),
                        rs.getString("author"),
                        rs.getString("mangadescription"),
                        rs.getString("status"),
                        rs.getDate("published_date")
                    );
                    list.add(entry);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    // New method to add or update reading history
    public static void addOrUpdateReadingHistory(int userId, int mangaId) {
        String checkSql = "SELECT id FROM reading_history WHERE user_id = ? AND manga_id = ?";
        String updateSql = "UPDATE reading_history SET last_read_date = CURRENT_TIMESTAMP WHERE id = ?";
        String insertSql = "INSERT INTO reading_history (user_id, manga_id, last_read_date) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, mangaId);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Exists, update the last_read_date
                    int historyId = rs.getInt("id");
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, historyId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Does not exist, insert new record
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

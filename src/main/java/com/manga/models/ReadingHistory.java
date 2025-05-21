package com.manga.models;

import com.manga.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadingHistory {
	
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

        public int getHistoryId() { return historyId; }
        public Timestamp getLastReadDate() { return lastReadDate; }
        public int getMangaId() { return mangaId; }
        public String getMangaTitle() { return mangaTitle; }
        public String getAuthor() { return author; }
        public String getDescription() { return description; }
        public String getStatus() { return status; }
        public Date getPublishedDate() { return publishedDate; }
    }

    //access data with pagination
    public static List<ReadingHistoryWithManga> getPaginatedWithMangaDetails(int offset, int limit) {
        List<ReadingHistoryWithManga> list = new ArrayList<>();
        String sql = "SELECT rh.id AS history_id, rh.last_read_date, " +
                     "m.manga_id, m.mangatitle, m.author, m.mangadescription, m.status, m.published_date " +
                     "FROM reading_history rh " +
                     "JOIN manga m ON rh.manga_id = m.manga_id " +
                     "ORDER BY rh.last_read_date DESC LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new ReadingHistoryWithManga(
                    rs.getInt("history_id"),
                    rs.getTimestamp("last_read_date"),
                    rs.getInt("manga_id"),
                    rs.getString("mangatitle"),
                    rs.getString("author"),
                    rs.getString("mangadescription"),
                    rs.getString("status"),
                    rs.getDate("published_date")
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM reading_history";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //when a user visits a specific manga, call this method everytime to update the reading history of this book
    public static void createOrUpdateHistory(int mangaId) {
        String selectSQL = "SELECT id FROM reading_history WHERE manga_id = ?";
        String insertSQL = "INSERT INTO reading_history (manga_id) VALUES (?)";
        String updateSQL = "UPDATE reading_history SET last_read_date = CURRENT_TIMESTAMP WHERE manga_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {

            selectStmt.setInt(1, mangaId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Exists: update last_read_date
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setInt(1, mangaId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Does not exist: insert new record
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    insertStmt.setInt(1, mangaId);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

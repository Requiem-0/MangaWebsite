package com.manga.controllers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.manga.database.DatabaseConnection;

public class BookmarkDAO {
    private Connection connection;

    public BookmarkDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addBookmark(int userId, int mangaId) {
        String sql = "INSERT IGNORE INTO bookmarks (user_id, manga_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBookmark(int userId, int mangaId) {
        String sql = "DELETE FROM bookmarks WHERE user_id = ? AND manga_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBookmarked(int userId, int mangaId) {
        String sql = "SELECT id FROM bookmarks WHERE user_id = ? AND manga_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

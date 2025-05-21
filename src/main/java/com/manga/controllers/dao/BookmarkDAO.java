
package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDAO {

    public List<Bookmark> getBookmarksByUserId(int userId) throws ClassNotFoundException {
        List<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT b.manga_id, m.manga_title, m.manga_image, u.username FROM bookmark b " +
                     "JOIN manga m ON b.manga_id = m.manga_id " +
                     "JOIN user u ON b.user_id = u.user_id " +
                     "WHERE b.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bookmark b = new Bookmark(userId, sql, sql, sql);
                b.setMangaId(rs.getInt("manga_id"));
                b.setMangaTitle(rs.getString("manga_title"));
                b.setMangaImage(rs.getString("manga_image"));
                b.setUsername(rs.getString("username"));
                bookmarks.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    public void addBookmark(int userId, int mangaId) throws ClassNotFoundException {
        String sql = "INSERT INTO bookmark (user_id, manga_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE user_id = user_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBookmark(int userId, int mangaId) throws ClassNotFoundException {
        String sql = "DELETE FROM bookmark WHERE user_id = ? AND manga_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

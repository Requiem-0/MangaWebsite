package com.manga.controllers.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.manga.models.Manga;
import com.manga.database.DatabaseConnection;

public class FavouriteDAO {

    public boolean addFavorite(int userId, int mangaId) {
        String sql = "INSERT IGNORE INTO user_manga (user_id, manga_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(int userId, int mangaId) {
        String sql = "DELETE FROM user_manga WHERE user_id = ? AND manga_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Manga> getFavorites(int userId) {
        List<Manga> favs = new ArrayList<>();
        String sql = "SELECT m.* FROM manga m JOIN user_manga um ON m.manga_id = um.manga_id WHERE um.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Manga m = new Manga();
                m.setMangaId(rs.getInt("manga_id"));
                m.setTitle(rs.getString("mangatitle"));
                m.setAuthor(rs.getString("author"));
                m.setDescription(rs.getString("mangadescription"));
                m.setStatus(rs.getString("status"));
                m.setPublishedDate(rs.getString("published_date"));
                m.setMangaImage(rs.getString("mangaImage"));
                favs.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favs;
    }

    public boolean isFavorite(int userId, int mangaId) {
        String sql = "SELECT * FROM user_manga WHERE user_id = ? AND manga_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mangaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.manga.controllers.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.manga.models.Comment;
import com.manga.database.DatabaseConnection;

public class CommentDAO {
    public boolean addComment(Comment comment) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO comment (user_id, manga_id, comment_text) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, comment.getUserId());
            stmt.setInt(2, comment.getMangaId());
            stmt.setString(3, comment.getText());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getCommentsByMangaId(int mangaId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT c.comment_text, c.created_at, u.username FROM comment c JOIN users u ON c.user_id = u.user_id WHERE c.manga_id = ? ORDER BY c.created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mangaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setText(rs.getString("comment_text"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comment.setUsername(rs.getString("username"));
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }
}

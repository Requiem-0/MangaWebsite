package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Chapter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {

    public boolean addChapter(Chapter chapter) {
        if (chapter == null || chapter.getChapterno() <= 0 || chapter.getVolumeId() <= 0) {
            System.out.println("Invalid chapter data.");
            return false;
        }

        boolean isAdded = false;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO chapter (chapterno, chaptertitle, volume_id, chapter_pdf) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, chapter.getChapterno());
            stmt.setString(2, chapter.getChaptertitle());
            stmt.setInt(3, chapter.getVolumeId());
            stmt.setString(4, chapter.getChapter_pdf());

            int rows = stmt.executeUpdate();
            isAdded = rows > 0;

        } catch (SQLException e) {
            System.out.println("SQL Error while adding chapter: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Database driver not found.");
        }

        return isAdded;
    }

    public boolean deleteChapter(int chapterId) {
        if (chapterId <= 0) {
            System.out.println("Invalid chapter ID.");
            return false;
        }

        boolean isDeleted = false;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM chapter WHERE chapter_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, chapterId);

            int rows = stmt.executeUpdate();
            isDeleted = rows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error deleting chapter: " + e.getMessage());
        }

        return isDeleted;
    }

    public List<Chapter> getAllChapters() {
        List<Chapter> chapterList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM chapter";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Chapter c = new Chapter();
                c.setChapterId(rs.getInt("chapter_id"));
                c.setChapterno(rs.getInt("chapterno"));
                c.setChaptertitle(rs.getString("chaptertitle"));
                c.setVolumeId(rs.getInt("volume_id"));
                c.setChapter_pdf(rs.getString("chapter_pdf"));

                chapterList.add(c);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error fetching chapters: " + e.getMessage());
        }

        return chapterList;
    }

    public List<Chapter> getChaptersByVolumeId(int volumeId) {
        List<Chapter> chapterList = new ArrayList<>();

        if (volumeId <= 0) {
            System.out.println("Invalid volume ID.");
            return chapterList;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM chapter WHERE volume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, volumeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Chapter c = new Chapter();
                c.setChapterId(rs.getInt("chapter_id"));
                c.setChapterno(rs.getInt("chapterno"));
                c.setChaptertitle(rs.getString("chaptertitle"));
                c.setVolumeId(rs.getInt("volume_id"));
                c.setChapter_pdf(rs.getString("chapter_pdf"));

                chapterList.add(c);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error fetching chapters by volume ID: " + e.getMessage());
        }

        return chapterList;
    }
}

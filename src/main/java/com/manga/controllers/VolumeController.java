package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;

import com.manga.controllers.dao.ReadingHistoryDAO;
import com.manga.database.DatabaseConnection;
import com.manga.models.Chapter;
import com.manga.models.Manga;
import com.manga.models.Volume;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/volume")
public class VolumeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ERROR_PAGE = "/pages/error.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mangaIdParam = request.getParameter("manga_id");
        int mangaId = 1;

        if (mangaIdParam != null && !mangaIdParam.trim().isEmpty()) {
            try {
                mangaId = Integer.parseInt(mangaIdParam);
            } catch (NumberFormatException e) {
                System.out.println("Invalid manga ID provided. Defaulting to manga ID 1.");
            }
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            HttpSession session = request.getSession(false);
            Integer userId = (session != null) ? (Integer) session.getAttribute("user_id") : null;

            if (userId != null) {
                ReadingHistoryDAO historyDAO = new ReadingHistoryDAO();
                historyDAO.addOrUpdateReadingHistory(userId, mangaId);
            }

            conn = DatabaseConnection.getConnection();

            // Fetch manga details
            String mangaQuery = "SELECT manga_id, mangatitle, author, mangaImage, mangadescription, status, published_date FROM manga WHERE manga_id = ?";
            pstmt = conn.prepareStatement(mangaQuery);
            pstmt.setInt(1, mangaId);
            rs = pstmt.executeQuery();

            Manga manga = null;
            if (rs.next()) {
                manga = new Manga();
                manga.setMangaId(rs.getInt("manga_id"));
                manga.setTitle(rs.getString("mangatitle"));
                manga.setAuthor(rs.getString("author"));
                manga.setMangaImage(rs.getString("mangaImage"));
                manga.setDescription(rs.getString("mangadescription"));
                manga.setStatus(rs.getString("status"));
                manga.setPublishedDate(rs.getString("published_date"));
            }
            request.setAttribute("manga", manga);
            closeQuietly(rs, pstmt);

            // Fetch genres
            String genreQuery = "SELECT g.genrename FROM genre g JOIN manga_genre mg ON g.genre_id = mg.genre_id WHERE mg.manga_id = ?";
            pstmt = conn.prepareStatement(genreQuery);
            pstmt.setInt(1, mangaId);
            rs = pstmt.executeQuery();

            List<String> genres = new ArrayList<>();
            while (rs.next()) {
                genres.add(rs.getString("genrename"));
            }
            request.setAttribute("genres", genres);
            closeQuietly(rs, pstmt);

            // Fetch volumes
            String volumeQuery = "SELECT volume_id, volumenumber, volume_img FROM volume WHERE manga_id = ? ORDER BY volumenumber";
            pstmt = conn.prepareStatement(volumeQuery);
            pstmt.setInt(1, mangaId);
            rs = pstmt.executeQuery();

            List<Volume> volumes = new ArrayList<>();
            while (rs.next()) {
                Volume volume = new Volume();
                volume.setVolumeId(rs.getInt("volume_id"));
                volume.setMangaId(mangaId);
                volume.setVolumeNumber(rs.getInt("volumenumber"));
                volume.setVolume_img(rs.getString("volume_img"));
                volumes.add(volume);
            }
            request.setAttribute("volumes", volumes);
            closeQuietly(rs, pstmt);

            // Fetch chapters for selected volume (if any)
            String selectedVolumeParam = request.getParameter("volume");
            if (selectedVolumeParam != null && !selectedVolumeParam.trim().isEmpty()) {
                try {
                    int selectedVolume = Integer.parseInt(selectedVolumeParam);
                    request.setAttribute("selectedVolume", selectedVolume);

                    int volumeId = -1;
                    String getVolumeIdQuery = "SELECT volume_id FROM volume WHERE manga_id = ? AND volumenumber = ?";
                    pstmt = conn.prepareStatement(getVolumeIdQuery);
                    pstmt.setInt(1, mangaId);
                    pstmt.setInt(2, selectedVolume);
                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        volumeId = rs.getInt("volume_id");
                    }
                    closeQuietly(rs, pstmt);

                    if (volumeId != -1) {
                        String chapterQuery = "SELECT chapterno, chaptertitle FROM chapter WHERE volume_id = ? ORDER BY chapterno";
                        pstmt = conn.prepareStatement(chapterQuery);
                        pstmt.setInt(1, volumeId);
                        rs = pstmt.executeQuery();

                        List<Chapter> chapters = new ArrayList<>();
                        while (rs.next()) {
                            Chapter chapter = new Chapter();
                            chapter.setChapterno(rs.getInt("chapterno"));
                            chapter.setChaptertitle(rs.getString("chaptertitle"));
                            chapters.add(chapter);
                        }
                        request.setAttribute("chapters", chapters);
                        closeQuietly(rs, pstmt);
                    }

                } catch (NumberFormatException ignored) {}
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading manga page: " + e.getMessage());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        } finally {
            closeQuietly(rs, pstmt);
            if (conn != null) try { conn.close(); } catch (Exception ignored) {}
        }

        request.getRequestDispatcher("/pages/volume.jsp").forward(request, response);
    }

    private void closeQuietly(ResultSet rs, PreparedStatement pstmt) {
        if (rs != null) try { rs.close(); } catch (Exception ignored) {}
        if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
    }
}

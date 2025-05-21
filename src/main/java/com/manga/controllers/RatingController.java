package com.manga.controllers;

import com.manga.controllers.dao.RatingDAO;
import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RatingController")
public class RatingController extends HttpServlet {

    private RatingDAO ratingDAO;
    private MangaDAO mangaDAO;

    @Override
    public void init() {
        mangaDAO = new MangaDAO();
        ratingDAO = new RatingDAO(mangaDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Example: show average rating & user rating for a manga
        String mangaIdStr = request.getParameter("mangaId");
        String userIdStr = request.getParameter("userId");

        if (mangaIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Manga ID is required");
            return;
        }

        int mangaId = Integer.parseInt(mangaIdStr);
        Integer userId = (userIdStr != null) ? Integer.parseInt(userIdStr) : null;

        double avgRating = ratingDAO.getAverageRating(mangaId);
        Integer userRating = null;
        if (userId != null) {
            userRating = ratingDAO.getUserRating(userId, mangaId);
        }

        Manga manga = mangaDAO.getMangaById(mangaId);

        request.setAttribute("avgRating", avgRating);
        request.setAttribute("userRating", userRating);
        request.setAttribute("manga", manga);

        request.getRequestDispatcher("/pages/rating.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle submitting or updating rating

        String mangaIdStr = request.getParameter("mangaId");
        String userIdStr = request.getParameter("userId");
        String ratingStr = request.getParameter("rating");

        if (mangaIdStr == null || userIdStr == null || ratingStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Manga ID, User ID, and Rating are required");
            return;
        }

        int mangaId = Integer.parseInt(mangaIdStr);
        int userId = Integer.parseInt(userIdStr);
        int rating = Integer.parseInt(ratingStr);

        boolean success = ratingDAO.submitOrUpdateRating(userId, mangaId, rating);

        if (success) {
            request.setAttribute("message", "Rating submitted successfully!");
        } else {
            request.setAttribute("error", "Failed to submit rating.");
        }

        // Redirect or forward back to GET to show updated info
        response.sendRedirect(request.getContextPath() + "/ManageRatingController?mangaId=" + mangaId + "&userId=" + userId);
    }
}

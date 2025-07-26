package com.manga.controllers;

import com.manga.models.Rating;
import com.manga.controllers.dao.RatingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RatingController")
public class RatingController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int mangaId = Integer.parseInt(request.getParameter("mangaId"));
        int userId = Integer.parseInt(request.getParameter("userId")); // or use session
        int ratingValue = Integer.parseInt(request.getParameter("rating"));

        Rating rating = new Rating();
        rating.setMangaId(mangaId);
        rating.setUserId(userId);
        rating.setRating(ratingValue);

        RatingDAO ratingDAO = new RatingDAO();
        ratingDAO.addOrUpdateRating(rating);

        response.sendRedirect("volume.jsp?manga_id=" + mangaId);
    }
}


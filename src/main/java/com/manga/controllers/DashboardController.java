package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.controllers.dao.UserDAO;
import com.manga.controllers.dao.ReviewDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {

    private MangaDAO mangaDAO;
    private UserDAO userDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        mangaDAO = new MangaDAO();
        userDAO = new UserDAO();
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int mangaCount = mangaDAO.getMangaCount();
            int genreCount = mangaDAO.getGenreCount();
            int userCount = userDAO.countUser();
            int reviewCount = reviewDAO.getReviewCount();

            // DEBUG: Print to console/server logs
            System.out.println("Manga Count: " + mangaCount);
            System.out.println("Genre Count: " + genreCount);
            System.out.println("User Count: " + userCount);
            System.out.println("Review Count: " + reviewCount);

            request.setAttribute("mangaCount", mangaCount);
            request.setAttribute("genreCount", genreCount);
            request.setAttribute("userCount", userCount);
            request.setAttribute("reviewCount", reviewCount);

            request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("DashboardController Error:");
            e.printStackTrace();

            request.setAttribute("error", "Unable to load dashboard data.");
            request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
        }
    }
}

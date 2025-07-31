package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.controllers.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {

    private MangaDAO mangaDAO;
    private UserDAO userDAO;


    @Override
    public void init() {
        mangaDAO = new MangaDAO();
        userDAO = new UserDAO();
 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int mangaCount = mangaDAO.getMangaCount();
            int genreCount = mangaDAO.getGenreCount();
            int userCount = userDAO.countUser();
      

            // DEBUG: Print to console/server logs
            System.out.println("Manga Count: " + mangaCount);
            System.out.println("Genre Count: " + genreCount);
            System.out.println("User Count: " + userCount);


            request.setAttribute("mangaCount", mangaCount);
            request.setAttribute("genreCount", genreCount);
            request.setAttribute("userCount", userCount);
        

            request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("DashboardController Error:");
            e.printStackTrace();

            request.setAttribute("error", "Unable to load dashboard data.");
            request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
        }
    }
}

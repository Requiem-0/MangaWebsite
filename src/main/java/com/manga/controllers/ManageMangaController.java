package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ManageMangaController")
public class ManageMangaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles GET requests — like showing all manga
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MangaDAO mangaDAO = new MangaDAO();
        List<Manga> mangaList = mangaDAO.getAllManga(); // Get all manga from DB
        request.setAttribute("mangaList", mangaList);   // Send the list to JSP
        request.getRequestDispatcher("/pages/manageManga.jsp").forward(request, response);
    }

    // Handles POST requests — like adding, updating, deleting manga
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the action from the form
        String action = request.getParameter("action");
        MangaDAO mangaDAO = new MangaDAO();

        if ("add".equals(action)) {
            // Add new manga
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String[] genresArray = request.getParameterValues("genres");
            String status = request.getParameter("status");
            String publishedDate = request.getParameter("publishedDate");
            String description = request.getParameter("description");

            List<String> genres = genresArray != null ? Arrays.asList(genresArray) : null;

            Manga manga = new Manga(title, author, genres, status, publishedDate, description);
            mangaDAO.addManga(manga);

        } else if ("update".equals(action)) {
            // Update manga
            int mangaId = Integer.parseInt(request.getParameter("mangaId"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String[] genresArray = request.getParameterValues("genres");
            String status = request.getParameter("status");
            String publishedDate = request.getParameter("publishedDate");
            String description = request.getParameter("description");

            List<String> genres = genresArray != null ? Arrays.asList(genresArray) : null;

            Manga manga = new Manga(mangaId, title, author, genres, status, publishedDate, description);
            mangaDAO.editManga(manga);

        } else if ("delete".equals(action)) {
            // Delete manga
            int mangaId = Integer.parseInt(request.getParameter("mangaId"));
            mangaDAO.deleteManga(mangaId);
        }

        // After any action, refresh the manga list page
        response.sendRedirect("ManageMangaController");
    }
}

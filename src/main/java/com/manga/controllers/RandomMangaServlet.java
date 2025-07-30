package com.manga.controllers;

import com.manga.controllers.dao.HomeMangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RandomMangaServlet")
public class RandomMangaServlet extends HttpServlet {
    private HomeMangaDAO homeMangaDAO;

    @Override
    public void init() {
        homeMangaDAO = new HomeMangaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Manga randomManga = homeMangaDAO.getRandomManga();
        if (randomManga != null) {
            // Redirect to the volume page with the manga ID
            response.sendRedirect(request.getContextPath() + "/pages/volume.jsp?manga_id=" + randomManga.getMangaId());
        } else {
            // Handle the case where no manga is found
            response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
        }
    }
}
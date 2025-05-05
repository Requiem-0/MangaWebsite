package com.manga.controllers;

import com.manga.controllers.dao.HomeMangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/HomeMangaServlet")
public class HomeMangaServlet extends HttpServlet {
    private HomeMangaDAO homeMangaDAO;

    @Override
    public void init() {
        homeMangaDAO = new HomeMangaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int recordsPerPage = 15;
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * recordsPerPage;

        List<Manga> mangaList = homeMangaDAO.getHomeManga(offset, recordsPerPage);
        int totalMangaCount = homeMangaDAO.getTotalMangaCount();
        int totalPages = (int) Math.ceil((double) totalMangaCount / recordsPerPage);

        request.setAttribute("mangaList", mangaList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);

        request.getRequestDispatcher("/pages/home.jsp").forward(request, response);
    }
}
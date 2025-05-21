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

        String genre = request.getParameter("genre");
        String sortBy = request.getParameter("sort");
        String searchTerm = request.getParameter("search");

        List<Manga> mangaList;
        int totalMangaCount;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            mangaList = homeMangaDAO.searchManga(searchTerm);
            totalMangaCount = mangaList.size();
        } else {
            mangaList = homeMangaDAO.getHomeManga(offset, recordsPerPage, genre, sortBy);
            totalMangaCount = homeMangaDAO.getTotalMangaCount(genre);
        }

        int totalPages = (int) Math.ceil((double) totalMangaCount / recordsPerPage);

        // Fetch ongoing manga
        List<Manga> ongoingMangaList = homeMangaDAO.getOngoingManga(10); // Limit to 10 for the ongoing section

        request.setAttribute("mangaList", mangaList);
        request.setAttribute("ongoingMangaList", ongoingMangaList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("genre", genre != null ? genre : "");
        request.setAttribute("sort", sortBy != null ? sortBy : "");
        request.setAttribute("searchTerm", searchTerm != null ? searchTerm : "");

        request.getRequestDispatcher("/pages/home.jsp").forward(request, response);
    }
}
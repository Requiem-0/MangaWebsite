package com.manga.controllers;

import com.manga.models.ReadingHistory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadingHistory")
public class ReadingHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<ReadingHistory.ReadingHistoryWithManga> historyList = ReadingHistory.getPaginatedWithMangaDetails((page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = ReadingHistory.getTotalCount();
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        request.setAttribute("historyList", historyList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/pages/history.jsp").forward(request, response);
    }
}

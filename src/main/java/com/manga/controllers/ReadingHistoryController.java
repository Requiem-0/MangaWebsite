package com.manga.controllers;

import com.manga.controllers.dao.ReadingHistoryDAO;
import com.manga.models.ReadingHistory;
import com.manga.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReadingHistory")
public class ReadingHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ReadingHistoryDAO readingHistoryDAO;

    public void init() {
        readingHistoryDAO = new ReadingHistoryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }

        User loggedInUser = (User) session.getAttribute("user");
        List<ReadingHistory.ReadingHistoryWithManga> historyList =
            readingHistoryDAO.getByUserIdWithMangaDetails(
            		//loggedInUser.getUserId()
            		1
            		);
        
        for (ReadingHistory.ReadingHistoryWithManga history : historyList) {
            System.out.println("Manga Title: " + history.getMangaTitle());
            System.out.println("Manga Image: " + history.getMangaImage());
        }

        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("/pages/history.jsp").forward(request, response);
    }
}

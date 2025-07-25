package com.manga.controllers;

import com.manga.controllers.dao.FavouriteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FavouriteServlet")
public class FavouriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FavouriteDAO favouriteDAO;

    public FavouriteServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        favouriteDAO = new FavouriteDAO(); // ✅ initialize the DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int mangaId = Integer.parseInt(request.getParameter("mangaId"));
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp"); // or show error
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        boolean success = false;

        if ("add".equalsIgnoreCase(action)) {
            success = favouriteDAO.addFavorite(userId, mangaId);
        } else if ("remove".equalsIgnoreCase(action)) {
            success = favouriteDAO.removeFavorite(userId, mangaId);
        }

        response.sendRedirect("manga-details.jsp?mangaId=" + mangaId + "&fav=" + success);
    }
}

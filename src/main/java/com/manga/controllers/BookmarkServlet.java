package com.manga.controllers;

import com.manga.controllers.dao.BookmarkDAO;
import com.manga.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/BookmarkServlet")
public class BookmarkServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int mangaId = Integer.parseInt(request.getParameter("mangaId"));
        String action = request.getParameter("action");

        BookmarkDAO bookmarkDAO = new BookmarkDAO();
        if ("add".equalsIgnoreCase(action)) {
            bookmarkDAO.addBookmark(user.getUserId(), mangaId);
        } else if ("remove".equalsIgnoreCase(action)) {
            bookmarkDAO.removeBookmark(user.getUserId(), mangaId);
        }

        response.sendRedirect("MangaDetailsServlet?id=" + mangaId); // Change this if needed
    }
}

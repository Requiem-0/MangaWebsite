
package com.manga.controllers;

import com.manga.controllers.dao.BookmarkDAO;
import com.manga.models.Bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/BookmarkServlet")
public class BookmarkServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookmarkDAO bookmarkDAO;

    @Override
    public void init() throws ServletException {
        bookmarkDAO = new BookmarkDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Handle bookmark deletion
        String removeMangaId = request.getParameter("remove");
        if (removeMangaId != null) {
            int mangaId = Integer.parseInt(removeMangaId);
            bookmarkDAO.removeBookmark(userId, mangaId);
            response.sendRedirect("BookmarkServlet");
            return;
        }

        List<Bookmark> bookmarks = bookmarkDAO.getBookmarksByUserId(userId);
        request.setAttribute("bookmarks", bookmarks);
        request.getRequestDispatcher("/pages/bookmark.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int mangaId = Integer.parseInt(request.getParameter("mangaId"));
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            bookmarkDAO.addBookmark(userId, mangaId);
        } else if ("remove".equals(action)) {
            bookmarkDAO.removeBookmark(userId, mangaId);
        }

        response.sendRedirect("BookmarkServlet");
    }
}

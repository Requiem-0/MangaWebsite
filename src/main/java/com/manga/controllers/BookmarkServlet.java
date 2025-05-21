package com.manga.controllers;

import com.manga.controllers.dao.BookmarkDAO;
import com.manga.models.Bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookmark")
public class BookmarkServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookmarkDAO bookmarkDAO = new BookmarkDAO();
        List<Bookmark> bookmarks = bookmarkDAO.getAllBookmarks();

        request.setAttribute("bookmarks", bookmarks);
        request.getRequestDispatcher("/pages/bookmark.jsp").forward(request, response);
    }
}

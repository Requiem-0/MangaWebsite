package com.manga.controllers;

import com.manga.controllers.dao.CommentDAO;
import com.manga.models.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/addComment")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute("user_id");
        int mangaId = Integer.parseInt(request.getParameter("manga_id"));
        String commentText = request.getParameter("comment");

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setMangaId(mangaId);
        comment.setText(commentText);

        CommentDAO dao = new CommentDAO();
        dao.addComment(comment);

        response.sendRedirect("volume.jsp?manga_id=" + mangaId + "#commentSection");
    }
}

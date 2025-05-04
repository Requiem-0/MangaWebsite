package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ManageMangaController")
public class ManageMangaController extends HttpServlet {
    private MangaDAO mangaDAO;

    @Override
    public void init() {
        mangaDAO = new MangaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listManga(request, response);
            } else if (action.equals("deleteManga")) {
                deleteManga(request, response);
            } else if (action.equals("editManga")) {
                // optional: forward to edit form page
            } else {
                listManga(request, response); // default
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong.");
            request.getRequestDispatcher("admin/manageManga.jsp").forward(request, response);
        }
    }

    private void listManga(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Manga> mangaList = mangaDAO.getAllManga();
        request.setAttribute("mangaList", mangaList);
        request.getRequestDispatcher("admin/manageManga.jsp").forward(request, response);
    }

    private void deleteManga(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("mangaId"));
        mangaDAO.deleteManga(id);
        response.sendRedirect("ManageMangaController?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	    String title = request.getParameter("title");
    	    String author = request.getParameter("author");
    	    String genre = request.getParameter("genre"); // from input field
    	    List<String> genres = new ArrayList<>();
    	    genres.add(genre); // wrap into list

    	    String status = request.getParameter("status");
    	    String publishedDate = request.getParameter("publishedDate");
    	    String description = request.getParameter("description");

    	    Manga manga = new Manga();
    	    manga.setTitle(title);
    	    manga.setAuthor(author);
    	    manga.setGenres(genres); 
    	    manga.setStatus(status);
    	    manga.setPublishedDate(publishedDate);
    	    manga.setDescription(description);

    	    boolean success = mangaDAO.addManga(manga);

    	    response.sendRedirect("ManageMangaController?action=list");
    	}

}

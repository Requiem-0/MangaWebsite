package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/ManageMangaController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 5,        // 5 MB
    maxRequestSize = 1024 * 1024 * 10     // 10 MB
)
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
                listManga(request, response);
            } else {
                listManga(request, response); // default action
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong.");
            listManga(request, response);
        }
    }

    private void listManga(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Manga> mangaList = mangaDAO.getAllManga();
        request.setAttribute("mangaList", mangaList);
        request.getRequestDispatcher("/pages/manageManga.jsp").forward(request, response);
    }

    private void deleteManga(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("mangaId"));
            mangaDAO.deleteManga(id);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while deleting the manga.");
        }
        listManga(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Because of file upload, get parameters carefully (multipart)
        
        // Parse text fields
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        List<String> genres = new ArrayList<>();
        if (genre != null && !genre.trim().isEmpty()) {
            for (String g : genre.split(",")) {
                genres.add(g.trim());
            }
        }

        String status = request.getParameter("status");
        String publishedDate = request.getParameter("publishedDate");
        String description = request.getParameter("description");

        // Handle file upload for manga image
        Part filePart = request.getPart("mangaImageFile");  // the input name for file upload in your form

        String mangaImagePath = null;  // to store relative image path

        if (filePart != null && filePart.getSize() > 0) {
            String uploadDirectory = request.getServletContext().getRealPath("/resources/images");
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String filePath = uploadDirectory + File.separator + fileName;

            try {
                filePart.write(filePath);
                System.out.println("Manga image uploaded to: " + filePath);
                mangaImagePath = "/resources/images/" + fileName; // relative path to save in DB
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error uploading manga image.");
                listManga(request, response);
                return;
            }
        } else {
            // No image uploaded, mangaImagePath stays null or default image can be assigned
            mangaImagePath = request.getParameter("existingMangaImage"); // in case edit and image not changed
        }

        Manga manga = new Manga();
        manga.setTitle(title);
        manga.setAuthor(author);
        manga.setGenres(genres);
        manga.setStatus(status);
        manga.setPublishedDate(publishedDate);
        manga.setDescription(description);
        manga.setMangaImage(mangaImagePath);

        boolean added = mangaDAO.addManga(manga);
        if (added) {
            request.setAttribute("message", "Manga added successfully.");
        } else {
            request.setAttribute("error", "Failed to add manga.");
        }
        listManga(request, response);
    }
}

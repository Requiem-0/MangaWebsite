package com.manga.controllers;

import com.manga.controllers.dao.ChapterDAO;
import com.manga.controllers.dao.VolumeDAO;
import com.manga.models.Chapter;
import com.manga.models.Volume;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/ManageChapterController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,       // 1 MB
    maxFileSize = 1024 * 1024 * 5,         // 5 MB
    maxRequestSize = 1024 * 1024 * 10      // 10 MB
)
public class ManageChapterController extends HttpServlet {
    private ChapterDAO chapterDAO;
    private VolumeDAO volumeDAO;

    @Override
    public void init() {
        chapterDAO = new ChapterDAO();
        volumeDAO = new VolumeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String volumeIdStr = request.getParameter("volumeId");
        int volumeId = (volumeIdStr != null) ? Integer.parseInt(volumeIdStr) : 0;

        try {
            if (action == null || action.equals("list")) {
                listChapters(request, response, volumeId);
            } else if (action.equals("deleteChapter")) {
                deleteChapter(request, response, volumeId);
            } else {
                listChapters(request, response, volumeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request.");
            listChapters(request, response, volumeId);
        }
    }

    private void listChapters(HttpServletRequest request, HttpServletResponse response, int volumeId) throws ServletException, IOException {
        List<Chapter> chapterList = chapterDAO.getChaptersByVolumeId(volumeId);
        request.setAttribute("chapterList", chapterList);
        request.setAttribute("volumeId", volumeId);

        // Load volumes for dropdown/select
        List<Volume> volumeList = volumeDAO.getAllVolumes();
        request.setAttribute("volumeList", volumeList);

        request.getRequestDispatcher("/pages/manageChapter.jsp").forward(request, response);
    }

    private void deleteChapter(HttpServletRequest request, HttpServletResponse response, int volumeId) throws ServletException, IOException {
        try {
            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            chapterDAO.deleteChapter(chapterId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error deleting chapter.");
        }
        listChapters(request, response, volumeId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String chapternoStr = request.getParameter("chapterno");
        String chaptertitle = request.getParameter("chaptertitle");
        String volumeIdStr = request.getParameter("volumeId");

        int chapterno = 0;
        int volumeId = 0;

        try {
            chapterno = Integer.parseInt(chapternoStr);
            volumeId = Integer.parseInt(volumeIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid chapter number or volume ID.");
            listChapters(request, response, volumeId);
            return;
        }

        // Handle PDF upload
        Part filePart = request.getPart("chapterPDF");
        String chapter_pdf = null;

        if (filePart != null && filePart.getSize() > 0) {
            String uploadDirectory = request.getServletContext().getRealPath("/pages/uploads");
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileName = UUID.randomUUID() + "_" + originalFileName;
            File file = new File(uploadDir, fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
                chapter_pdf = "pages/uploads/" + fileName;  // relative path saved to DB
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error uploading chapter PDF.");
                listChapters(request, response, volumeId);
                return;
            }
        }

        Chapter chapter = new Chapter();
        chapter.setChapterno(chapterno);
        chapter.setChaptertitle(chaptertitle);
        chapter.setVolumeId(volumeId);
        chapter.setChapter_pdf(chapter_pdf);

        boolean added = chapterDAO.addChapter(chapter);
        if (added) {
            request.setAttribute("message", "Chapter added successfully.");
        } else {
            request.setAttribute("error", "Failed to add chapter.");
        }

        listChapters(request, response, volumeId);
    }
}

 package com.manga.controllers;

import com.manga.controllers.dao.VolumeDAO;
import com.manga.controllers.dao.MangaDAO;  // <-- import MangaDAO
import com.manga.models.Volume;
import com.manga.models.Manga;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/ManageVolumeController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 5,        // 5 MB
    maxRequestSize = 1024 * 1024 * 10     // 10 MB
)
public class ManageVolumeController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VolumeDAO volumeDAO;

    

    private MangaDAO mangaDAO;  // Add MangaDAO instance

    @Override
    public void init() {
        volumeDAO = new VolumeDAO();
        mangaDAO = new MangaDAO();  // Initialize mangaDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String mangaIdStr = request.getParameter("mangaId");
        int mangaId = (mangaIdStr != null) ? Integer.parseInt(mangaIdStr) : 0;

        try {
            if (action == null || action.equals("list")) {
                listVolumes(request, response, mangaId);
            } else if (action.equals("deleteVolume")) {
                deleteVolume(request, response, mangaId);
            } else {
                listVolumes(request, response, mangaId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request.");
            listVolumes(request, response, mangaId);
        }
    }

    private void listVolumes(HttpServletRequest request, HttpServletResponse response, int mangaId) throws ServletException, IOException {
        List<Volume> volumeList = volumeDAO.getVolumesByMangaId(mangaId);
        request.setAttribute("volumeList", volumeList);
        request.setAttribute("mangaId", mangaId);

        // Load all manga for the dropdown
        List<Manga> mangaList = mangaDAO.getAllManga();
        request.setAttribute("mangaList", mangaList);

        request.getRequestDispatcher("/pages/manageVolume.jsp").forward(request, response);
    }

    private void deleteVolume(HttpServletRequest request, HttpServletResponse response, int mangaId) throws ServletException, IOException {
        try {
            int volumeId = Integer.parseInt(request.getParameter("volumeId"));
            volumeDAO.deleteVolume(volumeId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error deleting volume.");
        }
        listVolumes(request, response, mangaId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String volumeNumberStr = request.getParameter("volumeNumber");
        String mangaIdStr = request.getParameter("mangaId");

        int volumeNumber = 0;
        int mangaId = 0;

        try {
            volumeNumber = Integer.parseInt(volumeNumberStr);
            mangaId = Integer.parseInt(mangaIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid volume number or manga ID.");
            listVolumes(request, response, mangaId);
            return;
        }

        // Handle file upload
        Part filePart = request.getPart("volumeImageFile");
        String volume_img = null;

        if (filePart != null && filePart.getSize() > 0) {
            String uploadDirectory = request.getServletContext().getRealPath("/resources/images");
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String filePath = uploadDirectory + File.separator + fileName;

            try {
                filePart.write(filePath);
                volume_img = "/resources/images/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error uploading volume image.");
                listVolumes(request, response, mangaId);
                return;
            }
        }

        Volume volume = new Volume();
        volume.setIsbn(isbn);
        volume.setVolumeNumber(volumeNumber);
        volume.setVolume_img(volume_img);
        volume.setMangaId(mangaId);

        boolean added = volumeDAO.addVolume(volume);
        if (added) {
            request.setAttribute("message", "Volume added successfully.");
        } else {
            request.setAttribute("error", "Failed to add volume.");
        }

        listVolumes(request, response, mangaId);
    }
}
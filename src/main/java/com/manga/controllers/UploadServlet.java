package com.manga.controllers;

import com.manga.models.UploadPDF;
import com.manga.controllers.dao.UploadPDFDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        Part filePart = request.getPart("pdf");

        String originalFileName = new File(filePart.getSubmittedFileName()).getName();
        String fileName = originalFileName;

        String uploadPath = getServletContext().getRealPath("/pages/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File file = new File(uploadDir, fileName);
        int count = 1;

        // Rename if file already exists
        while (file.exists()) {
            String nameWithoutExt = originalFileName.replaceFirst("[.][^.]+$", "");
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            fileName = nameWithoutExt + "_" + count + extension;
            file = new File(uploadDir, fileName);
            count++;
        }

        // Save the uploaded file
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }

        String filePath = "pages/uploads/" + fileName;
        UploadPDF pdf = new UploadPDF(title, filePath);
        UploadPDFDAO dao = new UploadPDFDAO();

        if (dao.savePDF(pdf)) {
            response.sendRedirect("pages/view.jsp");
        } else {
            response.getWriter().println("Failed to save PDF.");
        }
    }
}

package com.manga.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLDecoder;

@WebServlet("/read")
public class ReadController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePathParam = request.getParameter("file");

        if (filePathParam == null || filePathParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File parameter is missing.");
            return;
        }

        // Decode the file path (already relative to webapp root like 'pages/uploads/abc.pdf')
        String decodedPath = URLDecoder.decode(filePathParam, "UTF-8");

        // ✅ Do NOT add "pages/uploads" again, the param already contains full relative path
        String absolutePath = getServletContext().getRealPath("/" + decodedPath);
        File pdfFile = new File(absolutePath);

        System.out.println("Resolved PDF file path: " + absolutePath); // For debug

        if (!pdfFile.exists() || pdfFile.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "PDF not found.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(pdfFile));
             BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading PDF file.");
            e.printStackTrace();
        }
    }
}

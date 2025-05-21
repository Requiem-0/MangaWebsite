package com.manga.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/delete-pdf")
public class DeletePDFServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "/pages/uploads/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        String filePath = getServletContext().getRealPath(UPLOAD_DIR + fileName);

        File file = new File(filePath);
        if (file.exists() && file.delete()) {
            response.getWriter().write("PDF deleted successfully.");
        } else {
            response.getWriter().write("Failed to delete PDF.");
        }
    }
}

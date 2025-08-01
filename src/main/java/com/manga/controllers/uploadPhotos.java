package com.manga.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class uploadPhotos
 */
@WebServlet("/uploadPhotos")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 *2, //2MB
			maxFileSize= 1024 * 1024 * 10, //10MB
			maxRequestSize = 1024 * 1024 *50) //50MB
public class uploadPhotos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadPhotos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part image =request.getPart("image");
		String fileName = image.getSubmittedFileName();
		
		String storePath =request.getServletContext().getRealPath("");
		String filePath ="photos" + File.separator + fileName;
		
		image.write(storePath+File.separator + filePath);
		
		String displayPath = request.getContextPath() + File.separator + filePath;
		
		request.setAttribute("path", displayPath);
		RequestDispatcher rd= request.getRequestDispatcher("/pages/uploadPhotos.jsp");
		rd.forward(request, response);
		
	}

}

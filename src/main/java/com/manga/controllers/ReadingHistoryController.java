package com.manga.controllers;

import com.manga.models.ReadingHistory;
import com.manga.models.User;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class ReadingHistoryController
 */
@WebServlet("/ReadingHistory")
public class ReadingHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReadingHistoryController() {
        super();
    }

    /**
     * Handles GET requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false); // false to avoid creating a new session if it doesn't exist
    	
    	
    	if(session == null) {
    	   response.sendRedirect(request.getContextPath() + "/LoginController");
      	  return;
    	}
    	
    	User loggedInUser = (User) session.getAttribute("user");
    	

    	//Redirect if not logged in
    	if (loggedInUser == null) {
    	  response.sendRedirect(request.getContextPath() + "/LoginController");
    	  return;
    	}

    	
    	// Get reading histories of the logged in user
        //List<ReadingHistory.ReadingHistoryWithManga> historyList = ReadingHistory.getByUserIdWithMangaDetails(loggedInUser.getUserId());
    	List<ReadingHistory.ReadingHistoryWithManga> historyList = ReadingHistory.getByUserIdWithMangaDetails(1);

         
        // Pass data to JSP (for later use on frontend)
        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("/pages/history.jsp").forward(request, response);
    }
}




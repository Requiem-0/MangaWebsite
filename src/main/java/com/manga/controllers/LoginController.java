package com.manga.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manga.controllers.dao.UserDAO;
import com.manga.models.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 // Check if the user is already logged in (i.e., session exists)
        HttpSession session = request.getSession(false); // Get session (do not create a new one)
        if (session != null && session.getAttribute("username") != null) {
            // If session exists, user is logged in. Redirect to profile page
            response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
        } else {
            // If no session exists, proceed to the login page
            response.getWriter().append("Served at: ").append(request.getContextPath());
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	     String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        UserDAO userDAO = new UserDAO();

	        // Checking if user exists and the password matches
	        User user = userDAO.loginUser(email, password);
	        
	        if (user != null) {
	        	 // Successful login: Store user info in session
	            HttpSession session = request.getSession();  // Creating or getting the existing session
	            session.setAttribute("username", user.getUsername());  // Storing username in session
	            
	            // Optionally, you can store other user info like email
	            session.setAttribute("email", user.getEmail());
	            // Successful login: redirect to the profile page
	            response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
	        } else {
	            // Invalid credentials: redirect back to login with error
	            response.sendRedirect(request.getContextPath() + "/pages/login.jsp?error=true");
	        }
	}

}
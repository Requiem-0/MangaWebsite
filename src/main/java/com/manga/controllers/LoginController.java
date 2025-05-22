package com.manga.controllers;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt; 


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
        	request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	     String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        UserDAO userDAO = new UserDAO();

		        // Checking if user exists and the password matches
		        User user = userDAO.loginUser(email, password);
		        
		        if (user != null) {
		        	 System.out.println("User role from DB: " + user.getRole());
		        	 // Successful login: Store user info in session
		            HttpSession session = request.getSession();  // Creating or getting the existing session
		           
		            session.setAttribute("username", user.getUsername());  // Storing username in session
		            
		            // Optionally, you can store other user info like email
		            session.setAttribute("email", user.getEmail());
		            
		            session.setAttribute("role", user.getRole());  // Storing user role for access control	
		            
		            session.setAttribute("user", user);  // Store full user object
		            
		            
		            System.out.println("---------------------------");
		            System.out.println(user.getUserId());
		            System.out.println("---------------------------");
		           
		            
		            // Redirect based on user role
		            String role = user.getRole();
		            
		            if ("admin".equalsIgnoreCase(role)) {
		                // If user is an admin, redirect to admin dashboard
		                response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
		            } else if ("user".equalsIgnoreCase(role)) {
		                // If user is a regular user, redirect to profile page
		                response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
		            } else {
		                // Fallback for unknown roles — redirect to login with error
		            	request.setAttribute("error", "unknown_role");
		            	request.getRequestDispatcher("/pages/login.jsp").forward(request, response);

		            }
		        } else {
		            // Invalid credentials: redirect back to login with error
		        	request.setAttribute("error", "invalid_credentials");
		        	request.getRequestDispatcher("/pages/login.jsp").forward(request, response);

		        }
		}
	
	}
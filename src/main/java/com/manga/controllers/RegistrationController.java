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
 * Servlet implementation class RegistrationController
 */
@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        HttpSession session = request.getSession(false); // Get session (do not create a new one)
        if (session != null && session.getAttribute("username") != null) {
            // If session exists, user is logged in. Redirect to profile page
            response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
        } else {
            // If no session exists, proceed to the registration page
            response.getWriter().append("Served at: ").append(request.getContextPath());
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        
        //Checking
        System.out.println("Form data: " + username + " | " + email + " | " + password + " | " + confirm);
       
     // Creating an instance of UserDAO to check if user exists
        UserDAO userDAO = new UserDAO();
        
        //Checking the password and confirm password
        if (!password.equals(confirm)) {
        	response.sendRedirect(request.getContextPath() + "/pages/registerUser.jsp?error=password_mismatch");
        	
            return;
        }
       
        // Checking if the user already exists by email
        else if (userDAO.userExists(email, password)) {
            response.sendRedirect(request.getContextPath() + "/pages/registerUser.jsp?error=user_exists");
        }
        // Proceeding with registration if no issues
        else {
        User user = new User(username, email, password);
       
        boolean success = userDAO.register(user);
        if (success) {
        	response.sendRedirect(request.getContextPath() + "/pages/registerUser.jsp?success=true");

        } else {
        	 System.out.println("Registration failed (DAO returned false)");
        	 response.sendRedirect(request.getContextPath() + "/pages/registerUser.jsp?error=fail");
        }
        
      } 
	}

}
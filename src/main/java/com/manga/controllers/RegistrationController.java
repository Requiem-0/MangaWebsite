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
            request.getRequestDispatcher("/pages/profile.jsp").forward(request, response);
        } else {
            // If no session exists, proceed to the registration page
        	request.getRequestDispatcher("/pages/registerUser.jsp").include(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        
        //Checking
        System.out.println("Form data: " + username + " | " + email + " | " + password + " | " + confirm);
       
     // Creating an instance of UserDAO to check if user exists
        UserDAO userDAO = new UserDAO();
        
        if (!password.equals(confirm)) {
            request.setAttribute("error", "password_mismatch");
            request.getRequestDispatcher("/pages/registerUser.jsp").include(request, response);
            return;
        }

        else if (userDAO.userExists(email, password)) {
            request.setAttribute("error", "user_exists");
            request.getRequestDispatcher("/pages/registerUser.jsp").include(request, response);
            return;
        }

        // Proceeding with registration if no issues
        else {
        User user = new User(username, email, password, null);
       
        boolean success = userDAO.register(user);
        if (success) {
            request.setAttribute("success", true);
            request.getRequestDispatcher("/pages/registerUser.jsp").include(request, response);
        } else {
            request.setAttribute("error", "fail");
            request.getRequestDispatcher("/pages/registerUser.jsp").include(request, response);
        }
        
      } 
	}

}
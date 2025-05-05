package com.manga.controllers;

import java.io.IOException;

import com.manga.models.User;
import com.manga.controllers.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet("/ProfileController")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileController() {
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
        String action = request.getParameter("action");

        if ("updateProfile".equals(action)) {
            updateUsername(request, response);
        } 
        else if ("changePassword".equals(action)) {
            changePassword(request, response);
        } 
	}
    // Method for updating the username
	public void updateUsername(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    // Get the user email from the session (assuming the user is logged in)
	    HttpSession session = request.getSession();
	    String email = (String) session.getAttribute("email");

	    // If no email is found in session, redirect to login page (user is not logged in)
	    if (email == null) {
	        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
	        return;
	    }
	        
	    // Retrieve the new username from the request
	    String newUsername = request.getParameter("newUsername");

	    // Debugging: Print the received username
	    System.out.println("Received newUsername: " + newUsername);
	    
	    // Basic validation: Ensure new username is not empty
	    if (newUsername != null && !newUsername.trim().isEmpty()) {
	        UserDAO userDAO = new UserDAO();
	        User user = userDAO.getUserByEmail(email);  // Get the logged-in user by email from the session

	        if (user != null) {
	            // If the new username is different from the current one
	            if (!user.getUsername().equals(newUsername)) {
	                // Update the username in the database
	                boolean isUpdated = userDAO.updateUsername(user.getEmail(), newUsername);
	                
	                if (isUpdated) {
	                    System.out.println("Username updated successfully.");
	                    // Update the username in session after successful update
	                    session.setAttribute("username", newUsername);
	                    // Forward to the profile page with a success message
	                    request.setAttribute("message", "Username updated successfully.");
	                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
	                    dispatcher.forward(request, response);
	                } else {
	                    System.out.println("Failed to update username.");
	                    // Forward to the profile page with an error message
	                    request.setAttribute("error", "Failed to update username.");
	                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
	                    dispatcher.forward(request, response);
	                }
	            } else {
	                // If the new username is the same as the current one
	                System.out.println("New username is the same as the current one.");
	                request.setAttribute("error", "New username is the same as the current one.");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
	                dispatcher.forward(request, response);
	            }
	        } else {
	            // User not found in the database (shouldn't happen if email is in session)
	            System.out.println("User not found in the database.");
	            request.setAttribute("error", "User not found.");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
	            dispatcher.forward(request, response);
	        }
	    } else {
	        // If new username is invalid or empty
	        System.out.println("Invalid or empty new username.");
	        request.setAttribute("error", "Invalid username.");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
	        dispatcher.forward(request, response);
	    }
	}


    // Method for changing the password
    public void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the user email from the session (assuming the user is logged in)
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        // If no email is found in session, redirect to login page (user is not logged in)
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }
            
        // Retrieve user input from the request
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Basic validation: Check if the new password and confirm password match
        if (newPassword.equals(confirmPassword)) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);  // Get the logged-in user by email from the session

            // Check if the current password matches the stored password
            if (user != null && user.getPassword().equals(currentPassword)) {
                // Update the password in the database
                boolean isUpdated = userDAO.updatePassword(user.getEmail(), newPassword);
                
                if (isUpdated) {
                    // Forward to the profile page with a success message
                    request.setAttribute("message", "Password updated successfully.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Forward to the profile page with an error message
                    request.setAttribute("error", "Failed to update password.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                // Current password does not match
                request.setAttribute("error", "Current password is incorrect.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // New password and confirmation do not match
            request.setAttribute("error", "New password and confirmation do not match.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
            dispatcher.forward(request, response);
        }
    }

    
    
    


}

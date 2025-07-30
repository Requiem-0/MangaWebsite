package com.manga.controllers;

import java.io.File;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import com.manga.models.User;
import com.manga.controllers.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet("/ProfileController")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,  // 1 MB
	    maxFileSize = 1024 * 1024 * 5,    // 5 MB
	    maxRequestSize = 1024 * 1024 * 10 // 10 MB
		)


public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 // Directory to store uploaded profile pictures
       
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
		request.getRequestDispatcher("/pages/profile.jsp").forward(request, response);
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
        else if ("uploadProfilePicture".equals(action)) {
            uploadProfilePicture(request, response); // Handle the profile picture upload
        }
        else if ("deleteProfilePicture".equals(action)) {
            deleteProfilePicture(request, response);
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
	                    user.setUsername(newUsername); // Update the User object
	                    session.setAttribute("user", user); // Update session with the modified User object

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
            if (user != null && BCrypt.checkpw(currentPassword, user.getPassword())) {
                // Update the password in the database
            	String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            	boolean isUpdated = userDAO.updatePassword(user.getEmail(), hashedNewPassword);

                
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
    
    // Method for uploading profile picture
    public void uploadProfilePicture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        // If no email is found in session, redirect to login page
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }
        
        String uploadDirectory = request.getServletContext().getRealPath("/resources/images");
        
     // Add debug log to check the resolved path
        System.out.println("Upload Directory: " + uploadDirectory);

        // Get the file part (profile picture)
        Part filePart = request.getPart("profilePicture");	
        
	     // Log the file size and name
	        System.out.println("File name: " + filePart.getSubmittedFileName());
	        System.out.println("File size: " + filePart.getSize());
	        
	        
        // Check if a file is uploaded
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = UUID.randomUUID().toString() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String filePath = uploadDirectory + File.separator + fileName;
            // Create the directory if it doesn't exist
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            if (uploadDir.canWrite()) {
                System.out.println("Write permission granted for the directory.");
            } else {
                System.out.println("No write permission for the directory.");
            }

            // Save the file
            try {
                filePart.write(filePath);
                System.out.println("File saved at: " + filePath);  // Log successful save
            } catch (IOException e) {
                System.out.println("Error while saving file: " + e.getMessage());
            }

            // Get the relative path to store in the database
            String relativePath = "/resources/images/" + fileName;

            // Update the user's profile picture in the database
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);  // Get the logged-in user

            if (user != null) {
                boolean isUpdated = userDAO.updateProfilePicture(user.getEmail(), relativePath);
             // Log the result of the database update
           

                if (isUpdated) {
                    System.out.println("Profile picture updated successfully for user: " + email);
                    user.setProfilePicture(relativePath);
                    session.setAttribute("user", user);

                    // Forward to the profile page with a success message
                    request.setAttribute("message", "Profile picture updated successfully.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // If update fails
                    System.out.println("Failed to update profile picture for user: " + email);
                    request.setAttribute("error", "Failed to update profile picture.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                System.out.println("User not found for email: " + email);
                request.setAttribute("error", "User not found.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // If no file is uploaded
            System.out.println("No file selected for upload.");

            request.setAttribute("error", "No file selected.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    
    
    // delete method
    public void deleteProfilePicture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            String currentPicPath = user.getProfilePicture();
            
            String defaultPic = null; 

            boolean isUpdated = userDAO.updateProfilePicture(user.getEmail(), defaultPic);

            if (isUpdated) {
                // Delete old profile picture file from server if exists and not default
                if (currentPicPath != null && !currentPicPath.trim().isEmpty()) {
                    String fullPath = request.getServletContext().getRealPath(currentPicPath);
                    File oldFile = new File(fullPath);
                    if (oldFile.exists()) {
                        if (oldFile.delete()) {
                            System.out.println("Old profile picture deleted: " + fullPath);
                        } else {
                            System.out.println("Failed to delete old profile picture: " + fullPath);
                        }
                    }
                }

                // Update session
                user.setProfilePicture(defaultPic);
                session.setAttribute("user", user);

                request.setAttribute("message", "Profile picture deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete profile picture.");
            }
        } else {
            request.setAttribute("error", "User not found.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
        dispatcher.forward(request, response);
    }

    
    
    


}

package com.manga.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manga.controllers.dao.UserDAO;
import com.manga.models.Manga;
import com.manga.models.User;

/**
 * Servlet implementation class ManageUsersServlet
 */
@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listUsers(request, response);
            } 
            else if (action.equals("deleteUser")) {
                deleteUser(request, response);
            } 
           } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("pages/error.jsp");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doPost() called");
    	System.out.println("Request action: " + request.getParameter("action"));

        String action = request.getParameter("action");
        if ("changeRole".equals(action)) {
        	
            System.out.println("Inside changeRole block");
            System.out.println("User ID: " + request.getParameter("userId"));
            System.out.println("New Role: " + request.getParameter("newRole"));
        	
            int userId = Integer.parseInt(request.getParameter("userId"));
            String newRole = request.getParameter("newRole");

            try {
                boolean success = userDAO.updateUserRole(userId, newRole);
                if (success) {
                    request.setAttribute("message", "User role updated successfully.");
                } else {
                    request.setAttribute("error", "Failed to update user role.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error updating user role.");
            }

            try {
                listUsers(request, response); // Refresh the user list
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("pages/error.jsp");
            }
        }

       
    }
  
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, ClassNotFoundException {
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/pages/manageUsers.jsp").forward(request, response);
    }



		private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		    int userId = Integer.parseInt(request.getParameter("userId"));
		    try {
				userDAO.deleteUser(userId);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // You need to create this method in your DAO
		    response.sendRedirect("ManageUsersServlet?action=list");
		}
	

}

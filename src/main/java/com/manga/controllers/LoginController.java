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

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Do not create a new session
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
        } else {
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            System.out.println("User role from DB: " + user.getRole());

            HttpSession session = request.getSession(); // Create or reuse session

            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("user", user);

            //  Set userId in session for bookmark support
            session.setAttribute("userId", user.getUserId());

            String role = user.getRole();
            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
            } else if ("user".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/pages/profile.jsp");
            } else {
                request.setAttribute("error", "unknown_role");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "invalid_credentials");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}

package com.manga.controllers;

import com.manga.controllers.dao.UserDAO;
import com.manga.models.User;

public class UserDAOTest {
    public static void main(String[] args) {
        // Create an instance of UserDAO
        UserDAO userDAO = new UserDAO();

        // Test: Registering a new user with Naruto theme
        User newUser = new User();
        newUser.setUsername("naruto_uzumaki");
        newUser.setEmail("naruto.uzumaki@konoha.com");
        newUser.setPassword("believeit123");
        newUser.setRole("ninja");

        boolean isRegistered = userDAO.register(newUser);
        System.out.println("Register User (Naruto): " + (isRegistered ? "Success" : "Failed"));

        // Test: Logging in as Naruto
        User loggedInUser = userDAO.loginUser("naruto.uzumaki@konoha.com", "believeit123");
        System.out.println("Login User (Naruto): " + (loggedInUser != null ? "Success" : "Failed"));

        // Test: Logging in with incorrect password
        User failedLoginUser = userDAO.loginUser("naruto.uzumaki@konoha.com", "wrongpassword");
        System.out.println("Failed Login (Naruto): " + (failedLoginUser == null ? "Failed" : "Success"));

        // Test: Registering another user (Sasuke)
        User sasukeUser = new User();
        sasukeUser.setUsername("sasuke_uchiha");
        sasukeUser.setEmail("sasuke.uchiha@konoha.com");
        sasukeUser.setPassword("cursemark123");
        sasukeUser.setRole("ninja");

        boolean isSasukeRegistered = userDAO.register(sasukeUser);
        System.out.println("Register User (Sasuke): " + (isSasukeRegistered ? "Success" : "Failed"));

        // Test: Logging in as Sasuke
        User loggedInSasuke = userDAO.loginUser("sasuke.uchiha@konoha.com", "cursemark123");
        System.out.println("Login User (Sasuke): " + (loggedInSasuke != null ? "Success" : "Failed"));

        // Test: Count total users (Naruto and Sasuke)
        int totalUsers = userDAO.countUser();
        System.out.println("Total Users in Database: " + totalUsers);
    }
}

package com.manga.controllers.dao;

import com.manga.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.manga.database.DatabaseConnection;

public class UserDAO {
	
	
    //Checking user existence
    public boolean userExists(String email, String password) {
        boolean exists = false;

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email = ?"; // checking by email
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email); // Set the email parameter

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = true; // If we find a record, it means the user exists
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }	
	
    //Register user
	public boolean register(User user){
		System.out.println("register() method called");
		boolean success = false;
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			if(conn != null) {
				System.out.println("Successfully connected to the database.");
			}
			else {
				System.out.println("DB connection failed");
			}
			 String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, user.getUsername());
	            stmt.setString(2, user.getEmail());
	            stmt.setString(3, user.getPassword());
	            stmt.setString(4, user.getRole());
	            
	            //Just checking in case
	            System.out.println("Executing SQL with values:");
	            System.out.println("Username: " + user.getUsername());
	            System.out.println("Email: " + user.getEmail());
	            System.out.println("Password: " + user.getPassword());
	            System.out.println("Role: " + user.getRole());
	            
	            int rows = stmt.executeUpdate();
	            success = rows > 0;
	            
	            //Checking again
	            System.out.println("Executing SQL: " + sql);
	            System.out.println("Inserting User: Username = " + user.getUsername() + ", Email = " + user.getEmail());
	            System.out.println("Rows inserted: " + rows);
	            
	            
		} catch (ClassNotFoundException e) {
			System.out.println("Unexpected error while inserting user");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("SQLException while inserting user");
			e.printStackTrace();
		}
		return success;
				
		
	//login user			 
	}
	
	public User loginUser(String email, String password) {
	    User user = null; // Will store the user if found

	    try {
	        // Get a connection to the database
	        Connection conn = DatabaseConnection.getConnection();

	        // SQL query to find user with matching email and password
	        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        // Set the email and password into the SQL query
	        stmt.setString(1, email);
	        stmt.setString(2, password);

	        // Execute the query
	        ResultSet rs = stmt.executeQuery();

	        // If a matching user is found
	        if (rs.next()) {
	            // Extract values from the database result
	            String username = rs.getString("username");
	            String role = rs.getString("role");
	            Timestamp createdAt = rs.getTimestamp("created_at");

	            // Create a User object and populate it
	            user = new User(username, email, password); // Using your constructor
	            user.setRole(role);
	            user.setCreatedAt(createdAt);

	            System.out.println("Login success for user: " + username);
	        } else {
	            // No user found with given email/password
	            System.out.println("Login failed: No matching user found.");
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        // Handle errors connecting to DB or JDBC driver not found
	        System.out.println("Exception during loginUser()");
	        e.printStackTrace();
	    }

	    // Return the User object (or null if login failed)
	    return user;
	}

}

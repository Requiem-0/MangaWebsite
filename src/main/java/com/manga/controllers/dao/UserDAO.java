package com.manga.controllers.dao;

import com.manga.models.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.manga.database.DatabaseConnection;

public class UserDAO {
	
	
    //Checking user existence
    public boolean userExists(String email) {
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
            String sql = "INSERT INTO users (username, email, password, role, profile_picture) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, user.getUsername());
	            stmt.setString(2, user.getEmail());
	            stmt.setString(3, user.getPassword());
	            stmt.setString(4, user.getRole());
	            stmt.setString(5, user.getProfilePicture());  // Insert profile picture (can be null initially)

	            
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
				
	}
	//login user			 
	
	
	public User loginUser(String email, String password) {
	    System.out.println("loginUser() called with email: " + email); // Removed password from logs for security
	    User user = null;

	    try {
	        // Get a connection to the database
	        Connection conn = DatabaseConnection.getConnection();

	        // 🔁 UPDATED: Query ONLY by email, NOT by password
	        String sql = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, email);

	        ResultSet rs = stmt.executeQuery();
	        System.out.println("Query executed, checking if user exists...");

	        if (rs.next()) {
	            // Get stored hashed password from DB
	            String storedHashedPassword = rs.getString("password");

	            // 🔁 UPDATED: Check plain password against stored hashed password using BCrypt
	            if (BCrypt.checkpw(password, storedHashedPassword)) {
	                // Password matches, create user object
	                String username = rs.getString("username");
	                String role = rs.getString("role");
	                Timestamp createdAt = rs.getTimestamp("created_at");
	                String profilePicture = rs.getString("profile_picture");

	                System.out.println("Password matched. Creating user object...");

	                System.out.println("Fetched data from DB:");
	                System.out.println("Username: " + username);
	                System.out.println("Role: " + role);
	                System.out.println("Created At: " + createdAt);

	                user = new User(username, email, storedHashedPassword, role, profilePicture);
	                user.setCreatedAt(createdAt);

	                System.out.println("User object created: Username = " + user.getUsername() + ", Role = " + user.getRole());
	            } else {
	                // Password does not match
	                System.out.println("Login failed: Password does not match.");
	            }
	        } else {
	            // No user found with given email
	            System.out.println("Login failed: No user found with email: " + email);
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        System.out.println("Exception during loginUser()");
	        e.printStackTrace();
	    }

	    return user;
	}

	
	    public int countUser() {
	        int userCount = 0;

	        try {
	            // Establish a connection to the database
	            Connection conn = DatabaseConnection.getConnection();
	            
	            // SQL query to count all distinct 'user_id' values in the 'users' table
	            String sql = "SELECT COUNT(user_id) FROM users";
	            PreparedStatement stmt = conn.prepareStatement(sql);

	            // Execute the query
	            ResultSet rs = stmt.executeQuery();

	            // If the result set is not empty, retrieve the count
	            if (rs.next()) {
	                userCount = rs.getInt(1); // Retrieve the first column (the count)
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            // Handle errors connecting to DB or JDBC driver not found
	            System.out.println("Exception during countUser()");
	            e.printStackTrace();
	        }

	        // Return the total user count
	        return userCount;
	    }

	   // Update the username in the database
	public boolean updateUsername(String email, String newUsername) {
	    boolean success = false;
	    System.out.println("updateUsername() called for user with email: " + email);

	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        if (conn == null) {
	            System.out.println("Failed to connect to the database in updateUsername.");
	            return false;
	        }

	        String sql = "UPDATE users SET username = ? WHERE email = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, newUsername); // Set new username
	        stmt.setString(2, email); // Set email
	        
	        int rowsUpdated = stmt.executeUpdate();
	        success = rowsUpdated > 0;
	        System.out.println("Rows updated: " + rowsUpdated);
	    } catch (SQLException | ClassNotFoundException e) {
	        System.out.println("Exception in updateUsername():");
	        e.printStackTrace();
	    }

	    return success;
	}

    
 // Update the password in the database
    public boolean updatePassword(String email, String newPassword) {
        boolean success = false;
        System.out.println("updatePassword() called for user with email: " + email);

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Failed to connect to database in updatePassword.");
                return false;
            }

            String sql = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);  // Set the new password
            stmt.setString(2, email);        // Set the user's email
            
            System.out.println("Executing SQL: " + sql);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            success = rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception in updatePassword():");
            e.printStackTrace();
        }
        return success;
    }

    
    public User getUserByEmail(String email) {
        User user = null;

        try {
            // Establish connection to the database
            Connection conn = DatabaseConnection.getConnection();

            // SQL query to get user by email
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);  // Set email parameter
            
            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // If a user is found, create a User object with the retrieved data
            if (rs.next()) {
                String username = rs.getString("username");
                String role = rs.getString("role");
                String password = rs.getString("password");
                Timestamp createdAt = rs.getTimestamp("created_at");
                
                String profilePicture = rs.getString("profile_picture");


                user = new User(username, email, password, role);
                user.setCreatedAt(createdAt);
                user.setProfilePicture(profilePicture);

            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exception and print the stack trace for debugging
            e.printStackTrace();
        }

        return user;
    }
    
    
    public boolean updateProfilePicture(String email, String profilePicturePath) {
        boolean success = false;

        try {
            Connection conn = DatabaseConnection.getConnection();
            
            String sql = "UPDATE users SET profile_picture = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, profilePicturePath);  // Update with the new profile picture path
            stmt.setString(2, email);  // The user's email to identify them
            
            int rowsUpdated = stmt.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return success;
    }


    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                
                // Debug print to check values fetched
                System.out.println("DEBUG: username=" + username + ", email=" + email + ", password=" + password + ", role=" + role);
                
                User user = new User(username, email, password, role);
                user.setUserId(rs.getInt("user_id"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        }
        return users;
    }
    
//update roles
    public boolean updateUserRole(int userId, String newRole) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    
    
    // Delete user by ID
    public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }

 

    
}

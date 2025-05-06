package com.manga.models;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String username;
    private String email;
    private String password;
    private String role;
    private String profilePicture;  // New field for profile picture path
    private Timestamp createdAt;

    // Constructor for registration (without profile picture)
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePicture = null;  // No profile picture initially
    }

    // Constructor for login (with profile picture)
    public User(String username, String email, String password, String role, String profilePicture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePicture = profilePicture;  // Profile picture might be available after login
    }

    // Getter and Setter methods
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

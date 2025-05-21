package com.manga.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    private final static String databaseName = "book_choda_comic_padha";
    private final static String username = "root";
    private final static String password = "";
    private final static String jdbcURL = "jdbc:mysql://localhost:3306/" + databaseName;
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, username, password);
    }    
    
}
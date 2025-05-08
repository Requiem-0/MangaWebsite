package com.manga.controllers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.manga.database.DatabaseConnection;

public class ReviewDAO {

    // Count total reviews in the database
    public int getReviewCount() {
        int count = 0;

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM review";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return count;
    }
}

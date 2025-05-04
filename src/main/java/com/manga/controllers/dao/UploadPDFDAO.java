package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.UploadPDF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UploadPDFDAO {

    public boolean savePDF(UploadPDF pdf) {
        String sql = "INSERT INTO uploaded_pdf (title, file_path) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pdf.getTitle());
            stmt.setString(2, pdf.getFilePath());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UploadPDF> getAllPDFs() {
        List<UploadPDF> list = new ArrayList<>();
        String sql = "SELECT * FROM uploaded_pdf";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UploadPDF pdf = new UploadPDF();
                pdf.setId(rs.getInt("id"));
                pdf.setTitle(rs.getString("title"));
                pdf.setFilePath(rs.getString("file_path"));
                list.add(pdf);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
}

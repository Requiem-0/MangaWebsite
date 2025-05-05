package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeMangaDAO {

    public List<Manga> getHomeManga(int offset, int recordsPerPage) {
        List<Manga> mangaList = new ArrayList<>();
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            String selectAllMangaSql = "SELECT * FROM manga LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectAllMangaSql);
            preparedStatement.setInt(1, recordsPerPage);
            preparedStatement.setInt(2, offset);
            ResultSet mangaResultSet = preparedStatement.executeQuery();

            while (mangaResultSet.next()) {
                Manga manga = new Manga();
                manga.setMangaId(mangaResultSet.getInt("manga_id"));
                manga.setTitle(mangaResultSet.getString("mangatitle"));
                manga.setAuthor(mangaResultSet.getString("author"));
                manga.setDescription(mangaResultSet.getString("mangadescription"));
                manga.setStatus(mangaResultSet.getString("status"));
                manga.setPublishedDate(mangaResultSet.getString("published_date"));
                mangaList.add(manga);
            }
            System.out.println("Manga List Size: " + mangaList.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangaList;
    }

    public int getTotalMangaCount() {
        int count = 0;
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            String countSql = "SELECT COUNT(*) FROM manga";
            Statement statement = databaseConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(countSql);
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }
}
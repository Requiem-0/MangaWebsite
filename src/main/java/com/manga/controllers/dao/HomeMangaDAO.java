package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeMangaDAO {

    public List<Manga> getHomeManga(int offset, int recordsPerPage, String genre, String sortBy) {
        List<Manga> mangaList = new ArrayList<>();
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            StringBuilder selectAllMangaSql = new StringBuilder("SELECT DISTINCT m.* FROM manga m");
            boolean hasWhereClause = false;

            if (genre != null && !genre.isEmpty()) {
                selectAllMangaSql.append(" JOIN manga_genre mg ON m.manga_id = mg.manga_id")
                                 .append(" JOIN genre g ON mg.genre_id = g.genre_id")
                                 .append(" WHERE g.genrename = ?");
                hasWhereClause = true;
            }

            if (sortBy != null && !sortBy.isEmpty()) {
                if (!hasWhereClause) {
                    selectAllMangaSql.append(" WHERE");
                } else {
                    selectAllMangaSql.append(" AND");
                }
                selectAllMangaSql.append(" 1=1"); // Placeholder to ensure the WHERE clause is always present
                selectAllMangaSql.append(" ORDER BY ");
                switch (sortBy) {
                    case "hottest":
                        selectAllMangaSql.append("m.published_date DESC");
                        break;
                    case "latest":
                        selectAllMangaSql.append("m.manga_id DESC");
                        break;
                    case "alpha":
                        selectAllMangaSql.append("m.mangatitle ASC");
                        break;
                }
            }

            selectAllMangaSql.append(" LIMIT ? OFFSET ?");
            System.out.println("SQL Query: " + selectAllMangaSql.toString()); // Debugging statement

            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectAllMangaSql.toString());
            int paramIndex = 1;
            if (genre != null && !genre.isEmpty()) {
                preparedStatement.setString(paramIndex++, genre);
                System.out.println("Genre: " + genre); // Debugging statement
            }
            preparedStatement.setInt(paramIndex++, recordsPerPage);
            preparedStatement.setInt(paramIndex, offset);
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
            System.out.println("Manga List Size: " + mangaList.size()); // Debugging statement
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangaList;
    }

    public int getTotalMangaCount(String genre) {
        int count = 0;
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            StringBuilder countSql = new StringBuilder("SELECT COUNT(DISTINCT m.manga_id) FROM manga m");
            if (genre != null && !genre.isEmpty()) {
                countSql.append(" JOIN manga_genre mg ON m.manga_id = mg.manga_id")
                        .append(" JOIN genre g ON mg.genre_id = g.genre_id")
                        .append(" WHERE g.genrename = ?");
            }
            System.out.println("Count SQL Query: " + countSql.toString()); // Debugging statement
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(countSql.toString());
            if (genre != null && !genre.isEmpty()) {
                preparedStatement.setString(1, genre);
                System.out.println("Genre for Count: " + genre); // Debugging statement
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Total Manga Count: " + count); // Debugging statement
        return count;
    }

    public List<Manga> getOngoingManga(int limit) {
        List<Manga> mangaList = new ArrayList<>();
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            String selectOngoingMangaSql = "SELECT * FROM manga WHERE status = 'Ongoing' LIMIT ?";
            System.out.println("Ongoing Manga SQL Query: " + selectOngoingMangaSql); // Debugging statement
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectOngoingMangaSql);
            preparedStatement.setInt(1, limit);
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
            System.out.println("Ongoing Manga List Size: " + mangaList.size()); // Debugging statement
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangaList;
    }
    
    public List<Manga> searchManga(String searchTerm) {
        List<Manga> mangaList = new ArrayList<>();
        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            String searchSql = "SELECT DISTINCT m.* FROM manga m " +
                               "JOIN manga_genre mg ON m.manga_id = mg.manga_id " +
                               "JOIN genre g ON mg.genre_id = g.genre_id " +
                               "WHERE m.mangatitle LIKE ? OR g.genrename LIKE ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(searchSql);
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
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
            System.out.println("Search Results Size: " + mangaList.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangaList;
    }
}
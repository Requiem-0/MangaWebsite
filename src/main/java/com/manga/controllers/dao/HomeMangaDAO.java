package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeMangaDAO {

    // Fetch manga for the home page with pagination, genre filter, and sort options
    public List<Manga> getHomeManga(int offset, int recordsPerPage, String genre, String sortBy) {
        List<Manga> mangaList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT m.* FROM manga m");
            boolean hasWhereClause = false;

            if (genre != null && !genre.isEmpty()) {
                queryBuilder.append(" JOIN manga_genre mg ON m.manga_id = mg.manga_id")
                            .append(" JOIN genre g ON mg.genre_id = g.genre_id")
                            .append(" WHERE g.genrename = ?");
                hasWhereClause = true;
            }

            if (sortBy != null && !sortBy.isEmpty()) {
                if (!hasWhereClause) {
                    queryBuilder.append(" WHERE 1=1"); // Placeholder to ensure the WHERE clause is always present
                } else {
                    queryBuilder.append(" AND");
                }
                queryBuilder.append(" ORDER BY ");
                switch (sortBy) {
                    case "hottest":
                        queryBuilder.append("m.published_date DESC");
                        break;
                    case "latest":
                        queryBuilder.append("m.manga_id DESC");
                        break;
                    case "alpha":
                        queryBuilder.append("m.mangatitle ASC");
                        break;
                }
            }

            queryBuilder.append(" LIMIT ? OFFSET ?");
            System.out.println("SQL Query: " + queryBuilder.toString()); // Debugging statement

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                int paramIndex = 1;
                if (genre != null && !genre.isEmpty()) {
                    preparedStatement.setString(paramIndex++, genre);
                    System.out.println("Genre: " + genre); // Debugging statement
                }
                preparedStatement.setInt(paramIndex++, recordsPerPage);
                preparedStatement.setInt(paramIndex, offset);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        mangaList.add(mapManga(resultSet));
                    }
                }
            }
            System.out.println("Manga List Size: " + mangaList.size()); // Debugging statement
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangaList;
    }

    // Get the total count of manga based on genre filter
    public int getTotalMangaCount(String genre) {
        int count = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            StringBuilder countSql = new StringBuilder("SELECT COUNT(DISTINCT m.manga_id) FROM manga m");
            if (genre != null && !genre.isEmpty()) {
                countSql.append(" JOIN manga_genre mg ON m.manga_id = mg.manga_id")
                        .append(" JOIN genre g ON mg.genre_id = g.genre_id")
                        .append(" WHERE g.genrename = ?");
            }
            System.out.println("Count SQL Query: " + countSql.toString()); // Debugging statement

            try (PreparedStatement preparedStatement = connection.prepareStatement(countSql.toString())) {
                if (genre != null && !genre.isEmpty()) {
                    preparedStatement.setString(1, genre);
                    System.out.println("Genre for Count: " + genre); // Debugging statement
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Total Manga Count: " + count); // Debugging statement
        return count;
    }

    // Fetch ongoing manga
    public List<Manga> getOngoingManga(int limit) {
        List<Manga> mangaList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM manga WHERE status = 'Ongoing' LIMIT ?";
            System.out.println("Ongoing Manga SQL Query: " + query); // Debugging statement

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, limit);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        mangaList.add(mapManga(resultSet));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Ongoing Manga List Size: " + mangaList.size()); // Debugging statement
        return mangaList;
    }

    // Search manga by title or genre
    public List<Manga> searchManga(String searchTerm) {
        List<Manga> mangaList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT m.* FROM manga m " +
                           "JOIN manga_genre mg ON m.manga_id = mg.manga_id " +
                           "JOIN genre g ON mg.genre_id = g.genre_id " +
                           "WHERE m.mangatitle LIKE ? OR g.genrename LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String searchPattern = "%" + searchTerm + "%";
                preparedStatement.setString(1, searchPattern);
                preparedStatement.setString(2, searchPattern);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        mangaList.add(mapManga(resultSet));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Search Results Size: " + mangaList.size());
        return mangaList;
    }

    // Fetch manga by ID
    public Manga getMangaById(int mangaId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM manga WHERE manga_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, mangaId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapManga(resultSet);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch genres for a manga by ID
    public List<String> getGenresByMangaId(int mangaId) {
        List<String> genres = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT g.genrename FROM genre g " +
                           "JOIN manga_genre mg ON g.genre_id = mg.genre_id " +
                           "WHERE mg.manga_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, mangaId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        genres.add(resultSet.getString("genrename"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return genres;
    }

    // Helper method to map ResultSet to Manga object
    private Manga mapManga(ResultSet resultSet) throws SQLException {
        Manga manga = new Manga();
        manga.setMangaId(resultSet.getInt("manga_id"));
        manga.setTitle(resultSet.getString("mangatitle"));
        manga.setAuthor(resultSet.getString("author"));
        manga.setDescription(resultSet.getString("mangadescription"));
        manga.setStatus(resultSet.getString("status"));
        manga.setPublishedDate(resultSet.getString("published_date"));
        manga.setMangaImage(resultSet.getString("mangaImage"));
        return manga;
    }
}
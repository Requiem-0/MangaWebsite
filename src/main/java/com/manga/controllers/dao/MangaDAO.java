package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MangaDAO {

    // Method to add a new manga to the database
    public boolean addManga(Manga manga) {
        boolean isAddedSuccessfully = false;

        try {
            // Get database connection
            Connection databaseConnection = DatabaseConnection.getConnection();

            // Insert manga into the manga table
            String insertMangaSql = "INSERT INTO manga (mangatitle, author, mangadescription, status, published_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertMangaSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manga.getTitle());
            preparedStatement.setString(2, manga.getAuthor());
            preparedStatement.setString(3, manga.getDescription());
            preparedStatement.setString(4, manga.getStatus());
            preparedStatement.setString(5, manga.getPublishedDate());

            int rowsInserted = preparedStatement.executeUpdate();

            // Check if the manga was successfully inserted
            if (rowsInserted > 0) {
                // Get the generated manga_id
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int mangaId = generatedKeys.getInt(1);
                    manga.setMangaId(mangaId); // Set the mangaId for the object

                    // Now insert genres into the manga_genre table
                    isAddedSuccessfully = addGenresToManga(mangaId, manga.getGenres(), databaseConnection);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print error if any issue happens
        }

        return isAddedSuccessfully;
    }

    // Helper method to insert genres into manga_genre table
    private boolean addGenresToManga(int mangaId, List<String> genres, Connection databaseConnection) {
        boolean isSuccessful = false;

        try {
            // Prepare SQL to insert genres into manga_genre
            String insertGenreSql = "INSERT INTO manga_genre (manga_id, genre_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertGenreSql);

            for (String genreName : genres) {
                // Check if the genre exists
                int genreId = getGenreIdByName(genreName, databaseConnection);
                if (genreId == -1) {
                    // If the genre doesn't exist, insert it into the genre table
                    genreId = insertGenre(genreName, databaseConnection);
                }

                // Link the manga with the genre
                preparedStatement.setInt(1, mangaId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.addBatch(); // Add to batch for performance
            }

            // Execute all genre insertions in batch
            int[] results = preparedStatement.executeBatch();
            isSuccessful = results.length == genres.size(); // If all genres were linked successfully

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccessful;
    }

    // Helper method to get genreId by genre name
    private int getGenreIdByName(String genreName, Connection conn) throws SQLException {
        String sql = "SELECT genre_id FROM genre WHERE genrename = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genreName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("genre_id");
            }
        }
        return -1; // Return -1 if genre does not exist
    }

    // Helper method to insert a new genre into the genre table
    private int insertGenre(String genreName, Connection conn) throws SQLException {
        String sql = "INSERT INTO genre (genrename) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genreName);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the new genre's ID
                }
            }
        }
        return -1; // Return -1 if the insert fails
    }

    // Method to retrieve all manga records from the database
    public List<Manga> getAllManga() {
        List<Manga> mangaList = new ArrayList<>();

        try {
            // Get database connection
            Connection databaseConnection = DatabaseConnection.getConnection();

            // Simple SQL SELECT query
            String selectAllMangaSql = "SELECT * FROM manga";

            // Use Statement (no parameters needed here)
            Statement sqlStatement = databaseConnection.createStatement();

            // Execute the query and get results
            ResultSet mangaResultSet = sqlStatement.executeQuery(selectAllMangaSql);

            // Loop through each result (row)
            while (mangaResultSet.next()) {
                Manga manga = new Manga();

                // Fill the Manga object with data from the row
                manga.setMangaId(mangaResultSet.getInt("manga_id"));
                manga.setTitle(mangaResultSet.getString("mangatitle"));
                manga.setAuthor(mangaResultSet.getString("author"));
                manga.setDescription(mangaResultSet.getString("mangadescription"));
                manga.setStatus(mangaResultSet.getString("status"));
                manga.setPublishedDate(mangaResultSet.getString("published_date"));

                // Add the manga to the list
                mangaList.add(manga);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return mangaList;
    }

    // Optional: Delete manga by ID
    public boolean deleteManga(int mangaId) {
        boolean isDeletedSuccessfully = false;

        try {
            Connection databaseConnection = DatabaseConnection.getConnection();

            // Step 1: Delete related genres from manga_genre
            String deleteGenresSql = "DELETE FROM manga_genre WHERE manga_id = ?";
            PreparedStatement genreStmt = databaseConnection.prepareStatement(deleteGenresSql);
            genreStmt.setInt(1, mangaId);
            genreStmt.executeUpdate();

            // Step 2: Now delete the manga
            String deleteMangaSql = "DELETE FROM manga WHERE manga_id = ?";
            PreparedStatement mangaStmt = databaseConnection.prepareStatement(deleteMangaSql);
            mangaStmt.setInt(1, mangaId);

            int rowsDeleted = mangaStmt.executeUpdate();
            isDeletedSuccessfully = rowsDeleted > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isDeletedSuccessfully;
    }

    public boolean editManga(Manga manga) {
        boolean isUpdatedSuccessfully = false;

        try {
            Connection databaseConnection = DatabaseConnection.getConnection();

            // SQL UPDATE with placeholders
            String updateMangaSql = "UPDATE manga SET mangatitle=?, author=?, mangadescription=?, status=?, published_date=? WHERE manga_id=?";

            // Prepare the SQL statement
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateMangaSql);

            // Set values from the manga object
            preparedStatement.setString(1, manga.getTitle());
            preparedStatement.setString(2, manga.getAuthor());
            preparedStatement.setString(3, manga.getDescription());
            preparedStatement.setString(4, manga.getStatus());
            preparedStatement.setString(5, manga.getPublishedDate());
            preparedStatement.setInt(6, manga.getMangaId()); // ID is used to locate the correct row

            int rowsUpdated = preparedStatement.executeUpdate();
            isUpdatedSuccessfully = rowsUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isUpdatedSuccessfully;
    }
}

package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MangaDAO {

    // Method to add a new manga to the database
	public boolean addManga(Manga manga) {
	    if (manga == null || manga.getTitle() == null || manga.getTitle().isEmpty()) {
	        System.out.println("Manga title is required.");
	        return false;
	    }
	    if (manga.getAuthor() == null || manga.getAuthor().isEmpty()) {
	        System.out.println("Manga author is required.");
	        return false;
	    }
	    if (manga.getDescription() == null || manga.getDescription().isEmpty()) {
	        System.out.println("Manga description is required.");
	        return false;
	    }

	    boolean isAddedSuccessfully = false;

	    try {
	        Connection databaseConnection = DatabaseConnection.getConnection();

	        String insertMangaSql = "INSERT INTO manga (mangatitle, author, mangadescription, status, published_date, mangaImage) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertMangaSql, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, manga.getTitle());
	        preparedStatement.setString(2, manga.getAuthor());
	        preparedStatement.setString(3, manga.getDescription());
	        preparedStatement.setString(4, manga.getStatus());
	        preparedStatement.setString(5, manga.getPublishedDate());
	        preparedStatement.setString(6, manga.getMangaImage());  // Use mangaImage here

	        int rowsInserted = preparedStatement.executeUpdate();

	        if (rowsInserted > 0) {
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int mangaId = generatedKeys.getInt(1);
	                manga.setMangaId(mangaId);

	                isAddedSuccessfully = addGenresToManga(mangaId, manga.getGenres(), databaseConnection);
	            }
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return isAddedSuccessfully;
	}


    private boolean addGenresToManga(int mangaId, List<String> genres, Connection databaseConnection) {
        if (genres == null || genres.isEmpty()) {
            System.out.println("Genres list is empty or null.");
            return false;
        }

        boolean isSuccessful = false;

        try {
            String insertGenreSql = "INSERT INTO manga_genre (manga_id, genre_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertGenreSql);

            for (String genreName : genres) {
                if (genreName == null || genreName.isEmpty()) {
                    System.out.println("Invalid genre name: " + genreName);
                    continue;
                }

                int genreId = getGenreIdByName(genreName, databaseConnection);
                if (genreId == -1) {
                    genreId = insertGenre(genreName, databaseConnection);
                }

                preparedStatement.setInt(1, mangaId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();
            isSuccessful = results.length == genres.size();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccessful;
    }

    private int getGenreIdByName(String genreName, Connection conn) throws SQLException {
        if (genreName == null || genreName.isEmpty()) {
            System.out.println("Invalid genre name: " + genreName);
            return -1;
        }

        String sql = "SELECT genre_id FROM genre WHERE genrename = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genreName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("genre_id");
            }
        }
        return -1;
    }

    private int insertGenre(String genreName, Connection conn) throws SQLException {
        if (genreName == null || genreName.isEmpty()) {
            System.out.println("Invalid genre name: " + genreName);
            return -1;
        }

        String sql = "INSERT INTO genre (genrename) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genreName);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }
    public List<Manga> getAllManga() {
        List<Manga> mangaList = new ArrayList<>();

        try {
            Connection databaseConnection = DatabaseConnection.getConnection();
            String selectAllMangaSql = "SELECT m.manga_id, m.mangatitle, m.author, m.mangadescription, m.status, m.published_date, m.mangaImage, g.genrename " +
                                       "FROM manga m " +
                                       "LEFT JOIN manga_genre mg ON m.manga_id = mg.manga_id " +
                                       "LEFT JOIN genre g ON mg.genre_id = g.genre_id";
            Statement sqlStatement = databaseConnection.createStatement();
            ResultSet mangaResultSet = sqlStatement.executeQuery(selectAllMangaSql);

            Manga currentManga = null;
            List<String> genres = null;

            while (mangaResultSet.next()) {
                int mangaId = mangaResultSet.getInt("manga_id");
                String title = mangaResultSet.getString("mangatitle");
                String author = mangaResultSet.getString("author");
                String description = mangaResultSet.getString("mangadescription");
                String status = mangaResultSet.getString("status");
                String publishedDate = mangaResultSet.getString("published_date");
                String mangaImage = mangaResultSet.getString("mangaImage");  // Add this
                String genreName = mangaResultSet.getString("genrename");

                if (currentManga == null || currentManga.getMangaId() != mangaId) {
                    if (currentManga != null) {
                        currentManga.setGenres(genres);
                        mangaList.add(currentManga);
                    }

                    currentManga = new Manga();
                    currentManga.setMangaId(mangaId);
                    currentManga.setTitle(title);
                    currentManga.setAuthor(author);
                    currentManga.setDescription(description);
                    currentManga.setStatus(status);
                    currentManga.setPublishedDate(publishedDate);
                    currentManga.setMangaImage(mangaImage);  // Set image here

                    genres = new ArrayList<>();
                }

                if (genreName != null) {
                    genres.add(genreName);
                }
            }

            if (currentManga != null) {
                currentManga.setGenres(genres);
                mangaList.add(currentManga);
            }

            System.out.println("Manga List Size: " + mangaList.size());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return mangaList;
    }


    public boolean deleteManga(int mangaId) {
        if (mangaId <= 0) {
            System.out.println("Invalid manga ID.");
            return false;
        }

        boolean isDeletedSuccessfully = false;

        try {
            Connection databaseConnection = DatabaseConnection.getConnection();

            String deleteGenresSql = "DELETE FROM manga_genre WHERE manga_id = ?";
            PreparedStatement genreStmt = databaseConnection.prepareStatement(deleteGenresSql);
            genreStmt.setInt(1, mangaId);
            genreStmt.executeUpdate();

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
        if (manga == null || manga.getMangaId() <= 0) {
            System.out.println("Invalid manga or manga ID.");
            return false;
        }
        if (manga.getTitle() == null || manga.getTitle().isEmpty()) {
            System.out.println("Manga title cannot be empty.");
            return false;
        }
        if (manga.getAuthor() == null || manga.getAuthor().isEmpty()) {
            System.out.println("Manga author cannot be empty.");
            return false;
        }

        boolean isUpdatedSuccessfully = false;

        try {
            Connection databaseConnection = DatabaseConnection.getConnection();

            String updateMangaSql = "UPDATE manga SET mangatitle=?, author=?, mangadescription=?, status=?, published_date=? WHERE manga_id=?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateMangaSql);
            preparedStatement.setString(1, manga.getTitle());
            preparedStatement.setString(2, manga.getAuthor());
            preparedStatement.setString(3, manga.getDescription());
            preparedStatement.setString(4, manga.getStatus());
            preparedStatement.setString(5, manga.getPublishedDate());
            preparedStatement.setInt(6, manga.getMangaId());

            int rowsUpdated = preparedStatement.executeUpdate();
            isUpdatedSuccessfully = rowsUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isUpdatedSuccessfully;
    }

    // New method to count total number of manga
    public int getMangaCount() {
        int count = 0;

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT COUNT(manga_id) FROM manga";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return count;
    }

    // New method to count total number of unique genres
    public int getGenreCount() {
        int count = 0;

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM genre";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return count;
    }

   
    public boolean uploadMangaImage(int mangaId, String mangaImage) {
        if (mangaId <= 0) {
            System.out.println("Invalid manga ID.");
            return false;
        }
        if (mangaImage == null || mangaImage.isEmpty()) {
            System.out.println("Image path cannot be null or empty.");
            return false;
        }

        boolean isUpdatedSuccessfully = false;

        try {
            Connection conn = DatabaseConnection.getConnection();

            // Make sure your manga table has a column like 'image_path' to store the filename/path
            String updateImageSql = "UPDATE manga SET mangaImage = ? WHERE manga_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateImageSql);
            stmt.setString(1, mangaImage);
            stmt.setInt(2, mangaId);

            int rowsUpdated = stmt.executeUpdate();
            isUpdatedSuccessfully = rowsUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isUpdatedSuccessfully;
    }
}

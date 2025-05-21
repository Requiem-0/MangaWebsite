package com.manga.controllers.dao;

import com.manga.database.DatabaseConnection;
import com.manga.models.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDAO {

	public List<Bookmark> getAllBookmarks() {
	    List<Bookmark> bookmarks = new ArrayList<>();

	    try (Connection conn = DatabaseConnection.getConnection()) {
	    	String sql = "SELECT b.manga_id, m.mangatitle AS manga_title, m.mangaImage AS manga_image, u.username " +
	                "FROM bookmarks b " +
	                "JOIN users u ON b.user_id = u.user_id " +
	                "JOIN manga m ON b.manga_id = m.manga_id";

	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            int mangaId = rs.getInt("manga_id");
	            String mangaTitle = rs.getString("manga_title");
	            String mangaImage = rs.getString("manga_image");
	            String username = rs.getString("username");
	            bookmarks.add(new Bookmark(mangaId, mangaTitle, mangaImage, username));
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return bookmarks;
	} }
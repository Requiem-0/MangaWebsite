	package com.manga.controllers.dao;
	
	import com.manga.database.DatabaseConnection;
	import com.manga.models.Volume;
	
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	
	public class VolumeDAO {
	
	    public boolean addVolume(Volume volume) {
	        if (volume == null || volume.getVolumeNumber() <= 0 || volume.getMangaId() <= 0) {
	            System.out.println("Invalid volume data.");
	            return false;
	        }
	
	        boolean isAdded = false;
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "INSERT INTO volume (isbn, volumenumber, volume_img, manga_id) VALUES (?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, volume.getIsbn());
	            stmt.setInt(2, volume.getVolumeNumber());
	            stmt.setString(3, volume.getVolume_img());
	            stmt.setInt(4, volume.getMangaId());
	
	            int rows = stmt.executeUpdate();
	            isAdded = rows > 0;
	
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
	        return isAdded;
	    }
	
	    public boolean deleteVolume(int volumeId) {
	        if (volumeId <= 0) {
	            System.out.println("Invalid volume ID.");
	            return false;
	        }
	
	        boolean isDeleted = false;
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "DELETE FROM volume WHERE volume_id = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, volumeId);
	
	            int rows = stmt.executeUpdate();
	            isDeleted = rows > 0;
	
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
	        return isDeleted;
	    }
	
	    public List<Volume> getAllVolumes() {
	        List<Volume> volumeList = new ArrayList<>();
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "SELECT * FROM volume";
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	
	            while (rs.next()) {
	                Volume v = new Volume();
	                v.setVolumeId(rs.getInt("volume_id"));
	                v.setIsbn(rs.getString("isbn"));
	                v.setVolumeNumber(rs.getInt("volumenumber"));
	                v.setVolume_img(rs.getString("volume_img"));
	                v.setMangaId(rs.getInt("manga_id"));
	
	                volumeList.add(v);
	            }
	
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
	        return volumeList;
	    }
	    
	    public List<Volume> getVolumesByMangaId(int mangaId) {
	        List<Volume> volumeList = new ArrayList<>();
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "SELECT * FROM volume WHERE manga_id = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, mangaId);
	            ResultSet rs = stmt.executeQuery();
	
	            while (rs.next()) {
	                Volume v = new Volume();
	                v.setVolumeId(rs.getInt("volume_id"));
	                v.setIsbn(rs.getString("isbn"));
	                v.setVolumeNumber(rs.getInt("volumenumber"));
	                v.setVolume_img(rs.getString("volume_img"));
	                v.setMangaId(rs.getInt("manga_id"));
	
	                volumeList.add(v);
	            }
	
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
	        return volumeList;
	    }
	
	
	    public int getVolumeCount() {
	        int count = 0;
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "SELECT COUNT(volume_id) FROM volume";
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
	
	    public boolean uploadVolumeImage(int volumeId, String volume_img) {
	        if (volumeId <= 0 || volume_img == null || volume_img.isEmpty()) {
	            System.out.println("Invalid volume ID or image path.");
	            return false;
	        }
	
	        boolean isUploaded = false;
	
	        try {
	            Connection conn = DatabaseConnection.getConnection();
	            String sql = "UPDATE volume SET volume_img = ? WHERE volume_id = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, volume_img);
	            stmt.setInt(2, volumeId);
	
	            int rows = stmt.executeUpdate();
	            isUploaded = rows > 0;
	
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
	        return isUploaded;
	    }
	}

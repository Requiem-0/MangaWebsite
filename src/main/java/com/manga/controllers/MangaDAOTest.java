package com.manga.controllers;

import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

import java.util.Arrays;
import java.util.List;

public class MangaDAOTest {
    public static void main(String[] args) {
        // Create DAO instance
        MangaDAO mangaDAO = new MangaDAO();

        // 1. Test addManga method
        List<String> genreList = Arrays.asList("Adventure", "Action"); // multiple genres
        Manga manga = new Manga("One Piece", "Eiichiro Oda", "Ongoing", "1997-07-22", "A story about pirates.");
        manga.setGenres(genreList);

        boolean isAdded = mangaDAO.addManga(manga);
        System.out.println("Manga added successfully: " + isAdded);

        // 2. Test getAllManga method
        List<Manga> mangaList = mangaDAO.getAllManga();
        System.out.println("\nAll Manga:");
        for (Manga m : mangaList) {
            System.out.println("Manga ID: " + m.getMangaId() +
                    ", Title: " + m.getTitle() +
                    ", Genres: " + m.getGenres());
        }

        // 3. Test deleteManga method (delete by ID)
        boolean isDeleted = mangaDAO.deleteManga(1); // Delete manga with ID 1 if exists
        System.out.println("\nManga deleted successfully: " + isDeleted);

        // 4. Test updateManga method (e.g., change description)
        if (!mangaList.isEmpty()) {
            Manga firstManga = mangaList.get(0);
            firstManga.setDescription("An updated story about pirates.");
            firstManga.setGenres(Arrays.asList("Adventure", "Drama")); // update genres too
            boolean isUpdated = mangaDAO.editManga(firstManga);
            System.out.println("\nManga updated successfully: " + isUpdated);
        }
    }
}

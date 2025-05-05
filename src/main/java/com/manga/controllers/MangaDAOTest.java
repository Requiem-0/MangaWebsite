package com.manga.controllers;

import java.util.List;

import com.manga.controllers.dao.MangaDAO;
import com.manga.models.Manga;

public class MangaDAOTest {
    public static void main(String[] args) {
        // Create an instance of MangaDAO
        MangaDAO mangaDAO = new MangaDAO();

        // Test: Adding a new manga
        Manga newManga = new Manga();
        newManga.setTitle("Naruto");
        newManga.setAuthor("Masashi Kishimoto");
        newManga.setDescription("A young ninja seeks recognition from his peers and dreams of becoming the Hokage.");
        newManga.setStatus("Ongoing");
        newManga.setPublishedDate("1999-09-21");
        newManga.setGenres(List.of("Action", "Adventure", "Shonen"));

        boolean isAdded = mangaDAO.addManga(newManga);
        System.out.println("Add Manga: " + (isAdded ? "Success" : "Failed"));

        // Test: Retrieving all manga
        List<Manga> mangaList = mangaDAO.getAllManga();
        System.out.println("Manga List: " + mangaList.size() + " manga(s) found.");
        for (Manga manga : mangaList) {
            System.out.println("Manga ID: " + manga.getMangaId());
            System.out.println("Title: " + manga.getTitle());
            System.out.println("Author: " + manga.getAuthor());
            System.out.println("Description: " + manga.getDescription());
            System.out.println("Status: " + manga.getStatus());
            System.out.println("Published Date: " + manga.getPublishedDate());
            System.out.println("Genres: " + (manga.getGenres() != null ? String.join(", ", manga.getGenres()) : "No genres"));
            System.out.println("--------------------------");
        }

        // Test: Editing a manga (update the description of the first manga)
        if (!mangaList.isEmpty()) {
            Manga mangaToEdit = mangaList.get(0);
            mangaToEdit.setDescription("Updated description for the manga.");
            boolean isEdited = mangaDAO.editManga(mangaToEdit);
            System.out.println("Edit Manga: " + (isEdited ? "Success" : "Failed"));
        }

        // Test: Deleting a manga (delete the first manga)
        if (!mangaList.isEmpty()) {
            Manga mangaToDelete = mangaList.get(0);
            boolean isDeleted = mangaDAO.deleteManga(mangaToDelete.getMangaId());
            System.out.println("Delete Manga: " + (isDeleted ? "Success" : "Failed"));
        }

        // Test: Count total manga
        int totalManga = mangaDAO.getMangaCount();
        System.out.println("Total Manga in Database: " + totalManga);

        // Test: Count total unique genres
        int totalGenres = mangaDAO.getGenreCount();
        System.out.println("Total Unique Genres in Database: " + totalGenres);
    }
}

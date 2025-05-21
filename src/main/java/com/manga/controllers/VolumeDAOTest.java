package com.manga.controllers;

import com.manga.controllers.dao.VolumeDAO;
import com.manga.models.Volume;

import java.util.List;

public class VolumeDAOTest {
    public static void main(String[] args) {
        VolumeDAO volumeDAO = new VolumeDAO();

        // Test: Adding a new volume
        Volume newVolume = new Volume();
        newVolume.setIsbn("978-1234567890");
        newVolume.setVolumeNumber(1);
        newVolume.setVolume_img("volume1_cover.jpg");
        newVolume.setMangaId(1);  // Assume manga with ID 1 exists

        boolean isAdded = volumeDAO.addVolume(newVolume);
        System.out.println("Add Volume: " + (isAdded ? "Success" : "Failed"));

        // Test: Retrieving all volumes
        List<Volume> volumes = volumeDAO.getAllVolumes();
        System.out.println("Total Volumes Found: " + volumes.size());
        for (Volume vol : volumes) {
            System.out.println("Volume ID: " + vol.getVolumeId());
            System.out.println("ISBN: " + vol.getIsbn());
            System.out.println("Volume Number: " + vol.getVolumeNumber());
            System.out.println("Image: " + vol.getVolume_img());
            System.out.println("Manga ID: " + vol.getMangaId());
            System.out.println("-------------------");
        }

        // Test: Get volumes by manga ID
        int mangaIdToSearch = 1;
        List<Volume> mangaVolumes = volumeDAO.getVolumesByMangaId(mangaIdToSearch);
        System.out.println("Volumes for Manga ID " + mangaIdToSearch + ": " + mangaVolumes.size());

        // Test: Get total volume count
        int volumeCount = volumeDAO.getVolumeCount();
        System.out.println("Total Volume Count: " + volumeCount);

        // Test: Upload (update) volume image of first volume
        if (!volumes.isEmpty()) {
            int volId = volumes.get(0).getVolumeId();
            boolean imageUpdated = volumeDAO.uploadVolumeImage(volId, "updated_cover.jpg");
            System.out.println("Update Volume Image: " + (imageUpdated ? "Success" : "Failed"));
        }

        // Test: Delete volume (delete last volume added)
        if (!volumes.isEmpty()) {
            int volIdToDelete = volumes.get(volumes.size() - 1).getVolumeId();
            boolean isDeleted = volumeDAO.deleteVolume(volIdToDelete);
            System.out.println("Delete Volume ID " + volIdToDelete + ": " + (isDeleted ? "Success" : "Failed"));
        }
    }
}

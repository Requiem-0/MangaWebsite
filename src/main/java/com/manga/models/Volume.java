package com.manga.models;

public class Volume {
    private int volumeId;
    private String isbn;
    private int volumeNumber;
    private String volume_img; 
    private int mangaId;

   
    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getVolume_img() {
        return volume_img;
    }

    public void setVolume_img(String volume_img) {
        this.volume_img = volume_img;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }
} 

package com.manga.models;

public class Chapter {
    private int chapterId;
    private int chapterno;
    private String chaptertitle;
    private int volumeId;
    private String chapter_pdf; // Renamed from pdfPath

    // No-argument constructor
    public Chapter() {
    }

    // Parameterized constructor
    public Chapter(int chapterId, int chapterno, String chaptertitle, int volumeId, String chapter_pdf) {
        this.chapterId = chapterId;
        this.chapterno = chapterno;
        this.chaptertitle = chaptertitle;
        this.volumeId = volumeId;
        this.chapter_pdf = chapter_pdf;
    }

    // Getters and Setters
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getChapterno() {
        return chapterno;
    }

    public void setChapterno(int chapterno) {
        this.chapterno = chapterno;
    }

    public String getChaptertitle() {
        return chaptertitle;
    }

    public void setChaptertitle(String chaptertitle) {
        this.chaptertitle = chaptertitle;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    public String getChapter_pdf() {
        return chapter_pdf;
    }

    public void setChapter_pdf(String chapter_pdf) {
        this.chapter_pdf = chapter_pdf;
    }
}
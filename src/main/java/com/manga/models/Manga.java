package com.manga.models;

public class Manga {
    private int mangaId;        // Primary key
    private String title;       // Manga title
    private String author;      // Author of the manga
    private String genre;       // Genre of the manga 
    private String status;      // Status of the manga 
    private String publishedDate; // The date when the manga was published
    private String description; // Manga description

    // Constructor
    public Manga() {
    }

    public Manga(String title, String author, String genre, String status, String publishedDate, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.publishedDate = publishedDate;
        this.description = description;
    }

	public int getMangaId() {
		return mangaId;
	}

	public void setMangaId(int mangaId) {
		this.mangaId = mangaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

   
}

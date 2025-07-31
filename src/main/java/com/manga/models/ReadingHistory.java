
package com.manga.models;

import java.sql.Date;
import java.sql.Timestamp;

public class ReadingHistory {

    public static class ReadingHistoryWithManga {
        private int historyId;
        private Timestamp lastReadDate;
        private int mangaId;
        private String mangaTitle;
        private String author;
        private String description;
        private String status;
        private Date publishedDate;
        private String mangaImage;  // NEW FIELD

        public ReadingHistoryWithManga(int historyId, Timestamp lastReadDate, int mangaId,
                                       String mangaTitle, String author, String description,
                                       String status, Date publishedDate, String mangaImage) {
            this.historyId = historyId;
            this.lastReadDate = lastReadDate;
            this.mangaId = mangaId;
            this.mangaTitle = mangaTitle;
            this.author = author;
            this.description = description;
            this.status = status;
            this.publishedDate = publishedDate;
            this.mangaImage = mangaImage;
        }

        // Getters
        public int getHistoryId() { return historyId; }
        public Timestamp getLastReadDate() { return lastReadDate; }
        public int getMangaId() { return mangaId; }
        public String getMangaTitle() { return mangaTitle; }
        public String getAuthor() { return author; }
        public String getDescription() { return description; }
        public String getStatus() { return status; }
        public Date getPublishedDate() { return publishedDate; }
        public String getMangaImage() { return mangaImage; }
    }
}
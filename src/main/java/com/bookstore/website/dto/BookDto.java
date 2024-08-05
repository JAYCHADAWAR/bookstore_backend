package com.bookstore.website.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class BookDto {
    @Id
    private UUID id;
    private String bookname;
    private String filepath;
    private String imagepath;
    private String likes;
    private boolean liked;
    // Constructor, getters, and setters
    public BookDto(UUID id, String bookname, String filepath, String imagepath, String likes, boolean liked) {
        this.id = id;
        this.bookname = bookname;
        this.filepath = filepath;
        this.imagepath = imagepath;
        this.likes = likes;
        this.liked = liked;
    }
}

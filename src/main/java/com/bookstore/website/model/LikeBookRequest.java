package com.bookstore.website.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeBookRequest {
    private String bookId;
    private int finalValue;

    // Default constructor is needed for some frameworks
    public LikeBookRequest() {
    }

    // Getter and Setter methods
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(int finalValue) {
        this.finalValue = finalValue;
    }
}

package com.bookstore.website.model;

public class BookLikedResponse {
    private String bookId;
    private int finalValue;

    // Constructor
    public BookLikedResponse(String bookId, int finalValue) {
        this.bookId = bookId;
        this.finalValue = finalValue;
    }

    // Getters
    public String getBookId() {
        return bookId;
    }

    public int getFinalValue() {
        return finalValue;
    }

    // Setters
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setFinalValue(int finalValue) {
        this.finalValue = finalValue;
    }
}

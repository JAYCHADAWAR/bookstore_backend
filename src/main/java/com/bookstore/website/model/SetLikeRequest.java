package com.bookstore.website.model;

import java.util.UUID;

public class SetLikeRequest {
    private UUID bookId;
    private boolean liked;

    // Default constructor
    public SetLikeRequest() {
    }

    // Parameterized constructor
    public SetLikeRequest(UUID bookId, boolean liked) {
        this.bookId = bookId;
        this.liked = liked;
    }

    // Getter and setter methods
    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}

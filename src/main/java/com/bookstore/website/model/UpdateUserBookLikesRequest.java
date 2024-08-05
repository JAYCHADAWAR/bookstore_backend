package com.bookstore.website.model;

import java.util.UUID;

public class UpdateUserBookLikesRequest {
    private UUID bookId;
    private boolean liked;
    private UUID userId;

    // Constructors, getters, and setters...

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


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
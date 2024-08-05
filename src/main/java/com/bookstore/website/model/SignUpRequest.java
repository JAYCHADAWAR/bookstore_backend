package com.bookstore.website.model;

import java.util.UUID;

public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private UUID id;

    // Constructors, getters, and setters
    public SignUpRequest(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter methods
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

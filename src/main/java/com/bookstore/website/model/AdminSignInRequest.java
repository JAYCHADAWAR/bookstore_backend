package com.bookstore.website.model;

public class AdminSignInRequest {

    private String email;
    private String password;

    // Constructors, getters, and setters
    public AdminSignInRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

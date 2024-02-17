package com.bookstore.website.model;

public class SignInRequest {

    private String email;
    private String password;

    // Constructors, getters, and setters
    public SignInRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAndPasswordString() {
        return "Email: " + this.email + ", Password: " + this.password;
    }


}

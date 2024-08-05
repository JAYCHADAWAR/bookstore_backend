package com.bookstore.website.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(UUID id,String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = password;
    }

    public String getUser() {
        return "Email: " + this.email + ", Password: " + this.password +",name:" + this.name;
    }
}

package com.bookstore.website.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    private UUID id;
    @Column(name="admin_name")
    private String adminName;
    @Column(name="admin_email")
    private String adminEmail;
    @Column (name="admin_password")
    private String adminPassword;

    public UUID getId() {
        return id;
    }

    // Constructors, getters, and setters
}

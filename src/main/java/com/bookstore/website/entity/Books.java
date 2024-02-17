package com.bookstore.website.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="books")
public class Books {

    @Id
    private UUID id;
    @Column(name="name")
    private String name;
    @Column(name="path")
    private String path;
    @Column (name="image_path")
    private String image_path;

    @Column(name="likes")
    private String likes;


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String image_path) {
        this.image_path = image_path;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }


    public Books(UUID id, String name, String likes) {
        this.id = id;
        this.name = name;
        this.likes = likes;
    }





}
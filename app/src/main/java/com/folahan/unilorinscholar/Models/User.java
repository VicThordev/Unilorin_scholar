package com.folahan.unilorinscholar.Models;

public class User {
    public String username, email, search, image, cover, uid;

    public User() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String username, String email, String search, String image, String cover, String uid) {
        this.username = username;
        this.email = email;
        this.search = search;
        this.image = image;
        this.cover = cover;
        this.uid = uid;
    }

    public User(String username, String email, String search, String image, String cover) {
        this.username = username;
        this.email = email;
        this.search = search;
        this.image = image;
        this.cover = cover;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}

package com.example.coursework2.model;

import java.util.UUID;

public class Item {

    private UUID id;
    private UUID userId;
    private String name;
    private String imageUrl;
    private String price;
    private String webUrl;

    public Item(String name, String photoUrl, String price, String siteUrl, UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.imageUrl = photoUrl;
        this.price = price;
        this.webUrl = siteUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}

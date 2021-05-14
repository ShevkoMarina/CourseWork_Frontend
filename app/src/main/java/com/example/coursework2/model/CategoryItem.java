package com.example.coursework2.model;

public class CategoryItem {

    private String imageUrl;
    private String category;

    public CategoryItem(String imageUrl, String category) {
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}

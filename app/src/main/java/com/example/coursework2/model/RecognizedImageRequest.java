package com.example.coursework2.model;

public class RecognizedImageRequest {

    private String Category;
    private String ImageUri;

    public RecognizedImageRequest(String category, String imageUri) {
        Category = category;
        ImageUri = imageUri;
    }

    public String getCategory() {
        return Category;
    }

    public String getImageUri() {
        return ImageUri;
    }
}

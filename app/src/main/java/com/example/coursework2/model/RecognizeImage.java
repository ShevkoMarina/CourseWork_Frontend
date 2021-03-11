package com.example.coursework2.model;

public class RecognizeImage {

    public RecognizeImage(String userId, String imageUri) {
        this.imageUri = imageUri;
        this.userId = userId;
    }

    private String userId;
    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


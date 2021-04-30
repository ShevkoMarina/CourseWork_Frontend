package com.example.coursework2.model;

public class RecognizedImageRequest {

    private String UserId;
    private String ImageUri;

    public RecognizedImageRequest(String userId, String imageUri) {
        UserId = userId;
        ImageUri = imageUri;
    }

    public String getUserId() {
        return UserId;
    }

    public String getImageUri() {
        return ImageUri;
    }
}

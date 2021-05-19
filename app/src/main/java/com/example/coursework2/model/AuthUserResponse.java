package com.example.coursework2.model;

public class AuthUserResponse {

    private String accessToken;
    private String userId;

    public AuthUserResponse(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserId() {
        return userId;
    }
}

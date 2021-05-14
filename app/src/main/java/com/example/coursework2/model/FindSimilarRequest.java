package com.example.coursework2.model;

import java.util.Collection;

public class FindSimilarRequest {

    private Collection<RecognizedImageRequest> items;

    private String UserId;

    public FindSimilarRequest(Collection<RecognizedImageRequest> items, String userId) {
        this.items = items;
        this.UserId = userId;
    }

    public Collection<RecognizedImageRequest> getItems() {
        return items;
    }

    public String getUserId() {
        return UserId;
    }
}

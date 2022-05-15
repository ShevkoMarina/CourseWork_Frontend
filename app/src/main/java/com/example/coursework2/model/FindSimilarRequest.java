package com.example.coursework2.model;

import java.util.Collection;

public class FindSimilarRequest {

    private Collection<RecognizedImageRequest> items;

    private String UserId;

    private SearchSettings searchSettings;

    public FindSimilarRequest(Collection<RecognizedImageRequest> items, String userId, SearchSettings searchSettings) {
        this.items = items;
        this.UserId = userId;
        this.searchSettings = searchSettings;
    }

    public Collection<RecognizedImageRequest> getItems() {
        return items;
    }

    public String getUserId() {
        return UserId;
    }

    public SearchSettings getSearchSettings() {return searchSettings; }
}

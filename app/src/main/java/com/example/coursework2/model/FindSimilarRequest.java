package com.example.coursework2.model;

import java.util.Collection;

public class FindSimilarRequest {

    private Collection<RecognizedImageRequest> items;

    public FindSimilarRequest(Collection<RecognizedImageRequest> items) {
        this.items = items;
    }

    public Collection<RecognizedImageRequest> getItems() {
        return items;
    }
}

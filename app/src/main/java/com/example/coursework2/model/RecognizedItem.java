package com.example.coursework2.model;

public class RecognizedItem {

    private String url;
    private String name;

    public RecognizedItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}

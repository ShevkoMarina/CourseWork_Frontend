package com.example.coursework2.model;

import java.util.List;

public class SaveItemsRequest {

    public SaveItemsRequest(List<Item> items) {
        this.items = items;
    }

    List<Item> items;
}

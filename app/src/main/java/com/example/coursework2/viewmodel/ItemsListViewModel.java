package com.example.coursework2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.coursework2.model.Item;
import com.example.coursework2.repository.ItemRepository;
import java.util.List;
import java.util.UUID;

public class ItemsListViewModel extends ViewModel {

    private ItemRepository itemRepository;

    public ItemsListViewModel() {
        itemRepository = ItemRepository.getInstance();
    }

    public LiveData<List<Item>> getItems() {
        return itemRepository.getItemsResponse();
    }

    // Calling method in view model
    public void  searchItemApi(UUID userId) {
        itemRepository.searchItemApi(userId);
    }
}

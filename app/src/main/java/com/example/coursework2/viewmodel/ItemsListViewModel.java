package com.example.coursework2.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.model.CategoryItem;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedImageRequest;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ItemsListViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private SharedPreferences sp;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;

    public ItemsListViewModel(Activity activity) {
        this.activity = activity;
        itemRepository = ItemRepository.getInstance();
    }

    public LiveData<List<Item>> getItems() {
        return itemRepository.getItemsResponse();
    }

    public LiveData<List<Item>> getSimilarItems() { return itemRepository.getSimilarItems();}

    public LiveData<List<CategoryItem>> getCategoryItems() {return itemRepository.getCategoryItems(); }

    // Calling method in view model
    public void getUserItems(UUID userId, String category) {
        //itemRepository.getUserItems(userId);
        itemRepository.getItemsByCategory(userId, category);
    }

    public void getSimilarItems(List<RecognizedItem> croppedImages) {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");

        Collection<RecognizedImageRequest> items = new ArrayList<>();
        for (int i = 0; i < croppedImages.size(); i++)  {
            items.add(new RecognizedImageRequest(croppedImages.get(i).getName(), croppedImages.get(i).getUrl()));
        }

        itemRepository.getSimilarItems(items, userId);
    }

    public void saveCheckedItems(List<Item> checkedItems) {
        itemRepository.saveCheckedItems(checkedItems);
    }

    public void getCategories() {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        itemRepository.getCategories(UUID.fromString(userId));
    }

}

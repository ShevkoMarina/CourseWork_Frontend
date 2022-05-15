package com.example.coursework2.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.model.CategoryItem;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedImageRequest;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.model.SearchSettings;
import com.example.coursework2.repository.ItemRepository;
import com.example.coursework2.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ItemsListViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private SharedPreferences sp;
    private Activity activity;
    private ConnectionDetector detector;

    public ItemsListViewModel(Activity activity) {
        this.activity = activity;
        itemRepository = ItemRepository.getInstance();
        detector = new ConnectionDetector(activity);
    }

    public LiveData<List<Item>> getItems() {
        return itemRepository.getItemsResponse();
    }

    public LiveData<List<Item>> getSimilarItems() { return itemRepository.getSimilarItems();}

    public LiveData<List<CategoryItem>> getCategoryItems() {return itemRepository.getCategoryItems(); }

    public void getUserItems(UUID userId, String category) {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String token =  sp.getString("token", "");
        if (detector.isConnected()) {
            itemRepository.getItemsByCategory(userId, category, token);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }

    public void getSimilarItems(List<RecognizedItem> croppedImages) {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        String token =  sp.getString("token", "");
        String searchSettings = sp.getString("searchSettings", "");

        Collection<RecognizedImageRequest> items = new ArrayList<>();
        for (int i = 0; i < croppedImages.size(); i++)  {
            items.add(new RecognizedImageRequest(croppedImages.get(i).getName(), croppedImages.get(i).getUrl()));
        }

        if (detector.isConnected()) {
            itemRepository.getSimilarItems(items, userId, token, Enum.valueOf(SearchSettings.class, searchSettings));
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }

    public void saveCheckedItems(List<Item> checkedItems) {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String token =  sp.getString("token", "");

        if (detector.isConnected()) {
            itemRepository.saveCheckedItems(checkedItems, token);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }

    public void getCategories() {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        String token =  sp.getString("token", "");

        if (detector.isConnected()) {
            itemRepository.getCategories(UUID.fromString(userId), token);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.coursework2.UI.saved_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursework2.R;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.model.Item;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedItemsActivity extends AppCompatActivity  implements  SavedItemsAdapter.OnSavedItemsListener {

    private RecyclerView recyclerView;
    private SavedItemsAdapter adapter;
    private ItemsListViewModel itemsListViewModel;
    private List<Item> savedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);
        recyclerView = findViewById(R.id.furniture_recycler_view);

        initObservers();
        initRecyclerView();

        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        String category = sp.getString("category", "");
        itemsListViewModel.getUserItems(UUID.fromString(userId), category);

    }

    private void initRecyclerView() {
        adapter = new SavedItemsAdapter(savedItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initObservers() {

        itemsListViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items != null) {
                    savedItems = items;
                    adapter.setSavedItems(items);
                }
            }
        });
    }

    @Override
    public void onSavedItemClick(int position) {
        List<Item> savedItems = adapter.getSavedItems();
        savedItems.get(position).openUrl(SavedItemsActivity.this);
        adapter.notifyItemChanged(position);
    }
}
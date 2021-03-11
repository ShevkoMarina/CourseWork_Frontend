package com.example.coursework2.UI.Items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;
import com.example.coursework2.model.Item;
import com.example.coursework2.viewmodel.ItemsListViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class ItemsListActivity extends AppCompatActivity implements OnFurnitureListener{

    // Recycler view
    private RecyclerView recyclerView;
    private FurnitureRecyclerView adapter;


    // ViewModel
    private ItemsListViewModel itemsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        itemsListViewModel = new ViewModelProvider(this).get(ItemsListViewModel.class);
        recyclerView = findViewById(R.id.furniture_recycler_view);

        // Calling the observers
        ObserveAnyChange();
        ConfigureRecyclerView();
        searchItemApi(UUID.fromString("3e5cd440-a499-4ac7-7f8c-08d8b8a6af56"));

    }

    // Observing any data change
    private void ObserveAnyChange() {

        itemsListViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                // Observing for any data change
                if (items != null) {
                        // Get the data &&&&&&&
                       adapter.setmFurnitureItems(items);
                }
            }
        });
    }

    // Calling method in Activity
    private void searchItemApi(UUID id) {
        itemsListViewModel.searchItemApi(id);
    }

    // Init recycler view & adding data to it
    private void ConfigureRecyclerView() {
        // Liva data can't be passed via the constructor
        adapter = new FurnitureRecyclerView(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onFurnitureClick(int position) {

    }

    @Override
    public void onWebClick(int position) {

    }
}
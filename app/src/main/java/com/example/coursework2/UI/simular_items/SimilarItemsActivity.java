package com.example.coursework2.UI.simular_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coursework2.R;
import com.example.coursework2.UI.saved_items.FurnitureRecyclerView;
import com.example.coursework2.UI.saved_items.OnFurnitureListener;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;
import com.example.coursework2.viewmodel.RecognizedItemsViewModel;
import com.example.coursework2.viewmodel.RecognizedItemsViewModelFactory;

import java.util.List;

public class SimilarItemsActivity extends AppCompatActivity implements OnFurnitureListener {

    private RecyclerView recyclerView;
    private FurnitureRecyclerView adapter;
    private ItemsListViewModel itemsListViewModel;
    private RecognizedItemsViewModel recognizedItemsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simular_items);
        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);
        recognizedItemsViewModel = new ViewModelProvider(this, new RecognizedItemsViewModelFactory(this)).get(RecognizedItemsViewModel.class);
        recyclerView = findViewById(R.id.similar_recycler);

        // Observers
        itemsListViewModel.getSimilarItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items != null) {
                    adapter.setmFurnitureItems(items);
                }
            }
        });

        ConfigureRecyclerView();

        List<RecognizedItem> recognizedItems = recognizedItemsViewModel.getItems().getValue();
        itemsListViewModel.getSimilarItems(recognizedItems);
    }

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
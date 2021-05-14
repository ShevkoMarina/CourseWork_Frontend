package com.example.coursework2.UI.saved_items_clicked;

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
import android.widget.Toast;

import com.example.coursework2.R;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.UI.saved_items.FurnitureRecyclerView;
import com.example.coursework2.UI.saved_items.ItemsListActivity;
import com.example.coursework2.model.Item;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedItemsActivity extends AppCompatActivity  implements  SavedItemsAdapter.OnSavedItemsListener {

    // UI
    private RecyclerView recyclerView;
    private SavedItemsAdapter adapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button recognizeBtn;

    // ViewModel
    private ItemsListViewModel itemsListViewModel;

    // Var
    private List<Item> savedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Bind View Model
        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);
        recyclerView = findViewById(R.id.furniture_recycler_view);
        recognizeBtn = findViewById(R.id.recognitionBtn);

        initObservers();
        initRecyclerView();

        sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        String category = sp.getString("category", "");
        //getUserItems(UUID.fromString(userId));
        itemsListViewModel.getUserItems(UUID.fromString(userId), category);

        recognizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedItemsActivity.this, GetPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        adapter = new SavedItemsAdapter(savedItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }


    // Observing any data change
    private void initObservers() {

        itemsListViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                // Observing for any data change
                if (items != null) {
                    // Get the data
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

    // Calling method in Activity
    private void getUserItems(UUID id) {
        //itemsListViewModel.getUserItems(id);
    }
}
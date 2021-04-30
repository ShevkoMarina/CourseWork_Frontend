package com.example.coursework2.UI.saved_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.model.Item;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class ItemsListActivity extends AppCompatActivity implements OnFurnitureListener{

    Button recognizeBtn;
    // Recycler view
    private RecyclerView recyclerView;
    private FurnitureRecyclerView adapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // ViewModel
    private ItemsListViewModel itemsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);
        recyclerView = findViewById(R.id.furniture_recycler_view);
        recognizeBtn = findViewById(R.id.recognitionBtn);

        // Calling the observers
        ObserveAnyChange();
        ConfigureRecyclerView();

        sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        getUserItems(UUID.fromString(userId));

        recognizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsListActivity.this, GetPhotoActivity.class);
                startActivity(intent);
            }
        });
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
    private void getUserItems(UUID id) {
        itemsListViewModel.getUserItems(id);
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
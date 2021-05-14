package com.example.coursework2.UI.category_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursework2.R;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.UI.saved_items_clicked.SavedItemsActivity;
import com.example.coursework2.UI.saved_items_clicked.SavedItemsAdapter;
import com.example.coursework2.model.CategoryItem;
import com.example.coursework2.model.Item;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity implements CategoryItemsAdapter.OnCategoryListener {

    private RecyclerView recyclerView;
    private CategoryItemsAdapter adapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button addBtn;

    // ViewModel
    private ItemsListViewModel itemsListViewModel;

    private List<CategoryItem> categoryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        // Bind View Model
        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);

        recyclerView = findViewById(R.id.category_rv);
        addBtn = findViewById(R.id.category_add_btn);

        itemsListViewModel.getCategoryItems().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(List<CategoryItem> items) {
                if (items != null) {
                    categoryItems = items;
                    adapter.setCategoryItems(items);
                }
            }
        });

        adapter = new CategoryItemsAdapter(categoryItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryItemsActivity.this, GetPhotoActivity.class);
                startActivity(intent);
            }
        });
        itemsListViewModel.getCategories();
    }

    @Override
    public void onCategoryItemClick(int position) {
        // записать в shared prefs категорию. и взять ее на новой странице

        sp = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("category", categoryItems.get(position).getCategory());
        editor.apply();

        Intent intent = new Intent(CategoryItemsActivity.this, SavedItemsActivity.class);
        startActivity(intent);
    }

    
}
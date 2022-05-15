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
import android.widget.TextView;

import com.example.coursework2.R;
import com.example.coursework2.UI.SettingsActivity;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.UI.saved_items.SavedItemsActivity;
import com.example.coursework2.model.CategoryItem;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity implements CategoryItemsAdapter.OnCategoryListener {

    private CategoryItemsAdapter adapter;
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        ItemsListViewModel itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.category_rv);
        Button addBtn = findViewById(R.id.category_add_btn);
        TextView nothingCat = findViewById(R.id.nothing_cat_tv);
        Button settingsBtn = findViewById(R.id.setting_btn);

        itemsListViewModel.getCategoryItems().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(List<CategoryItem> items) {
                if (items != null) {
                    if (items.size() != 0) {
                        nothingCat.setVisibility(View.INVISIBLE);
                        categoryItems = items;
                        adapter.setCategoryItems(items);
                    }
                    else {
                        nothingCat.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    nothingCat.setVisibility(View.VISIBLE);
                }
            }
        });

        adapter = new CategoryItemsAdapter(categoryItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CategoryItemsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

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
        SharedPreferences sp = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("category", categoryItems.get(position).getCategory());
        editor.apply();

        Intent intent = new Intent(CategoryItemsActivity.this, SavedItemsActivity.class);
        startActivity(intent);
    }
}
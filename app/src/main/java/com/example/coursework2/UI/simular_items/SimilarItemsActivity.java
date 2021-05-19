package com.example.coursework2.UI.simular_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.coursework2.R;
import com.example.coursework2.UI.category_items.CategoryItemsActivity;
import com.example.coursework2.UI.saved_items.SavedItemsActivity;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.viewmodel.ItemsListViewModel;
import com.example.coursework2.viewmodel.ItemsListViewModelFactory;
import com.example.coursework2.viewmodel.RecognizedItemsViewModel;
import com.example.coursework2.viewmodel.RecognizedItemsViewModelFactory;

import java.util.List;

public class SimilarItemsActivity extends AppCompatActivity implements OnSimilarItemListener {

    private RecyclerView recyclerView;
    private SimilarAdapter adapter;
    private ItemsListViewModel itemsListViewModel;
    private List<Item> checkedItems;
    private ProgressBar progressBar;
    private TextView nothingFoundTV;
    private List<Item> similarItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simular_items);
        itemsListViewModel = new ViewModelProvider(this, new ItemsListViewModelFactory(this)).get(ItemsListViewModel.class);
        RecognizedItemsViewModel recognizedItemsViewModel = new ViewModelProvider(this, new RecognizedItemsViewModelFactory(this)).get(RecognizedItemsViewModel.class);
        recyclerView = findViewById(R.id.similar_recycler);
        Button addBtn = findViewById(R.id.similar_save_btn);
        addBtn.setEnabled(false);
        progressBar = findViewById(R.id.similar_progress_bar);
        nothingFoundTV = findViewById(R.id.nothing_found_tv);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItems = adapter.getCheckedItems();
                if (checkedItems != null) {
                    itemsListViewModel.saveCheckedItems(checkedItems);
                    Intent i =new Intent(SimilarItemsActivity.this, CategoryItemsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        });

        itemsListViewModel.getSimilarItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                progressBar.setVisibility(View.INVISIBLE);
                if (items != null) {
                    if (items.size() == 0) {
                        nothingFoundTV.setVisibility(View.VISIBLE);
                    } else {
                        similarItems = items;
                        addBtn.setEnabled(true);
                        adapter.setmSimilarItems(items);
                        nothingFoundTV.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        ConfigureRecyclerView();

        List<RecognizedItem> recognizedItems = recognizedItemsViewModel.getItems().getValue();
        if (recognizedItems != null) {
            progressBar.setVisibility(View.VISIBLE);
            itemsListViewModel.getSimilarItems(recognizedItems);
        }
    }

    private void ConfigureRecyclerView() {
        adapter = new SimilarAdapter(similarItems, this, SimilarItemsActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onSimilarItemClick(View v, int position) {
        similarItems.get(position).openUrl(SimilarItemsActivity.this);
    }

    @Override
    public void onWebClick(int position) {

    }
}
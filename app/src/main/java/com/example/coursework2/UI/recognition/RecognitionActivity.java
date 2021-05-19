package com.example.coursework2.UI.recognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import android.widget.Toast;

import com.example.coursework2.R;
import com.example.coursework2.UI.simular_items.SimilarItemsActivity;
import com.example.coursework2.databinding.ActivityRecognitzingBinding;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.viewmodel.RecognizedItemsViewModel;
import com.example.coursework2.viewmodel.RecognizedItemsViewModelFactory;

import java.util.List;

public class RecognitionActivity extends AppCompatActivity  implements OnRecognitionListener{

    private RecognizedItemsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognitzing);
        RecognizedItemsViewModel recognizedItemsViewModel = new ViewModelProvider(this, new RecognizedItemsViewModelFactory(this)).get(RecognizedItemsViewModel.class);
        recyclerView = findViewById(R.id.recognizedItems);
        progressBar = findViewById(R.id.rec_progress_bar);
        TextView nothingRecognizedTV = findViewById(R.id.nothing_rec_tv);
        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setEnabled(false);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimilarItemsPage();
            }
        });

        // Observers
        recognizedItemsViewModel.getItems().observe(this, new Observer<List<RecognizedItem>>() {
            @Override
            public void onChanged(List<RecognizedItem> recognizedItems) {
                if (recognizedItems != null) {
                    continueBtn.setText("Продолжить");
                    progressBar.setVisibility(View.INVISIBLE);
                    if (recognizedItems.size() != 0) {
                        nothingRecognizedTV.setVisibility(View.INVISIBLE);
                        adapter.setRecognizedItems(recognizedItems);
                        continueBtn.setEnabled(true);
                    }
                    else {
                        nothingRecognizedTV.setVisibility(View.VISIBLE);
                        continueBtn.setText("Назад");
                    }
                } else {
                    nothingRecognizedTV.setVisibility(View.VISIBLE);
                    continueBtn.setText("Назад");
                }
            }
        });
        ConfigureRecyclerView();
        progressBar.setVisibility(View.VISIBLE);
        recognizedItemsViewModel.getRecognizedItems();
    }

    private void ConfigureRecyclerView() {
        adapter = new RecognizedItemsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public void openSimilarItemsPage() {
        if (continueBtn.getText() == "Назад") {
            finish();
            // Или поставиь переход на новую страницу для загрузки
        }
        else {
            Intent intent = new Intent(RecognitionActivity.this, SimilarItemsActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    public void onRecognizedItemClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
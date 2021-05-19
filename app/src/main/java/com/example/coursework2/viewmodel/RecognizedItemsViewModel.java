package com.example.coursework2.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.UI.recognition.RecognitionActivity;
import com.example.coursework2.UI.simular_items.SimilarItemsActivity;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.repository.RecognitionRepository;
import com.example.coursework2.utils.ConnectionDetector;

import java.util.List;

public class RecognizedItemsViewModel extends ViewModel {

    private SharedPreferences sp;
    private RecognitionRepository recognitionRepository;
    private Activity activity;

    public LiveData<List<RecognizedItem>> getItems() {
        return recognitionRepository.getCroppedItems();
    }
    
    public RecognizedItemsViewModel(Activity activity) {
        recognitionRepository = RecognitionRepository.getInstance();
        this.activity = activity;
    }

    public void getRecognizedItems() {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String photoUrl = sp.getString("bloburl", "");
        String token = sp.getString("token", "");

        if (new ConnectionDetector(activity).isConnected()) {
            recognitionRepository.getCroppedImages(photoUrl, token);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }
}

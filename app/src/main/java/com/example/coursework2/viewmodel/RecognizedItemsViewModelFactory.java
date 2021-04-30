package com.example.coursework2.viewmodel;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecognizedItemsViewModelFactory implements ViewModelProvider.Factory{
    private Activity activity;

    public RecognizedItemsViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T)new RecognizedItemsViewModel(activity);
    }
}

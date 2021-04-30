package com.example.coursework2.viewmodel;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ItemsListViewModelFactory implements ViewModelProvider.Factory{
    private Activity activity;

    public ItemsListViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T)new ItemsListViewModel(activity);
    }
}

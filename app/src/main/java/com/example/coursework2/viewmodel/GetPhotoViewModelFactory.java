package com.example.coursework2.viewmodel;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class GetPhotoViewModelFactory  implements ViewModelProvider.Factory {
    private Activity activity;

    public GetPhotoViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T)new GetPhotoViewModel(activity);
    }
}

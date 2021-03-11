package com.example.coursework2.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursework2.UI.authorization.AuthorizationActivity;
import com.example.coursework2.model.User;

public class AuthorizationViewModelFactory implements ViewModelProvider.Factory {

    private User user;
    private Context context;

    public AuthorizationViewModelFactory(AuthorizationActivity authorizationActivity, User user) {
        this.context = authorizationActivity;
        this.user = user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new AuthorizationViewModel(context, user);
    }
}

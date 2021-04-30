package com.example.coursework2.UI.authorization;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursework2.UI.saved_items.ItemsListActivity;
import com.example.coursework2.model.User;
import com.example.coursework2.R;
import com.example.coursework2.databinding.ActivityAuthorizationBinding;
import com.example.coursework2.viewmodel.AuthorizationViewModel;
import com.example.coursework2.viewmodel.AuthorizationViewModelFactory;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActivityAuthorizationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorization);
        AuthorizationViewModel authorizationViewModel = ViewModelProviders.of(this, new AuthorizationViewModelFactory(new User(), this)).get(AuthorizationViewModel.class);
        binding.setAuthModel(authorizationViewModel);
        binding.setLifecycleOwner(this);

        // подписывыаем активити на изменение
        authorizationViewModel.getAuthPassed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    openUsersSavedItemsPage();
                }
            }
        });
    }

    private void openUsersSavedItemsPage() {
        Intent intent = new Intent(AuthorizationActivity.this, ItemsListActivity.class);
        startActivity(intent);
    }
}
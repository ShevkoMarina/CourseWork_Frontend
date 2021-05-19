package com.example.coursework2.UI.authorization;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursework2.UI.category_items.CategoryItemsActivity;
import com.example.coursework2.model.User;
import com.example.coursework2.R;
import com.example.coursework2.viewmodel.AuthorizationViewModel;
import com.example.coursework2.viewmodel.AuthorizationViewModelFactory;

public class AuthorizationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText loginTV;
    private EditText passwordTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        AuthorizationViewModel authorizationViewModel = ViewModelProviders.of(this, new AuthorizationViewModelFactory(new User(null, null), this)).get(AuthorizationViewModel.class);
        Button loginBtn = findViewById(R.id.login_btn);
        Button signBtn = findViewById(R.id.sign_btn);
        passwordTV = findViewById(R.id.password);
        loginTV = findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorizationViewModel.authorizeUserClicked(new User(loginTV.getText().toString(), passwordTV.getText().toString()));
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorizationViewModel.registerUserClicked(new User(loginTV.getText().toString(), passwordTV.getText().toString()));
            }
        });

        authorizationViewModel.getAuthPassed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    openUsersSavedItemsPage();
                } else {
                    Toast.makeText(AuthorizationActivity.this, "Неверный логин или пароль!", Toast.LENGTH_LONG).show();
                }
            }
        });

        authorizationViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String token) {
                if (token != null) {
                    sharedPreferences =  getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                }
            }
        });

        authorizationViewModel.getUserId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String userId) {
                if (userId != null) {
                    sharedPreferences =  getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("userid", userId);
                    editor.apply();
                }
            }
        });

        authorizationViewModel.getRegPassed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean passed) {
                if (passed) {
                    Toast.makeText(AuthorizationActivity.this, "Вы успешно зарегестрированы!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AuthorizationActivity.this, "Неверный логин или пароль!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void openUsersSavedItemsPage() {
        Intent intent = new Intent(AuthorizationActivity.this, CategoryItemsActivity.class);
        startActivity(intent);
        finish();
    }
}
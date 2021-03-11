package com.example.coursework2.UI.authorization;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursework2.UI.Items.ItemsListActivity;
import com.example.coursework2.UI.saveditems.MainActivity;
import com.example.coursework2.model.User;
import com.example.coursework2.R;
import com.example.coursework2.databinding.ActivityAuthorizationBinding;
import com.example.coursework2.viewmodel.AuthorizationViewModel;
import com.example.coursework2.viewmodel.AuthorizationViewModelFactory;

public class AuthorizationActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    private Button mSignUpButton;
    private Button mLogInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActivityAuthorizationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorization);
        AuthorizationViewModel authorizationViewModel = ViewModelProviders.of(this, new AuthorizationViewModelFactory(this, new User())).get(AuthorizationViewModel.class);
        binding.setAuthModel(authorizationViewModel);

        /*
        // подписывыаем активити на изменение логина во втюмодели и оно отображается
        authorizationViewModel.login.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mLogin.setText(s);
            }
        });

         */

        // можно заменить на биндинг вроде или здесь оставить
        Button btn = (Button) findViewById(R.id.sendphoto_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationActivity.this, ItemsListActivity.class);
                startActivity(intent);
            }
        });
    }
}
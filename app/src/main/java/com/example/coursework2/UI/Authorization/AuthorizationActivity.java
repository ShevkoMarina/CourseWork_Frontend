package com.example.coursework2.UI.Authorization;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursework2.model.User;
import com.example.coursework2.R;
import com.example.coursework2.databinding.ActivityAuthorizationBinding;
import com.example.coursework2.viewmodel.AuthorizationViewModel;
import com.example.coursework2.viewmodel.AuthorizationViewModelFactory;

public class AuthorizationActivity extends AppCompatActivity {

    private AuthorizationViewModel authorizationViewModel;

    private EditText mLogin;
    private EditText mPassword;
    private Button mSignUpButton;
    private Button mLogInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActivityAuthorizationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorization);
        authorizationViewModel = ViewModelProviders.of(this, new AuthorizationViewModelFactory(this, new User())).get(AuthorizationViewModel.class);
        binding.setAuthModel(authorizationViewModel);
    }

    /*
    @Override
    protected Fragment getFragment() {
        return AuthorizationFragment.newInstance() ;
    }

     */
}
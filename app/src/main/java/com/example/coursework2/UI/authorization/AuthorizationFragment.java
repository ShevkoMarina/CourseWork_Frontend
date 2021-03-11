package com.example.coursework2.UI.authorization;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursework2.R;

public class AuthorizationFragment extends Fragment {


    private EditText mLogin;
    private EditText mPassword;
    private Button mSignUpButton;
    private Button mLogInButton;

    // todo: отом убрать отсбда
    private static final String SERVER_URL = "https://courseworkapi.azurewebsites.net/";

    public static AuthorizationFragment newInstance() {
        Bundle args = new Bundle();
        AuthorizationFragment fragment = new AuthorizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // todo: сделать биндинги
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_authorization, container, false);

        mLogin = view.findViewById(R.id.login);
        mPassword = view.findViewById(R.id.password);
        mSignUpButton = view.findViewById(R.id.sign_btn);
        mLogInButton = view.findViewById(R.id.login_btn);

        return view;
    }
}
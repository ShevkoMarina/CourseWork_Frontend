package com.example.coursework2.viewmodel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.model.User;
import com.example.coursework2.repository.UsersRepository;
import com.example.coursework2.utils.ConnectionDetector;

public class AuthorizationViewModel extends ViewModel {

    private ConnectionDetector connectionDetector;

    private UsersRepository usersRepository;

    public LiveData<Boolean> getAuthPassed() {
        return usersRepository.getAuthPassed();
    }

    public LiveData<String> getToken() {
        return usersRepository.getToken();
    }

    public LiveData<String> getUserId() {
        return usersRepository.getUserId();
    }

    public LiveData<Boolean> getRegPassed() {
        return usersRepository.getRegistrationPassed();
    }

    private Activity activity;


    public AuthorizationViewModel(User user, Activity activity) {
        connectionDetector = new ConnectionDetector(activity);
        this.activity = activity;
        this.usersRepository = UsersRepository.getInstance();
    }

    public void authorizeUserClicked(User user) {
        if (connectionDetector.isConnected()) {
            usersRepository.authorizeUser(user);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }

    public void registerUserClicked(User user) {
        if (connectionDetector.isConnected()) {
            usersRepository.registerUser(user);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }
}
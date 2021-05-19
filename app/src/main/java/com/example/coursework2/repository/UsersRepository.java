package com.example.coursework2.repository;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.coursework2.model.AuthUserResponse;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.User;
import com.example.coursework2.remote.APIService;
import com.example.coursework2.remote.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {

    private MutableLiveData<String> login = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Boolean> authPassed = new MutableLiveData<Boolean>();
    private MutableLiveData<String> token = new MutableLiveData<>();
    private MutableLiveData<String> userId = new MutableLiveData<>();
    private MutableLiveData<Boolean> registrationPassed = new MutableLiveData<>();

    private static UsersRepository instance;

    public static UsersRepository getInstance() {
        if (instance == null) {
            instance = new UsersRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getLogin() {
        return login;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<Boolean> getAuthPassed() {
        return authPassed;
    }

    public MutableLiveData<String> getToken() { return token; }

    public MutableLiveData<String> getUserId() { return userId; }

    public MutableLiveData<Boolean> getRegistrationPassed() {
        return registrationPassed;
    }

    public void authorizeUser(User user) {
        Call<AuthUserResponse> call = NetworkService.getAPIService().getToken(user);
        call.enqueue(new Callback<AuthUserResponse>() {
            @Override
            public void onResponse(Call<AuthUserResponse> call, Response<AuthUserResponse> response) {
                if (response.isSuccessful()) {
                    authPassed.postValue(true);
                    token.postValue(response.body().getAccessToken());
                    userId.postValue(response.body().getUserId());
                } else {
                    authPassed.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AuthUserResponse> call, Throwable t) {
                login.postValue("");
                password.postValue("");
                authPassed.postValue(false);
            }
        });
    }

    public void registerUser(User user) {
        Call<String> call = NetworkService.getAPIService().registerUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                login.postValue(null);
                password.postValue(null);
                registrationPassed.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                login.postValue("");
                password.postValue("");
                registrationPassed.postValue(false);
            }
        });
    }
}

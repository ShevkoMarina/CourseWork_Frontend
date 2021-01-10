package com.example.coursework2.viewmodel;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.model.User;
import com.example.coursework2.remote.APIService;
import com.example.coursework2.remote.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationViewModel extends ViewModel {

    public MutableLiveData<String> login = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public User user;
    // потом убрать
    private Context context;

    public AuthorizationViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void authorizeUserClicked() {

        user.setLogin(login.getValue());
        user.setPassword(password.getValue());

        APIService apiService = NetworkService.getAPIService();
        Call<String> call = apiService.authorizeUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 400 && response.body().equals("Wrong password")) {
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, "Sucessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerUserClicked() {
        user.setLogin(login.getValue());
        user.setPassword(password.getValue());

        APIService apiService = NetworkService.getAPIService();
        Call<String> call = apiService.registerUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
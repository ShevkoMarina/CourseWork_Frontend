package com.example.coursework2.viewmodel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.coursework2.model.User;
import com.example.coursework2.remote.APIError;
import com.example.coursework2.remote.APIService;
import com.example.coursework2.remote.ErrorUtils;
import com.example.coursework2.remote.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationViewModel extends ViewModel {

    // сделать репозиторий
    // если понадобится context - спользуй androidviewmodel
    // вроде их незачем делать ливдатой - мы подписываемся на нее и когда она изменяется об этом уведоляется вью
    // - делать приватными и добавитб геттеры. там может бытьметод
    private MutableLiveData<String> login = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Boolean> authPassed = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> getAuthPassed() {
        return authPassed;
    }

    public MutableLiveData<String> getLogin() {
        return login;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public User user;
    // потом убрать
    @SuppressLint("StaticFieldLeak")
    private Activity activity;


    public AuthorizationViewModel(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
    }

    // убрать из логина возможность сделать вниз нессколько строк
    public void authorizeUserClicked() {

        user.setLogin(login.getValue());
        user.setPassword(password.getValue());

        APIService apiService = NetworkService.getAPIService();
        Call<String> call = apiService.authorizeUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(context, "Sucessful", Toast.LENGTH_LONG).show();
                    authPassed.postValue(true);
                    sharedPreferences = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    String userId = response.body();
                    editor.putString("userid", userId);
                    editor.apply();
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                   // Toast.makeText(context, error.getTitle(), Toast.LENGTH_LONG).show();
                    login.postValue("");
                    password.postValue("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               //Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    // нельзя регистрировать с таким же логином
    // в первый раз делает ошибку
    public void registerUserClicked() {
        user.setLogin(login.getValue());
        user.setPassword(password.getValue());

        APIService apiService = NetworkService.getAPIService();
        Call<String> call = apiService.registerUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               // Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              //  Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


}
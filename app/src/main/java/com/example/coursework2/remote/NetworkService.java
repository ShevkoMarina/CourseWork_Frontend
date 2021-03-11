package com.example.coursework2.remote;

import android.text.format.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://coursebackapiserver.azurewebsites.net/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    static Retrofit getRetroClient() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit =  new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build();
        }
        return retrofit;
    }

    public static APIService getAPIService() {
        return getRetroClient().create(APIService.class);
    }
}

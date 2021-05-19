package com.example.coursework2.remote;

import android.text.format.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://coursebackapi.azurewebsites.net/";
    private static Retrofit retrofit;
    private static Retrofit retrofitToken;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .build();


    static Retrofit getRetroClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit =  new Retrofit.Builder()
                 .baseUrl(BASE_URL).client(okHttpClient)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build();
        }
        return retrofit;
    }

    static Retrofit getRetrofitWithToken(String token) {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();

        if (retrofitToken == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofitToken =  new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitToken;
    }

    public static APIService getAPIService() {
        return getRetroClient().create(APIService.class);
    }

    public static APIService getAPIWithTokenService(String token) {
        return getRetrofitWithToken(token).create(APIService.class);
    }
}

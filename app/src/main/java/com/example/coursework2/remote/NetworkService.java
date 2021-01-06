package com.example.coursework2.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://courseworkapi.azurewebsites.net/";
    private static Retrofit retrofit;

    private static Retrofit getRetroClient() {

        if (retrofit == null) {
       //  Gson gson = new GsonBuilder().setLenient().create();

            retrofit =  new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
        }
        return retrofit;
    }

    public static APIService getAPIService() {
        return getRetroClient().create(APIService.class);
    }
}

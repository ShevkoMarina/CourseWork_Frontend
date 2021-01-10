package com.example.coursework2.remote;

import com.example.coursework2.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers("Content-type: application/json")
    @POST("/users/AuthorizeUser")
    Call<String> authorizeUser(@Body User user);

    @Headers("Content-type: application/json")
    @POST("/users/AddUser")
    Call<String> registerUser(@Body User user);
}

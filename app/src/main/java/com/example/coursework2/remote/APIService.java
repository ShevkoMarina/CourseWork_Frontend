package com.example.coursework2.remote;

import com.example.coursework2.model.AuthUserResponse;
import com.example.coursework2.model.CategoryItem;
import com.example.coursework2.model.FindSimilarRequest;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizeImage;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.model.SaveItemsRequest;
import com.example.coursework2.model.User;

import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @Headers("Content-type: application/json")
    @POST("/users/AuthorizeUser")
    Call<String> authorizeUser(@Body User user);

    @Headers("Content-type: application/json")
    @POST("/users/AddUser")
    Call<String> registerUser(@Body User user);

    @Headers("Content-type: application/json")
    @POST("/SavedItems/FindByUrl")
    Call<String> findByUrl(@Body RecognizeImage image);

    @Multipart
    @POST("/SavedItems/UploadToBlob")
    Call<String> uploadToBlob(@Part MultipartBody.Part file,
                           @Part("id") RequestBody id);

    @Headers("Content-type: application/json")
    @GET("/SavedItems/{id}")
    Call<List<Item>> getUserItems(@Path("id") UUID id);

    @Headers("Content-type: application/json")
    @GET("/SavedItems/MakePrediction")
    Call<List<RecognizedItem>> makePrediction(@Query("url") String photoUrl);

    @Headers("Content-type: application/json")
    @POST("/SavedItems/FindSimilarByUrl")
    Call<List<Item>> findSimilar(@Body FindSimilarRequest request);

    @Headers("Content-type: application/json")
    @POST("/SavedItems/SaveItems")
    Call<Void> saveItems(@Body List<Item> request);

    @Headers("Content-type: application/json")
    @GET("/SavedItems/GetUserCategories")
    Call<List<CategoryItem>> getCategories(@Query("guid") UUID userId);

    @Headers("Content-type: application/json")
    @GET("/SavedItems/GetUserItemsByCategory")
    Call<List<Item>> getUserItemsByCategory(@Query("userId") UUID userId, @Query("category") String category);

    @Headers("Content-type: application/json")
    @POST("/Security")
    Call<AuthUserResponse> getToken(@Body User user);
}

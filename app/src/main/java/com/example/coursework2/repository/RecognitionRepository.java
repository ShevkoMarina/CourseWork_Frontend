package com.example.coursework2.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.remote.APIError;
import com.example.coursework2.remote.APIService;
import com.example.coursework2.remote.ErrorUtils;
import com.example.coursework2.remote.NetworkService;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecognitionRepository {

    // Singleton pattern
    private static RecognitionRepository instance;
    private MutableLiveData<List<RecognizedItem>> croppedItems = new MutableLiveData<>();
    private MutableLiveData<String> blobUrl = new MutableLiveData<>();

    public MutableLiveData<String> getBlobUrl() {
        return blobUrl;
    }

    public static RecognitionRepository getInstance() {
        if (instance == null) {
            instance = new RecognitionRepository();
        }
        return instance;
    }

    public LiveData<List<RecognizedItem>> getCroppedItems() {
        return croppedItems;
    }

    public void getCroppedImages(String photoUrl, String token) {
        Call<List<RecognizedItem>> call = NetworkService.getAPIWithTokenService(token).makePrediction(photoUrl);
        call.enqueue(new Callback<List<RecognizedItem>>() {
            @Override
            public void onResponse(Call<List<RecognizedItem>> call, Response<List<RecognizedItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        croppedItems.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RecognizedItem>> call, Throwable t) {
                croppedItems.postValue(null);
            }
        });

    }

    public void uploadPhotoToBlob(File file, String userId, String token) {
        APIService apiService = NetworkService.getAPIWithTokenService(token);
        RequestBody requestUserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("file", file.getName(), requestFile );
        Call<String> call = apiService.uploadToBlob(requestImage, requestUserId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    blobUrl.postValue(response.body());
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}

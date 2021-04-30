package com.example.coursework2.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coursework2.model.FindSimilarRequest;
import com.example.coursework2.model.Item;
import com.example.coursework2.model.RecognizedImageRequest;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.remote.NetworkService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {

    // Singleton pattern
    private static ItemRepository instance;
    private MutableLiveData<List<Item>> itemsResponse = new MutableLiveData<>();
    private MutableLiveData<List<Item>> similarItems = new MutableLiveData<>();

    public static ItemRepository getInstance() {
        if (instance == null) {
            instance = new ItemRepository();
        }
        return instance;
    }

    public LiveData<List<Item>> getSimilarItems() {
        return similarItems;
    }

    public LiveData<List<Item>> getItemsResponse() {
        return itemsResponse;
    }

    // Get saved items of user
    public  void getUserItems(UUID userId) {

        Call<List<Item>> call = NetworkService.getAPIService().getUserItems(userId);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) { // подумать
                    itemsResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }

    // Get similar items
    public void getSimilarItems(Collection<RecognizedImageRequest> request) {

        Call<List<Item>> call = NetworkService.getAPIService().findSimilar(new FindSimilarRequest(request));
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    similarItems.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}


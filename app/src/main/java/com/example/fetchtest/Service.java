package com.example.fetchtest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface Service {
    @GET("/hiring.json")
    Call<List<Items>> getItems();
}

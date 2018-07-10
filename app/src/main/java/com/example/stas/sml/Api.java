package com.example.stas.sml;

import com.example.stas.sml.Model.PlaceResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/venues/search")
    Call<List<PlaceResponce>> search(@Query("name") String resourceName);

}

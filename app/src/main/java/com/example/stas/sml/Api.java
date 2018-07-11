package com.example.stas.sml;

import com.example.stas.sml.Model.PlaceResponce;
import com.example.stas.sml.VenueDetailedModel.VenueDetailed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/venues/search")
    Call<PlaceResponce> search(@Query("ll") String ll,
                               @Query("llAcc") double llAcc);


    @GET("v2/venues/VENUE_ID")
    Call<VenueDetailed> getVenue(
            @Query("VENUE_ID") String id);
}





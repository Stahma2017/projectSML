package com.example.stas.sml;

import com.example.stas.sml.model.PlaceResponce;
import com.example.stas.sml.model.VenueDetailsResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/venues/search")
    Observable<PlaceResponce> search(@Query("ll") String ll,
                                 @Query("llAcc") double llAcc);


    @GET("v2/venues/{VENUE_ID}")
    Observable<VenueDetailsResponse> getVenue(@Path("VENUE_ID") String id);
}





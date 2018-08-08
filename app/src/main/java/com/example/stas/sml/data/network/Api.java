package com.example.stas.sml.data.network;

import com.example.stas.sml.data.model.venuesearch.PlaceResponce;
import com.example.stas.sml.data.model.venuedetailedmodel.VenueDetailsResponse;
import com.example.stas.sml.data.model.venuesearch.SearchResponse;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.data.model.venuesuggestion.Response;
import com.example.stas.sml.data.model.venuesuggestion.SuggestionResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/venues/search")
    Observable<PlaceResponce> search(@Query("ll") String ll,
                                 @Query("llAcc") double llAcc);


    @GET("v2/venues/{VENUE_ID}")
    Observable<VenueDetailsResponse> getVenue(@Path("VENUE_ID") String id);


    @GET("/v2/venues/search")
    Observable<SearchResponse> searchNew(@Query("ll") String ll,
                                         @Query("llAcc") double llAcc, @Query("categoryId") String categoryId, @Query("limit") Integer limit);

    @GET("https://api.foursquare.com/v2/venues/suggestcompletion")
    Observable<SuggestionResponse> searchSuggestions(@Query("ll") String ll, @Query("query") String query);
}





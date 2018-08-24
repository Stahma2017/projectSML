package com.example.stas.sml.domain.interactor;

import android.location.Location;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.main.ActivityContract;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import java.util.List;
import io.reactivex.Observable;

public class MapsModel implements ActivityContract.Model {
    private final Api serverApi;
    private final VenueMapper mapper;

    public MapsModel(Api serverApi, VenueMapper venueMapper) {
        this.serverApi = serverApi;
        this.mapper = venueMapper;
    }

    @Override
    public Observable<Boolean> observeConnectionStates() {
        return ReactiveNetwork.observeInternetConnectivity();
    }

    @Override
    public Observable<VenueEntity> loadVenuesWithCategory(Location location, String categoryId) {
        String ll = location.getLatitude() + ", " + location.getLongitude();
        return serverApi.searchWithCategory(ll, 1000.0, categoryId, 1)
                .map(searchResponce -> searchResponce.getResponse().getVenues())
                .flatMapIterable(items -> items)
                .flatMap(venuesearch -> serverApi.getVenue(venuesearch.getId())
                        .map(venueDetailsResponse -> {
                          venueDetailsResponse.getVenueDto().getVenue().setDistance(venuesearch.getLocation().getDistance());
                           return mapper.map(venueDetailsResponse.getVenueDto().getVenue());
                        }));
    }

    @Override
    public Observable<List<Minivenue>> loadTextSuggestions(String querry){
        return serverApi.searchSuggestions("45.045583, 38.978452", querry)
              .map(suggestionResponse -> suggestionResponse.getResponse().getMinivenues());
    }

    @Override
    public Observable<VenueEntity> loadVenuesByQuerySubmition(Location location, String query){
        String ll = location.getLatitude() + ", " + location.getLongitude();
        return serverApi.searchWithQueryParam(ll, 1000.0, query, 2)
                .map(searchResponce -> searchResponce.getResponse().getVenues())
                .flatMapIterable(items -> items)
                .flatMap(venuesearch -> serverApi.getVenue(venuesearch.getId())
                        .map(venueDetailsResponse -> {
                            venueDetailsResponse.getVenueDto().getVenue().setDistance(venuesearch.getLocation().getDistance());
                            return mapper.map(venueDetailsResponse.getVenueDto().getVenue());
                        }));
    }

    @Override
    public Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> loadDetailedVenues(String venueId){
        return serverApi.getVenue(venueId)
                .map(venueDetailsResponse -> mapper.map(venueDetailsResponse.getVenueDto().getVenue()));

    }
}


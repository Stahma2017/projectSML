package com.example.stas.sml.domain.interactor;

import android.location.Location;

import com.example.stas.sml.data.model.venuedetailedmodel.VenueDetailsResponse;

import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.data.model.venuesearch.SearchResponse;

import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.presentation.feature.map.MapsContract;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class MapsModel implements MapsContract.Model {
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
    public Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> search(Location location, String categoryId) {

        String ll = location.getLatitude() + ", " + location.getLongitude();

        return serverApi.searchNew(ll, 1000.0, categoryId, 1)
                .map(searchResponce -> searchResponce.getResponse().getVenues())
                .flatMapIterable(items -> items)
                .flatMap(venuesearch -> serverApi.getVenue(venuesearch.getId())
                        .map(venueDetailsResponse -> {
                          venueDetailsResponse.getVenueDto().getVenue().setDistance(venuesearch.getLocation().getDistance());
                           return mapper.map(venueDetailsResponse.getVenueDto().getVenue());
                        }));
    }
}


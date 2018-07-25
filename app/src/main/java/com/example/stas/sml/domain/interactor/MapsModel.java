package com.example.stas.sml.domain.interactor;


import com.example.stas.sml.RetrofitClient;
import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.presentation.feature.map.MapsContract;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import io.reactivex.Observable;


public class MapsModel implements MapsContract.Model {
   /* private final Api serverApi;
    private final VenueMapper mapper;*/

    private Api serverApi = RetrofitClient.getInstance().getApi();
    private VenueMapper mapper = new VenueMapper();

    /*public MapsModel(Api serverApi, VenueMapper venueMapper){
        this.serverApi = serverApi;
        this.mapper = venueMapper;
    }*/

    // TODO: 16.07.2018 Single, Flowable, Completable, Observable, Maybe
    @Override
    public Observable<VenueEntity> loadPhotos(String latLng){
       return serverApi.search(latLng, 1000.0)
               .map(placeResponce -> placeResponce.getResponse().getVenues().get(0).getId())
               .flatMap(serverApi::getVenue)
               .map(venueDetailsResponse -> mapper.map(venueDetailsResponse.getVenueDto().getVenue()));
    }
    @Override
    public Observable<Boolean> observeConnectionStates() {
      return ReactiveNetwork.observeInternetConnectivity();
    }
}

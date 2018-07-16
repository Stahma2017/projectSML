package com.example.stas.sml;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import io.reactivex.Observable;
import io.reactivex.Single;


public class MapsModel implements MapsContract.Model{

    private Api serverApi = RetrofitClient.getInstance().getApi();

    // TODO: 16.07.2018 Single, Flowable, Completable, Observable, Maybe

    @Override
    public Single<String> loadVenueId(String latLng) {
        return serverApi.search(latLng, 1000.0)
             .map(placeResponce -> placeResponce.getResponse().getVenues().get(0).getId());
    }

    @Override
    public Observable<Boolean> observeConnectionStates() {
      return ReactiveNetwork.observeInternetConnectivity();
    }
}

package com.example.stas.sml;

import com.example.stas.sml.model.Item;
import com.example.stas.sml.model.PlaceResponce;
import com.example.stas.sml.model.VenueDetailsResponse;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;


public class MapsModel implements MapsContract.Model{

    private Api serverApi = RetrofitClient.getInstance().getApi();

    // TODO: 16.07.2018 Single, Flowable, Completable, Observable, Maybe

    /*@Override
    public Single<String> loadVenueId(String latLng) {
        return serverApi.search(latLng, 1000.0)
             .map(placeResponce -> placeResponce.getResponse().getVenues().get(0).getId());
    }*/
    public Observable<HashMap<String,String>> loadPhotos(String latLng){
       return serverApi.search(latLng, 1000.0)
                .flatMap(new Function<PlaceResponce, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> apply(PlaceResponce placeResponce) throws Exception {
                      String id = placeResponce.getResponse().getVenues().get(0).getId();
                      List<String> urls = new ArrayList<String>();
                        return serverApi.getVenue(id).map(new Function<VenueDetailsResponse, List<String>>() {
                            @Override
                            public List<String> apply(VenueDetailsResponse venueDetailsResponse) throws Exception {
                                for (int i = 0; i<5; i++){
                                    String s = venueDetailsResponse.getVenueDto().getVenue().getPhotos().getGroups().get(0).getItems().get(i).getPrefix()
                                            + "400x400" + venueDetailsResponse.getVenueDto().getVenue().getPhotos().getGroups().get(0).getItems().get(i).getSuffix();
                                    urls.add(s);
                                }
                                return urls;
                            }

                        })
                    }
                });
    }

    @Override
    public Observable<Boolean> observeConnectionStates() {
      return ReactiveNetwork.observeInternetConnectivity();
    }
}

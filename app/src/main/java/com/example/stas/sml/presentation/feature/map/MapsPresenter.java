package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter  {

    private WeakReference<MapsContract.MapsView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;

    public MapsPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
    }
    public void attachView(MapsContract.MapsView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void getVenuesWithCategory(String categoryId){
        Location currentLocation = view.get().getCurrentLocation();

        Disposable venueListDisposable = interactor.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                    view.get().showPlacesByCategory(venue);
                }
//                ,errorHandler::proceed
                );
        compositeDisposable.add(venueListDisposable);
    }

    public void getLocation(){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(Location location) throws Exception {
                       view.get().showLocation(location);
                    }
                });
        compositeDisposable.add(dis);


    }

    public void getTextSuggestions(String querry){

        Disposable bla = interactor.loadTextSuggestions(querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues ->
                        view.get().showSearchSuggestions(minivenues)
                );
        compositeDisposable.add(bla);
    }

}

package com.example.stas.sml.presentation.feature.venueselected;

import android.location.Location;

import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VenueSelectedPresenter {

    private WeakReference<VenueSelectContract.VenueSelectView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;

    public VenueSelectedPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
    }

    public void attachView(VenueSelectContract.VenueSelectView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void getLocationForVenueDetailed(String venueId){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {view.get().deliverLocationForpreveious(location, venueId);}
                        , errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    public void getVenuesWithCategory(String venueId,Location location){

        Disposable venueListDisposable = interactor.loadDetailedVenues(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venue -> {
                    view.get().showVenueSelected(venue, location);
                }, errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);

    }
}

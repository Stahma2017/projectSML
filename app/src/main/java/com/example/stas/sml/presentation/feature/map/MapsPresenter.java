package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.Category;
import com.example.stas.sml.data.model.venuesearch.Venue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    public void getVenuesWithCategory(String categoryId, Location currentLocation){

        List<VenueEntity> venues = new ArrayList<>();
        Disposable venueListDisposable = interactor.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                    venues.add(venue);
                    view.get().showPlacesByCategory(venues);

                }
//                ,errorHandler::proceed
                );
        compositeDisposable.add(venueListDisposable);
    }

    public void getLocation(LatLng latLng){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().showBottomSheet(location, latLng));
        compositeDisposable.add(dis);
    }

    public void getLocationForCategories(String category){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().deliverLocationToCategories(location, category));
        compositeDisposable.add(dis);
    }

    public void getLocationForLocationButton(){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().toCurrentLocation(location));
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

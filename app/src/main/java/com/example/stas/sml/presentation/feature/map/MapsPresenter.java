package com.example.stas.sml.presentation.feature.map;

import android.location.Location;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.google.android.gms.maps.model.LatLng;

import org.reactivestreams.Subscriber;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter  {

    private WeakReference<MapsContract.MapsView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;

    public MapsPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
    }
    public void attachView(MapsContract.MapsView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void detachView() {
        view.clear();
        view = null;
        compositeDisposable.dispose();
    }


    public void getVenuesWithCategory(String categoryId, Location currentLocation){
        view.get().displayProgressbar();
        List<VenueEntity> venues = new ArrayList<>();
        Disposable venueListDisposable = interactor.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venueEntity -> {
                    view.get().hideProgressbar();
                    venues.add(venueEntity);
                    view.get().showPlacesByCategory(venues);
                }, throwable -> {
                    errorHandler.proceed(throwable);
                    view.get().hideProgressbar();
                });
        compositeDisposable.add(venueListDisposable);
    }


    public void getLocation(LatLng latLng){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().showBottomSheet(location, latLng)
                , errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    public void getLocationForCategories(String category){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().deliverLocationToCategories(location, category)
                , errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    public void getLocationForLocationButton(){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().toCurrentLocation(location)
                        ,errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    public void getLocationForSubmit(String submit){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().deliverLocationToSumbit(location, submit)
                        , errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    public void getTextSuggestions(String querry){
        Disposable bla = interactor.loadTextSuggestions(querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues -> view.get().showSearchSuggestions(minivenues),
                        errorHandler::proceed);
        compositeDisposable.add(bla);
    }

    public void getVenuesBySubmit(Location location, String submit){
        List<VenueEntity> venues = new ArrayList<>();
        Disposable bla = interactor.loadVenuesByQuerySubmition(location, submit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venue -> {
                    venues.add(venue);
                    view.get().showPlacesBySubmit(venues);
                }, errorHandler::proceed,
                        () -> view.get().hideProgressbar());
              compositeDisposable.add(bla);
    }


}

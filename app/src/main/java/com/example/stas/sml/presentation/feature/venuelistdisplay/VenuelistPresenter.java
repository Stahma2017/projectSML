package com.example.stas.sml.presentation.feature.venuelistdisplay;

import android.location.Location;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VenuelistPresenter {


    private WeakReference<VenuelistContract.VenuelistView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;

    public VenuelistPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void attachView(VenuelistContract.VenuelistView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void getLocationForCategories(String category){
            Disposable dis = locationGateway.getCurrentLocation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(location ->
                                view.get().deliverLocationForpreveious(location, category)
                            ,errorHandler::proceed);
            compositeDisposable.add(dis);
    }

    public void getVenuesWithCategory(String categoryId, Location currentLocation){
        List<VenueEntity> venues = new ArrayList<>();
        Disposable venueListDisposable = interactor.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venue -> {
                    venues.add(venue);
                    view.get().showPlacesByCategory(venues);
                }, errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);
    }

    public void getTextSuggestions(String query){
        Disposable textSuggestions = interactor.loadTextSuggestions(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues -> view.get().showSearchSuggestions(minivenues),
                        errorHandler::proceed);
        compositeDisposable.add(textSuggestions);
    }

    void getLocationToSubmit(String query){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().deliverLocationToQuerySumbit(location, query)
                        , errorHandler::proceed);
        compositeDisposable.add(dis);
    }

    void getVenuesBuSubmit(Location location,String query){
        List<VenueEntity> venues = new ArrayList<>();
        Disposable bla = interactor.loadVenuesByQuerySubmition(location, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venue -> {
                    venues.add(venue);
                    view.get().showPlacesBySubmit(venues);
                }, errorHandler::proceed);
        compositeDisposable.add(bla);
    }

}
